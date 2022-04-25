package com.example.storyapp.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.components.EmailEditText
import com.example.storyapp.components.LogButton
import com.example.storyapp.components.PasswordEditText
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.network.ApiConfig
import com.example.storyapp.network.LoginResponse
import com.example.storyapp.network.SessionPreference
import com.example.storyapp.network.TokenPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var myButton: LogButton
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var linkRegisterText: TextView
    private lateinit var emailFinal: String
    private lateinit var passwordFinal: String
    private lateinit var mTokenPreference: TokenPreference
    private lateinit var mSessionPreference: SessionPreference
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myButton = findViewById(R.id.login_button)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        linkRegisterText = findViewById(R.id.link_register)
        mTokenPreference = TokenPreference(this@LoginActivity)
        mSessionPreference = SessionPreference(this@LoginActivity)

        if (mSessionPreference.getSession() == "LOGGED") {
            Log.d("Sudah login", "Yes")
            val storyIntent = Intent(this@LoginActivity, StoryActivity::class.java)
            startActivity(storyIntent)

            finish()
        }

        setMyButtonEnable()

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (passwordEditText.text!!.isNotEmpty()) {
                    setMyButtonEnable()
                }
            }

            override fun afterTextChanged(s: Editable) {
                emailFinal = emailEditText.text.toString()
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (emailEditText.text!!.isNotEmpty()) {
                    setMyButtonEnable()
                }
            }

            override fun afterTextChanged(s: Editable) {
                passwordFinal = passwordEditText.text.toString()
            }
        })

        myButton.setOnClickListener {
            postLogin(emailFinal, passwordFinal)
        }

        linkRegisterText.setOnClickListener {
            val regIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(regIntent)
        }

        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logoTitle, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val email = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 2f).setDuration(500)
        val pass = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 2f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(email, pass)
            start()
        }
    }

    private fun setMyButtonEnable() {
        val result = emailEditText.text
        myButton.isEnabled = result != null && result.toString().isNotEmpty()
    }

    private fun postLogin(email: String, password: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e(ContentValues.TAG, "onSuccess: ${response.message()}")
                    Toast.makeText(this@LoginActivity, "Success login ${email}", Toast.LENGTH_SHORT)
                        .show()

                    mTokenPreference.setToken(responseBody.loginResult.token)
                    mSessionPreference.setSession()
                    finish()

                    val storyIntent = Intent(this@LoginActivity, StoryActivity::class.java)
                    startActivity(storyIntent)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Email atau password tidak sesuai",
                        Toast.LENGTH_SHORT
                    ).show()
                    emailEditText.error = response.message()
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")

                    emailEditText.text?.clear()
                    passwordEditText.text?.clear()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT)
                    .show()
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        val pgBar: ProgressBar = findViewById(R.id.progress_bar)

        if (isLoading) {
            pgBar.visibility = View.VISIBLE
        } else {
            pgBar.visibility = View.GONE
        }
    }
}


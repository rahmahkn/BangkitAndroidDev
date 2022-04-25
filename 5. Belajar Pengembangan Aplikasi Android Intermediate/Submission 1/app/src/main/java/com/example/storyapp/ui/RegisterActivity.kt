package com.example.storyapp.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.components.EmailEditText
import com.example.storyapp.components.LogButton
import com.example.storyapp.components.PasswordEditText
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.network.ApiConfig
import com.example.storyapp.network.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var myButton: LogButton
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var linkLoginText: TextView
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var nameFinal: String
    private lateinit var emailFinal: String
    private lateinit var passwordFinal: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myButton = findViewById(R.id.register_button)
        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        linkLoginText = findViewById(R.id.link_login)

        setMyButtonEnable()

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (passwordEditText.text!!.isNotEmpty() && nameEditText.text!!.isNotEmpty()) {
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
                if (emailEditText.text!!.isNotEmpty() && nameEditText.text!!.isNotEmpty()) {
                    setMyButtonEnable()
                }
            }

            override fun afterTextChanged(s: Editable) {
                passwordFinal = passwordEditText.text.toString()
            }
        })

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (emailEditText.text!!.isNotEmpty() && passwordEditText.text!!.isNotEmpty()) {
                    setMyButtonEnable()
                }
            }

            override fun afterTextChanged(s: Editable) {
                nameFinal = nameEditText.text.toString()
            }
        })

        myButton.setOnClickListener {
            postRegister(nameFinal, emailFinal, passwordFinal)
        }

        linkLoginText.setOnClickListener {
            val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun setMyButtonEnable() {
        val result = emailEditText.text
        myButton.isEnabled = result != null && result.toString().isNotEmpty()
    }

    private fun postRegister(name: String, email: String, password: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().postRegister(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e(TAG, "onSuccess: ${response.message()}")
                    Toast.makeText(
                        this@RegisterActivity,
                        "Success registering ${email}",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                    val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(loginIntent)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    emailEditText.error = "Email is already taken"
                    Toast.makeText(
                        this@RegisterActivity,
                        "Email is already taken",
                        Toast.LENGTH_SHORT
                    ).show()

                    emailEditText.text?.clear()
                    nameEditText.text?.clear()
                    passwordEditText.text?.clear()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
                emailEditText.error = "Email is already taken"
            }
        })

        emailEditText.error = null
        passwordEditText.error = null
        emailEditText.setCompoundDrawables(null, null, null, null);
        passwordEditText.setCompoundDrawables(null, null, null, null);
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
package com.example.storyapp.ui

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.storyapp.components.EmailEditText
import com.example.storyapp.components.LogButton
import com.example.storyapp.components.PasswordEditText
import com.example.storyapp.R
import com.example.storyapp.network.ApiConfig
import com.example.storyapp.network.LoginResponse
import com.example.storyapp.network.RegisterResponse
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        myButton = findViewById(R.id.login_button)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        linkRegisterText = findViewById(R.id.link_register)
        mTokenPreference = TokenPreference(this@LoginActivity)

        if (mTokenPreference.getToken() != "") {
            val storyIntent = Intent(this@LoginActivity, StoryActivity::class.java)
            startActivity(storyIntent)
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
//            Toast.makeText(this@LoginActivity, emailEditText.text, Toast.LENGTH_SHORT).show()
            postLogin(emailFinal, passwordFinal)

            emailEditText.text?.clear()
        }

        linkRegisterText.setOnClickListener {
            val regIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(regIntent)
        }
    }

    private fun setMyButtonEnable() {
        val result = emailEditText.text
        myButton.isEnabled = result != null && result.toString().isNotEmpty()
    }

    private fun postLogin(email: String, password: String) {
//        showLoading(true)
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
//                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e(ContentValues.TAG, "onSuccess: ${response.message()}")
                    Toast.makeText(this@LoginActivity, "Success login ${email}", Toast.LENGTH_SHORT).show()

                    mTokenPreference.setToken(responseBody.loginResult.token)

                    val storyIntent = Intent(this@LoginActivity, StoryActivity::class.java)
                    startActivity(storyIntent)
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                showLoading(false)
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}


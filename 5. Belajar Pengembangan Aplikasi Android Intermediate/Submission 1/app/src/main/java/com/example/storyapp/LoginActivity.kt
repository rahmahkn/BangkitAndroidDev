package com.example.storyapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var myButton: LogButton
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        myButton = findViewById(R.id.login_button)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        setMyButtonEnable()

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                setMyButtonEnable()
                if (passwordEditText.text!!.isNotEmpty()) {
                    setMyButtonEnable()
                }
            }
            override fun afterTextChanged(s: Editable) {
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
            }
        })

        myButton.setOnClickListener {
            Toast.makeText(this@LoginActivity, emailEditText.text, Toast.LENGTH_SHORT).show()
            emailEditText.text?.clear()
        }
    }

    private fun setMyButtonEnable() {
        val result = emailEditText.text
        myButton.isEnabled = result != null && result.toString().isNotEmpty()
    }
}


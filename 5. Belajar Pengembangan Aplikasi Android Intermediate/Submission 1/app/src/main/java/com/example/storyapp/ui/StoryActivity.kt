package com.example.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.storyapp.R
import com.example.storyapp.network.TokenPreference
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StoryActivity : AppCompatActivity() {
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var mTokenPreference: TokenPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        mTokenPreference = TokenPreference(this@StoryActivity)

        Toast.makeText(this@StoryActivity, "Success login ${mTokenPreference.getToken()}", Toast.LENGTH_SHORT).show()

        fabAdd.setOnClickListener {
            val addStoryIntent = Intent(this@StoryActivity, AddStoryActivity::class.java)
            startActivity(addStoryIntent)
        }
    }
}
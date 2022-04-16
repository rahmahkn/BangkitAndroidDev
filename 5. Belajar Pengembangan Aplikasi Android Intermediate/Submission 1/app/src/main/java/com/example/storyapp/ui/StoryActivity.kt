package com.example.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storyapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StoryActivity : AppCompatActivity() {
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)

        fabAdd.setOnClickListener {
            val addStoryIntent = Intent(this@StoryActivity, AddStoryActivity::class.java)
            startActivity(addStoryIntent)
        }
    }
}
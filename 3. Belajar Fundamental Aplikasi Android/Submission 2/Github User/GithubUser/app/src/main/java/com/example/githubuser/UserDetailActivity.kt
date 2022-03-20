package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.githubuser.network.User
import java.lang.StringBuilder

class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        // Getting view in detail user page
        val tvAvatar: ImageView = findViewById(R.id.tv_detail_avatar)
        val tvUsername: TextView = findViewById(R.id.tv_detail_username)
        val tvName: TextView = findViewById(R.id.tv_detail_name)
        val tvLocation: TextView = findViewById(R.id.tv_detail_location)
        val tvCompany: TextView = findViewById(R.id.tv_detail_company)
        val tvFollRepo: TextView = findViewById(R.id.tv_detail_foll_repo)

        // Change the view attribute user data from main activity
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        user.avatar?.let { tvAvatar.setImageResource(it) }
        tvUsername.text = user.username
        tvName.text = user.name
        tvLocation.text = StringBuilder("Location: ").append(user.location)
        tvCompany.text = StringBuilder("Company: ").append(user.company)
        tvFollRepo.text = StringBuilder(user.followers).append(" followers | ").append(user.following).append(" following | ").append(user.repository).append(" repository")
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}
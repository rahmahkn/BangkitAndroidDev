package com.example.githubuser

import android.content.Intent.EXTRA_USER
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

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
        tvLocation.text = "Location: ${user.location}"
        tvCompany.text = "Company: ${user.company}"
        tvFollRepo.text = "${user.followers} followers | ${user.following} following | ${user.repository} repositories"
    }
}
package com.example.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.network.detail.UserApiConfig
import com.example.githubuser.network.detail.UserResponse
import com.example.githubuser.network.search.SearchApiConfig
import com.example.githubuser.ui.search.SearchAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        getUserData()

        val sectionsPagerAdapter = DetailAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun getUserData() {
        val username = intent.getStringExtra(EXTRA_USER)
        // Getting view in detail user page
        val tvAvatar: ImageView = findViewById(R.id.tv_detail_avatar)
        val tvUsername: TextView = findViewById(R.id.tv_detail_username)
        val tvName: TextView = findViewById(R.id.tv_detail_name)
        val tvLocation: TextView = findViewById(R.id.tv_detail_location)
        val tvCompany: TextView = findViewById(R.id.tv_detail_company)
        val tvFollRepo: TextView = findViewById(R.id.tv_detail_foll_repo)

        showLoading(true)
        val client = UserApiConfig.getUserApiService().getUser(username!!)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val user = responseBody
                        Log.d("Nama: ", user!!.name!!)

                        // Change the view attribute user data from main activity
                        Glide.with(this@UserDetailActivity)
                            .load(user.avatarUrl)
                            .into(tvAvatar)
                        tvUsername.text = user!!.login
                        tvName.text = username
                        tvLocation.text = StringBuilder("Location: ").append(user!!.location)
                        tvCompany.text = StringBuilder("Company: ").append(user!!.company)
                        tvFollRepo.text =
                            user!!.followers?.let {
                                StringBuilder(it).append(" followers | ").append(user!!.following)
                                    .append(" following | ").append(user!!.publicRepos).append(" repository")
                            }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        val pgBar: ProgressBar = findViewById(R.id.progressBar2)

        if (isLoading) {
            pgBar.visibility = View.VISIBLE
        } else {
            pgBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "UserDetailActivity"
        const val EXTRA_USER = "sidiqpermana"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
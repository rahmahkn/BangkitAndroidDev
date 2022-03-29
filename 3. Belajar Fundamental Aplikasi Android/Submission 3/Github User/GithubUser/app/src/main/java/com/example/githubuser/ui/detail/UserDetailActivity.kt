package com.example.githubuser.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.database.FavoritedUser
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.example.githubuser.network.detail.UserApiConfig
import com.example.githubuser.network.detail.UserResponse
import com.example.githubuser.ui.favorites.helper.FavoritedAddDeleteViewModel
import com.example.githubuser.ui.favorites.helper.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {
    private var numFollowers = "Followers"
    private var numFollowing = "Following"
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var favoritedAddDeleteViewModel: FavoritedAddDeleteViewModel
    private lateinit var favUser: FavoritedUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoritedAddDeleteViewModel = obtainViewModel(this@UserDetailActivity)

        val dataUsername = intent.getStringExtra(EXTRA_USER)!!
        val dataAvatar = intent.getStringExtra(EXTRA_AVATAR)!!
        getUserData()

        val TAB_TITLES = arrayOf(
            numFollowers, numFollowing
        )

        var isFavorited = favoritedAddDeleteViewModel.isRowIsExist(dataUsername)
        val fabFav = findViewById<FloatingActionButton>(R.id.fab_favorite)

        if (isFavorited) fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)

        binding.fabFavorite.setOnClickListener {
            favUser = FavoritedUser(dataUsername, dataAvatar)

            isFavorited = if (!isFavorited) {
                favoritedAddDeleteViewModel.insert(favUser)
                fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                showToast("Adding $dataUsername to favorites")
                true
            } else {
                favoritedAddDeleteViewModel.delete(favUser)
                fabFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                showToast("Removing $dataUsername from favorites")
                false
            }
        }

        val sectionsPagerAdapter = DetailAdapter(this, intent.getStringExtra(EXTRA_USER)!!)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoritedAddDeleteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoritedAddDeleteViewModel::class.java]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getUserData() {
        val username = intent.getStringExtra(EXTRA_USER)

        // Getting view in detail user page
        val tvAvatar: ImageView = findViewById(R.id.tv_detail_avatar)
        val tvUsername: TextView = findViewById(R.id.tv_detail_username)
        val tvName: TextView = findViewById(R.id.tv_detail_name)
        val tvLocation: TextView = findViewById(R.id.tv_detail_location)

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

                        // Change the view attribute user data from main activity
                        Glide.with(this@UserDetailActivity)
                            .load(user.avatarUrl)
                            .into(tvAvatar)
                        tvUsername.text = user.login
                        tvName.text = user.name
                        tvLocation.text =
                            user.followers?.let {
                                StringBuilder(it).append(user.company)
                                    .append(" • ").append(user.location)
                            }

                        numFollowers = "Followers (" + user.followers.toString() + ")"
                        numFollowing = "Following (" + user.following.toString() + ")"
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
        const val EXTRA_USER = "extra_user"
        const val EXTRA_AVATAR = "extra_avatar"
    }
}
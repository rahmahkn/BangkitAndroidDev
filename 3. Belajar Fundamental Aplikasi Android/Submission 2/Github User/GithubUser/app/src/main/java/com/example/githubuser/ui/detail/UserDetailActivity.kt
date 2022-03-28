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
import com.example.githubuser.ui.helper.FavoritedAddUpdateViewModel
import com.example.githubuser.ui.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {
    private var numFollowers = "Followers"
    private var numFollowing = "Following"
    private var dataUsername = "username"
    private var dataAvatarUrl = "avatarUrl"
    private lateinit var favoritedAddUpdateViewModel: FavoritedAddUpdateViewModel
    private var _activityFavoritedAddUpdateBinding: ActivityUserDetailBinding? = null
    private val binding get() = _activityFavoritedAddUpdateBinding
    private var isFavorited = false
    private lateinit var favUser: FavoritedUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        _activityFavoritedAddUpdateBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        favoritedAddUpdateViewModel = obtainViewModel(this@UserDetailActivity)
        isFavorited = favoritedAddUpdateViewModel.isRowIsExist(EXTRA_USER)
        showToast(isFavorited.toString())

        getUserData()

        val TAB_TITLES = arrayOf(
            numFollowers, numFollowing
        )

        binding?.fabFavorite?.setOnClickListener {
            favUser = FavoritedUser(EXTRA_USER, EXTRA_AVATAR)
            if (!isFavorited) {
                favoritedAddUpdateViewModel.insert(favUser)
                showToast("Adding $EXTRA_USER to favorites")
            } else {
                favoritedAddUpdateViewModel.delete(favUser)
                showToast("Removing $EXTRA_USER from favorites")
            }
        }

//        val sectionsPagerAdapter = DetailAdapter(this, intent.getStringExtra(EXTRA_USER)!!)
        val sectionsPagerAdapter = DetailAdapter(this, "rahmahkn")
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoritedAddUpdateBinding = null
    }
    private fun obtainViewModel(activity: AppCompatActivity): FavoritedAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoritedAddUpdateViewModel::class.java)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getUserData() {
//        val username = intent.getStringExtra(EXTRA_USER)
        val username = EXTRA_USER

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

                        dataUsername = user.login
                        dataAvatarUrl = user.avatarUrl

                        // Change the view attribute user data from main activity
                        Glide.with(this@UserDetailActivity)
                            .load(user.avatarUrl)
                            .into(tvAvatar)
                        tvUsername.text = user.login
                        tvName.text = user.name

                        tvLocation.text = StringBuilder("").append(user.company).append(" • ")
                            .append(user.location)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@UserDetailActivity, t.message, Toast.LENGTH_SHORT).show();
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
        const val EXTRA_USER = "rahmahkn"
        const val EXTRA_AVATAR = "https://avatars.githubusercontent.com/u/66777934?v=4"
        const val EXTRA_FAVORITED = "extra_favorited"
    }
}
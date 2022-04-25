package com.example.storyapp.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.network.ApiConfig
import com.example.storyapp.network.GetStoryResponse
import com.example.storyapp.network.TokenPreference
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryActivity : AppCompatActivity() {
    private lateinit var rvStories: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var mTokenPreference: TokenPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        rvStories = findViewById(R.id.rv_stories)
        fabAdd = findViewById(R.id.fab_add)
        mTokenPreference = TokenPreference(this@StoryActivity)
        val token = mTokenPreference.getToken()

        fabAdd.setOnClickListener {
            val addStoryIntent = Intent(this@StoryActivity, AddStoryActivity::class.java)
            startActivity(addStoryIntent)
        }

        getStories(token)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                val settings1: SharedPreferences =
                    this@StoryActivity.getSharedPreferences("token_pref", Context.MODE_PRIVATE)
                settings1.edit().clear().apply()

                val settings2: SharedPreferences =
                    this@StoryActivity.getSharedPreferences("session_pref", Context.MODE_PRIVATE)
                settings2.edit().clear().apply()

                finish()

                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return true
    }

    private fun getStories(token: String) {
        showLoading(true)

        val client = ApiConfig.getApiService().getStories("Bearer $token")
        client.enqueue(object : Callback<GetStoryResponse> {
            override fun onResponse(
                call: Call<GetStoryResponse>,
                response: Response<GetStoryResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {

                        if (responseBody.listStory.isEmpty()) {
                            Log.d("GetStories", "empty")
                        } else {
                            rvStories.layoutManager = LinearLayoutManager(this@StoryActivity)

                            val listStoryFinal =
                                responseBody.listStory.sortedByDescending { it.createdAt }
                            val listStoriesAdapter = StoryAdapter(listStoryFinal)
                            rvStories.adapter = listStoriesAdapter

                        }
                    } else {
                        Log.d("Jumlah: ", "null")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetStoryResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
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
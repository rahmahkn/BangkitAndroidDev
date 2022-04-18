package com.example.storyapp.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.network.ApiConfig
import com.example.storyapp.network.GetStoryResponse
import com.example.storyapp.network.ListStoryItem
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
        fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        mTokenPreference = TokenPreference(this@StoryActivity)

        fabAdd.setOnClickListener {
            val addStoryIntent = Intent(this@StoryActivity, AddStoryActivity::class.java)
            startActivity(addStoryIntent)
        }

        getStories()
    }

    private fun getStories() {
//        val tvError = R.layout.findViewById<TextView>(R.id.followers_error)
//        showLoading(true)

        val client = ApiConfig.getApiService().getStories("Bearer ${mTokenPreference.getToken()}")
        client.enqueue(object : Callback<GetStoryResponse> {
            override fun onResponse(
                call: Call<GetStoryResponse>,
                response: Response<GetStoryResponse>
            ) {
//                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("GetStories", responseBody.listStory.toString())

                        if (responseBody.listStory.isEmpty()) {
                            Log.d("GetStories", "empty")
                        } else {
//                            binding.mainError.visibility = View.GONE
                            rvStories.layoutManager = LinearLayoutManager(this@StoryActivity)
                            val listStoriesAdapter = StoryAdapter(responseBody.listStory)
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
//                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
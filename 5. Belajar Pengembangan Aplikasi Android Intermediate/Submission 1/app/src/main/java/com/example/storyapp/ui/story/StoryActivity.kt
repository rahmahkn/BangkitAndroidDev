package com.example.storyapp.ui.story

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.helper.MainViewModel
import com.example.storyapp.network.TokenPreference
import com.example.storyapp.ui.add_story.AddStoryActivity
import com.example.storyapp.ui.auth.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.helper.StoryViewModelFactory
import com.example.storyapp.network.ApiService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StoryActivity : AppCompatActivity() {
    private lateinit var rvStories: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var mTokenPreference: TokenPreference
//    private val mainViewModel: MainViewModel by viewModels {
//        ViewModelFactory("")
//    }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        rvStories = findViewById(R.id.rv_stories)
        fabAdd = findViewById(R.id.fab_add)
        mTokenPreference = TokenPreference(this@StoryActivity)
        token = mTokenPreference.getToken()

//        var factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                return  MainViewModel() as T
//            }
//        }
//        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
//        mainViewModel = ViewModelProviders(this).get(ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                @Suppress("UNCHECKED_CAST")
//                return MainViewModel(storyRepository) as T
//            }
//        })[MainViewModel::class.java]

        setupViewModel()
        setupView()
        setupList()

        fabAdd.setOnClickListener {
            val addStoryIntent = Intent(this@StoryActivity, AddStoryActivity::class.java)
            startActivity(addStoryIntent)
        }

//        getStories(token)
    }

    private fun setupViewModel() {
        mTokenPreference = TokenPreference(this@StoryActivity)
        token = mTokenPreference.getToken()
        val factory = StoryViewModelFactory(ApiService(), token)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        showLoading(true)
    }

    private fun setupView() {
        storyAdapter = StoryAdapter()
        rvStories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = storyAdapter
            setHasFixedSize(true)
        }
        showLoading(true)
    }

    private fun setupList() {
        lifecycleScope.launch {
            mainViewModel.quote.collectLatest { pagedData ->
                storyAdapter.submitData(pagedData)
            }
        }
        showLoading(false)
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
//        showLoading(true)

//        val adapter = StoryAdapter()
//        rvStories.layoutManager = LinearLayoutManager(this@StoryActivity)
//        rvStories.adapter = adapter
//        mainViewModel.quote.observe(this) {
//            adapter.submitData(lifecycle, it)
//        }

//        val client = ApiConfig.getApiService().getStories("Bearer $token")
//        client.enqueue(object : Callback<GetStoryResponse> {
//            override fun onResponse(
//                call: Call<GetStoryResponse>,
//                response: Response<GetStoryResponse>
//            ) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//
//                        if (responseBody.listStory.isEmpty()) {
//                            Log.d("GetStories", "empty")
//                        } else {
//                            rvStories.layoutManager = LinearLayoutManager(this@StoryActivity)
//
//                            val listStoryFinal =
//                                responseBody.listStory.sortedByDescending { it.createdAt }
//                            val listStoriesAdapter = StoryAdapter(listStoryFinal)
//                            rvStories.adapter = listStoriesAdapter
//
//                        }
//                    } else {
//                        Log.d("Jumlah: ", "null")
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<GetStoryResponse>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
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
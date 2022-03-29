package com.example.githubuser.ui.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.network.SearchItem
import com.example.githubuser.network.SearchResponse
import com.example.githubuser.network.search.SearchApiConfig
import com.example.githubuser.ui.detail.UserDetailActivity
import com.example.githubuser.ui.favorites.FavoriteActivity
import com.example.githubuser.ui.settings.SettingsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUsers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUsers = findViewById(R.id.rv_users)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search User"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)

                showSearchResults(query)
                searchView.clearFocus()

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val mainError = findViewById<TextView>(R.id.main_error)

                mainError.visibility = View.GONE

                showLoading(true)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
            R.id.menu_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return true
    }

    private fun showSearchResults(query: String) {
        showLoading(true)
        val client = SearchApiConfig.getSearchApiService().getSearch(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("Jumlah response: ", responseBody.items.size.toString())

                        if (responseBody.items.isEmpty()) {
                            binding.mainError.visibility = View.VISIBLE
                        } else {
                            binding.mainError.visibility = View.GONE
                            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
                            val listUserAdapter = SearchAdapter(responseBody.items)
                            rvUsers.adapter = listUserAdapter

                            listUserAdapter.setOnItemClickCallback(object :
                                SearchAdapter.OnItemClickCallback {
                                override fun onItemClicked(data: SearchItem) {
                                    val moveDataWithIntent =
                                        Intent(this@MainActivity, UserDetailActivity::class.java)

                                    moveDataWithIntent.putExtra(
                                        UserDetailActivity.EXTRA_USER,
                                        data.login
                                    )
                                    moveDataWithIntent.putExtra(
                                        UserDetailActivity.EXTRA_AVATAR,
                                        data.avatar_url
                                    )

                                    startActivity(moveDataWithIntent)
                                }
                            })
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
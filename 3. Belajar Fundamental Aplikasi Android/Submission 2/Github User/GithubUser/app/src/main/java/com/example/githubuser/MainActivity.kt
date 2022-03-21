package com.example.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.network.SearchItem
import com.example.githubuser.network.SearchResponse
import com.example.githubuser.network.User
import com.example.githubuser.network.search.SearchApiConfig
import com.example.githubuser.ui.detail.UserDetailActivity
import com.example.githubuser.ui.search.SearchAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUsers: RecyclerView

    companion object {
        private const val TAG = "MainActivity"
        private const val USERNAME = "sidiqpermana"
    }

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

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search User"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, "Cari " + query, Toast.LENGTH_SHORT).show()
                showSearchResults(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                Toast.makeText(this, "Klik search bar", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
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

                        rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
                        val listUserAdapter = SearchAdapter(responseBody.items)
                        rvUsers.adapter = listUserAdapter

                        listUserAdapter.setOnItemClickCallback(object: SearchAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: SearchItem) {
                                val moveDataWithIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
                                moveDataWithIntent.putExtra(UserDetailActivity.EXTRA_USER, data.login)
                                startActivity(moveDataWithIntent)
                            }
                        })
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
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "You clicked " + user.username, Toast.LENGTH_SHORT).show()
    }
}
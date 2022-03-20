package com.example.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.network.SearchItem
import com.example.githubuser.network.SearchResponse
import com.example.githubuser.network.User
import com.example.githubuser.network.search.SearchApiConfig
import com.example.githubuser.ui.search.SearchAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUsers: RecyclerView
    private var list = ArrayList<SearchItem>()

    companion object {
        private const val TAG = "MainActivity"
        private  const val USERNAME = "sidiqpermana"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        showSearchResults()
//        showRecyclerList()
    }

    private fun showSearchResults() {
        showLoading(true)
        val client = SearchApiConfig.getSearchApiService().getSearch(USERNAME)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setSearchData(responseBody.items as List<SearchItem>)
                    }
                } else {
                    Log.e(TAG, "onFailre: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setSearchData(users: List<SearchItem>) {
        val listUser = ArrayList<SearchItem>()
        for (user in users) {
            listUser.add(
                SearchItem(user.login, user.avatar_url, user.name)
            )
        }

//        val adapter = SearchAdapter(listUser)
//        binding.rvUsers.adapter = adapter

        rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = SearchAdapter(list)
        rvUsers.adapter = listUserAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showRecyclerList() {
        rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = SearchAdapter(list)
        rvUsers.adapter = listUserAdapter

//        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: User) {
//                // Show the toast
//                showSelectedUser(data)
//
//                // Move to detail user page using intent
//                val moveUserIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
//                moveUserIntent.putExtra(UserDetailActivity.EXTRA_USER, data)
//                startActivity(moveUserIntent)
//            }
//        })
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "You clicked " + user.username, Toast.LENGTH_SHORT).show()
    }
}
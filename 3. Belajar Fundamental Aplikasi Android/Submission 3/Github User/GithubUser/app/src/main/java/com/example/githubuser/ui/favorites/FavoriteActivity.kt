package com.example.githubuser.ui.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.database.FavoritedUser
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.ui.favorites.helper.FavoritedAdapter
import com.example.githubuser.ui.favorites.helper.MainViewModel
import com.example.githubuser.ui.favorites.helper.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var rvUsers: RecyclerView
    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding
    private lateinit var adapter: FavoritedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        rvUsers = findViewById(R.id.rv_users)

        // setting up view model
        val mainViewModel = obtainViewModel(this@FavoriteActivity)
        mainViewModel.getAllFavorites().observe(this) { userList ->
            if (userList != null) {
                // setting up adapter
                val listFavorited = ArrayList<FavoritedUser>()
                listFavorited.clear()
                listFavorited.addAll(userList)
                adapter = FavoritedAdapter(userList)
                adapter.setListNotes(listFavorited)

                val layoutManager = LinearLayoutManager(this)
                binding?.rvUsers?.layoutManager = layoutManager
                binding?.rvUsers?.setHasFixedSize(true)
                binding?.rvUsers?.adapter = adapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }
}

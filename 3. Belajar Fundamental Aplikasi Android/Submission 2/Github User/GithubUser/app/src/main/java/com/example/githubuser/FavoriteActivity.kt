package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.example.githubuser.network.SearchItem
import com.example.githubuser.network.detail.UserResponse
import com.example.githubuser.ui.helper.FavoritedAddUpdateViewModel
import com.example.githubuser.ui.helper.MainViewModel
import com.example.githubuser.ui.helper.ViewModelFactory
import com.example.githubuser.ui.search.SearchAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var rvUsers: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        rvUsers = binding?.rvUsers as RecyclerView
        mainViewModel = obtainViewModel(this@FavoriteActivity)

        val layoutManager = LinearLayoutManager(this)
        binding!!.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding!!.rvUsers.addItemDecoration(itemDecoration)

        val listFavorites = mainViewModel.getAllFavorites()

        val listUsers = List<SearchItem>()

        val listUserAdapter = SearchAdapter(listFavorites)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

}

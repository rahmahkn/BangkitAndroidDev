package com.example.storyapp.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.storyapp.network.ApiService

class MainViewModel(private val api: ApiService, private val token: String) : ViewModel() {
    val quote = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = {
            StoryPagingDataSource(api, token)}).flow.cachedIn(viewModelScope)
}


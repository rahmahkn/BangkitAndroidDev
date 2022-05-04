package com.example.storyapp.helper

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.helper.StoryPagingDataSource
import com.example.storyapp.network.ApiService
import com.example.storyapp.network.ListStoryItem

class StoryRepository(
//    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService,
    private val token: String
) {
    fun getQuote(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                StoryPagingDataSource(apiService, token)
            }
        ).liveData
    }
}
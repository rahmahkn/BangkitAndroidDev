package com.example.storyapp.helper

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.network.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryPagingDataSource(
    private val service: ApiService,
    private val token: String) :
    PagingSource<Int, ListStoryItem>() {

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        val pageNumber = params.key ?: 1
        return try {
            val pagedResponse = service.getStories("Bearer $token", pageNumber)
            var listStories = pagedResponse.listStory.sortedByDescending { it.createdAt }
            Log.d("GetStories", pagedResponse.toString())

            LoadResult.Page(
                data = listStories,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (pagedResponse.listStory.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            Log.d("GetStories", e.toString())
            LoadResult.Error(e)
        }
    }
}
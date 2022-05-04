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
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
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
            var pagedResponse = listOf<ListStoryItem>()

            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
//                val response = mainRepository.getAllMovies()
                withContext(Dispatchers.Main) {
                    val client = ApiConfig.getApiService().getStories("Bearer $token", pageNumber)
                    client.enqueue(object : Callback<GetStoryResponse> {
                        override fun onResponse(
                            call: Call<GetStoryResponse>,
                            response: Response<GetStoryResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()
                                if (responseBody != null) {

                                    if (responseBody.listStory.isEmpty()) {
                                        Log.d("GetStories", "empty")
                                    } else {
                                        pagedResponse = responseBody.listStory
                                    }
                                } else {
                                    Log.d("Jumlah: ", "null")
                                }
                            } else {
                                Log.e(TAG, "onFailure: ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<GetStoryResponse>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                        }
                    })
//                    pagedResponse = ApiConfig.getApiService().getStories("Bearer $token", pageNumber).listStory
                }
            }

            LoadResult.Page(
                data = pagedResponse,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (pagedResponse.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
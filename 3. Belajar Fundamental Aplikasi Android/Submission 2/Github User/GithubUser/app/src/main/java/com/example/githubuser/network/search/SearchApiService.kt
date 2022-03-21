package com.example.githubuser.network.search

import com.example.githubuser.network.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearch(@Query("q") username: String): Call<SearchResponse>
}
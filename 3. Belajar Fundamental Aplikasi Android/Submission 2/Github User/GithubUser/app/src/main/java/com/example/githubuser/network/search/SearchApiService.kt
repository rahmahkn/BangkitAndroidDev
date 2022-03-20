package com.example.githubuser.network.search

import com.example.githubuser.network.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApiService {
    @GET("users/{username}")
    fun getSearch(
        @Path("username") username: String
    ): Call<SearchResponse>
}
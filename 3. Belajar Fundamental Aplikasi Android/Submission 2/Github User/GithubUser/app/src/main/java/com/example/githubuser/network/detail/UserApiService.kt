package com.example.githubuser.network.detail

import com.example.githubuser.network.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(@Path("username") username: String): Call<UserResponse>
}
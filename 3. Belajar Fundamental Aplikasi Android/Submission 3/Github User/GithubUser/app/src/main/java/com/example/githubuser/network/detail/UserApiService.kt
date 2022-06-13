package com.example.githubuser.network.detail

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserApiService {
    @GET("users/{username}")
    @Headers("Authorization: token $TOKEN")
    fun getUser(@Path("username") username: String): Call<UserResponse>
}

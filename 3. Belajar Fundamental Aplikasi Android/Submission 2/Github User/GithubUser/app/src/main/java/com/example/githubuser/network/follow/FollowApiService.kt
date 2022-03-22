package com.example.githubuser.network.follow

import com.example.githubuser.network.FollowResponse
import com.example.githubuser.network.FollowResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FollowApiService {
    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<FollowResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<List<FollowResponseItem>>
}
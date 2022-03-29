package com.example.githubuser.network.follow

import com.example.githubuser.network.detail.RetrofitGithub

class FollowApiConfig {
    companion object {
        fun getFollowApiService(): FollowApiService {
            return RetrofitGithub.getInstance().create(FollowApiService::class.java)
        }
    }
}
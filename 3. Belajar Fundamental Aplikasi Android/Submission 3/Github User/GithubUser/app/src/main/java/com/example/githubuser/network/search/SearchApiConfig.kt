package com.example.githubuser.network.search

import com.example.githubuser.network.detail.RetrofitGithub

class SearchApiConfig {
    companion object {
        fun getSearchApiService(): SearchApiService {
            return RetrofitGithub.getInstance().create(SearchApiService::class.java)
        }
    }
}
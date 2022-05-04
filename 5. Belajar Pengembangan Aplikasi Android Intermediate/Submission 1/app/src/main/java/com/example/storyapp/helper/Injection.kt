package com.example.storyapp.database

import com.example.storyapp.helper.StoryRepository
import com.example.storyapp.network.ApiConfig

object Injection {
    fun provideRepository(token: String): StoryRepository {
//        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService, token)
    }
}
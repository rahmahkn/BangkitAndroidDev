package com.example.storyapp.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.network.ApiService

class StoryViewModelFactory(
    private val api: ApiService,
    private val token: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(api, token) as T
    }
}

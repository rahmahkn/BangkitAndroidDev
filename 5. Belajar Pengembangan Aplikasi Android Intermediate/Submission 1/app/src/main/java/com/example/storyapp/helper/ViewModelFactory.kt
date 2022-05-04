package com.example.storyapp.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.database.Injection

//class StoryViewModelFactory(
//    val context: Context,
//    val token: String
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return MainViewModel(Injection.provideRepository(context, token)) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

package com.example.githubuser.ui.helper

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoritedRepository
import com.example.githubuser.database.FavoritedUser

class MainViewModel(application: Application) : ViewModel() {
    private val mFavoritedRepository: FavoritedRepository = FavoritedRepository(application)
    fun getAllFavorites(): LiveData<List<FavoritedUser>> = mFavoritedRepository.getAllNotes()
}
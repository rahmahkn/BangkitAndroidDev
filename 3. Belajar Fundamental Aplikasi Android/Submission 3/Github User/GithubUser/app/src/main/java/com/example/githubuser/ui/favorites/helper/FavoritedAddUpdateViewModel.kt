package com.example.githubuser.ui.favorites.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoritedRepository
import com.example.githubuser.database.FavoritedUser

class FavoritedAddDeleteViewModel(application: Application) : ViewModel() {
    private val mFavoritedRepository: FavoritedRepository = FavoritedRepository(application)
    fun insert(user: FavoritedUser) {
        mFavoritedRepository.insert(user)
    }

    fun delete(user: FavoritedUser) {
        mFavoritedRepository.delete(user)
    }

    fun isRowIsExist(username: String): Boolean {
        return mFavoritedRepository.isRowIsExist(username)
    }
}
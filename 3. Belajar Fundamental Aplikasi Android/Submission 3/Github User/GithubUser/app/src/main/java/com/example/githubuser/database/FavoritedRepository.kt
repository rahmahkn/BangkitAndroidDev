package com.example.githubuser.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoritedRepository(application: Application) {
    private val mFavoritedDao: FavoritedDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoritedDatabase.getDatabase(application)
        mFavoritedDao = db.favoritedDao()
    }

    fun getAllFavorites(): LiveData<List<FavoritedUser>> = mFavoritedDao.getAllFavorites()

    fun insert(user: FavoritedUser) {
        executorService.execute { mFavoritedDao.insert(user) }
    }

    fun delete(user: FavoritedUser) {
        executorService.execute { mFavoritedDao.delete(user) }
    }

    fun isRowIsExist(username: String): Boolean = mFavoritedDao.isRowIsExist(username)
}
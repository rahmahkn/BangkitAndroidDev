package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritedDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoritedUser)

    @Delete
    fun delete(user: FavoritedUser)

    @Query("SELECT * FROM favoritedUser")
    fun getAllFavorites() : LiveData<List<FavoritedUser>>

    @Query("SELECT EXISTS(SELECT * FROM favoritedUser WHERE username = :username)")
    fun isRowIsExist(username : String) : Boolean
}
package com.example.githubuser.ui.favorites.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.database.FavoritedUser

class FavoritedDiffCallback(
    private val mOldFavoritedList: List<FavoritedUser>,
    private val mNewFavoritedList: List<FavoritedUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoritedList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoritedList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoritedList[oldItemPosition].username == mNewFavoritedList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldFavoritedList[oldItemPosition]
        val newUser = mNewFavoritedList[newItemPosition]
        return oldUser.username == newUser.username && oldUser.avatarUrl == oldUser.avatarUrl
    }
}
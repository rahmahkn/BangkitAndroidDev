package com.example.githubuser.ui.favorites.helper

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.database.FavoritedUser
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.ui.detail.UserDetailActivity

class FavoritedAdapter(private val listFavorited: List<FavoritedUser>) :
    RecyclerView.Adapter<FavoritedAdapter.FavoritedViewHolder>() {
    fun setListNotes(listFavoritedFinal: ArrayList<FavoritedUser>) {
        val diffCallback = FavoritedDiffCallback(listFavorited, listFavoritedFinal)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listFavoritedFinal.clear()
        listFavoritedFinal.addAll(listFavorited)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritedViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritedViewHolder, position: Int) {
        holder.bind(listFavorited[position])
    }

    override fun getItemCount(): Int {
        return listFavorited.size
    }

    inner class FavoritedViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavoritedUser) {
            binding.tvItemUsername.text = user.username
            Glide.with(this@FavoritedViewHolder.itemView.context)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)

            itemView.setOnClickListener {
                val intent = Intent(it.context, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_USER, user.username)
                intent.putExtra(UserDetailActivity.EXTRA_AVATAR, user.avatarUrl)
                it.context.startActivity(intent)
            }
        }
    }
}
package com.example.githubuser.ui.detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.network.FollowResponseItem

class FollowAdapter(private val listSearch: ArrayList<FollowResponseItem>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.itemView.context)
            .load(listSearch[position].avatarUrl)
            .into(viewHolder.ivAvatar)

        viewHolder.tvUsername.text = listSearch[position].login

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(it.context, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.EXTRA_USER, listSearch[position].login)
            intent.putExtra(UserDetailActivity.EXTRA_AVATAR, listSearch[position].avatarUrl)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listSearch.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar: ImageView = view.findViewById(R.id.img_item_photo)
        val tvUsername: TextView = view.findViewById(R.id.tv_item_username)
    }
}
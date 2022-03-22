package com.example.githubuser.ui.detail

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.network.FollowResponseItem
import com.example.githubuser.network.SearchItem

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
    }

    override fun getItemCount() = listSearch.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar: ImageView = view.findViewById(R.id.img_item_photo)
        val tvUsername: TextView = view.findViewById(R.id.tv_item_username)
    }
}
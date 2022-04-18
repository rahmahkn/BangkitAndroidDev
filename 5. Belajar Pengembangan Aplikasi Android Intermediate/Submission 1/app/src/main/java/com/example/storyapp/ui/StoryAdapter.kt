package com.example.storyapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.network.ListStoryItem

class StoryAdapter(private val listStories: List<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
        private lateinit var onItemClickCallback: OnItemClickCallback

        interface OnItemClickCallback {
            fun onItemClicked(data: ListStoryItem)
        }

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
            ViewHolder(
                LayoutInflater.from(viewGroup.context).inflate(R.layout.item_story, viewGroup, false)
            )

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            Glide.with(viewHolder.itemView.context)
                .load(listStories[position].photoUrl)
                .into(viewHolder.ivImage)

            viewHolder.tvName.text = listStories[position].name
            viewHolder.tvDescription.text = listStories[position].description

            viewHolder.itemView.setOnClickListener {
                val intent = Intent(it.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.EXTRA_IMAGE, listStories[position].photoUrl)
                intent.putExtra(DetailStoryActivity.EXTRA_NAME, listStories[position].name)
                intent.putExtra(DetailStoryActivity.EXTRA_DESC, listStories[position].description)
                it.context.startActivity(intent)
            }
        }

        override fun getItemCount() = listStories.size

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivImage: ImageView = view.findViewById(R.id.item_image)
            val tvName: TextView = view.findViewById(R.id.item_name)
            val tvDescription: TextView = view.findViewById(R.id.item_description)
        }
}
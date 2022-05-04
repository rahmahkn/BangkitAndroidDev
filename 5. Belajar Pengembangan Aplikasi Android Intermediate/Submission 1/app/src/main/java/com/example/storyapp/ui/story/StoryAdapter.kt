package com.example.storyapp.ui.story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.network.ListStoryItem
import java.text.SimpleDateFormat

class StoryAdapter :
    PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {
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
        val data = getItem(position)

        Glide.with(viewHolder.itemView.context)
            .load(data?.photoUrl)
            .into(viewHolder.ivImage)

        viewHolder.tvName.text = data?.name
        viewHolder.tvDescription.text = data?.description

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val formattedDate = formatter.format(parser.parse(data?.createdAt))
        viewHolder.tvTime.text = formattedDate

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailStoryActivity::class.java)
            intent.putExtra(DetailStoryActivity.EXTRA_IMAGE, data?.photoUrl)
            intent.putExtra(DetailStoryActivity.EXTRA_NAME, data?.name)
            intent.putExtra(DetailStoryActivity.EXTRA_DESC, data?.description)
            intent.putExtra(DetailStoryActivity.EXTRA_TIME, formattedDate)

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    it.context as Activity,
                    androidx.core.util.Pair(viewHolder.ivImage, "foto"),
                    androidx.core.util.Pair(viewHolder.tvName, "nama"),
                    androidx.core.util.Pair(viewHolder.tvDescription, "deskripsi"),
                    androidx.core.util.Pair(viewHolder.tvTime, "waktu")
                )
            it.context.startActivity(intent, optionsCompat.toBundle())
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.item_image)
        val tvName: TextView = view.findViewById(R.id.item_name)
        val tvDescription: TextView = view.findViewById(R.id.item_description)
        val tvTime: TextView = view.findViewById(R.id.item_time)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
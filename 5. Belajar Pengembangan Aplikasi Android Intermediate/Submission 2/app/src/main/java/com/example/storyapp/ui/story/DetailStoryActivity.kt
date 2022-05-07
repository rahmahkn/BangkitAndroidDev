package com.example.storyapp.ui.story

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var ivFoto: ImageView
    private lateinit var tvNama: TextView
    private lateinit var tvDeskripsi: TextView
    private lateinit var tvTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ivFoto = binding.detailFoto
        tvNama = binding.detailNama
        tvDeskripsi = binding.detailDeskripsi
        tvTime = binding.detailWaktu

        Glide.with(this@DetailStoryActivity)
            .load(intent.getStringExtra(EXTRA_IMAGE))
            .into(binding.detailFoto)
        tvNama.text = intent.getStringExtra(EXTRA_NAME)
        tvDeskripsi.text = intent.getStringExtra(EXTRA_DESC)
        tvTime.text = intent.getStringExtra(EXTRA_TIME)
    }

    companion object {
        private const val TAG = "DetailStoryActivity"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_TIME = "extra_time"
    }
}
package com.example.githubuser.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FollowResponse(
	val userItemResponse: List<FollowResponseItem>
)

@Parcelize
data class FollowResponseItem(
	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,
) : Parcelable

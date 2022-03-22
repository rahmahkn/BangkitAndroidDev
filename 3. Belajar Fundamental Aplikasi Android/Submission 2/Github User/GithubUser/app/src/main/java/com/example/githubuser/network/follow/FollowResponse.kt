package com.example.githubuser.network

import com.google.gson.annotations.SerializedName

data class FollowResponse(

	@field:SerializedName("UserItemResponse")
	val userItemResponse: List<FollowResponseItem>
)

data class FollowResponseItem(
	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,
)

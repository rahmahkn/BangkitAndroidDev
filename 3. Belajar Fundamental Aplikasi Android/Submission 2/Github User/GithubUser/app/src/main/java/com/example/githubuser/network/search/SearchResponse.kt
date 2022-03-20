package com.example.githubuser.network

import com.google.gson.annotations.SerializedName

data class SearchResponse(
	@field:SerializedName("items")
	val items: List<SearchItem?>? = null
)

data class SearchItem(
	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatar_url: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

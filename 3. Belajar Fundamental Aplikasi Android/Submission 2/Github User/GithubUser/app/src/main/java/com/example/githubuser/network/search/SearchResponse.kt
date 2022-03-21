package com.example.githubuser.network

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @field:SerializedName("items")
    val items: List<SearchItem>
)

data class SearchItem(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatar_url: String,

    @field:SerializedName("name")
    val name: String
)

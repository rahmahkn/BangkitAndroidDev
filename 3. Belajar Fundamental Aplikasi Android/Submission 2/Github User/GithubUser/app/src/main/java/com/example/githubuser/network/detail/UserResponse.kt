package com.example.githubuser.network.detail

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("location")
    val location: String,
)

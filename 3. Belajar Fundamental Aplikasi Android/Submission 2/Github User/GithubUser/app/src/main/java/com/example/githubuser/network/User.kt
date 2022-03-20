package com.example.githubuser.network

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String?,
    var name: String?,
    var avatar: Int?,
    var location: String?,
    var repository: String?,
    var company: String?,
    var followers: String?,
    var following: String?
) : Parcelable


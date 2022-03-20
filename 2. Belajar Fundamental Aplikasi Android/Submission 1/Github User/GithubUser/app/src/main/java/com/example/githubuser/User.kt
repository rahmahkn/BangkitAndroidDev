package com.example.githubuser

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
        }

    companion object : Parceler<User> {
        override fun User.write(parcel: Parcel, flags: Int) {
            parcel.writeString(username)
            parcel.writeString(name)
            parcel.writeValue(avatar)
            parcel.writeString(location)
            parcel.writeString(repository)
            parcel.writeString(company)
            parcel.writeString(followers)
            parcel.writeString(following)
        }

        override fun create(parcel: Parcel): User {
            return User(parcel)
        }
    }
}



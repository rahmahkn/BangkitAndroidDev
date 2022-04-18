package com.example.storyapp.network

import android.content.Context

class TokenPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "token_pref"
        private const val TOKEN = "token"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setToken(token: String) {
        val editor = preference.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String {
        return preference.getString(TOKEN, "").toString()
    }
}
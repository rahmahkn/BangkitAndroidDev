package com.example.storyapp.network

import android.content.Context

class TokenPreference(context: Context) {
    companion object {
        private const val PREFS_NAME1 = "token_pref"
        private const val TOKEN = "token"
    }

    private val preference1 = context.getSharedPreferences(PREFS_NAME1, Context.MODE_PRIVATE)

    fun setToken(token: String) {
        val editor = preference1.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String {
        return preference1.getString(TOKEN, "").toString()
    }
}
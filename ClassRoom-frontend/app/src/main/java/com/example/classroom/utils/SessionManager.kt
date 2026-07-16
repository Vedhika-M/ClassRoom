package com.example.classroom.utils

import android.content.Context

object SessionManager {
    private const val PREF_NAME = "classroom_pref"

    private const val KEY_TOKEN = "token"
    private const val KEY_ROLE = "role"
    private const val KEY_USERNAME = "username"
    private const val KEY_EMAIL = "email"

    private lateinit var preferences: android.content.SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun saveToken(token: String) {
        preferences.edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    fun getToken(): String? {
        return preferences.getString(
            KEY_TOKEN,
            null
        )
    }

    fun saveRole(role: String) {
        preferences.edit()
            .putString(KEY_ROLE, role)
            .apply()
    }

    fun getRole(): String? {
        return preferences.getString(
            KEY_ROLE,
            null
        )
    }

    fun saveUsername(username: String) {
        preferences.edit()
            .putString(KEY_USERNAME, username)
            .apply()
    }

    fun getUsername(): String? {
        return preferences.getString(
            KEY_USERNAME,
            null
        )
    }

    fun saveEmail(email: String) {
        preferences.edit()
            .putString(KEY_EMAIL, email)
            .apply()
    }

    fun clearSession() {
        preferences.edit()
            .clear()
            .apply()
    }
}
package com.example.bankingbuddy

import android.content.Context

object SessionManager {

    private const val PREF_NAME = "login_session"
    private const val KEY_LOGGED_IN = "logged_in"

    // SAVE LOGIN STATE
    fun setLoggedIn(context: Context, status: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_LOGGED_IN, status).apply()
    }

    // CHECK IF USER IS LOGGED IN
    fun isLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_LOGGED_IN, false)
    }

    // LOGOUT USER
    fun logout(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}

package com.example.bankingbuddy

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object UserManager {

    private const val PREF_NAME = "users_db"
    private const val KEY_USERS = "users"

    // ================= REGISTER USER =================
    fun registerUser(
        context: Context,
        name: String,
        surname: String,
        phone: String,
        email: String,
        username: String,
        password: String
    ): Boolean {

        val users = getUsers(context)

        // 🔥 FIXED LOOP (NO MORE ERROR)
        for (i in 0 until users.length()) {
            val u = users.getJSONObject(i)

            if (u.getString("username") == username) {
                return false // username already exists
            }
        }

        val newUser = JSONObject()
        newUser.put("name", name)
        newUser.put("surname", surname)
        newUser.put("phone", phone)
        newUser.put("email", email)
        newUser.put("username", username)
        newUser.put("password", password)

        users.put(newUser)

        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_USERS, users.toString()).apply()

        return true
    }

    // ================= LOGIN USER =================
    fun loginUser(context: Context, username: String, password: String): Boolean {

        val users = getUsers(context)

        // 🔥 FIXED LOOP (NO MORE ERROR)
        for (i in 0 until users.length()) {
            val u = users.getJSONObject(i)

            if (u.getString("username") == username &&
                u.getString("password") == password
            ) {
                return true
            }
        }

        return false
    }

    // ================= GET USERS =================
    private fun getUsers(context: Context): JSONArray {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_USERS, "[]")
        return JSONArray(json)
    }
}

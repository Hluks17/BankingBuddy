package com.example.bankingbuddy

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ExpenseManager {

    private const val PREF_NAME = "expenses"
    private const val KEY = "data"

    fun saveExpenses(context: Context, list: MutableList<Expense>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = Gson().toJson(list)
        editor.putString(KEY, json)
        editor.apply()
    }

    fun getExpenses(context: Context): MutableList<Expense> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY, null)

        return if (json != null) {
            val type = object : TypeToken<MutableList<Expense>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    //  NEW: RESET / CLEAR ALL EXPENSES
    fun clearExpenses(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}

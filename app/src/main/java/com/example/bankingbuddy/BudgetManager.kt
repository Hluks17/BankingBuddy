package com.example.bankingbuddy

import android.content.Context

object BudgetManager {

    private const val PREF_NAME = "BudgetPrefs"
    private const val KEY_BUDGET = "monthly_budget"

    fun saveBudget(context: Context, amount: Double) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putFloat(KEY_BUDGET, amount.toFloat()).apply()
    }

    fun getBudget(context: Context): Double {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(KEY_BUDGET, 0f).toDouble()
    }
}

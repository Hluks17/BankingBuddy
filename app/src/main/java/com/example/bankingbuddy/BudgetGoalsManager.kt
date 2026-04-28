package com.example.bankingbuddy

import android.content.Context

object BudgetGoalsManager {

    private const val PREF_NAME = "BudgetGoalsPrefs"

    private const val KEY_MIN = "min_goal"
    private const val KEY_MAX = "max_goal"

    fun saveGoals(context: Context, min: Double, max: Double) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putFloat(KEY_MIN, min.toFloat())
            .putFloat(KEY_MAX, max.toFloat())
            .apply()
    }

    fun getMinGoal(context: Context): Double {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(KEY_MIN, 0f).toDouble()
    }

    fun getMaxGoal(context: Context): Double {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(KEY_MAX, 0f).toDouble()
    }
}

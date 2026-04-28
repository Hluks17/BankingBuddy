package com.example.bankingbuddy

import android.content.Context

data class Account(
    var name: String,
    var type: String,
    var balance: Double
)

object AccountManager {

    private const val PREF_NAME = "accounts_pref"

    fun saveAccounts(context: Context, accounts: MutableList<Account>) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()

        val data = accounts.joinToString("|") {
            "${it.name},${it.type},${it.balance}"
        }

        editor.putString("accounts", data)
        editor.apply()
    }

    fun getAccounts(context: Context): MutableList<Account> {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val data = pref.getString("accounts", "") ?: ""

        val list = mutableListOf<Account>()

        if (data.isNotEmpty()) {
            val items = data.split("|")
            for (item in items) {
                val parts = item.split(",")
                if (parts.size == 3) {
                    list.add(
                        Account(
                            parts[0],
                            parts[1],
                            parts[2].toDouble()
                        )
                    )
                }
            }
        }

        return list
    }
}

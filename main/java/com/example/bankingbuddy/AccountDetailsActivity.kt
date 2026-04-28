package com.example.bankingbuddy

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AccountDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvBalance = findViewById<TextView>(R.id.tvBalance)
        val btnDeposit = findViewById<Button>(R.id.btnDeposit)

        val accounts = AccountManager.getAccounts(this)

        if (accounts.isNotEmpty()) {
            val acc = accounts.last()

            tvName.text = acc.name
            tvBalance.text = "R ${acc.balance}"
        }

        btnDeposit.setOnClickListener {
            val accountsList = AccountManager.getAccounts(this)

            if (accountsList.isNotEmpty()) {
                val acc = accountsList.last()

                acc.balance += 500   // dummy deposit

                AccountManager.saveAccounts(this, accountsList)

                tvBalance.text = "R ${acc.balance}"
            }
        }
    }
}

package com.example.bankingbuddy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SavingsActivity : AppCompatActivity() {

    lateinit var container: LinearLayout
    lateinit var dropContent: LinearLayout
    lateinit var transactionContainer: LinearLayout

    var isOpen = false
    var accountCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings)

        val account1 = findViewById<CardView>(R.id.account1)
        val addBtn = findViewById<Button>(R.id.btnAddAccount)

        container = findViewById(R.id.savingsLayout)
        dropContent = findViewById(R.id.dropContent)
        transactionContainer = findViewById(R.id.transactionContainer)

        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        account1.startAnimation(animation)

        // 🔽 DROPDOWN TOGGLE
        account1.setOnClickListener {
            isOpen = !isOpen
            dropContent.visibility = if (isOpen) View.VISIBLE else View.GONE

            if (isOpen) {
                loadDummyTransactions()
                loadRealExpenses() // ✅ NEW
            }
        }

        // ➕ ADD ACCOUNT
        addBtn.setOnClickListener {
            if (accountCount >= 4) {
                Toast.makeText(this, "Max 4 accounts reached", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startActivityForResult(
                Intent(this, CreateAccountActivity::class.java),
                1
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val name = data?.getStringExtra("name") ?: return
            val type = data.getStringExtra("type") ?: ""
            val balance = data.getDoubleExtra("balance", 0.0)

            addNewAccount(name, type, balance)
            accountCount++
        }
    }

    // 🏦 CREATE ACCOUNT CARD
    private fun addNewAccount(name: String, type: String, balance: Double) {

        val card = CardView(this)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 16, 0, 0)
        card.layoutParams = params
        card.radius = 20f
        card.cardElevation = 10f

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(30, 30, 30, 30)

        val title = TextView(this)
        title.text = name
        title.textSize = 16f

        val subtitle = TextView(this)
        subtitle.text = "$type • R $balance"

        val deleteBtn = Button(this)
        deleteBtn.text = "Delete Account"
        deleteBtn.setBackgroundColor(0xFFFF4444.toInt())

        deleteBtn.setOnClickListener {
            container.removeView(card)
            accountCount--
        }

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(deleteBtn)

        card.addView(layout)
        container.addView(card)
    }

    // 💰 DUMMY TRANSACTIONS
    private fun loadDummyTransactions() {

        transactionContainer.removeAllViews()

        val transactions = listOf(
            "Transfer to John - R500 | 12 Apr",
            "Groceries - R320 | 10 Apr",
            "Fuel - R600 | 09 Apr",
            "Netflix - R150 | 08 Apr",
            "Electricity - R900 | 07 Apr",
            "Uber - R120 | 06 Apr",
            "Coffee - R45 | 05 Apr",
            "Salary +8000 | 01 Apr",
            "Gym - R300 | 30 Mar",
            "Clothes - R1200 | 29 Mar",
            "Takeout - R200 | 28 Mar",
            "Insurance - R700 | 27 Mar",
            "Airtime - R100 | 26 Mar",
            "Transfer from Mike +R1000 | 25 Mar",
            "Snacks - R60 | 24 Mar"
        )

        for (t in transactions) {
            val tv = TextView(this)
            tv.text = t
            tv.setPadding(12, 12, 12, 12)
            tv.textSize = 14f

            transactionContainer.addView(tv)
        }
    }

    // ✅ REAL EXPENSES FROM DATABASE
    private fun loadRealExpenses() {

        val expenses = ExpenseManager.getExpenses(this)

        if (expenses.isEmpty()) return

        val title = TextView(this)
        title.text = "Your Expenses"
        title.textSize = 16f
        title.setPadding(0, 20, 0, 10)

        transactionContainer.addView(title)

        for (e in expenses) {

            val tv = TextView(this)
            tv.text = "${e.title} - R${e.amount} (${e.category})"
            tv.setPadding(12, 12, 12, 12)

            transactionContainer.addView(tv)
        }
    }
}

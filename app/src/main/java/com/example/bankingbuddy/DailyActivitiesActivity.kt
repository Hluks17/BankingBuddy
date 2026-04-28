package com.example.bankingbuddy

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DailyActivitiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_activities)

        // ===== LAYOUT SECTIONS =====
        val airtimeLayout = findViewById<LinearLayout>(R.id.airtimeLayout)
        val voucherLayout = findViewById<LinearLayout>(R.id.voucherLayout)
        val cashLayout = findViewById<LinearLayout>(R.id.cashLayout)
        val billLayout = findViewById<LinearLayout>(R.id.billLayout)
        val budgetLayout = findViewById<LinearLayout>(R.id.budgetLayout)
        val statementLayout = findViewById<LinearLayout>(R.id.statementLayout)

        // ===== BUTTONS =====
        val btnAirtime = findViewById<Button>(R.id.btnAirtime)
        val btnVoucher = findViewById<Button>(R.id.btnVoucher)
        val btnCash = findViewById<Button>(R.id.btnCash)
        val btnBills = findViewById<Button>(R.id.btnBills)
        val btnBudget = findViewById<Button>(R.id.btnBudget)
        val btnStatement = findViewById<Button>(R.id.btnStatement)

        // ===== BUDGET VIEWS =====
        val budgetInput = findViewById<EditText>(R.id.etBudget)
        val minGoalInput = findViewById<EditText>(R.id.etMinGoal)
        val maxGoalInput = findViewById<EditText>(R.id.etMaxGoal)

        val totalSpentText = findViewById<TextView>(R.id.tvTotalSpent)
        val remainingText = findViewById<TextView>(R.id.tvRemaining)
        val warningText = findViewById<TextView>(R.id.tvWarning)

        // ===== LOAD SAVED BUDGET =====
        val savedBudget = BudgetManager.getBudget(this)

        if (savedBudget > 0) {
            budgetInput.setText(savedBudget.toString())

            val expenses = ExpenseManager.getExpenses(this)

            var totalSpent = 0.0
            for (exp in expenses) {
                totalSpent += exp.amount
            }

            val remaining = savedBudget - totalSpent

            totalSpentText.text = "Total Spent: R $totalSpent"
            remainingText.text = "Remaining: R $remaining"

            if (remaining < 0) {
                warningText.text = "⚠ You exceeded your budget!"
            } else {
                warningText.text = "✔ Budget under control"
            }
        }

        // ===== TOGGLE DROPDOWNS =====
        btnAirtime.setOnClickListener { toggleView(airtimeLayout) }
        btnVoucher.setOnClickListener { toggleView(voucherLayout) }
        btnCash.setOnClickListener { toggleView(cashLayout) }
        btnBills.setOnClickListener { toggleView(billLayout) }
        btnBudget.setOnClickListener { toggleView(budgetLayout) }
        btnStatement.setOnClickListener { toggleView(statementLayout) }

        // ===== AIRTIME =====
        findViewById<Button>(R.id.buyAirtimeBtn).setOnClickListener {
            Toast.makeText(this, "Airtime purchased successfully ✔", Toast.LENGTH_SHORT).show()
        }

        // ===== VOUCHER =====
        findViewById<Button>(R.id.buyVoucherBtn).setOnClickListener {
            Toast.makeText(this, "Voucher purchased ✔", Toast.LENGTH_SHORT).show()
        }

        // ===== CASH SEND =====
        findViewById<Button>(R.id.sendCashBtn).setOnClickListener {
            Toast.makeText(this, "Cash sent successfully ✔", Toast.LENGTH_SHORT).show()
        }

        // ===== BILLS =====
        findViewById<Button>(R.id.payBillBtn).setOnClickListener {
            Toast.makeText(this, "Bill paid successfully ✔", Toast.LENGTH_SHORT).show()
        }

        // ===== REAL BUDGET SYSTEM (UPGRADED) =====
        findViewById<Button>(R.id.trackBudgetBtn).setOnClickListener {

            val budgetText = budgetInput.text.toString()
            val minText = minGoalInput.text.toString()
            val maxText = maxGoalInput.text.toString()

            if (budgetText.isEmpty()) {
                Toast.makeText(this, "Enter budget first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val budget = budgetText.toDouble()
            val minGoal = if (minText.isEmpty()) 0.0 else minText.toDouble()
            val maxGoal = if (maxText.isEmpty()) budget else maxText.toDouble()

            // SAVE MAIN BUDGET
            BudgetManager.saveBudget(this, budget)

            // GET EXPENSES
            val expenses = ExpenseManager.getExpenses(this)

            var totalSpent = 0.0
            for (exp in expenses) {
                totalSpent += exp.amount
            }

            val remaining = budget - totalSpent

            // DISPLAY
            totalSpentText.text = "Total Spent: R $totalSpent"
            remainingText.text = "Remaining: R $remaining"

            //  SMART MONITORING
            when {
                totalSpent < minGoal -> {
                    warningText.text = "ℹ You are below your spending goal"
                }
                totalSpent in minGoal..maxGoal -> {
                    warningText.text = " You are within safe range"
                }
                totalSpent > maxGoal -> {
                    warningText.text = " You exceeded your limit!"
                }
            }

            Toast.makeText(this, "Budget Saved & Monitored ✔", Toast.LENGTH_SHORT).show()
        }

        // ===== STATEMENT =====
        btnStatement.setOnClickListener {
            Toast.makeText(this, "Statement Downloaded ✔", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleView(view: View) {
        view.visibility =
            if (view.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }
}

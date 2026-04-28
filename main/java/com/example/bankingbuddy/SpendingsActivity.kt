package com.example.bankingbuddy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData

class SpendingsActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spendings)

        val addBtn = findViewById<Button>(R.id.btnAddExpense)
        val resetBtn = findViewById<Button>(R.id.btnResetExpenses)

        // ===== ADD EXPENSE =====
        addBtn.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        // ===== RESET EXPENSES =====
        resetBtn.setOnClickListener {

            ExpenseManager.clearExpenses(this)

            Toast.makeText(this, "All expenses cleared ✔", Toast.LENGTH_SHORT).show()

            loadData() // refresh chart + list
        }
    }


    private fun loadData() {

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        val container = findViewById<LinearLayout>(R.id.expenseContainer)
        val totalText = findViewById<TextView>(R.id.totalText)

        val expenses = ExpenseManager.getExpenses(this)

        container.removeAllViews()

        var total = 0.0
        val categoryMap = HashMap<String, Float>()

        for (exp in expenses) {

            total += exp.amount

            val card = LinearLayout(this)
            card.orientation = LinearLayout.VERTICAL
            card.setPadding(20, 20, 20, 20)

            val title = TextView(this)
            title.text = exp.title
            title.textSize = 16f

            val details = TextView(this)
            details.text = "R ${exp.amount} • ${exp.category}"

            card.addView(title)
            card.addView(details)

            container.addView(card)

            val current = categoryMap[exp.category] ?: 0f
            categoryMap[exp.category] = current + exp.amount.toFloat()
        }

        totalText.text = "Total: R $total"

        // CHART
        val entries = ArrayList<PieEntry>()

        for ((key, value) in categoryMap) {
            entries.add(PieEntry(value, key))
        }

        if (entries.isEmpty()) {
            entries.add(PieEntry(1f, "No Data"))
        }

        val dataSet = PieDataSet(entries, "Expenses")

        dataSet.colors = listOf(
            Color.parseColor("#1B6CA8"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#FF5252"),
            Color.parseColor("#FFC107")
        )

        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.animateY(1200)
        pieChart.invalidate()
    }
}


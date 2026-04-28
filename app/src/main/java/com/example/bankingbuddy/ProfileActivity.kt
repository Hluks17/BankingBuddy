package com.example.bankingbuddy

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupCard(R.id.cardContact, R.id.contactBody)
        setupCard(R.id.cardEmployment, R.id.employmentBody)
        setupCard(R.id.cardIncome, R.id.incomeBody)
        setupCard(R.id.cardPersonal, R.id.personalBody)
    }

    private fun setupCard(cardId: Int, contentId: Int) {
        val card = findViewById<LinearLayout>(cardId)
        val content = findViewById<LinearLayout>(contentId)

        card.setOnClickListener {
            content.visibility =
                if (content.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }
}

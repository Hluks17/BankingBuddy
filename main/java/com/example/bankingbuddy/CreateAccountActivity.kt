package com.example.bankingbuddy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val name = findViewById<EditText>(R.id.etAccountName)
        val balance = findViewById<EditText>(R.id.etBalance)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupType)
        val btnCreate = findViewById<Button>(R.id.btnCreate)

        btnCreate.setOnClickListener {

            val accName = name.text.toString().trim()
            val accBalance = balance.text.toString().trim()

            // ✅ VALIDATION
            if (accName.isEmpty() || accBalance.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ CHECK RADIO SELECTED
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Select account type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRadio = findViewById<RadioButton>(selectedId)
            val type = selectedRadio.text.toString()

            val balanceValue = accBalance.toDoubleOrNull()
            if (balanceValue == null) {
                Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ SAVE USING YOUR SYSTEM
            val accounts = AccountManager.getAccounts(this)

            accounts.add(
                Account(
                    accName,
                    type,
                    balanceValue
                )
            )

            AccountManager.saveAccounts(this, accounts)

            // ✅ SEND DATA BACK TO SAVINGS SCREEN
            val intent = Intent()
            intent.putExtra("name", accName)
            intent.putExtra("type", type)
            intent.putExtra("balance", balanceValue)

            setResult(Activity.RESULT_OK, intent)

            Toast.makeText(this, "Account Created ✅", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}

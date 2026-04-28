package com.example.bankingbuddy

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val amount = findViewById<EditText>(R.id.editAmount)
        val description = findViewById<EditText>(R.id.editDescription)
        val category = findViewById<Spinner>(R.id.spinnerCategory)

        //  NEW FIELDS
        val startDate = findViewById<EditText>(R.id.editStartDate)
        val startTime = findViewById<EditText>(R.id.editStartTime)
        val endTime = findViewById<EditText>(R.id.editEndTime)

        val btnSave = findViewById<Button>(R.id.btnSaveExpense)
        val btnPick = findViewById<Button>(R.id.btnPickImage)
        val imgPreview = findViewById<ImageView>(R.id.imgPreview)

        // =========================
        //  DATE PICKER
        // =========================
        startDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, y, m, d ->
                    val selectedDate = "$y-${m + 1}-$d"
                    startDate.setText(selectedDate)
                },
                year, month, day
            )

            datePicker.show()
        }

        // =========================
        //  START TIME PICKER
        // =========================
        startTime.setOnClickListener {
            val calendar = Calendar.getInstance()

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(
                this,
                { _, h, m ->
                    val selectedTime = String.format("%02d:%02d", h, m)
                    startTime.setText(selectedTime)
                },
                hour, minute, true
            )

            timePicker.show()
        }

        // =========================
        //  END TIME PICKER
        // =========================
        endTime.setOnClickListener {
            val calendar = Calendar.getInstance()

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(
                this,
                { _, h, m ->
                    val selectedTime = String.format("%02d:%02d", h, m)
                    endTime.setText(selectedTime)
                },
                hour, minute, true
            )

            timePicker.show()
        }

        // =========================
        // SPINNER DATA
        // =========================
        val categories = arrayOf("Food", "Transport", "Bills", "Entertainment", "Other")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )
        category.adapter = adapter

        // =========================
        //  PICK IMAGE
        // =========================
        btnPick.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        // =========================
        //  SAVE EXPENSE
        // =========================
        btnSave.setOnClickListener {

            val amtText = amount.text.toString().trim()
            val desc = description.text.toString().trim()
            val cat = category.selectedItem.toString()

            //  NEW VALUES
            val date = startDate.text.toString().trim()
            val startT = startTime.text.toString().trim()
            val endT = endTime.text.toString().trim()

            if (amtText.isEmpty() || desc.isEmpty() || date.isEmpty() || startT.isEmpty() || endT.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amt = amtText.toDoubleOrNull()

            if (amt == null) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val expenses = ExpenseManager.getExpenses(this)

            expenses.add(
                Expense(
                    desc,
                    cat,
                    amt,
                    imageUri?.toString() ?: "",
                    date,
                    startT,
                    endT
                )
            )

            ExpenseManager.saveExpenses(this, expenses)

            Toast.makeText(this, "Expense Saved ", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // =========================
    //  IMAGE RESULT
    // =========================
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data

            val imgPreview = findViewById<ImageView>(R.id.imgPreview)
            imgPreview.setImageURI(imageUri)
        }
    }
}

package com.example.bankingbuddy

data class Expense(
    val title: String,
    val category: String,
    val amount: Double,
    val imageUri: String,
    val startDate: String,
    val startTime: String,
    val endTime: String
)

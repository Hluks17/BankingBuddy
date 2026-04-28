package com.example.bankingbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val name = findViewById<EditText>(R.id.etName)
        val surname = findViewById<EditText>(R.id.etSurname)
        val phone = findViewById<EditText>(R.id.etPhone)
        val email = findViewById<EditText>(R.id.etEmail)
        val username = findViewById<EditText>(R.id.etUsername)
        val password = findViewById<EditText>(R.id.etPassword)
        val registerBtn = findViewById<Button>(R.id.btnRegister)
        val loginText = findViewById<TextView>(R.id.tvLogin)

        registerBtn.setOnClickListener {

            val userName = name.text.toString().trim()
            val userSurname = surname.text.toString().trim()
            val userPhone = phone.text.toString().trim()
            val userEmail = email.text.toString().trim()
            val userUsername = username.text.toString().trim()
            val userPassword = password.text.toString().trim()

            when {
                userName.isEmpty() || userSurname.isEmpty() ||
                        userPhone.isEmpty() || userEmail.isEmpty() ||
                        userUsername.isEmpty() || userPassword.isEmpty() -> {

                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }

                !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches() -> {
                    Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
                }

                userPhone.length < 10 -> {
                    Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
                }

                userUsername.length > 15 -> {
                    Toast.makeText(this, "Username too long", Toast.LENGTH_SHORT).show()
                }

                userPassword.length < 8 -> {
                    Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                }

                else -> {

                    val success = UserManager.registerUser(
                        this,
                        userName,
                        userSurname,
                        userPhone,
                        userEmail,
                        userUsername,
                        userPassword
                    )

                    if (success) {
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}

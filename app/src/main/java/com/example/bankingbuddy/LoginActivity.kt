package com.example.bankingbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.etUsername)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val loginBtn = findViewById<Button>(R.id.btnLogin)

        loginBtn.setOnClickListener {

            val inputUsername = usernameInput.text.toString().trim()
            val inputPassword = passwordInput.text.toString().trim()

            when {
                inputUsername.isEmpty() || inputPassword.isEmpty() -> {
                    Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show()
                }

                !UserManager.loginUser(this, inputUsername, inputPassword) -> {
                    Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                }

                else -> {

                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                    //  SAVE LOGIN SESSION (IMPORTANT FOR LOGOUT FEATURE)
                    SessionManager.setLoggedIn(this, true)

                    val intent = Intent(this, Homepage::class.java)

                    //  PREVENT BACK BUTTON FROM RETURNING TO LOGIN
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}

package com.example.bankingbuddy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.content.Intent
import android.widget.Button

class Homepage : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        //  OPTIONAL: BLOCK ACCESS IF NOT LOGGED IN
        if (!SessionManager.isLoggedIn(this)) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)

        // ===== EXISTING BUTTONS =====
        val btnInvestments = findViewById<Button>(R.id.btnInvestments)
        val btnSpendings = findViewById<Button>(R.id.btnSpendings)
        val btnSavings = findViewById<Button>(R.id.btnSavings)
        val btnDailyActivities = findViewById<Button>(R.id.btnDailyActivities)

        btnInvestments.setOnClickListener {
            startActivity(Intent(this, InvestmentsActivity::class.java))
        }

        btnSpendings.setOnClickListener {
            startActivity(Intent(this, SpendingsActivity::class.java))
        }

        btnSavings.setOnClickListener {
            startActivity(Intent(this, SavingsActivity::class.java))
        }

        btnDailyActivities.setOnClickListener {
            startActivity(Intent(this, DailyActivitiesActivity::class.java))
        }

        // ===== TOOLBAR =====
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // ===== NAVIGATION MENU =====
        navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.nav_home -> {
                    toast("Home")
                }

                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }

                R.id.nav_contact -> {
                    startActivity(Intent(this, ContactActivity::class.java))
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }

                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }

                //  FIXED LOGOUT (REAL FUNCTION)
                R.id.nav_logout -> {

                    SessionManager.logout(this)

                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)

                    //  CLEAR BACK STACK (IMPORTANT)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                    finish()
                }
            }

            drawerLayout.closeDrawers()
            true
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

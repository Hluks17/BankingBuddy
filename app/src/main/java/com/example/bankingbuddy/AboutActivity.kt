package com.example.bankingbuddy

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val layout = findViewById<LinearLayout>(R.id.rootLayout)

        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        layout.startAnimation(animation)
    }
}

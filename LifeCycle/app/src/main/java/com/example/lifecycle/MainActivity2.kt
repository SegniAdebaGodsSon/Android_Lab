package com.example.lifecycle

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // get info passed down
        val extras = intent.extras?:return

        val user = extras.getString("user")
        val age = extras.getInt("age")

        val display = findViewById<TextView>(R.id.display)
        display.text = "User: $user age: $age"
    }
}
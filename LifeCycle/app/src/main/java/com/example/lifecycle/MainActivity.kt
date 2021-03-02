package com.example.lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonOne = findViewById<Button>(R.id.buttonOne)
        buttonOne.setOnClickListener{
//            Toast.makeText(this, "hello world", Toast.LENGTH_LONG).show()
            val i = Intent(applicationContext, MainActivity2::class.java)  // explicit intent ... applicationContext or 'this'
            // how to send info to another activity
            i.putExtra("user", "Segni")
            i.putExtra("age", 22)
            startActivity(i)
        }
    }
}
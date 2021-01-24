package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        val myInput = findViewById<EditText>(R.id.myInput)
        val myBtn = findViewById<Button>(R.id.myButton)
        val myDisplay = findViewById<TextView>(R.id.display)

        myBtn.setOnClickListener{
            myDisplay.text = myInput.text.toString()
        }
    }
}
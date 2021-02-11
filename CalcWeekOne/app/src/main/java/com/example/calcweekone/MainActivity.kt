package com.example.calcweekone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstnumber =findViewById<EditText>(R.id.firstnumber)
        val secondnumber =findViewById<EditText>(R.id.secondnumber)


        val addbutton =findViewById<Button>(R.id.addbutton)
        val subtractbutton =findViewById<Button>(R.id.subtractbutton)
        val multiplybutton =findViewById<Button>(R.id.multiplybutton)
        val dividebutton =findViewById<Button>(R.id.dividebutton)

        val resultdisplay =findViewById<TextView>(R.id.resultdisplay)

        addbutton.setOnClickListener {

            resultdisplay.text = (firstnumber.text.toString().toInt() + secondnumber.text.toString()
                    .toInt()).toString()

        }
        subtractbutton.setOnClickListener {
            resultdisplay.text = (firstnumber.text.toString().toInt() - secondnumber.text.toString()
                    .toInt()).toString()
        }

        multiplybutton.setOnClickListener {

            resultdisplay.text = (firstnumber.text.toString().toInt() * secondnumber.text.toString()
                    .toInt()).toString()
        }

        dividebutton.setOnClickListener {
            resultdisplay.text =
                    (firstnumber.text.toString().toFloat() / secondnumber.text.toString()
                            .toFloat()).toString()
        }

    }
}
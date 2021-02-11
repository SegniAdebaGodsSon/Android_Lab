package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fun basic(rightNum:String?, leftNum:String?, op:String?): Double =
                when (op) {
                "+" -> {
                    (rightNum?.toDouble()!! + leftNum?.toDouble()!!)
                }
                "-" -> {
                    (rightNum?.toDouble()!! - leftNum?.toDouble()!!)
                }
                "*" -> {
                    (rightNum?.toDouble()!! * leftNum?.toDouble()!!)
                }
                "^" -> {
                    ((rightNum?.toDouble()!!).pow(leftNum?.toDouble()!!))
                }
                else -> {
                    (rightNum?.toDouble()!! / leftNum?.toDouble()!!)
                }
            }


        fun elemInside(mainString:String?, listCheck:List<String>):Boolean {
            for (ops in listCheck) {
                if (mainString?.contains(ops)!!){
                    return true
                }
            }
            return false
        }

        fun getOpIndex(query: String?, operations:List<String>):Array<Int> {
            var allIndex:Array<Int> = arrayOf()
            var dupQuery = query
            while (elemInside(dupQuery, operations)) {
                for (op in operations) {
                    if (dupQuery?.contains(op)!!) {
                        allIndex = allIndex.plusElement(dupQuery.indexOf(op))
                        dupQuery = dupQuery.substring(0, dupQuery.indexOf(op)) + '1' + dupQuery.substring(dupQuery.indexOf(op) + 1)
                    }
                }
            }

            allIndex.sort()
            return allIndex
        }


        fun parseSimple(query:String?):Double? {
            val operations = listOf("^", "/", "*", "-", "+")
            var allIndex: Array<Int> = arrayOf()

            var calcQuery = query
            while (elemInside(calcQuery, operations) && (allIndex.size > 1 || if (allIndex.isEmpty()) true else allIndex[0] != 0)) {
                for (op in operations) {
                    calcQuery = calcQuery?.replace("-+", "-")
                    calcQuery = calcQuery?.replace("--", "+")
                    calcQuery = calcQuery?.replace("+-", "-")
                    allIndex = getOpIndex(calcQuery, operations)
                    if (calcQuery?.contains(op)!!) {
                        val indexOp = calcQuery.indexOf(op)
                        val indexIndexOp = allIndex.indexOf(indexOp)
                        val rightIndex =
                                if (indexIndexOp == allIndex.lastIndex) calcQuery.lastIndex else allIndex[indexIndexOp + 1]
                        val leftIndex = if (indexIndexOp == 0) 0 else allIndex[indexIndexOp - 1]
                        val rightNum =
                                calcQuery.slice(if (rightIndex == calcQuery.lastIndex) indexOp + 1..rightIndex else indexOp + 1 until rightIndex)
                        val leftNum = calcQuery.slice(if (leftIndex == 0) leftIndex until indexOp else leftIndex + 1  until indexOp)
                        val result = basic(leftNum, rightNum, op)
                        calcQuery = (if (leftIndex != 0) calcQuery.substring(
                                0,
                                leftIndex + 1
                        ) else "") + result.toString() + (if(rightIndex != calcQuery.lastIndex) calcQuery.substring(
                                rightIndex..calcQuery.lastIndex
                        ) else "")
                    }
                }
            }
            return calcQuery?.toDouble()
        }

        fun getAllIndex(query: String?, char: Char, replacement:String="%"):List<Int> {
            var myQuery = query
            var indexes:List<Int> = listOf()
            while (char in myQuery!!) {
                val indexFinded = myQuery.indexOf(char)
                indexes = indexes.plus(indexFinded)
                myQuery = myQuery.substring(0 until indexFinded) + replacement + myQuery.substring(indexFinded+1..myQuery.lastIndex)
            }
            return indexes
        }

        fun getBrackets(query: String?): List<Int> {
            val allEndIndex = getAllIndex(query, ')')
            val allStartIndex = getAllIndex(query, '(')
            val firstIndex = allStartIndex[0]
            for (endIndex in allEndIndex) {
                val inBrac = query?.substring(firstIndex+1 until endIndex)
                val inBracStart = getAllIndex(inBrac, '(')
                val inBracEnd = getAllIndex(inBrac, ')')
                if (inBracStart.size == inBracEnd.size){
                    return listOf(firstIndex, endIndex)
                }
            }
            return listOf(-1, -1)
        }

        fun evaluate(query:String?):Double? {
            var calcQuery = query
            var index = 0;
            // Check if brackets are present
            while (calcQuery?.contains('(')!! && index < 200){
                val startBrackets = getBrackets(calcQuery)[0]
                val endBrackets = getBrackets(calcQuery)[1]
                val inBrackets = calcQuery!!.slice(startBrackets+1 until endBrackets)
                if ('(' in inBrackets && ')' in inBrackets){
                    val inBracValue = evaluate(inBrackets)
                    calcQuery = calcQuery!!.substring(0, startBrackets) + inBracValue.toString() + (if(endBrackets == calcQuery!!.lastIndex) "" else calcQuery!!.substring(endBrackets+1..calcQuery!!.lastIndex))
                }
                else {
                    val inBracValue = parseSimple(inBrackets)
                    calcQuery = calcQuery!!.substring(0, startBrackets) + inBracValue.toString() + (if(endBrackets == calcQuery!!.lastIndex) "" else calcQuery!!.substring(endBrackets+1..calcQuery!!.lastIndex))
                }
                index++
            }

            return parseSimple(calcQuery)
        }


        val one = findViewById<Button>(R.id.one)
        val two = findViewById<Button>(R.id.two)
        val three = findViewById<Button>(R.id.three)
        val four = findViewById<Button>(R.id.four)
        val five = findViewById<Button>(R.id.five)
        val six = findViewById<Button>(R.id.six)
        val seven = findViewById<Button>(R.id.seven)
        val eight = findViewById<Button>(R.id.eight)
        val nine = findViewById<Button>(R.id.nine)
        val zero = findViewById<Button>(R.id.zero)

        val openingBracket = findViewById<Button>(R.id.openingBracket)
        val closingBracket = findViewById<Button>(R.id.closingBracket)

        val add = findViewById<Button>(R.id.add)
        val subtract = findViewById<Button>(R.id.subtract)
        val multiply = findViewById<Button>(R.id.multiply)
        val divide = findViewById<Button>(R.id.divide)
        val power = findViewById<Button>(R.id.power)

        val equals = findViewById<Button>(R.id.equals)
        val clearAll = findViewById<Button>(R.id.clear_all)
        val clear = findViewById<Button>(R.id.clear)

        val inputArea = findViewById<TextView>(R.id.result)

        zero.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "0"
            inputArea.text = prev;
        }
        one.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "1"
            inputArea.text = prev;

        }

        two.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "2"
            inputArea.text = prev;

        }
        three.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "3"
            inputArea.text = prev;

        }
        four.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "4"
            inputArea.text = prev;

        }
        five.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "5"
            inputArea.text = prev;
        }
        six.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "6"
            inputArea.text = prev;
        }
        seven.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "7"
            inputArea.text = prev;
        }
        eight.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "8"
            inputArea.text = prev;
        }
        nine.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "9"
            inputArea.text = prev;
        }

        power.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "^"
            inputArea.text = prev;
        }

        add.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "+"
            inputArea.text = prev;
        }
        subtract.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "-"
            inputArea.text = prev;
        }
        multiply.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "*"
            inputArea.text = prev;
        }
        divide.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "/"
            inputArea.text = prev;
        }

        openingBracket.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += "("
            inputArea.text = prev;
        }


        closingBracket.setOnClickListener {
            var prev = inputArea.text.toString()
            prev += ")"
            inputArea.text = prev;
        }


        equals.setOnClickListener{
            val equation = inputArea.text.toString()
            try{
                val ans = evaluate(equation)
                inputArea.text = ans.toString()
            }catch(e: NumberFormatException){
                Toast.makeText(this,"Invalid input!",Toast.LENGTH_SHORT).show();
            }
        }


        clearAll.setOnClickListener{
            inputArea.text = ""
        }

        clear.setOnClickListener{
            var prev = inputArea.text.toString()
            prev = prev.dropLast(1)
            inputArea.text = prev
        }

    }

}
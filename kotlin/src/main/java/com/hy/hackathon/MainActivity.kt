package com.hy.hackathon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "@MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val student = Student("huangye", true, 99)
        mTextView.text = student.toString()

        method3()

        var p = add(x = 1, y = 2)
        var p1 = add(y = 1, x = 2)

        var p2 = add(3, 4)

        var p3 = add(2)
        var p4 = sum(1, 3, 4, 5, 2, 6, 6, 7, 7, 8, 8, 8, 8, 8)

        var array = intArrayOf(1, 3, 4, 5, 6, 7, 8, 9)
        var p5 = sum(*array)

        lambda()
    }


    fun method1() {
        var time = Pair(1, 1)
        val message = "${time.first}月${time.second}日"
        Log.d(TAG, message)

        time = Pair(2, 2)
        Log.d(TAG, message)

        var i = 15
        val message2 = "num: $i"
        Log.d(TAG, message2)
        i = 20
        Log.d(TAG, message2)
    }

    fun method2() {
        val list = arrayListOf<String>("hello", "world", "ni hao")
        for (str in list) {
            Log.d(TAG, str)
        }
        val list2 = arrayOf(1, 2, 3)

        val stations = Array(20, { "1" })
        for (station in stations) {
//            Log.d(TAG, station)
        }

        val oneToHundred = Array(10, { i -> i + 1 })
        oneToHundred[1] = 999
        for (i in oneToHundred) {
            Log.d(TAG, i.toString())
        }
        val num = oneToHundred.count { index ->
            index % 2 == 0
        }
        Log.d(TAG, num.toString())
    }

    fun method3() {
        for (i in 1..5) {
            Log.d(TAG, "第$i")
        }

        var times = 0
        var total = 0

        while (total < 1996_0317) {
            times++
            total += times
        }
        Log.d(TAG, "要加到${times}次")

        var a = 5
        var b = 4

        val c = if (a > b) "hello" else -1
        when (c) {
            is String -> {
                Log.d(TAG, "String")
            }
            is Int -> {
                Log.d(TAG, "Int")
            }
        }


        val pop = 10
        when (pop) {
            in 1..2 -> {
                Log.d(TAG, "1")
            }
            10, 11, 12 -> {
                Log.d(TAG, "10")
            }
            else -> {
                Log.d(TAG, "else")

            }
        }
    }

    fun add(x: Int, y: Int = 0): Int {
        return x + y
    }

    fun sum(vararg x: Int): Int {
        var total = 0;
        for (i in x) {
            total += i
        }
        return total
    }

    fun lambda() {
        val arr1 = arrayOf(1, 2, 3, 4, 5)
        val arr2 = arr1.map { i ->
            when (i) {
                in 1..3 -> {
                    "normal${i}"
                }
                else -> {
                    "high${i}"
                }
            }
        }
        for (s in arr2) {
            Log.d(TAG, s)
        }

        val arr3 = arr1.map { "第${it}" }
        for (s in arr3) {
            Log.d(TAG, s)
        }

        var total = 0
        var times = 0
        arr1.filter { it % 2 == 0 }.forEach {
            total += it
            times++
        }

        var student = Student("huangye", true, 99)

        var cc = 1 shr 2

    }

    fun multi(x: Int, y: Int) = x * y

    data class Student(
            val name: String,
            val sex: Boolean,
            var grade: Int)

    fun method4() {
        var i  = 10
        val cal = Runnable {
            i++
        }

        val thread1 = Thread(cal)
        thread1.start()

    }

}

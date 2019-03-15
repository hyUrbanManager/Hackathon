package com.hy.hackathon.kotlin

data class Customer(val name: String, val age: Int)

fun main() {
    val list = listOf("a", "b", "c")
    val list2 = ArrayList<String>()

    val p: String by lazy {
        ""
    }

    var x: String? = null
    val len = x?.length

    x?.let {

    }

}


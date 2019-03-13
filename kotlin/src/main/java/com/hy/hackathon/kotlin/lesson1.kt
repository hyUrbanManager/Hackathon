package com.hy.hackathon.kotlin

fun main(args: Array<String>) {
    val str = "hello, world \$"
    println(str)
    println(sum(getStringLength(str), 2))

}

fun sum(a: Int, b: Int): Int {
    print("sum of $a + $b is ${a + b}")
    return a + b
}

fun max(a: Int, b: Int): Int? {
    return if (a > b) {
        a
    } else if (a < b) {
        b
    } else {
        null
    }
}

fun getStringLength(obj: Any): Int {
    if (obj is String) {
        return obj.length
    }
    return 0
}
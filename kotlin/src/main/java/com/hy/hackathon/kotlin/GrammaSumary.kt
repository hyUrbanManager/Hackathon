package com.hy.hackathon.kotlin

fun main(args: Array<String>) {
    val apple = "apple"
    val str = "this is $apple"
    val str2 = "those are ${apple + "s"}"
    describeNullRangeParam(arg1 = 1)
    describeNullRangeParam(0)
    describeLambda()
    describeIterator()
}

fun describeNullRangeParam(arg1: Int, arg2: Int? = null, arg3: Any? = ""): Float? {
    val items = listOf("apple", "banana", "orange")
    val range = 10
    for (item in items) {
        if (item.length in 2..range) {
            print(item.length)
        }
        if (item.length !in 0..5) {
            print(item.length)
        }
    }
    for (i in 0..10 step 2) {
        print(i)
    }
    for (i in 10 downTo 2) {
        print(i)
    }
    for (i in 0 until 10) {
        print(i)
    }
    println()
    return null
}

fun describeWhen(obj: Any?): Float {
    when (obj) {
        null -> return 1F
        1 -> {
        }
        is Long -> return 2F
        !is String -> return 3F
        else -> return 5F
    }
    return 0F
}

fun describeLambda() {
    val fruits = listOf("banana", "orange", "pairs")
    fruits.filter { it.startsWith("o") }
            .sortedBy { it.length }
            .map { it.toUpperCase() }
            .forEach { print(it) }
}

fun describeIterator() {
    val map = mapOf(Pair("k", "v"), Pair("k2", "v2"), Pair("k3", "v3"))
    println("map key is k, value: ${map["k"]}")
    for ((k, v) in map) {
        print("key: $k, value: $v")
    }
    val cnt = map.count { entry -> entry.key.length > 1 }
}

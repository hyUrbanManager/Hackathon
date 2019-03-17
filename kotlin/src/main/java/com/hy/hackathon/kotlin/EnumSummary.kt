package com.hy.hackathon.kotlin

fun main() {
    println("color red: ${Color.RED.r}, ${Color.RED.g}, ${Color.RED.b}")
}

enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
}
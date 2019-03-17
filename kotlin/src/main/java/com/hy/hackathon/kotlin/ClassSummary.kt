package com.hy.hackathon.kotlin

data class Customer(val name: String, val age: Int)

fun main() {
    val customer = Customer("hy", 23)

    val a = A("hy")
    val b = B("xld")
    val b2 = B("xld", 110)
}

open class A(var name: String) {
    init {
        println("A init.")
    }
}

class B(var country: String) : A("b name") {
    init {
        println("B init.")
    }

    constructor(country: String, countryCode: Int) : this(country) {
        println("B constructor.")
    }
}

class C private constructor(fistName: String) {

}

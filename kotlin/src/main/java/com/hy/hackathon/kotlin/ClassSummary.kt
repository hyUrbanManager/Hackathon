package com.hy.hackathon.kotlin

data class Customer(val name: String, val age: Int)

fun main() {
    val customer = Customer("hy", 23)

    val a = A("hy")
    val b = B("xld")
    val b2 = B("xld", 110)

    b2.action1()

    val c = C("h")
    c.mLastName = ""
}

// 加var、val声明有该成员，否则无，只是作为构造函数的传参。
open class A(name: String) {

    open var property: Int = 0

    init {
        println("A init.")
    }

    open fun action1() {
        println("A action 1.")
    }

    private fun action2() {

    }
}

class B(var country: String) : A("b name") {

    override var property: Int
        get() = super.property
        set(value) {
            var a = value
        }

    init {
        println("B init.")
    }

    constructor(country: String, countryCode: Int) : this(country) {
        println("B constructor.")
    }

    override fun action1() {
        super.action1()
        println("B action 1.")
    }

}

class C constructor(mFirstName: String) {
    init {
        println(mFirstName)
    }

    private var getCnt = 0
    private var setCnt = 0

    var mLastName: String?
        get() {
            getCnt++
            println("get $getCnt")
            return ""
        }
        set(value) {
            setCnt++
            println("set $setCnt")
        }

    var name = ""

    val isEmpty get() = name.isEmpty()

}

abstract class BaseAdp {
    open fun onBind() {
    }
}

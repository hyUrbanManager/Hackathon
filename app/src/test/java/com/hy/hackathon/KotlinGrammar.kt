package com.hy.hackathon

/**
 * Created by Administrator on 2017/11/4.
 *
 * @author hy 2017/11/4
 */
var mile = 5
var km: Int = 0
var fl: Float = 1.0F
var pi: Double = 3.14

var isPlay = false
var isDone: Boolean = true

var list = ArrayList<String>()

var miles: Array<Int>? = null

fun main(args: Array<String>) {

    print("每天跑${mile}公里")
    val (day, method, course) = Triple(1, "视频", "Kotlin课程")

    print("day is $day, method is $method, course is $course")

    val friend = Pair("小明", "小红")

    print(friend.first + friend.second)

    val shoes = Triple(1, "Nike", 4.99)
    print("my shoes: ${shoes.first}, ${shoes.second}, ${shoes.third}")

    // 可空类型
    val address: String? = "上海师范"
    var phoneNum: String

    var isMan: Boolean? = null

    if (address != null) {

    }
    if (isMan!!) {

    }
    isMan = true
    var opp: Boolean? = false
    opp = null


    val a = -100
    var b = -a
    var c = 10_000

    // String
    val title = "顺丰菜鸟" + "大战123"

    var index = title.indexOf('1')
    title.isEmpty()
    var num7 = 'a'
    val chars = title.toCharArray()
    for (letter in chars) {
        print(letter)
    }

    var jj = "123"
    jj += ('a' + "123" + 'e')

    var p = "${jj + "1"}pppp";

    var list: Array<String>?

    var list2 = arrayListOf<String>("", "", "")


}









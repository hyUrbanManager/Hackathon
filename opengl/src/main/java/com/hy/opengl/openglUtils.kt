package com.hy.opengl

import android.annotation.SuppressLint
import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


@SuppressLint("StaticFieldLeak")
var mContext: Context? = null

fun setApplicationContext(context: Context) {
    mContext = context
}

fun readGLSL(fileName: String): String {
    val buffer = StringBuffer()
    try {
        val inReader = BufferedReader(InputStreamReader(mContext!!.assets.open(fileName)))
        var item = inReader.readLine()
        while (item != null) {
            buffer.append(item).append("\n")
            item = inReader.readLine()
        }
        inReader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return buffer.toString()
}

fun genFloatBuffer(rawData: FloatArray): FloatBuffer {
    // 先初始化buffer,数组的长度*4,因为一个float占4个字节
    val mbb = ByteBuffer.allocateDirect(rawData.size * 4)
    // 数组排列用nativeOrder
    mbb.order(ByteOrder.nativeOrder())
    val floatBuffer = mbb.asFloatBuffer()
    floatBuffer.put(rawData)
    floatBuffer.position(0)
    return floatBuffer
}

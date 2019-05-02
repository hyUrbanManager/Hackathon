package com.hy.opengl

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setApplicationContext(applicationContext)
    }
}
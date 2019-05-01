package com.hy.opengl.blursimple

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hy.opengl.R
import kotlinx.android.synthetic.main.activity_main.*

class BlueSimpleMainActivity : AppCompatActivity() {

    lateinit var mBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.peppers)

        blur_image_view.setImageBitmap(mBitmap)
    }

    override fun onResume() {
        super.onResume()
        blur_image_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        blur_image_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBitmap.recycle()
    }

}


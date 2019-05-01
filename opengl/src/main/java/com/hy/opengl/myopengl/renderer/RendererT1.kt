package com.hy.opengl.myopengl.renderer

import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20.*

/**
 * @author huangye
 */
class RendererT1 : GLSurfaceView.Renderer {

    companion object {
        private const val TAG = "@RendererT1"
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG, "onSurfaceCreated")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceChanged, $width, $height")
        gl?.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.d(TAG, "onDrawFrame")
        gl?.glClear(GL_COLOR_BUFFER_BIT)
        gl?.glClearColor(1.0F, 0F, 0F, 1.0F)
    }


}
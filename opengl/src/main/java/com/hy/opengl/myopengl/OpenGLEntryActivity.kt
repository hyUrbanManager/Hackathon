package com.hy.opengl.myopengl

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.hy.opengl.R
import com.hy.opengl.myopengl.renderer.*
import kotlinx.android.synthetic.main.activity_opengl.*

/**
 * @author huangye
 */
class OpenGLEntryActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "@OpenGl"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opengl)
        checkOpenglInfo()
        setGlSurfaceView()
    }

    override fun onResume() {
        super.onResume()
        gl_surface_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        gl_surface_view.onPause()
    }

    private fun setGlSurfaceView() {
        gl_surface_view.setEGLContextClientVersion(2)

        // renderer
        gl_surface_view.setRenderer(RendererT8())
        gl_surface_view.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    /**
     * 检查opengl信息
     */
    fun checkOpenglInfo() {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        try {
            Log.d(TAG, "device: ${am.deviceConfigurationInfo}")
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        }
    }

}

package com.hy.opengl.myopengl.renderer

import android.graphics.Rect
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import com.hy.opengl.BuildConfig
import com.hy.opengl.genFloatBuffer
import com.hy.opengl.readGLSL
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @author huangye
 */
class RendererT2 : GLSurfaceView.Renderer {

    companion object {
        private const val TAG = "@RendererT2"

        private val POINTS = floatArrayOf(
                // triangle1
                -0.5F, -0.5F,
                0.5F, -0.5F,
                0.5F, 0.5F,
                // triangle2
                -0.5F, -0.5F,
                0.5F, 0.5F,
                -0.5F, 0.5F,
                // line
                -0.5F, 0.0F,
                0.5F, 0.0F
        )
    }

    private var my_Color = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val vertexCode = readGLSL("renderert2/vertex_shader.glsl")
        val vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, vertexCode)
        glCompileShader(vertexShader)
        checkShaderCompile(vertexShader)

        val fragmentCode = readGLSL("renderert2/fragment_shader.glsl")
        val fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentShader, fragmentCode)
        glCompileShader(fragmentShader)
        checkShaderCompile(fragmentShader)

        val program = glCreateProgram()
        glAttachShader(program, vertexShader)
        glAttachShader(program, fragmentShader)
        glLinkProgram(program)
        checkProgramLink(program)
        if (BuildConfig.DEBUG) {
            checkProgramValidate(program)
        }

        glUseProgram(program)

        val my_Position = glGetAttribLocation(program, "my_Position")
        val buffer = genFloatBuffer(POINTS)
        glVertexAttribPointer(my_Position, 2, GL_FLOAT, false, 0, buffer)
        glEnableVertexAttribArray(my_Position)

        my_Color = glGetUniformLocation(program, "my_Color")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        val rect = Rect(0, 0, width, height)
        glViewport(rect.left, rect.top, rect.right, rect.bottom)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        // triangle
        glUniform4f(my_Color, 0F, 0.5F, 0.5F, 1F)
        glDrawArrays(GL_TRIANGLES, 0, 6)
        // line
        glUniform4f(my_Color, 1F, 0F, 0F, 1F)
        glDrawArrays(GL_LINES, 6, 2)

    }

    private fun checkShaderCompile(shader: Int) {
        val compileStatus = intArrayOf(0)
        glGetShaderiv(shader, GL_COMPILE_STATUS, compileStatus, 0)
        val infoLog = glGetShaderInfoLog(shader)
        Log.d(TAG, "check compile: ${compileStatus[0]}, $infoLog")
    }

    private fun checkProgramLink(program: Int) {
        val linkStatus = intArrayOf(0)
        glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0)
        Log.d(TAG, "checkProgramLink: ${linkStatus[0]}")
    }

    private fun checkProgramValidate(program: Int) {
        glValidateProgram(program);
        val validateStatus = intArrayOf(0)
        glGetProgramiv(program, GL_VALIDATE_STATUS, validateStatus, 0)
        val infoLog = glGetProgramInfoLog(program)
        Log.d(TAG, "check validate: ${validateStatus[0]}, $infoLog")
    }


}
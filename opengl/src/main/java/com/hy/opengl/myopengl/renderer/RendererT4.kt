package com.hy.opengl.myopengl.renderer

import android.graphics.Rect
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.util.Log
import com.hy.opengl.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @author huangye
 */
class RendererT4 : GLSurfaceView.Renderer {

    companion object {
        private const val TAG = "@OpenGl RendererT3"

        // OpenGL、Android y坐标相反，使用y坐标为相反值。
        private val POINTS = floatArrayOf(
                // triangle1, st.
                -0.5F, -0.5F, 0.0F, 1.0F,
                0.5F, -0.5F, 1.0F, 1.0F,
                0.5F, 0.5F, 1.0F, 0.0F,
                // triangle2
                -0.5F, -0.5F, 0.0F, 1.0F,
                0.5F, 0.5F, 1.0F, 0.0F,
                -0.5F, 0.5F, 0.0F, 0.0F
        )
    }

    private var a_Position = 0
    private var a_TextureCoordinates = 0
    private var u_TextureUnit = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val vertexCode = readGLSL("renderert4/vertex_shader.glsl")
        val vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, vertexCode)
        glCompileShader(vertexShader)
        checkShaderCompile(vertexShader)

        val fragmentCode = readGLSL("renderert4/fragment_shader.glsl")
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

        val texture = intArrayOf(0)
        glGenTextures(1, texture, 0)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, texture[0])
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, readBitmap(R.drawable.peppers), 0)
        // importance.
        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0)

        a_Position = glGetAttribLocation(program, "a_Position")
        val pointBuffer = genFloatBuffer(POINTS)
        glVertexAttribPointer(a_Position, 2, GL_FLOAT, false, 4 * 4, pointBuffer)
        a_TextureCoordinates = glGetAttribLocation(program, "a_TextureCoordinates")
        pointBuffer.position(2)
        glVertexAttribPointer(a_TextureCoordinates, 2, GL_FLOAT, false, 4 * 4, pointBuffer)

        u_TextureUnit = glGetUniformLocation(program, "u_TextureUnit")
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, texture[0])
        glUniform1i(u_TextureUnit, 0)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        val rect = Rect(0, 0, width, height)
        glViewport(rect.left, rect.top, rect.right, rect.bottom)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        glEnableVertexAttribArray(a_Position)
        glEnableVertexAttribArray(a_TextureCoordinates)

        // triangle
        glDrawArrays(GL_TRIANGLES, 0, 6)

        glDisableVertexAttribArray(a_Position)
        glDisableVertexAttribArray(a_TextureCoordinates)
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
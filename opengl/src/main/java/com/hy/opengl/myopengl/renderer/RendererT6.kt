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
class RendererT6 : GLSurfaceView.Renderer {

    companion object {
        private const val TAG = "@OpenGl RendererT6"

        // OpenGL、Android y坐标相反，bitmap加载为纹理，使用y坐标为相反值。
        private val POINTS = floatArrayOf(
                // triangle1, st.
                -1.0F, -1.0F, 0.0F, 1.0F,
                1.0F, -1.0F, 1.0F, 1.0F,
                1.0F, 1.0F, 1.0F, 0.0F,
                // triangle2
                -1.0F, -1.0F, 0.0F, 1.0F,
                1.0F, 1.0F, 1.0F, 0.0F,
                -1.0F, 1.0F, 0.0F, 0.0F
        )

        // fb作为纹理画到另外一个fb上，使用y坐标为正值。
        private val POINTS2 = floatArrayOf(
                // triangle1, st.
                -1.0F, -1.0F, 0.0F, 0.0F,
                1.0F, -1.0F, 1.0F, 0.0F,
                1.0F, 1.0F, 1.0F, 1.0F,
                // triangle2
                -1.0F, -1.0F, 0.0F, 0.0F,
                1.0F, 1.0F, 1.0F, 1.0F,
                -1.0F, 1.0F, 0.0F, 1.0F
        )

        // fbo，相反，使用y坐标为相反值。
        private val POINTS3 = floatArrayOf(
                // triangle1, st.
                -1.0F, -1.0F, 0.0F, 0.0F,
                1.0F, -1.0F, 1.0F, 0.0F,
                1.0F, 1.0F, 1.0F, 1.0F,
                // triangle1, st.
//                -1.0F, -1.0F, -1.0F, -1.0F,
//                1.0F, -1.0F, 1.0F, -1.0F,
//                1.0F, 1.0F, 1.0F, 1.0F,
                // triangle2
                -1.0F, -1.0F, 0.0F, 0.0F,
//                1.0F, 1.0F, 2.0F, 2.0F,
//                -1.0F, 1.0F, 0.0F, 2.0F
                1.0F, 1.0F, 0.5F, 0.5F,
                -1.0F, 1.0F, 0.0F, 0.5F
                // triangle2
//                -1.0F, -1.0F, -1.0F, -1.0F,
//                1.0F, 1.0F, 1.0F, 1.0F,
//                -1.0F, 1.0F, 1.0F, 1.0F
        )
    }

    private var a_Position = 0
    private var a_TextureCoordinates = 0
    private var u_TextureUnit = 0
    private var u_Radius = 0
    private var u_WidthOffset = 0
    private var u_HeightOffset = 0
    private var u_IsDrawOrigin = 0

    private var mFrameBuffer = intArrayOf(0, 0)
    private var mTextures = intArrayOf(0, 0, 0)

    private val mRect = Rect()

    private val mPointBuffer = genFloatBuffer(POINTS)
    private val mPointBuffer2 = genFloatBuffer(POINTS2)
    private val mPointBuffer3 = genFloatBuffer(POINTS3)

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // program
        val vertexCode = readGLSL("renderert6/vertex_shader.glsl")
        val vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, vertexCode)
        glCompileShader(vertexShader)
        checkShaderCompile(vertexShader)

        val fragmentCode = readGLSL("renderert6/fragment_shader.glsl")
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

        // picture texture, draw texture 1, draw texture 2.
        glGenTextures(3, mTextures, 0)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mTextures[0])
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, readScaledBitmap(R.drawable.peppers), 0)
        glGenerateMipmap(GL_TEXTURE_2D) // importance.
        glBindTexture(GL_TEXTURE_2D, mTextures[1])
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 1200, 800, 0, GL_RGBA, GL_UNSIGNED_BYTE, null)
//        GLUtils.texImage2D(GL_TEXTURE_2D, 0, readBitmap(R.drawable.peppers), 0)
        glGenerateMipmap(GL_TEXTURE_2D)
        glBindTexture(GL_TEXTURE_2D, mTextures[2])
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 1200, 800, 0, GL_RGBA, GL_UNSIGNED_BYTE, null)
//        GLUtils.texImage2D(GL_TEXTURE_2D, 0, readBitmap(R.drawable.peppers), 0)
        glGenerateMipmap(GL_TEXTURE_2D)
        glBindTexture(GL_TEXTURE_2D, 0)

        // draw framebuffer 1, draw framebuffer 2
        glGenFramebuffers(2, mFrameBuffer, 0)
        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffer[0])
        glBindTexture(GL_TEXTURE_2D, mTextures[1])
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, mTextures[1], 0)
        glClear(GL_COLOR_BUFFER_BIT) // test

        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffer[1])
        glBindTexture(GL_TEXTURE_2D, mTextures[2])
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, mTextures[2], 0)
        glClear(GL_COLOR_BUFFER_BIT) // test

        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        glBindTexture(GL_TEXTURE_2D, 0)
        Log.d(TAG, "framebuffer status: ${glCheckFramebufferStatus(GL_FRAMEBUFFER)
                == GL_FRAMEBUFFER_COMPLETE} ")

        // var
        a_Position = glGetAttribLocation(program, "a_Position")
        a_TextureCoordinates = glGetAttribLocation(program, "a_TextureCoordinates")

        // 不需要给fragment shader传递纹理单元，传0，绑定当前texture即可。
        u_TextureUnit = glGetUniformLocation(program, "u_TextureUnit")
        u_Radius = glGetUniformLocation(program, "u_Radius")
        u_WidthOffset = glGetUniformLocation(program, "u_WidthOffset")
        u_HeightOffset = glGetUniformLocation(program, "u_HeightOffset")
        u_IsDrawOrigin = glGetUniformLocation(program, "u_IsDrawOrigin")

        glBindTexture(GL_TEXTURE_2D, 0)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        val rect = Rect(0, 0, width, height)
        glViewport(rect.left, rect.top, rect.right, rect.bottom)
        mRect.set(rect)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        // 横向模糊，画到fb1
        glEnableVertexAttribArray(a_Position)
        glEnableVertexAttribArray(a_TextureCoordinates)
        mPointBuffer.position(0)
        glVertexAttribPointer(a_Position, 2, GL_FLOAT, false, 4 * 4, mPointBuffer)
        mPointBuffer.position(2)
        glVertexAttribPointer(a_TextureCoordinates, 2, GL_FLOAT, false, 4 * 4, mPointBuffer)
        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffer[0])
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mTextures[0])
        glUniform1i(u_TextureUnit, 0)
        glUniform1i(u_Radius, 50)
        glUniform1f(u_WidthOffset, 1F / 1200)
        glUniform1f(u_HeightOffset, 0F)
        glUniform1i(u_IsDrawOrigin, 0)
        glViewport(mRect.left, mRect.top, mRect.right, mRect.bottom)
        glDrawArrays(GL_TRIANGLES, 0, 6)
        glDisableVertexAttribArray(a_Position)
        glDisableVertexAttribArray(a_TextureCoordinates)

        // 纵向模糊，画到fb2
        glEnableVertexAttribArray(a_Position)
        glEnableVertexAttribArray(a_TextureCoordinates)
        mPointBuffer2.position(0)
        glVertexAttribPointer(a_Position, 2, GL_FLOAT, false, 4 * 4, mPointBuffer2)
        mPointBuffer2.position(2)
        glVertexAttribPointer(a_TextureCoordinates, 2, GL_FLOAT, false, 4 * 4, mPointBuffer2)
        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffer[1])
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mTextures[1])
        glUniform1i(u_TextureUnit, 0)
        glUniform1i(u_Radius, 50)
        glUniform1f(u_WidthOffset, 0F)
        glUniform1f(u_HeightOffset, 1F / 800)
        glUniform1i(u_IsDrawOrigin, 0)
        glViewport(mRect.left, mRect.top, mRect.right, mRect.bottom)
        glDrawArrays(GL_TRIANGLES, 0, 6)
        glDisableVertexAttribArray(a_Position)
        glDisableVertexAttribArray(a_TextureCoordinates)

        // fb2的结果通过texture画到屏幕上
        glEnableVertexAttribArray(a_Position)
        glEnableVertexAttribArray(a_TextureCoordinates)
        mPointBuffer2.position(0)
        glVertexAttribPointer(a_Position, 2, GL_FLOAT, false, 4 * 4, mPointBuffer2)
        mPointBuffer2.position(2)
        glVertexAttribPointer(a_TextureCoordinates, 2, GL_FLOAT, false, 4 * 4, mPointBuffer2)
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mTextures[1])
        glUniform1i(u_TextureUnit, 0)
        glUniform1i(u_IsDrawOrigin, 1)
        glViewport(mRect.left, mRect.top, mRect.right, mRect.bottom)
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
        glValidateProgram(program)
        val validateStatus = intArrayOf(0)
        glGetProgramiv(program, GL_VALIDATE_STATUS, validateStatus, 0)
        val infoLog = glGetProgramInfoLog(program)
        Log.d(TAG, "check validate: ${validateStatus[0]}, $infoLog")
    }


}
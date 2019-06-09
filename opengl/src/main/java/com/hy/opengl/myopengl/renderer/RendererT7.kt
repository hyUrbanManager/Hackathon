package com.hy.opengl.myopengl.renderer

import android.graphics.Rect
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.util.Log
import com.hy.opengl.*
import java.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @author huangye
 */
class RendererT7 : GLSurfaceView.Renderer {

    companion object {
        private const val TAG = "@OpenGl RendererT6"

        // OpenGL、Android y坐标相反，bitmap画到纹理上，使用y坐标为相反值。
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

        // texture画到Android屏幕上，使用正交投影产生的值。
        private val POINTS3 = floatArrayOf(
                // triangle1, st.
                -1.0F, -1.0F, 0.0F, 0.0F,
                1.0F, -1.0F, 1.0F, 0.0F,
                1.0F, 1.0F, 1.0F, 1.0F,
                // triangle2
                -1.0F, -1.0F, 0.0F, 0.0F,
                1.0F, 1.0F, 1.0F, 1.0F,
                -1.0F, 1.0F, 0.0F, 1.0F
        )

        private const val BLUR_RADIUS = 10
        private const val SCALE = 1
        private const val CAPTURE_WIDTH = (1200 - 500) / SCALE
        private const val CAPTURE_HEIGHT = (800 + 0) / SCALE
        private const val TEX_WIDTH = 1200 / SCALE
        private const val TEX_HEIGHT = 800 / SCALE
    }

    private val a_Position = 0
    private val a_TextureCoordinates = 1
    private var u_TextureUnit = 0
    private var u_Radius = 0
    private var u_WidthOffset = 0
    private var u_HeightOffset = 0
    private var u_IsDrawOrigin = 0

    private var mArrayBuffers = intArrayOf(0, 0, 0)
    private var mFrameBuffers = intArrayOf(0, 0, 0)
    private var mTextures = intArrayOf(0, 0, 0)

    private val mRect = Rect()

    private val mPointBuffer = genFloatBuffer(POINTS)
    private val mPointBuffer2 = genFloatBuffer(POINTS2)
    private val mPointBuffer3 = genFloatBuffer(POINTS3)

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // program
        val vertexCode = readGLSL("renderert7/vertex_shader.glsl")
        val vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, vertexCode)
        glCompileShader(vertexShader)
        checkShaderCompile(vertexShader)

        val fragmentCode = readGLSL("renderert7/fragment_shader.glsl")
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

        // array buffer.
        glGenBuffers(3, mArrayBuffers, 0)
        glBindBuffer(GL_ARRAY_BUFFER, mArrayBuffers[0])
        glBufferData(GL_ARRAY_BUFFER, 24 * 4, mPointBuffer, GL_STATIC_DRAW)
        glBindBuffer(GL_ARRAY_BUFFER, mArrayBuffers[1])
        glBufferData(GL_ARRAY_BUFFER, 24 * 4, mPointBuffer2, GL_STATIC_DRAW)
        glBindBuffer(GL_ARRAY_BUFFER, mArrayBuffers[2])
        glBufferData(GL_ARRAY_BUFFER, 24 * 4, mPointBuffer3, GL_STATIC_DRAW)
        glEnableVertexAttribArray(0)

        // picture texture, draw texture 1, draw texture 2.
        glGenTextures(3, mTextures, 0)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mTextures[0])
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, readScaledBitmap(R.drawable.peppers), 0)
        glBindTexture(GL_TEXTURE_2D, mTextures[1])
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        // framebuffer、texture绑定问题。大小设置。
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, CAPTURE_WIDTH, CAPTURE_HEIGHT, 0, GL_RGBA, GL_UNSIGNED_BYTE, null)
        glBindTexture(GL_TEXTURE_2D, mTextures[2])
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, CAPTURE_WIDTH, CAPTURE_HEIGHT, 0, GL_RGBA, GL_UNSIGNED_BYTE, null)
        glBindTexture(GL_TEXTURE_2D, 0)

        // captrue framebuffer. draw framebuffer 1, draw framebuffer 2
        glGenFramebuffers(3, mFrameBuffers, 0)

        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffers[1])
        glBindTexture(GL_TEXTURE_2D, mTextures[1])
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, mTextures[1], 0)
        glClear(GL_COLOR_BUFFER_BIT) // test

        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffers[2])
        glBindTexture(GL_TEXTURE_2D, mTextures[2])
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, mTextures[2], 0)
        glClear(GL_COLOR_BUFFER_BIT) // test

        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        glBindTexture(GL_TEXTURE_2D, 0)
        Log.d(TAG, "framebuffer status: ${glCheckFramebufferStatus(GL_FRAMEBUFFER)
                == GL_FRAMEBUFFER_COMPLETE} ")

        // var
        glBindAttribLocation(program, a_Position, "a_Position")
        glBindAttribLocation(program, a_TextureCoordinates, "a_TextureCoordinates")

        // 不需要给fragment shader传递纹理单元，传0，绑定当前texture即可。
        u_TextureUnit = glGetUniformLocation(program, "u_TextureUnit")
        u_Radius = glGetUniformLocation(program, "u_Radius")
        u_WidthOffset = glGetUniformLocation(program, "u_WidthOffset")
        u_HeightOffset = glGetUniformLocation(program, "u_HeightOffset")
        u_IsDrawOrigin = glGetUniformLocation(program, "u_IsDrawOrigin")

        glBindTexture(GL_TEXTURE_2D, 0)

        Log.d(TAG, "arraybuffers: ${Arrays.toString(mArrayBuffers)}, textures: ${Arrays.toString(mTextures)}, framebuffers: ${Arrays.toString(mFrameBuffers)}," +
                "$a_Position, $a_TextureCoordinates, $u_TextureUnit, $u_Radius, $u_WidthOffset, $u_HeightOffset, " +
                "$u_IsDrawOrigin")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        val rect = Rect(0, 0, width, height)
        glViewport(rect.left, rect.top, rect.right, rect.bottom)
        mRect.set(rect)
        Log.d(TAG, mRect.toShortString())
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        glEnableVertexAttribArray(1)

        // 横向模糊，画到fb1
        glBindBuffer(GL_ARRAY_BUFFER, mArrayBuffers[0])
        glVertexAttribPointer(a_Position, 2, GL_FLOAT, false, 4 * 4, 0)
        glVertexAttribPointer(a_TextureCoordinates, 2, GL_FLOAT, false, 4 * 4, 2 * 4)
        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffers[1])
        // view port，绑定framebuffer后要调整为framebuffer大小。framebuffer大小即texture大小。
        glViewport(0, 0, CAPTURE_WIDTH, CAPTURE_HEIGHT)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mTextures[0])
        glUniform1i(u_TextureUnit, 0)
        glUniform1i(u_Radius, BLUR_RADIUS)
        glUniform1f(u_WidthOffset, 1F / CAPTURE_WIDTH)
        glUniform1f(u_HeightOffset, 0F)
        glUniform1i(u_IsDrawOrigin, 0)
        glDrawArrays(GL_TRIANGLES, 0, 6)

        // 纵向模糊，画到fb2
        glBindBuffer(GL_ARRAY_BUFFER, mArrayBuffers[1])
        glVertexAttribPointer(a_Position, 2, GL_FLOAT, false, 4 * 4, 0)
        glVertexAttribPointer(a_TextureCoordinates, 2, GL_FLOAT, false, 4 * 4, 2 * 4)
        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffers[2])
        // view port，绑定framebuffer后要调整为framebuffer大小。framebuffer大小即texture大小。
        glViewport(0, 0, CAPTURE_WIDTH, CAPTURE_HEIGHT)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mTextures[1])
        glUniform1i(u_TextureUnit, 0)
        glUniform1i(u_Radius, BLUR_RADIUS)
        glUniform1f(u_WidthOffset, 0F)
        glUniform1f(u_HeightOffset, 1F / CAPTURE_HEIGHT)
        glUniform1i(u_IsDrawOrigin, 0)
        glDrawArrays(GL_TRIANGLES, 0, 6)

        // fb2的结果通过texture画到屏幕上
        glBindBuffer(GL_ARRAY_BUFFER, mArrayBuffers[2])
        glVertexAttribPointer(a_Position, 2, GL_FLOAT, false, 4 * 4, 0)
        glVertexAttribPointer(a_TextureCoordinates, 2, GL_FLOAT, false, 4 * 4, 2 * 4)
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        // view port 恢复为屏幕大小。
        glViewport(mRect.left, mRect.top, mRect.right, mRect.bottom)
        glClearColor(1.0F, 0F, 0F, 0.0F)// todo
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mTextures[2])
        glUniform1i(u_TextureUnit, 0)
        glUniform1i(u_IsDrawOrigin, 1)
        glDrawArrays(GL_TRIANGLES, 0, 6)

        glDisableVertexAttribArray(1)
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
//        glGenerateMipmap(GL_TEXTURE_2D) // importance. no need.
//        glGenerateMipmap(GL_TEXTURE_2D)
//        glGenerateMipmap(GL_TEXTURE_2D)

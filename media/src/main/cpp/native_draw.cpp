//
// Created by Administrator on 2018/3/24.
//
#include <jni.h>
#include <unistd.h>
#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <android/log.h>

#define log(...) \
        __android_log_print(ANDROID_LOG_INFO, "@jni", __VA_ARGS__)

extern "C" {

JNIEXPORT void JNICALL
Java_com_hy_media_NativeDrawActivity_native_1draw1(JNIEnv* env, jobject instance, jobject surface) {
    ANativeWindow* window = ANativeWindow_fromSurface(env, surface);
    ANativeWindow_acquire(window);

    int width = ANativeWindow_getWidth(window);
    int height = ANativeWindow_getHeight(window);
    int format = ANativeWindow_getFormat(window);
    log("width: %d, height: %d, format: %d", width, height, format);

    ANativeWindow_Buffer buffer;

    // 设置rgb 565
    int set_ret = ANativeWindow_setBuffersGeometry(window, width, height, WINDOW_FORMAT_RGB_565);
    int lock_ret = ANativeWindow_lock(window, &buffer, NULL);

    memset(buffer.bits, 0x55, (size_t) (buffer.stride * height * 2));

    int unlock_ret = ANativeWindow_unlockAndPost(window);
    ANativeWindow_release(window);

    log("set ret: %d, lock ret: %d, unlock_ret: %d", set_ret, lock_ret, unlock_ret);
}

JNIEXPORT void JNICALL
Java_com_hy_media_NativeDrawActivity_native_1draw2(JNIEnv* env, jobject instance, jobject surface) {
    ANativeWindow* window = ANativeWindow_fromSurface(env, surface);

    int width = 640, height = 360;
    int format = ANativeWindow_getFormat(window);
    log("width: %d, height: %d, format: %d", width, height, format);

    ANativeWindow_Buffer buffer;

    // 设置rgb 565
    int set_ret = ANativeWindow_setBuffersGeometry(window, width, height, WINDOW_FORMAT_RGBA_8888);
    int lock_ret = ANativeWindow_lock(window, &buffer, NULL);

    memset(buffer.bits, 0x55, (size_t) (width * height * 2));

    int unlock_ret = ANativeWindow_unlockAndPost(window);
    log("set ret: %d, lock ret: %d, unlock_ret: %d", set_ret, lock_ret, unlock_ret);
}

}

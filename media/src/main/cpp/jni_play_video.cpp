//
// Created by Administrator on 2018/3/20.
//
#include <jni.h>


extern "C" {

JNIEXPORT void JNICALL
Java_com_hy_media_player_FFPlayer_play(
        JNIEnv* env, jobject instance, jstring path_, jobject surfaceView) {
    const char* path = env->GetStringUTFChars(path_, 0);


    env->ReleaseStringUTFChars(path_, path);
}

JNIEXPORT void JNICALL
Java_com_hy_media_player_FFPlayer_pause(JNIEnv* env, jclass type) {

    // TODO

}

}
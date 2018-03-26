//
// Created by Administrator on 2018/3/26.
//
#include <jni.h>
#include "libavformat/avformat.h"

extern "C" {

JNIEXPORT jstring JNICALL
Java_com_hy_media_player_FFTest_getInfo(JNIEnv* env, jclass type, jstring path_) {
    const char* path = env->GetStringUTFChars(path_, 0);

    // TODO

    env->ReleaseStringUTFChars(path_, path);

    return env->NewStringUTF(path);
}
}

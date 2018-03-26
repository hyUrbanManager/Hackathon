//
// Created by Administrator on 2018/3/26.
//
#include <jni.h>

extern "C" {

#include "libavformat/avformat.h"

JNIEXPORT jint JNICALL
Java_com_hy_media_player_FFLoader_init(JNIEnv* env, jclass type) {
    av_register_all();
    return 0;
}

}

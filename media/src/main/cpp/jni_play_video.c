//
// Created by Administrator on 2018/3/20.
//
#include <jni.h>
#include "libavformat/avformat.h"

JNIEXPORT jint JNICALL
Java_com_hy_media_player_FFPlayer_init(JNIEnv* env, jclass type) {
    av_register_all();
    return 0;
}

JNIEXPORT void JNICALL
Java_com_hy_media_player_FFPlayer_play(
        JNIEnv* env, jobject instance, jstring path_, jobject surfaceView) {
    const char* path = (*env)->GetStringUTFChars(env, path_, 0);

    // 打开文件。
    AVFormatContext* av_fmt_ctx = avformat_alloc_context();
    avformat_open_input(&av_fmt_ctx, path, NULL, NULL);
    avformat_find_stream_info(av_fmt_ctx, NULL);

    (*env)->ReleaseStringUTFChars(env, path_, path);
}

JNIEXPORT void JNICALL
Java_com_hy_media_player_FFPlayer_pause(JNIEnv* env, jclass type) {

    // TODO

}

//}
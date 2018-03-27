//
// Created by Administrator on 2018/3/26.
//
#include <jni.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include "hy_lib.h"

extern "C" {

#include "libavformat/avformat.h"

JNIEXPORT jstring JNICALL
Java_com_hy_media_player_FFTest_getInfo(JNIEnv* env, jclass type, jstring path_) {
    const char* path = env->GetStringUTFChars(path_, 0);
    char* info = (char*) calloc(1, 1024 * 2);

    // format context
    AVFormatContext* av_fmt_ctx = avformat_alloc_context();
    avformat_open_input(&av_fmt_ctx, path, NULL, NULL);
    avformat_find_stream_info(av_fmt_ctx, NULL);

    strcat(info, "filename: ");
    strcat(info, av_fmt_ctx->filename);
    strcat(info, "\n");

    strcat(info, "av input format: \n");
    AVInputFormat* av_input_fmt = av_fmt_ctx->iformat;
    while (av_input_fmt != NULL) {
        strcat(info, "name: ");
        strcat(info, av_input_fmt->name);
        strcat(info, " ");
        strcat(info, "long name: ");
        strcat(info, av_input_fmt->long_name);
        strcat(info, "\n");
        av_input_fmt = av_input_fmt->next;
    }

    strcat(info, "av stream: \n");
    AVStream* av_stream;
    for (int i = 0; i < av_fmt_ctx->nb_streams; i++) {
        av_stream = av_fmt_ctx->streams[i];
        strcat(info, "long name: ");
//        strcat(info, itos(av_stream->duration));
        strcat(info, " ");

    }

    env->ReleaseStringUTFChars(path_, path);

    avformat_close_input(&av_fmt_ctx);
    avformat_free_context(av_fmt_ctx);

    return env->NewStringUTF(info);
}

}

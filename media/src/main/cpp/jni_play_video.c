//
// Created by Administrator on 2018/3/20.
//
#include <jni.h>
#include <android/log.h>
#include <unistd.h>
#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <android/window.h>
#include "libavformat/avformat.h"
#include "libavcodec/avcodec.h"
#include "libswscale/swscale.h"

#define log(...) \
        __android_log_print(ANDROID_LOG_INFO, "@jni", __VA_ARGS__)

void fill_color(ANativeWindow_Buffer* window_buffer);

JNIEXPORT jint JNICALL
Java_com_hy_media_player_FFPlayer_init(JNIEnv* env, jclass type) {
    av_register_all();
    return 0;
}

JNIEXPORT void JNICALL
Java_com_hy_media_player_FFPlayer_play(
        JNIEnv* env, jobject instance, jstring path_, jobject surface) {
    const char* path = (*env)->GetStringUTFChars(env, path_, 0);

    // 打开文件。
    AVFormatContext* av_fmt_ctx = avformat_alloc_context();
    avformat_open_input(&av_fmt_ctx, path, NULL, NULL);
    avformat_find_stream_info(av_fmt_ctx, NULL);

    // 获取视频流位置。
    int i = 0, video_stream_idx = -1;
    for (; av_fmt_ctx->nb_streams > 0; i++) {
        if (av_fmt_ctx->streams[i]->codec->codec_type == AVMEDIA_TYPE_VIDEO) {
            video_stream_idx = i;
            log("video index is %d", video_stream_idx);
            break;
        }
    }

    // 获取视频流解码器解码。
    AVCodecContext* codec_ctx = av_fmt_ctx->streams[video_stream_idx]->codec;
    AVCodec* codec = avcodec_find_decoder(codec_ctx->codec_id);
    AVFrame* yuv_frame = av_frame_alloc();
    AVFrame* rgb_frame = av_frame_alloc();

    // 打开解码器。
    int ret = avcodec_open2(codec_ctx, codec, NULL);

    // 初始化native window。
    ANativeWindow* window = ANativeWindow_fromSurface(env, surface);
    ANativeWindow_Buffer window_buffer;

    struct SwsContext* sws_ctx = sws_getContext(
            codec_ctx->width, codec_ctx->height, codec_ctx->pix_fmt,
            codec_ctx->width, codec_ctx->height, AV_PIX_FMT_RGBA,
            SWS_BILINEAR, NULL, NULL, NULL);

    // 读取。
    int hasDecodeAFrame;
    AVPacket* packet = (AVPacket*) av_malloc(sizeof(AVPacket));
    while (av_read_frame(av_fmt_ctx, packet) >= 0) {
        // 解码。
        if (packet->stream_index == video_stream_idx) {
            avcodec_decode_video2(codec_ctx, yuv_frame, &hasDecodeAFrame, packet);
            if (hasDecodeAFrame) {
                log("success frame: %x, width: %d, height: %d",
                    *yuv_frame->data[0], codec_ctx->width, codec_ctx->height);
                int width = codec_ctx->width;
                int height = codec_ctx->height;

                // 设置缓冲区。
                ANativeWindow_setBuffersGeometry(window, width, height, WINDOW_FORMAT_RGBA_8888);
                ANativeWindow_lock(window, &window_buffer, NULL);
                avpicture_fill(rgb_frame, window_buffer.bits, AV_PIX_FMT_RGBA, width, height);

                // 格式转换
                sws_scale(sws_ctx, (uint8_t const* const*) yuv_frame->data,
                          yuv_frame->linesize, 0, codec_ctx->height,
                          rgb_frame->data, rgb_frame->linesize);

                ANativeWindow_unlockAndPost(window);
            }
            usleep(1000 * 16);
        }
    }

    (*env)->ReleaseStringUTFChars(env, path_, path);
}

JNIEXPORT void JNICALL
Java_com_hy_media_player_FFPlayer_pause(JNIEnv* env, jclass type) {

    // TODO

}




/**
 * log.h
 * libcbw库日志输出宏定义
 */
#ifndef LOG_H
#define LOG_H

#include <android/log.h>
#include "include/config.h"

// Android 下的log
#define EXTERNAL_LOGV(LOG_TAG, ...) \
        __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#define EXTERNAL_LOGD(LOG_TAG, ...) \
        __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define EXTERNAL_LOGI(LOG_TAG, ...) \
        __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define EXTERNAL_LOGW(LOG_TAG, ...) \
        __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define EXTERNAL_LOGE(LOG_TAG, ...) \
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

/*
 * 定义log模块，引用的模块需要二次定义log。
 * 例如：
 * #define log(...)  LOGD("MyModel", __VA_ARGS__)
 * #define logi(...) LOGI("MyModel", __VA_ARGS__)
 * #define loge(...) LOGE("MyModel", __VA_ARGS__)
 *
 * 不同平台依赖其外部实现 EXTERNAL_LOGV等
 *
 */
#if FUNCTION_LOG

#define LOGV(LOG_TAG, ...) EXTERNAL_LOGV(LOG_TAG, __VA_ARGS__)
#define LOGD(LOG_TAG, ...) EXTERNAL_LOGD(LOG_TAG, __VA_ARGS__)
#define LOGI(LOG_TAG, ...) EXTERNAL_LOGI(LOG_TAG, __VA_ARGS__)
#define LOGW(LOG_TAG, ...) EXTERNAL_LOGW(LOG_TAG, __VA_ARGS__)
#define LOGE(LOG_TAG, ...) EXTERNAL_LOGE(LOG_TAG, __VA_ARGS__)

#else
#define LOGV(LOG_TAG, ...)
#define LOGD(LOG_TAG, ...)
#define LOGI(LOG_TAG, ...)
#define LOGW(LOG_TAG, ...)
#define LOGE(LOG_TAG, ...)
#endif

#endif //LOG_H

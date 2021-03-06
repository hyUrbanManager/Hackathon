# Cmake最低版本3.4.1
cmake_minimum_required(VERSION 3.4.1)

# 头文件文件夹
include_directories(
             src/main/cpp/ffmpeg/include
             src/main/cpp/
)

# build文件夹一直回退到最顶层文件夹
set(distribution_dir ../../../../src/main/assets/lib)

# 增加ffmpeg库
add_library( avcodec
             SHARED
             IMPORTED )
set_target_properties( avcodec
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavcodec.so.57 )
add_library( avdevice
             SHARED
             IMPORTED )
set_target_properties( avdevice
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavdevice.so.57 )
add_library( avfilter
             SHARED
             IMPORTED )
set_target_properties( avfilter
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavfilter.so.6 )
add_library( avformat
             SHARED
             IMPORTED )
set_target_properties( avformat
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavformat.so.57 )
add_library( avutil
             SHARED
             IMPORTED )
set_target_properties( avutil
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavutil.so.55 )
add_library( swresample
             SHARED
             IMPORTED )
set_target_properties( swresample
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libswresample.so.2 )
add_library( swscale
             SHARED
             IMPORTED )
set_target_properties( swscale
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libswscale.so.4 )

# 编译动态库
add_library( # Sets the name of the library.
             video

             # Sets the library as a shared library.
             SHARED

             # video
             src/main/cpp/jni_play_video.c
             src/main/cpp/native_draw.cpp
             src/main/cpp/ffmpeg_test.cpp
             src/main/cpp/jni_loader.cpp
             src/main/cpp/hy_lib.cpp
             )


# 把Android库打包成自己的库
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )
find_library( # Sets the name of the path variable.
              android-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              android )

# 每个库要依赖的库
target_link_libraries( # Specifies the target library.
                       video
                       avformat
                       avcodec
                       avdevice
                       avfilter
                       avformat
                       avutil
                       swresample
                       swscale

                       log
                       android )


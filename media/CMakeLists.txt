# Cmake最低版本3.4.1
cmake_minimum_required(VERSION 3.4.1)

# 头文件文件夹
include_directories(
             src/main/cpp/
             libs/include/
)

# build文件夹一直回退到最顶层文件夹
set(distribution_dir ../../../../libs)

# 增加ffmpeg库
add_library( avcodec
             SHARED
             IMPORTED )
set_target_properties( avcodec
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavcodec.so )
add_library( avdevice
             SHARED
             IMPORTED )
set_target_properties( avdevice
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavdevice.so )
add_library( avfilter
             SHARED
             IMPORTED )
set_target_properties( avfilter
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavfilter.so )
add_library( avformat
             SHARED
             IMPORTED )
set_target_properties( avformat
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavformat.so )
add_library( avutil
             SHARED
             IMPORTED )
set_target_properties( avutil
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libavutil.so )
add_library( swresample
             SHARED
             IMPORTED )
set_target_properties( swresample
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libswresample.so )
add_library( swscale
             SHARED
             IMPORTED )
set_target_properties( swscale
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_dir}/${ANDROID_ABI}/libswscale.so )

# 编译动态库
add_library( # Sets the name of the library.
             video

             # Sets the library as a shared library.
             SHARED

             # video
             src/main/cpp/jni_play_video.c
             )


# 把Android库打包成自己的库
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )
# 设置cmake
# set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

# 每个库要依赖的库
target_link_libraries( # Specifies the target library.
                       video
                       avformat

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
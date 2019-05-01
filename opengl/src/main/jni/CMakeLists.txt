cmake_minimum_required(VERSION 3.6.0)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -Wall")

AUX_SOURCE_DIRECTORY(cpp SRC)

# 头文件文件夹
include_directories(
        include
)

# 打包库
add_library(my-opengl
        SHARED

        cpp/blursimple/blurimageview.cpp

        include)

# add lib dependencies
target_link_libraries(my-opengl
        android
        log
        EGL
        GLESv3
        jnigraphics)

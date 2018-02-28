# Cmake最低版本3.4.1
cmake_minimum_required(VERSION 3.4.1)

# 头文件文件夹
include_directories(
             src/main/cpp/
             src/main/cpp/include
)

# 编译动态库
add_library( # Sets the name of the library.
             androidlib

             # Sets the library as a shared library.
             SHARED

             # libcbw
             src/main/cpp/memleak.cpp

             )


# 把Android库打包成自己的库
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )


# 每个库要依赖的库
target_link_libraries( # Specifies the target library.
                       androidlib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
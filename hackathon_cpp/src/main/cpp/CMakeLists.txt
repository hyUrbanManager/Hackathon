# Cmake最低版本3.4.1
cmake_minimum_required(VERSION 3.4.1)

project(hackathon_cpp)

# demo
aux_source_directory(demo DIR_DEMO_SRCS)
add_executable(demo ${DIR_DEMO_SRCS})

# syntax
aux_source_directory(syntax DIR_SYNTAX_SRCS)
add_executable(syntax ${DIR_SYNTAX_SRCS})


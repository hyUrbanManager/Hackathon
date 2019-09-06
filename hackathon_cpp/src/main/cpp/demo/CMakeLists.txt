# Cmake最低版本3.4.1
cmake_minimum_required(VERSION 3.4.1)

aux_source_directory(. DIR_DEMO_SRCS)

add_executable(executable ${DIR_DEMO_SRCS})

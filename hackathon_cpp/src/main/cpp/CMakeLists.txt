# Cmake最低版本3.4.1
cmake_minimum_required(VERSION 3.4.1)

project(hackathon_cpp)

# Add header file include directories
include_directories(
        ./demo
)

# Add block directories
add_subdirectory(
        demo
)

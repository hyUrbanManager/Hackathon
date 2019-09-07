#!/usr/bin/env bash

project=`pwd`

cd build
cmake ${project}/src/main/cpp

make

echo =========================================
./demo

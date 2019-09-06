#!/usr/bin/env bash

# 运行
src=`pwd`
cd ../../../../build

cmake ${src}
make

echo =========================================
./executable

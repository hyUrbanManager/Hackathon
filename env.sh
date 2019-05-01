#!/bin/bash

#add current path to environment path
export PATH=$PATH:./

#遍历目录生成settings.gradle
list_all_dir(){
	> settings.gradle #清空
  index=0;
  for projectname in `ls $1`
  do
    if [[ ${projectname} != "gradle" && ${projectname} != "capture" && ${projectname} != "build" ]];then
        if [[ -d "$1/$projectname" ]];then
          echo "include ':$projectname'" >> settings.gradle
          all_project[$index]=${projectname};
          ((index++))
        fi
    fi
  done
}

list_all_dir .


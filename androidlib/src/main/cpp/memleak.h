/**
 * memleak.h
 * 提供内存泄漏检测的工具。
 */
#ifndef MEMLEAK_H
#define MEMLEAK_H

#include "include/type.h"
#include "include/log.h"

/*
 * 储存已经分配了的内存信息。
 * p: 分配的指针
 * size: 分配了的字节大小
 */
typedef struct mem_info {
    u8* p; // 方便查看内存结构。
    int size;

    struct mem_info* next;
} MemInfo;


/*
 * 安全分配内存，提供检测内存泄漏。
 *
 * item_count 块数，通常为1。
 * item_size 快大小。
 * return 分配的指针。
 */
void* scalloc(size_t item_size);

/*
 * 安全分配内存，提供检测内存泄漏。
 *
 * p 内存指针。
 */
void sfree(void* p);

#endif //MEMLEAK_H
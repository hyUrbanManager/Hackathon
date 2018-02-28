/**
 * memleak.cpp
 * 提供内存泄漏检测的工具。
 */
#include <string.h>
#include "memleak.h"
#include "include/log.h"

#define log(...)  LOGD("memleak", __VA_ARGS__)
#define logi(...) LOGI("memleak", __VA_ARGS__)
#define loge(...) LOGE("memleak", __VA_ARGS__)

#define log_mem() logi("still use: %ld, size is %ld.", mem_block_num(), mem_block_size())

// 分配了的内存链表。
MemInfo* head;

long mem_block_num() {
    long num = 0;
    MemInfo* p = head;
    while (p != null) {
        num++;
        p = p->next;
    }
    return num;
}

long mem_block_size() {
    long size = 0;
    MemInfo* p = head;
    while (p != null) {
        size += p->size;
        p = p->next;
    }
    return size;
}

/*
 * 安全分配内存，提供检测内存泄漏。
 *
 * item_size 块大小。
 * return 分配的指针。
 */
void* scalloc(size_t item_size) {
    void* p = calloc(1, item_size);
    if (p == null) {
        loge("call calloc fail");
        return null;
    }

    if (head == null) {
        head = (MemInfo*) calloc(1, sizeof(MemInfo));
        head->size = item_size;
        head->p = (u8*) p;
    } else {
        MemInfo* pm = head;
        MemInfo* last = head;
        while (pm != null) {
            last = pm;
            pm = pm->next;
        }
        pm = (MemInfo*) calloc(1, sizeof(MemInfo));
        last->next = pm;
        pm->size = item_size;
        pm->p = (u8*) p;
    }

    return p;
}

/*
 * 安全分配内存，提供检测内存泄漏。
 *
 * p 内存指针。
 */
void sfree(void* p) {
    if (p == 0) {
        return;
    }

    if (head == null) {
        loge("something wrong");
    } else {
        if (head->p == p) {
            MemInfo* pm = head;
            head = head->next;
            free(pm);
        } else {
            MemInfo* pm = head;
            MemInfo* last = head;
            while (pm != null) {
                if (pm->p == p) {
                    last->next = pm->next;
                    free(pm);
                    break;
                }
                last = pm;
                pm = pm->next;
            }
        }
    }

    log_mem();
    free(p);
}

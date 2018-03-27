//
// Created by Administrator on 2018/3/27.
//

#include "hy_lib.h"
#include <unistd.h>

static const char* internal_ntos(long l);

/*
 * 数字转字符串。
 */
const char* itos(int i) {
    return internal_ntos(i);
}

/*
 * 内部转换数字。
 */
static const char* internal_ntos(long l) {
    char* str = (char*) calloc(1, 15);
    int di = 0;
    if (l < 0) {
        str[0] = '-';
        di = 1;
    }

    char tcs[15] = {0};
    int si = 0;
    long t = l;
    while (t > 0) {
        tcs[si++] = (char) (t % 10 + '0');
        t /= 10;
    }

    while (si >= 0) {
        str[di++] = tcs[--si];
    }

    return str;
}

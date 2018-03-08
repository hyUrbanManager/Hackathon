package com;

import org.junit.Test;

import java.util.Random;

/**
 * 字符串匹配kmp算法。
 * 说明
 * <p>
 * KMP算法看懂了觉得特别简单，思路很简单，看不懂之前，查各种资料，看的稀里糊涂，即使网上最简单的解释，依然看的稀里糊涂。
 * 我花了半天时间，争取用最短的篇幅大致搞明白这玩意到底是啥。
 * 这里不扯概念，只讲算法过程和代码理解：
 * <p>
 * KMP算法求解什么类型问题
 * <p>
 * 字符串匹配。给你两个字符串，寻找其中一个字符串是否包含另一个字符串，如果包含，返回包含的起始位置。
 * 如下面两个字符串：
 * <p>
 * char *str = "bacbababadababacambabacaddababacasdsd";
 * char *ptr = "ababaca";
 * 1
 * 2
 * str有两处包含ptr
 * 分别在str的下标10，26处包含ptr。
 * <p>
 * “bacbababadababacambabacaddababacasdsd”;\
 * 这里写图片描述
 * <p>
 * 问题类型很简单，下面直接介绍算法
 * <p>
 * 算法说明
 * <p>
 * 一般匹配字符串时，我们从目标字符串str（假设长度为n）的第一个下标选取和ptr长度（长度为m）一样的子字符串进行比较，如果一样，就返回开始处的下标值，不一样，选取str下一个下标，同样选取长度为n的字符串进行比较，直到str的末尾（实际比较时，下标移动到n-m）。这样的时间复杂度是O(n*m)。
 * <p>
 * KMP算法：可以实现复杂度为O(m+n)
 * <p>
 * 为何简化了时间复杂度：
 * 充分利用了目标字符串ptr的性质（比如里面部分字符串的重复性，即使不存在重复字段，在比较时，实现最大的移动量）。
 * 上面理不理解无所谓，我说的其实也没有深刻剖析里面的内部原因。
 * <p>
 * 考察目标字符串ptr：
 * ababaca
 * 这里我们要计算一个长度为m的转移函数next。
 * <p>
 * next数组的含义就是一个固定字符串的最长前缀和最长后缀相同的长度。
 * <p>
 * 比如：abcjkdabc，那么这个数组的最长前缀和最长后缀相同必然是abc。
 * cbcbc，最长前缀和最长后缀相同是cbc。
 * abcbc，最长前缀和最长后缀相同是不存在的。
 * <p>
 * *注意最长前缀：是说以第一个字符开始，但是不包含最后一个字符。
 * 比如aaaa相同的最长前缀和最长后缀是aaa。**
 * 对于目标字符串ptr，ababaca，长度是7，所以next[0]，next[1]，next[2]，next[3]，next[4]，next[5]，next[6]分别计算的是
 * a，ab，aba，abab，ababa，ababac，ababaca的相同的最长前缀和最长后缀的长度。由于a，ab，aba，abab，ababa，ababac，ababaca的相同的最长前缀和最长后缀是“”，“”，“a”，“ab”，“aba”，“”，“a”,所以next数组的值是[-1,-1,0,1,2,-1,0]，这里-1表示不存在，0表示存在长度为1，2表示存在长度为3。这是为了和代码相对应。
 * <p>
 * next数组就是说一旦在某处不匹配时（下图绿色位置A和B），移动ptr字符串，使str的对应的最大后缀（红色2）和ptr对应的最大前缀（红色3）对齐，然后比较A和Ｃ。
 * <p>
 * next数组的值，就是下次往前移动字符串ptr的移动距离。比如next中某个字符对应的值是4，则在该字符后的下一个字符不匹配时，可以直接移动往前移动ptr 5个长度，再次进行比较判别。
 * <p>
 * 这里写图片描述
 * <p>
 * 这里写图片描述
 * <p>
 * 代码解析
 * <p>
 * void cal_next(char *str, int *next, int len)
 * {
 * next[0] = -1;//next[0]初始化为-1，-1表示不存在相同的最大前缀和最大后缀
 * int k = -1;//k初始化为-1
 * for (int q = 1; q <= len-1; q++)
 * {
 * while (k > -1 && str[k + 1] != str[q])//如果下一个不同，那么k就变成next[k]，注意next[k]是小于k的，无论k取任何值。
 * {
 * k = next[k];//往前回溯
 * }
 * if (str[k + 1] == str[q])//如果相同，k++
 * {
 * k = k + 1;
 * }
 * next[q] = k;//这个是把算的k的值（就是相同的最大前缀和最大后缀长）赋给next[q]
 * }
 * }
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 7
 * 8
 * 9
 * 10
 * 11
 * 12
 * 13
 * 14
 * 15
 * 16
 * 17
 * KMP
 * <p>
 * 这个和next很像，具体就看代码，其实上面已经大概说完了整个匹配过程。
 * <p>
 * int KMP(char *str, int slen, char *ptr, int plen)
 * {
 * int *next = new int[plen];
 * cal_next(ptr, next, plen);//计算next数组
 * int k = -1;
 * for (int i = 0; i < slen; i++)
 * {
 * while (k >-1&& ptr[k + 1] != str[i])//ptr和str不匹配，且k>-1（表示ptr和str有部分匹配）
 * k = next[k];//往前回溯
 * if (ptr[k + 1] == str[i])
 * k = k + 1;
 * if (k == plen-1)//说明k移动到ptr的最末端
 * {
 * //cout << "在位置" << i-plen+1<< endl;
 * //k = -1;//重新初始化，寻找下一个
 * //i = i - plen + 1;//i定位到该位置，外层for循环i++可以继续找下一个（这里默认存在两个匹配字符串可以部分重叠），感谢评论中同学指出错误。
 * return i-plen+1;//返回相应的位置
 * }
 * }
 * return -1;
 * }
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 7
 * 8
 * 9
 * 10
 * 11
 * 12
 * 13
 * 14
 * 15
 * 16
 * 17
 * 18
 * 19
 * 20
 * 21
 * 测试
 * <p>
 * char *str = "bacbababadababacambabacaddababacasdsd";
 * char *ptr = "ababaca";
 * int a = KMP(str, 36, ptr, 7);
 * return 0;
 * 1
 * 2
 * 3
 * 4
 * 注意如果str里有多个匹配ptr的字符串，要想求出所有的满足要求的下标位置，在KMP算法需要稍微修改一下。见上面注释掉的代码。
 * <p>
 * 复杂度分析
 * <p>
 * next函数计算复杂度是(m)，开始以为是O(m^2)，后来仔细想了想，cal__next里的while循环，以及外层for循环，利用均摊思想，其实是O(m)，这个以后想好了再写上。
 *
 * @author hy 2018/3/8
 */
public class Kmp {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
//            String a = randomStr1();
//            String b = randomStr2();
            String a = "aabaabbbbaabbabaabab";
            String b = "abb";
            System.out.println(a);
            System.out.println(b);
            System.out.println(kmp(a.toCharArray(), b.toCharArray()));
            System.out.println();
        }
    }

    public String randomStr1() {
        Random random = new Random();
        char[] cc = new char[20];
        for (int i = 0; i < 20; i++) {
            char c = (char) (random.nextInt(2) + 'a');
            cc[i] = c;
        }
        return new String(cc);
    }

    public String randomStr2() {
        Random random = new Random();
        char[] cc = new char[3];
        for (int i = 0; i < 3; i++) {
            char c = (char) (random.nextInt(2) + 'a');
            cc[i] = c;
        }
        return new String(cc);
    }

    /**
     * 字符串匹配。
     *
     * @param src
     * @param des
     * @return
     */
    public int kmp(char[] src, char[] des) {
        int[] next = getNextTable(des);
        int i = 0, j = 0;
        while (i <= src.length - des.length) {
            while (j < des.length && src[i++] == des[j]) {
                j++;
            }
            if (j == des.length) {
                return i - des.length;
            } else {
                // 找到偏转的位置。
                if (j != 0) {
                    j = next[j - 1];
                }
            }
        }
        return -1;
    }

    /**
     * 获取Next数组，计算目标字符串前后缀匹配字符长度。从0开始。
     * TODO debug
     *
     * @param cs
     * @return
     */
    int[] getNextTable(char[] cs) {
        int len = cs.length;
        int[] next = new int[len];

        next[0] = 0;
        int k = 0;
        int j = 0;
        while (j < len - 1) {
            if (k == 0 || cs[j] == cs[k]) {
                k++;
                j++;
                next[j] = k;
            } else {
                k = next[k];
            }
        }

        return next;
    }

}

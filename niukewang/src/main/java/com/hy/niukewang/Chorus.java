package com.hy.niukewang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 合唱团，使用动态规划。
 * <p>
 * 题目描述
 * 有 n 个学生站成一排，每个学生有一个能力值，牛牛想从这 n 个学生中按照顺序选取 k 名学生，要求相邻两个学生的位置编号的差不超过 d，使得这 k 个学生的能力值的乘积最大，你能返回最大的乘积吗？
 * 输入描述:
 * 每个输入包含 1 个测试用例。每个测试数据的第一行包含一个整数 n (1 <= n <= 50)，表示学生的个数，接下来的一行，包含 n 个整数，按顺序表示每个学生的能力值 ai（-50 <= ai <= 50）。接下来的一行包含两个整数，k 和 d (1 <= k <= 10, 1 <= d <= 50)。
 * 输出描述:
 * 输出一行表示最大的乘积。
 * 示例1
 * 输入
 * <p>
 * 3
 * 7 4 7
 * 2 50
 * 输出
 * <p>
 * 49
 * <p>
 * 链接：https://www.nowcoder.com/questionTerminal/661c49118ca241909add3a11c96408c8
 * 来源：牛客网
 * <p>
 * 网易_合唱团解析
 * 1. 题目分析
 * 题目要求n各学生中选择k个，使这k个学生的能力值乘积最大。这是一个最优化的问题。另外，在优化过程中，提出了相邻两个学生的位置编号差不超过d的约束。
 * 如果不用递归或者动态规划，问题很难入手，并且，限制条件d也需要对每一个进行约束，编程十分复杂
 * 所以，解决的方法是采用动态规划（理由：1.求解的是最优化问题；2.可以分解为最优子结构）
 * 2. 问题分解
 * 1.对该问题的分解是关键。
 * 从n个学生中，选择k个，可以看成是：先从n个学生里选择最后1个，然后在剩下的里选择k-1个，并且让这1个和前k-1个满足约束条件
 * 2.数学描述
 * 为了能够编程实现，需要归纳出其递推公式，而在写递推公式之前，首先又需要对其进行数学描述
 * 记第k个人的位置为one,则可以用f[one][k]表示从n个人中选择k个的方案。然后，它的子问题，需要从one前面的left个人里面，选择k-1个，这里left表示k-1个人中最后一个（即第k-1个）人的位置，因此，子问题可以表示成f[left][k-1].
 * 学生能力数组记为arr[n+1],第i个学生的能力值为arr[i]
 * one表示最后一个人，其取值范围为[1,n];
 * left表示第k-1个人所处的位置，需要和第k个人的位置差不超过d，因此
 * max{k-1,one-d}<=left<=one-1
 * 在n和k定了之后，需要求解出n个学生选择k个能力值乘积的最大值。因为能力值有正有负，所以
 * 当one对应的学生能力值为正时，
 * f[one][k] = max{f[left][k-1]arr[i]}(min{k-1,one-d}<=left<=one-1);
 * 当one对应的学生能力值为负时
 * f[one][k] = max{g[left][k-1]arr[i]}(min{k-1,one-d}<=left<=one-1);
 * 此处g[][]是存储n个选k个能力值乘积的最小值数组
 * 3.编程实现
 * 遍历。因为一般看解答的多半是自己遇到问题不大会的，所以程序里面有写注释。如果大家不懂可以再问我，我回复或者再把解答写详细点。欢迎讨论。
 * import java.util.Scanner;
 * <p>
 * public class Main_jrh_AC {
 * public static void main(String[] args){
 * Scanner sc = new Scanner(System.in);
 * while(sc.hasNext()) {
 * //总人数
 * int n = sc.nextInt();
 * //学生能力值数组，第i个人直接对应arr[i]
 * int[] arr = new int[n + 1];
 * //初始化
 * for (int i = 1; i <= n; i++) {//人直接对应坐标
 * arr[i] = sc.nextInt();
 * }
 * //选择的学生数
 * int kk = sc.nextInt();
 * //间距
 * int dd = sc.nextInt();
 * <p>
 * <p>
 * 递推的时候，以f[one][k]的形式表示
 * 其中：one表示最后一个人的位置，k为包括这个人，一共有k个人
 * 原问题和子问题的关系：f[one][k]=max{f[left][k-1]*arr[one],g[left][k-1]*arr[one]}
 * <p>
 * //规划数组
 * long[][]f=new long[n+1][kk+1];//人直接对应坐标,n和kk都要+1
 * long[][]g=new long[n+1][kk+1];
 * //初始化k=1的情况
 * for(int one=1;one<=n;one++){
 * f[one][1]=arr[one];
 * g[one][1]=arr[one];
 * }
 * //自底向上递推
 * for(int k=2;k<=kk;k++){
 * for(int one=k;one<=n;one++){
 * //求解当one和k定的时候，最大的分割点
 * long tempmax=Long.MIN_VALUE;
 * long tempmin=Long.MAX_VALUE;
 * for(int left=Math.max(k-1,one-dd);left<=one-1;left++){
 * if(tempmax<Math.max(f[left][k-1]*arr[one],g[left][k-1]*arr[one])){
 * tempmax=Math.max(f[left][k-1]*arr[one],g[left][k-1]*arr[one]);
 * }
 * if(tempmin>Math.min(f[left][k-1]*arr[one],g[left][k-1]*arr[one])){
 * tempmin=Math.min(f[left][k-1]*arr[one],g[left][k-1]*arr[one]);
 * }
 * }
 * f[one][k]=tempmax;
 * g[one][k]=tempmin;
 * }
 * }
 * //n选k最大的需要从最后一个最大的位置选
 * long result=Long.MIN_VALUE;
 * for(int one=kk;one<=n;one++){
 * if(result<f[one][kk]){
 * result=f[one][kk];
 * }
 * }
 * System.out.println(result);
 * }
 * }
 * }
 *
 * @author hy 2018/3/7
 */
public class Chorus {

    public static void main(String[] args) throws Exception {
        File f = new File("G:\\AndroidStudioProject2\\Hackathon\\niukewang\\" +
                "src\\main\\java\\com\\hy\\niukewang\\Chorus.txt");
        FileInputStream fis = new FileInputStream(f);
        Scanner scanner = new Scanner(fis);

        int num = scanner.nextInt();
        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();
        int d = scanner.nextInt();


    }

}

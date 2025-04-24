

public class Bit {
    
    // 下一个大于 x 的 二进制1个数与 x 等同的数
    int g(int x){
        int o = x & -x;
        int t = x + o;
        int c = t & -t;
        int m = (c / o >> 1) - 1;
        return t | m;
    }

    /*
    对集合 s 的子集遍历：
    for (int i = s; i > 0; i = (i - 1) & s) {
        
    }
    */

    // 求前 n 个整数的二进制状态1的个数
    long[] cnt(long n) {
        n++;
        long[] ans = new long[63];
        long pre = 0;
        for (int i = 62; i >= 0; i--) {
            ans[i] += pre >> 1;
            if ((n >> i & 1) != 0) {
                pre |= 1L << i;
                n ^= 1L << i;
                ans[i] += n;
            }
        }
        return ans;
    }

    // 获取某一位上二进制 1 的个数
    long get(long n, int k){
        return (n + 1) / (1L << k + 1) * (1L << k) + Math.max((n + 1) % (1L << k + 1) - (1L << k), 0L);
    }

}

package algorithm.math;

public class PreNBitStatus {
    
    // 求前 n 个整数的二进制状态1的个数
    static long[] cnt(long n) {
        n++;
        long[] ans = new long[61];
        long pre = 0;
        for (int i = 60; i >= 0; i--) {
            ans[i] += pre >> 1;
            if ((n >> i & 1) != 0) {
                pre |= 1L << i;
                n ^= 1L << i;
                ans[i] += n;
            }
        }
        return ans;
    }
}

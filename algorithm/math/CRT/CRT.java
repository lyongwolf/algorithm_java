package algorithm.math.CRT;
import static algorithm.zz.U.*;

import java.util.*;


/**
 * 中国剩余定理
 * 测试链接：https://www.luogu.com.cn/problem/P1495
 */
public class CRT {

    long MOD;

    void solve() {
        int n = ni();
        long[] a = new long[n], b = new long[n];
        long s = 1;
        for (int i = 0; i < n; i++) {
            a[i] = nl();
            b[i] = nl();
            s *= a[i];
        }
        if (n == 1) {
            println(b[0]);
            return;
        }
        MOD = s;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            long x = s / a[i];
            ans = (ans + times(times(b[i], x), inv(x, a[i]))) % MOD;
        }
        println(ans);
    }

    long times(long a, long b) {
        long res = 0;
        while (b > 0) {
            if ((b & 1) != 0) {
                res = (res + a) % MOD;
            }
            a = (a + a) % MOD;
            b >>= 1;
        }
        return res;
    }

    long inv(long a, long m) {
        return (exgcd(a, m)[0] % m + m) % m;
    }

    long[] exgcd(long a, long b) {
        if (b == 0) {
            return new long[]{1, 0};
        }
        long[] ans = exgcd(b, a % b);
        ans[1] = ans[0] - a / b * (ans[0] = ans[1]);
        return ans;
    }

}

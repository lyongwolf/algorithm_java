package algorithm.math.CRT;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 扩展中国剩余定理
 * 测试链接：https://www.luogu.com.cn/problem/P4777
 */

public class EXCRT {

    void solve() {
        int n = sc.nextInt();
        long[] m = new long[n], r = new long[n];
        for (int i = 0; i < n; i++) {
            m[i] = sc.nextLong();
            r[i] = sc.nextLong();
        }
        long lcm = 1, tail = 0;
        for (int i = 0; i < n; i++) {
            long a = lcm, b = m[i], c = r[i] - tail;
            long[] ans = exgcd(a, b);
            long x0 = ans[0], y0 = ans[1], g = a * x0 + b * y0;
            if (c % g != 0) {
                out.println(-1);
                return;
            }
            lcm = a / g * b;
            x0 = times(x0, c / g, b / g);
            tail = (tail + times(a, x0, lcm)) % lcm;
        }
        out.println(tail);
    }

    long[] exgcd(long a, long b) {
        if (b == 0) {
            return new long[]{1, 0};
        }
        long[] ans = exgcd(b, a % b);
        ans[1] = ans[0] - a / b * (ans[0] = ans[1]);
        return ans;
    }

    long times(long a, long b, long m) {
        a = (a % m + m) % m;
        b = (b % m + m) % m;
        long res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = (res + a) % m;
            }
            a = (a + a) % m;
            b >>= 1;
        }
        return res;
    }
    
}

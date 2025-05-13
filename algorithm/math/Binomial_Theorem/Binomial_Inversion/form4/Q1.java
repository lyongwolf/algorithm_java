package algorithm.math.Binomial_Theorem.Binomial_Inversion.form4;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 测试链接：https://www.luogu.com.cn/problem/P10596
 */

public class Q1 {

    int MOD = (int) 1e9 + 7, MAXN = (int) 1e6;
    long[] F = new long[MAXN + 1], IF = new long[MAXN + 1];

    Q1() {
        F[0] = F[1] = 1;
        for (int i = 2; i <= MAXN; i++) {
            F[i] = F[i - 1] * i % MOD;
        }
        IF[MAXN] = pow(F[MAXN], MOD - 2);
        for (int i = MAXN - 1; i >= 0; i--) {
            IF[i] = IF[i + 1] * (i + 1) % MOD;
        }
    }

    void solve() {
        int n = ni(), k = ni();
        long[] g = new long[n + 1];
        g[n] = 2;
        for (int i = n - 1; i >= 0; i--) {
            g[i] = g[i + 1] * g[i + 1] % MOD;
        }
        for (int i = 0; i <= n; i++) {
            g[i] = (g[i] - 1 + MOD) * comb(n, i) % MOD;
        }
        long ans = 0;
        for (int i = k; i <= n; i++) {
            if (((i - k) & 1) == 0) {
                ans += comb(i, k) * g[i] % MOD;
            } else {
                ans += comb(i, k) * g[i] % MOD * (MOD - 1) % MOD;
            }
        }
        println(ans % MOD);
    }

    long comb(int n, int m) {
        return F[n] * IF[n - m] % MOD * IF[m] % MOD;
    }

    long pow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) != 0) {
                res = (res * a) % MOD;
            }
            a = a * a % MOD;
            b >>= 1;
        }
        return res;
    }
}
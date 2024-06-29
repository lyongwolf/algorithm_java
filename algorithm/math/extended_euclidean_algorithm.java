package algorithm.math;

/**
 * 扩展欧几里得算法
 */

public class extended_euclidean_algorithm {
    
    public static boolean linearEquation(long[] ans, long a, long b, long m) {
        long d = ext_gcd(ans, a, b);
        if (m % d != 0) {
            return false;
        }
        long n = m / d;
        ans[0] *= n;
        ans[1] *= n;
        return true;
    }

    public static long ext_gcd(long[] ans, long a, long b) {
        if (b == 0) {
            ans[0] = 1;
            ans[1] = 0;
            return a;
        }
        long res = ext_gcd(ans, b, a % b);
        long tmp = ans[0];
        ans[0] = ans[1];
        ans[1] = tmp - a / b * ans[1];
        return res;
    }
}

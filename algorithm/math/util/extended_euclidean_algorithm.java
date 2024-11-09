package algorithm.math.util;

/**
 * 扩展欧几里得算法
 */

public class extended_euclidean_algorithm {
    
    static int[] exgcd(int a, int b) {
        if (b == 0) {
            return new int[]{1, 0};
        }
        int[] ans = exgcd(b, a % b);
        ans[1] = ans[0] - a / b * (ans[0] = ans[1]);
        return ans;
    }
}

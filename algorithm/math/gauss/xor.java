package algorithm.math.gauss;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 高斯消元解异或方程组
 * 测试链接：https://acm.hdu.edu.cn/showproblem.php?pid=5833
 */
public class xor {

    int testCase;
    int MOD = (int) 1e9 + 7;
    long[] A = new long[301];
    int[] p = new int[303];
    xor() {
        A[0] = 1;
        for (int i = 1; i <= 300; i++) {
            A[i] = A[i - 1] * 2 % MOD;
        }
        boolean[] isP = new boolean[2001];
        for (int i = 2; i <= 2000; i++) {
            isP[i] = true;
        }
        for (int i = 2, k = 0; i <= 2000; i++) {
            if (isP[i]) {
                p[k++] = i;
                for (int j = i; j * i <= 2000; j++) {
                    isP[j * i] = false;
                }
            }
        }
    }

    void solve() {
        int n = ni();
        int[][] mat = new int[303][304];
        for (int j = 0; j < n; j++) {
            long v = nl();
            for (int i = 0; i < 303; i++) {
                while (v % p[i] == 0) {
                    v /= p[i];
                    mat[i][j] ^= 1;
                }
            }
        }
        for (int i = 0; i < 303; i++) {
            for (int j = 0, b = i; j < 303; j++) {
                if (j < i && mat[j][j] == 1) {
                    continue;
                }
                if (mat[j][i] == 1) {
                    int[] tmp = mat[b];
                    mat[b] = mat[j];
                    mat[j] = tmp;
                    break;
                }
            }
            if (mat[i][i] == 0) {
                continue;
            }
            for (int j = 0; j < 303; j++) {
                if (i != j && mat[j][i] == 1) {
                    for (int k = i; k < 304; k++) {
                        mat[j][k] ^= mat[i][k];
                    }
                }
            }
        }
        int cnt = n;
        for (int i = 0; i < n; i++) {
            cnt -= mat[i][i];
        }
        println("Case #" + (++testCase) + ":");
        println((A[cnt] - 1 + MOD) % MOD);
    }

}

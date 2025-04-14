package algorithm.math.gauss;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 高斯消元
 * 解 n 元线性一次方程组
 * 测试链接：https://www.luogu.com.cn/problem/P2455
 */
public class add {

    double eps = 1e-7;

    void solve() {
        int n = sc.nextInt();
        double[][] mat = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                mat[i][j] = sc.nextDouble();
            }
        }
        for (int i = 0; i < n; i++) {
            int max = i;
            for (int j = 0; j < n; j++) {
                if (j < i && Math.abs(mat[j][j]) >= eps) {
                    continue;
                }
                if (Math.abs(mat[j][i]) > Math.abs(mat[max][i])) {
                    max = j;
                }
            }
            double[] tmp = mat[i];
            mat[i] = mat[max];
            mat[max] = tmp;
            if (Math.abs(mat[i][i]) >= eps) {
                for (int j = n; j >= i; j--) {
                    mat[i][j] /= mat[i][i];
                }
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        for (int k = n; k >= i; k--) {
                            mat[j][k] -= mat[i][k] * mat[j][i];
                        }
                    }
                }
            }
        }
        boolean multi = false;
        for (int i = 0; i < n; i++) {
            if (Math.abs(mat[i][i]) < eps) {
                if (Math.abs(mat[i][n]) >= eps) {
                    out.println(-1);
                    return;
                }
                multi = true;
            }
        }
        if (multi) {
            out.println(0);
            return;
        }
        for (int i = 0; i < n; i++) {
            out.println(mat[i][n], 2);
        }
    }

   
}
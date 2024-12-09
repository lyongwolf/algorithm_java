package algorithm.math.util;

public class matrix {
    
    // 矩阵乘法
    static long[][] mul(long[][] mat1, long[][] mat2) {
        if (mat1[0].length != mat2.length) {
            return null;
        }
        int n = mat1.length, m = mat2[0].length;
        long[][] ans = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < mat2.length; k++) {
                    ans[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }
        return ans;
    }
}

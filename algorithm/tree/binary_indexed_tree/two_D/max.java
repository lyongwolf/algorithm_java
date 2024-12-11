package algorithm.tree.binary_indexed_tree.two_D;

public class max {
    
    static class Fenwick {
        private long[][] max;
        private int N, M;

        public Fenwick(int n, int m) {
            N = n + 1;
            M = m + 1;
            max = new long[N + 1][M + 1];
            for (int i = 0; i <= N; i++) {
                for (int j = 0; j <= M; j++) {
                    max[i][j] = Long.MIN_VALUE;
                }
            }
        }

        public void update(int x, int y, long v) {
            for (int i = x; i < N; i += i & -i) {
                for (int j = y; j < M; j += j & -j) {
                    max[i][j] = Math.max(max[i][j], v);
                }
            }
        }

        public long query(int x, int y) {
            long ans = Long.MIN_VALUE;
            for (int i = x; i < N; i += i & -i) {
                for (int j = y; j < M; j += j & -j) {
                    ans = Math.max(ans, max[i][j]);
                }
            }
            return ans;
        }
    }
}

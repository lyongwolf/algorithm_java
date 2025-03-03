package algorithm.tree.binary_indexed_tree.two_D;

public class sum {
    
    class Fenwick {
        private long[][] sum;
        private int N, M;

        public Fenwick(int n, int m) {
            N = n + 1;
            M = m + 1;
            sum = new long[N + 1][M + 1];
        }

        public void add(int x, int y, long v) {
            for (int i = x; i < N; i += i & -i) {
                for (int j = y; j < M; j += j & -j) {
                    sum[i][j] += v;
                }
            }
        }

        public long query(int x, int y) {
            long ans = 0;
            for (int i = x; i < N; i += i & -i) {
                for (int j = y; j < M; j += j & -j) {
                    ans += sum[i][j];
                }
            }
            return ans;
        }

        public long range(int x1, int y1, int x2, int y2) {
            return query(x2, y2) + query(x1 - 1, y1 - 1) - query(x2, y1 - 1) - query(x1 - 1, y2); 
        }
    }
}

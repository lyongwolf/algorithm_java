class Fenwick {
    private long[][] min;
    private int N, M;

    public Fenwick(int n, int m) {
        N = n + 1;
        M = m + 1;
        min = new long[N + 1][M + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                min[i][j] = Long.MAX_VALUE;
            }
        }
    }

    public void update(int x, int y, long v) {
        for (int i = x; i < N; i += i & -i) {
            for (int j = y; j < M; j += j & -j) {
                min[i][j] = Math.min(min[i][j], v);
            }
        }
    }

    public long query(int x, int y) {
        long ans = Long.MAX_VALUE;
        for (int i = x; i < N; i += i & -i) {
            for (int j = y; j < M; j += j & -j) {
                ans = Math.min(ans, min[i][j]);
            }
        }
        return ans;
    }
}

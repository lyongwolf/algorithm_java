class Fenwick {
    private static final int MIN = -(int) 1e9;
    private int[] mx;
    private int N;

    public Fenwick(int len) {
        N = len;
        mx = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            mx[i] = MIN;
        }
    }

    public void update(int i, int v) {
        while (i <= N) {
            mx[i] = Math.max(mx[i], v);
            i += i & -i;
        }
    }

    public int query(int i) {
        int ans = MIN;
        while (i > 0) {
            ans = Math.max(ans, mx[i]);
            i -= i & -i;
        }
        return ans;
    }

}
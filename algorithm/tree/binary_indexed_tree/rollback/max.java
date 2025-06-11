class Fenwick {
    private static final int MIN = -0x3f3f3f3f;
    private int[] mx;
    private boolean[] mark;
    private int[] use;
    private int n, z;

    public Fenwick(int len) {
        n = len;
        mx = new int[n + 1];
        mark = new boolean[n + 1];
        use = new int[n];
        for (int i = 1; i <= n; i++) {
            mx[i] = MIN;
        }
    }

    public void setMax(int i, int v) {
        while (i <= n) {
            if (mx[i] < v) {
                mx[i] = v;
                if (!mark[i]) {
                    mark[i] = true;
                    use[z++] = i;
                }
            }
            i += i & -i;
        }
    }

    public int pre(int i) {
        int ans = MIN;
        while (i > 0) {
            ans = Math.max(ans, mx[i]);
            i -= i & -i;
        }
        return ans;
    }

    public void clear() {
        for (int i = 0; i < z; i++) {
            mx[use[i]] = MIN;
            mark[use[i]] = false;
        }
        z = 0;
    }
}

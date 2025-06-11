class Fenwick {
    private static final int MAX = 0x3f3f3f3f;
    private int[] mn;
    private boolean[] mark;
    private int[] use;
    private int n, z;

    public Fenwick(int len) {
        n = len;
        mn = new int[n + 1];
        mark = new boolean[n + 1];
        use = new int[n];
        for (int i = 1; i <= n; i++) {
            mn[i] = MAX;
        }
    }

    public void setMin(int i, int v) {
        while (i <= n) {
            if (mn[i] > v) {
                mn[i] = v;
                if (!mark[i]) {
                    mark[i] = true;
                    use[z++] = i;
                }
            }
            i += i & -i;
        }
    }

    public int pre(int i) {
        int ans = MAX;
        while (i > 0) {
            ans = Math.min(ans, mn[i]);
            i -= i & -i;
        }
        return ans;
    }

    public void clear() {
        for (int i = 0; i < z; i++) {
            mn[use[i]] = MAX;
            mark[use[i]] = false;
        }
        z = 0;
    }
}
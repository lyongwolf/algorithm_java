class Fenwick {
    private int[] sum;
    private boolean[] mark;
    private int[] use;
    private int n, z;

    public Fenwick(int len) {
        n = len;
        sum = new int[n + 1];
        mark = new boolean[n + 1];
        use = new int[n];
    }

    public void add(int i, int v) {
        while (i <= n) {
            sum[i] += v;
            if (!mark[i]) {
                mark[i] = true;
                use[z++] = i;
            }
            i += i & -i;
        }
    }

    public int pre(int i) {
        int ans = 0;
        while (i > 0) {
            ans += sum[i];
            i -= i & -i;
        }
        return ans;
    }

    public int suf(int i) {
        return range(i, n);
    }

    public int range(int l, int r) {
        return l > r ? 0 : pre(r) - pre(l - 1);
    }

    public void clear() {
        for (int i = 0; i < z; i++) {
            sum[use[i]] = 0;
            mark[use[i]] = false;
        }
        z = 0;
    }
}

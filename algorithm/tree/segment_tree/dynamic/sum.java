class SegTree {
    private static final int MAXT = 3000000;
    private static int[] lc = new int[MAXT], rc = new int[MAXT];
    private static long[] sum = new long[MAXT];
    private static int no;
    
    private final int root, low, high;

    public SegTree(int low, int high) {
        this.low = low;
        this.high = high;
        root = ++no;
    }

    public static void clear() {
        for (int i = 1; i <= no; i++) {
            sum[i] = lc[i] = rc[i] = 0;
        }
        no = 0;
    }

    public void set(int o, long v) {
        set(o, v, low, high, root);
    }

    private void set(int o, long v, int l, int r, int i) {
        if (l == r) {
            sum[i] = v;
            return;
        }
        int m = l + (r - l) / 2;
        if (o <= m) {
            if (lc[i] == 0) {
                lc[i] = ++no;
            }
            set(o, v, l, m, lc[i]);
        } else {
            if (rc[i] == 0) {
                rc[i] = ++no;
            }
            set(o, v, m + 1, r, rc[i]);
        }
        sum[i] = sum[lc[i]] + sum[rc[i]];
    }

    public void add(int o, long v) {
        add(o, v, low, high, root);
    }

    private void add(int o, long v, int l, int r, int i) {
        sum[i] += v;
        if (l == r) {
            return;
        }
        int m = l + (r - l) / 2;
        if (o <= m) {
            if (lc[i] == 0) {
                lc[i] = ++no;
            }
            add(o, v, l, m, lc[i]);
        } else {
            if (rc[i] == 0) {
                rc[i] = ++no;
            }
            add(o, v, m + 1, r, rc[i]);
        }
    }

    public long query(int l, int r) {
        return query(l, r, low, high, root);
    }

    private long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return sum[i];
        }
        int m = l + (r - l) / 2;
        long ans = 0;
        if (L <= m && lc[i] != 0) {
            ans += query(L, R, l, m, lc[i]);
        }
        if (R > m && rc[i] != 0) {
            ans += query(L, R, m + 1, r, rc[i]);
        }
        return ans;
    }

}
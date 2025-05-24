class SegTree {
    private static final int MAXT = 3000000;
    private static int[] lc = new int[MAXT], rc = new int[MAXT];
    private static long[] sum = new long[MAXT], tag = new long[MAXT];
    private static int no;
    
    private final int root, low, high;

    public SegTree(int low, int high) {
        this.low = low;
        this.high = high;
        root = ++no;
    }

    public static void clear() {
        for (int i = 1; i <= no; i++) {
            tag[i] = sum[i] = lc[i] = rc[i] = 0;
        }
        no = 0;
    }

    public void add(int l, int r, long v) {
        add(l, r, v, low, high, root);
    }

    private void add(int L, int R, long v, int l, int r, int i) {
        if (L <= l && r <= R) {
            tag[i] += v;
            sum[i] += v * (r - l + 1);
            return;
        }
        int m = l + (r - l) / 2;
        down(i, m - l + 1, r - m);
        if (L <= m) {
            add(L, R, v, l, m, lc[i]);
        }
        if (R > m) {
            add(L, R, v, m + 1, r, rc[i]);
        }
        sum[i] = sum[lc[i]] + sum[rc[i]];
    }

    public long query(int l, int r) {
        return query(l, r, low, high, root);
    }

    private long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return sum[i];
        }
        int m = l + (r - l) / 2;
        down(i, m - l + 1, r - m);
        long ans = 0;
        if (L <= m) {
            ans += query(L, R, l, m, lc[i]);
        }
        if (R > m) {
            ans += query(L, R, m + 1, r, rc[i]);
        }
        return ans;
    }

    public void down(int i, int ln, int rn) {
        if (lc[i] == 0) {
            lc[i] = ++no;
            rc[i] = ++no;
        }
        if (tag[i] != 0) {
            tag[lc[i]] += tag[i];
            tag[rc[i]] += tag[i];
            sum[lc[i]] += tag[i] * ln;
            sum[rc[i]] += tag[i] * rn;
            tag[i] = 0;
        }
    }

}
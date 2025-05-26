class SegTree {
    private static final int MAXT = 3000000;
    private static final long MIN = Long.MIN_VALUE, INIT = 0;
    private static int[] lc = new int[MAXT], rc = new int[MAXT];
    private static long[] mx = new long[MAXT];
    private static int no;
    static {
        java.util.Arrays.fill(mx, INIT);
    }
    
    private final int root, low, high;

    public SegTree(int low, int high) {
        this.low = low;
        this.high = high;
        root = ++no;
    }

    public static void clear() {
        for (int i = 1; i <= no; i++) {
            lc[i] = rc[i] = 0; 
            mx[i] = INIT;
        }
        no = 0;
    }

    public void set(int o, long v) {
        set(o, v, low, high, root);
    }

    private void set(int o, long v, int l, int r, int i) {
        if (l == r) {
            mx[i] = v;
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
        mx[i] = Math.max(mx[lc[i]], mx[rc[i]]);
    }

    public void add(int o, long v) {
        add(o, v, low, high, root);
    }

    private void add(int o, long v, int l, int r, int i) {
        if (l == r) {
            mx[i] += v;
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
        mx[i] = Math.max(mx[lc[i]], mx[rc[i]]);
    }

    public long query(int l, int r) {
        return query(l, r, low, high, root);
    }

    private long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return mx[i];
        }
        int m = l + (r - l) / 2;
        long ans = MIN;
        if (L <= m) {
            ans = Math.max(ans, lc[i] == 0 ? INIT : query(L, R, l, m, lc[i]));
        }
        if (R > m) {
            ans = Math.max(ans, rc[i] == 0 ? INIT : query(L, R, m + 1, r, rc[i]));
        }
        return ans;
    }
}

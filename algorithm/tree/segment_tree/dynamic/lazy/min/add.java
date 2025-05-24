class SegTree {
    private static final int MAXT = 3000000;
    private static final long MAX = Long.MAX_VALUE, INIT = 0;
    private static int[] lc = new int[MAXT], rc = new int[MAXT];
    private static long[] mn = new long[MAXT], tag = new long[MAXT];
    private static int no;
    static {
        java.util.Arrays.fill(mn, INIT);
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
            mn[i] = INIT;
            tag[i] = 0;
        }
        no = 0;
    }

    public void add(int l, int r, long v) {
        add(l, r, v, low, high, root);
    }

    private void add(int L, int R, long v, int l, int r, int i) {
        if (L <= l && r <= R) {
            tag[i] += v;
            mn[i] += v;
            return;
        }
        int m = l + (r - l) / 2;
        down(i);
        if (L <= m) {
            add(L, R, v, l, m, lc[i]);
        }
        if (R > m) {
            add(L, R, v, m + 1, r, rc[i]);
        }
        mn[i] = Math.min(mn[lc[i]], mn[rc[i]]);
    }

    public long query(int l, int r) {
        return query(l, r, low, high, root);
    }

    private long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return mn[i];
        }
        int m = l + (r - l) / 2;
        down(i);
        long ans = MAX;
        if (L <= m) {
            ans = Math.min(ans, query(L, R, l, m, lc[i]));
        }
        if (R > m) {
            ans = Math.min(ans, query(L, R, m + 1, r, rc[i]));
        }
        return ans;
    }

    void down(int i) {
        if (lc[i] == 0) {
            lc[i] = ++no;
            rc[i] = ++no;
        }
        if (tag[i] != 0) {
            tag[lc[i]] += tag[i];
            tag[rc[i]] += tag[i];
            mn[lc[i]] += tag[i];
            mn[rc[i]] += tag[i];
            tag[i] = 0;
        }
    }

    void up(int i) {
        mn[i] = Math.min(mn[lc[i]], mn[rc[i]]);
    }

}
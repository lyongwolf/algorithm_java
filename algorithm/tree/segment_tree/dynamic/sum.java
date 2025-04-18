package algorithm.tree.segment_tree.dynamic;

import java.util.Arrays;

class SegTree {
    private int[] lc, rc;
    private long[] sum;
    private int no;
    private final int low, high;

    public SegTree(int low, int high) {
        this.low = low;
        this.high = high;
        sum = new long[2];
        lc = new int[2];
        rc = new int[2];
        no = 1;
    }

    public void set(int o, long v) {
        set(o, v, low, high, 1);
    }

    private void set(int o, long v, int l, int r, int i) {
        if (l == r) {
            sum[i] = v;
            return;
        }
        int m = (l + r) / 2;
        down(i);
        if (o <= m) {
            set(o, v, l, m, lc[i]);
        } else {
            set(o, v, m + 1, r, rc[i]);
        }
        sum[i] = sum[lc[i]] + sum[rc[i]];
    }

    public void add(int o, long v) {
        add(o, v, low, high, 1);
    }

    private void add(int o, long v, int l, int r, int i) {
        if (l == r) {
            sum[i] += v;
            return;
        }
        int m = (l + r) / 2;
        down(i);
        if (o <= m) {
            add(o, v, l, m, lc[i]);
        } else {
            add(o, v, m + 1, r, rc[i]);
        }
        sum[i] = sum[lc[i]] + sum[rc[i]];
    }

    public long query(int l, int r) {
        return query(l, r, low, high, 1);
    }

    private long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return sum[i];
        }
        int m = (l + r) / 2;
        down(i);
        long ans = 0;
        if (L <= m) {
            ans += query(L, R, l, m, lc[i]);
        }
        if (R > m) {
            ans += query(L, R, m + 1, r, rc[i]);
        }
        return ans;
    }

    private void down(int i) {
        if (lc[i] == 0) {
            create();
            lc[i] = no;
            create();
            rc[i] = no;
        }
    }

    private void create() {
        if (++no == sum.length) {
            sum = Arrays.copyOf(sum, no << 1);
            lc = Arrays.copyOf(lc, no << 1);
            rc = Arrays.copyOf(rc, no << 1);
        }
    }
}
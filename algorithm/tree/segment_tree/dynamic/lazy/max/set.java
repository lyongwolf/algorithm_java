package algorithm.tree.segment_tree.dynamic.lazy.max;
import java.util.*;

class SegTree {
    private static final int MAXT = 3000000;
    private static final long MIN = Long.MIN_VALUE, INIT = 0;
    private static int[] lc = new int[MAXT], rc = new int[MAXT];
    private static long[] mx = new long[MAXT], tag = new long[MAXT];
    private static int no;
    static {
        Arrays.fill(mx, INIT);
        Arrays.fill(tag, Long.MIN_VALUE);
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
            tag[i] = Long.MIN_VALUE;
        }
        no = 0;
    }

    public void set(int l, int r, long v) {
        set(l, r, v, low, high, root);
    }

    private void set(int L, int R, long v, int l, int r, int i) {
        if (L <= l && r <= R) {
            tag[i] = v;
            mx[i] = v;
            return;
        }
        int m = l + (r - l) / 2;
        down(i);
        if (L <= m) {
            set(L, R, v, l, m, lc[i]);
        }
        if (R > m) {
            set(L, R, v, m + 1, r, rc[i]);
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
        down(i);
        long ans = MIN;
        if (L <= m) {
            ans = Math.max(ans, query(L, R, l, m, lc[i]));
        }
        if (R > m) {
            ans = Math.max(ans, query(L, R, m + 1, r, rc[i]));
        }
        return ans;
    }

    void down(int i) {
        if (lc[i] == 0) {
            lc[i] = ++no;
            rc[i] = ++no;
        }
        if (tag[i] != Long.MIN_VALUE) {
            tag[lc[i]] = tag[i];
            tag[rc[i]] = tag[i];
            mx[lc[i]] = tag[i];
            mx[rc[i]] = tag[i];
            tag[i] = Long.MIN_VALUE;
        }
    }

    void up(int i) {
        mx[i] = Math.max(mx[lc[i]], mx[rc[i]]);
    }

}

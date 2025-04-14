package algorithm.tree.segment_tree.lazy.min;

import java.util.Arrays;
/**
 * 区间设置值
 */
class SegTree {
    private long[] mn;
    private long[] lazy;
    private int N;

    public SegTree(int len) {
        N = len;
        int tot = 1 << (33 - Integer.numberOfLeadingZeros(N - 1));
        mn = new long[tot];
        lazy = new long[tot];
        Arrays.fill(mn, Long.MAX_VALUE);
        Arrays.fill(lazy, Long.MIN_VALUE);
    }

    public SegTree(int[] arr) {
        N = arr.length - 1;
        int tot = 1 << (33 - Integer.numberOfLeadingZeros(N - 1));
        mn = new long[tot];
        lazy = new long[tot];
        Arrays.fill(lazy, Long.MIN_VALUE);
        build(arr, 1, N, 1);
    }

    private void build(int[] arr, int l, int r, int i) {
        if (l == r) {
            mn[i] = arr[l];
            return;
        }
        int m = (l + r) >> 1;
        build(arr, l, m, i << 1);
        build(arr, m + 1, r, i << 1 | 1);
        up(i);
    }

    private void up(int i) {
        mn[i] = Math.min(mn[i << 1], mn[i << 1 | 1]);
    }

    private void down(int i) {
        if (lazy[i] != Long.MIN_VALUE) {
            lazy[i << 1] = lazy[i];
            lazy[i << 1 | 1] = lazy[i];
            mn[i << 1] = lazy[i];
            mn[i << 1 | 1] = lazy[i];
            lazy[i] = Long.MIN_VALUE;
        }
    }

    public void set(int l, int r, long v) {
        set(l, r, v, 1, N, 1);
    }

    private void set(int L, int R, long v, int l, int r, int i) {
        if (L <= l && r <= R) {
            lazy[i] = v;
            mn[i] = v;
            return;
        }
        int m = (l + r) >> 1;
        down(i);
        if (L <= m) {
            set(L, R, v, l, m, i << 1);
        }
        if (R > m) {
            set(L, R, v, m + 1, r, i << 1 | 1);
        }
        up(i);
    }

    public long query(int l, int r) {
        return query(l, r, 1, N, 1);
    }

    private long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return mn[i];
        }
        int m  = (l + r) >> 1;
        down(i);
        long ans = Long.MAX_VALUE;
        if (L <= m) {
            ans = Math.min(ans, query(L, R, l, m, i << 1));
        }
        if (R > m) {
            ans = Math.min(ans, query(L, R, m + 1, r, i << 1 | 1));
        }
        return ans;
    }

    public long query(int o) {
        return query(o, 1, N, 1);
    }

    private long query(int o, int l, int r, int i) {
        if (l == r) {
            return mn[i];
        }
        int m  = (l + r) >> 1;
        down(i);
        if (o <= m) {
            return query(o, l, m, i << 1);
        } else {
            return query(o, m + 1, r, i << 1 | 1);
        }
    }
}
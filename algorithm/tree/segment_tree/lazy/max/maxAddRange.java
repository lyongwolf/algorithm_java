package algorithm.tree.segment_tree.lazy.max;
/**
 * 区间添加值
 */
class SegTree {
    private long[] mx;
    private long[] lazy;
    private final int low, high;

    public SegTree(int low, int high) {
        this.low = low;
        this.high = high;
        int tot = 1 << (33 - Integer.numberOfLeadingZeros(N - 1));
        mx = new long[tot];
        lazy = new long[tot];
    }

    public SegTree(int[] arr, int low, int high) {
        this.low = low;
        this.high = high;
        int tot = 1 << (33 - Integer.numberOfLeadingZeros(high - low));
        mx = new long[tot];
        lazy = new long[tot];
        build(arr, low, high, 1);
    }

    private void build(int[] arr, int l, int r, int i) {
        if (l == r) {
            mx[i] = arr[l];
            return;
        }
        int m = (l + r) >> 1;
        build(arr, l, m, i << 1);
        build(arr, m + 1, r, i << 1 | 1);
        up(i);
    }

    private void up(int i) {
        mx[i] = Math.max(mx[i << 1], mx[i << 1 | 1]);
    }

    private void down(int i) {
        if (lazy[i] != 0) {
            lazy[i << 1] += lazy[i];
            lazy[i << 1 | 1] += lazy[i];
            mx[i << 1] += lazy[i];
            mx[i << 1 | 1] += lazy[i];
            lazy[i] = 0;
        }
    }

    public void add(int l, int r, long v) {
        add(l, r, v, low, high, 1);
    }

    private void add(int L, int R, long v, int l, int r, int i) {
        if (L <= l && r <= R) {
            lazy[i] += v;
            mx[i] += v;
            return;
        }
        int m = (l + r) >> 1;
        down(i);
        if (L <= m) {
            add(L, R, v, l, m, i << 1);
        }
        if (R > m) {
            add(L, R, v, m + 1, r, i << 1 | 1);
        }
        up(i);
    }

    public long query(int l, int r) {
        return query(l, r, low, high, 1);
    }

    private long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return mx[i];
        }
        int m  = (l + r) >> 1;
        down(i);
        long ans = Long.MIN_VALUE;
        if (L <= m) {
            ans = Math.max(ans, query(L, R, l, m, i << 1));
        }
        if (R > m) {
            ans = Math.max(ans, query(L, R, m + 1, r, i << 1 | 1));
        }
        return ans;
    }

    public long query(int o) {
        return query(o, low, high, 1);
    }

    private long query(int o, int l, int r, int i) {
        if (l == r) {
            return mx[i];
        }
        int m  = (l + r) >> 1;
        down(i);
        if (o <= m) {
            return query(o, l, m, i << 1);
        } else {
            return query(o, m + 1, r, i << 1 | 1);
        }
    }

    // [L, R] 上大于等于 v 的最右侧下标
    public int right(int L, int R, long v) {
        int ans;
        if (R + 1 <= high) {
            long p = v - mx[1] - 1;
            add(R + 1, high, p, low, high, 1);
            ans = right(L, R, v, low, high, 1);
            add(R + 1, high, -p, low, high, 1);
        } else {
            ans = right(L, R, v, low, high, 1);
        }
        return ans;
    }

    private int right(int L, int R, long v, int l, int r, int i) {
        if (l == r) {
            return mx[i] >= v ? l : -1;
        }
        int m = (l + r) >> 1;
        down(i);
        if (R > m && mx[i << 1 | 1] >= v) {
            return right(L, R, v, m + 1, r, i << 1 | 1);
        }
        return L <= m ? right(L, R, v, l, m, i << 1) : -1;
    }

    // [L, R] 上小于等于 v 的最左侧下标
    public int left(int L, int R, long v) {
        int ans;
        if (L - 1 >= low) {
            long p = v - mx[1] - 1;
            add(low, L - 1, p, low, high, 1);
            ans = left(L, R, v, low, high, 1);
            add(low, L - 1, -p, low, high, 1);
        } else {
            ans = left(L, R, v, low, high, 1);
        }
        return ans;
    }

    private int left(int L, int R, long v, int l, int r, int i) {
        if (l == r) {
            return mx[i] >= v ? l : -1;
        }
        int m = (l + r) >> 1;
        down(i);
        if (L <= m && mx[i << 1] >= v) {
            return left(L, R, v, l, m, i << 1);
        }
        return R > m ? left(L, R, v, m + 1, r, i << 1 | 1) : -1;
    }
}
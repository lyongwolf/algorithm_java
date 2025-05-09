package algorithm.tree.segment_tree;

class SegTree {
    private long[] mx;
    private int N;

    public SegTree(int len) {
        N = len;
        mx = new long[1 << (33 - Integer.numberOfLeadingZeros(N - 1))];
    }
    
    public SegTree(int[] arr) {
        N = arr.length - 1;
        mx = new long[1 << (33 - Integer.numberOfLeadingZeros(N - 1))];
        build(arr, 1, N, 1);
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

    public void add(int o, long v) {
        add(o, v, 1, N, 1);
    }

    private void add(int o, long v, int l, int r, int i) {
        if (l == r) {
            mx[i] = v;
            return;
        }
        int m = (l + r) >> 1;
        if (o <= m) {
            add(o, v, l, m, i << 1);
        } else {
            add(o, v, m + 1, r, i << 1 | 1);
        }
        up(i);
    }

    public void set(int o, long v) {
        set(o, v, 1, N, 1);
    }

    private void set(int o, long v, int l, int r, int i) {
        if (l == r) {
            mx[i] = v;
            return;
        }
        int m = (l + r) >> 1;
        if (o <= m) {
            set(o, v, l, m, i << 1);
        } else {
            set(o, v, m + 1, r, i << 1 | 1);
        }
        up(i);
    }

    public long query(int l, int r) {
        return query(l, r, 1, N, 1);
    }

    private long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return mx[i];
        }
        int m  = (l + r) >> 1;
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
        return query(o, 1, N, 1);
    }

    private long query(int o, int l, int r, int i) {
        if (l == r) {
            return mx[i];
        }
        int m  = (l + r) >> 1;
        if (o <= m) {
            return query(o, l, m, i << 1);
        } else {
            return query(o, m + 1, r, i << 1 | 1);
        }
    }
}
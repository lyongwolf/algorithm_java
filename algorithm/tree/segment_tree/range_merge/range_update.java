abstract class SegTree<A> {
    private final int low, high;
    private A[] a;

    public SegTree(int[] arr, int low, int high) {
        this.low = low;
        this.high = high;
        a = (A[]) new Object[1 << (33 - Integer.numberOfLeadingZeros(high - low))];
        build(arr, low, high, 1);
    }

    private void build(int[] arr, int l, int r, int i) {
        if (l == r) {
            a[i] = create(arr[l]);
            return;
        }
        int m = (l + r) >> 1;
        build(arr, l, m, i << 1);
        build(arr, m + 1, r, i << 1 | 1);
        a[i] = up(a[i << 1], a[i << 1 | 1]);
    }

    public void update(int l, int r, int v) {
        update(l, r, v, low, high, 1);
    }

    private void update(int L, int R, int v, int l, int r, int i) {
        if (l == r) {
            a[i] = create(v);
            return;
        }
        down(i);
        int m = (l + r) >> 1;
        if (L <= m) {
            update(L, R, v, l, m, i << 1);
        }
        if (R > m) {
            update(L, R, v, m + 1, r, i << 1 | 1);
        }
        a[i] = up(a[i << 1], a[i << 1 | 1]);
    }

    public A query(int l, int r) {
        return query(l, r, low, high, 1);
    }

    private A query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return a[i];
        }
        down(i);
        int m = (l + r) >> 1;
        if (R <= m) {
            return query(L, R, l, m, i << 1);
        }
        if (L > m) {
            return query(L, R, m + 1, r, i << 1 | 1);
        }
        return up(query(L, R, l, m, i << 1), query(L, R, m + 1, r, i << 1 | 1));
    }

    abstract A create(int v);

    abstract A up(A l, A r);

    abstract void down(int i);
}

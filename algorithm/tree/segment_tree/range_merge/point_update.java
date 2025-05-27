class SegTree<E> {
    private final java.util.function.BiFunction<E, E, E> merge;
    private final int low, high;
    private E[] tree;

    public SegTree(E[] origin, int low, int high, java.util.function.BiFunction<E, E, E> merge) {
        this.merge = merge;
        this.low = low;
        this.high = high;
        tree = (E[]) new Object[1 << (33 - Integer.numberOfLeadingZeros(high - low))];
        build(origin, low, high, 1);
    }

    private void build(E[] origin, int l, int r, int i) {
        if (l == r) {
            tree[i] = origin[l];
            return;
        }
        int m = (l + r) >> 1;
        build(origin, l, m, i << 1);
        build(origin, m + 1, r, i << 1 | 1);
        tree[i] = merge.apply(tree[i << 1], tree[i << 1 | 1]);
    }

    public void update(int o, E e) {
        update(o, e, low, high, 1);
    }

    private void update(int o, E e, int l, int r, int i) {
        if (l == r) {
            tree[i] = e;
            return;
        }
        int m = (l + r) >> 1;
        if (o <= m) {
            update(o, e, l, m, i << 1);
        } else {
            update(o, e, m + 1, r, i << 1 | 1);
        }
        tree[i] = merge.apply(tree[i << 1], tree[i << 1 | 1]);
    }

    public E query(int l, int r) {
        return query(l, r, low, high, 1);
    }

    private E query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return tree[i];
        }
        int m = (l + r) >> 1;
        if (R <= m) {
            return query(L, R, l, m, i << 1);
        }
        if (L > m) {
            return query(L, R, m + 1, r, i << 1 | 1);
        }
        return merge.apply(query(L, R, l, m, i << 1), query(L, R, m + 1, r, i << 1 | 1));
    }

    public String toString() {
        return "[" + print(low, high, 1).trim() + "]";
    }

    private String print(int l, int r, int i) {
        if (l == r) {
            return tree[i].toString();
        }
        int m = (l + r) >> 1;
        return print(l, m, i << 1) + ", " + print(m + 1, r, i << 1 | 1);
    }
}

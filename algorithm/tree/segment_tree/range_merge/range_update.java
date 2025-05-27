class E {

    E() {
       
    }

    E merge(E er) {
        return null;
    }

    void lazy(E e) {
        
    }
    
    void clearTag() {
        
    }
}
class SegTree {
    private final int low, high;
    private E[] tree;

    public SegTree(E[] origin, int low, int high) {
        this.low = low;
        this.high = high;
        tree = new E[1 << (33 - Integer.numberOfLeadingZeros(high - low))];
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
        tree[i] = tree[i << 1].merge(tree[i << 1 | 1]);
    }

    public void update(int l, int r, E e) {
        update(l, r, e, low, high, 1);
    }

    private void update(int L, int R, E e, int l, int r, int i) {
        if (L <= l && r <= R) {
            tree[i].lazy(e);
            return;
        }
        down(i);
        int m = (l + r) >> 1;
        if (L <= m) {
            update(L, R, e, l, m, i << 1);
        }
        if (R > m) {
            update(L, R, e, m + 1, r, i << 1 | 1);
        }
        tree[i] = tree[i << 1].merge(tree[i << 1 | 1]);
    }

    public E query(int l, int r) {
        return query(l, r, low, high, 1);
    }

    private E query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return tree[i];
        }
        down(i);
        int m = (l + r) >> 1;
        if (R <= m) {
            return query(L, R, l, m, i << 1);
        }
        if (L > m) {
            return query(L, R, m + 1, r, i << 1 | 1);
        }
        return query(L, R, l, m, i << 1).merge(query(L, R, m + 1, r, i << 1 | 1));
    }

    private void down(int i) {
        tree[i << 1].lazy(tree[i]);
        tree[i << 1 | 1].lazy(tree[i]);
        tree[i].clearTag();
    }

    public String toString() {
        return "[" + print(low, high, 1).trim() + "]";
    }

    private String print(int l, int r, int i) {
        if (l == r) {
            return tree[i].toString();
        }
        int m = (l + r) >> 1;
        down(i);
        return print(l, m, i << 1) + ", " + print(m + 1, r, i << 1 | 1);
    }
}

package algorithm.tree.binary_indexed_tree;

import java.util.Arrays;

class Fenwick {
    private long[] mx;
    private int N;

    public Fenwick(int len) {
        N = len + 1;
        mx = new long[N + 1];
        Arrays.fill(mx, Long.MIN_VALUE);
    }

    public void update(int i, long v) {
        while (i < N) {
            mx[i] = Math.max(mx[i], v);
            i += i & -i;
        }
    }

    public long query(int i) {
        long ans = Long.MIN_VALUE;
        while (i > 0) {
            ans = Math.max(ans, mx[i]);
            i -= i & -i;
        }
        return ans;
    }

}
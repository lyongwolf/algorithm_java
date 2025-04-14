package algorithm.tree.binary_indexed_tree;

import java.util.Arrays;

class Fenwick {
    private long[] mn;
    private int N;

    public Fenwick(int len) {
        N = len + 1;
        mn = new long[N + 1];
        Arrays.fill(mn, Long.MAX_VALUE);
    }

    public void update(int i, long v) {
        while (i < N) {
            mn[i] = Math.min(mn[i], v);
            i += i & -i;
        }
    }

    public long query(int i) {
        long ans = Long.MAX_VALUE;
        while (i > 0) {
            ans = Math.min(ans, mn[i]);
            i -= i & -i;
        }
        return ans;
    }

}
package algorithm.binary_indexed_tree;

import java.util.Arrays;

public class max {
    
    static class Fenwick {
        private long[] max;
        private int N;

        public Fenwick(int len) {
            N = len + 1;
            max = new long[N + 1];
            Arrays.fill(max, Long.MIN_VALUE);
        }

        public void update(int i, long v) {
            while (i < N) {
                max[i] = Math.max(max[i], v);
                i += i & -i;
            }
        }

        public long query(int i) {
            long ans = Long.MIN_VALUE;
            while (i > 0) {
                ans = Math.max(ans, max[i]);
                i -= i & -i;
            }
            return ans;
        }

    }
}

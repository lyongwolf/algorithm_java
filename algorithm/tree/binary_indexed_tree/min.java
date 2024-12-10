package algorithm.tree.binary_indexed_tree;

import java.util.Arrays;

public class min {
    
    static class Fenwick {
        private long[] min;
        private int N;

        public Fenwick(int len) {
            N = len + 1;
            min = new long[N + 1];
            Arrays.fill(min, Long.MAX_VALUE);
        }

        public void update(int i, long v) {
            while (i < N) {
                min[i] = Math.min(min[i], v);
                i += i & -i;
            }
        }

        public long query(int i) {
            long ans = Long.MAX_VALUE;
            while (i > 0) {
                ans = Math.min(ans, min[i]);
                i -= i & -i;
            }
            return ans;
        }

    }

    public static void main(String[] args) {
        
    }
}

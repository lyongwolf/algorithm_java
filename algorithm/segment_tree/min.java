package algorithm.segment_tree;

import java.util.Arrays;

public class min {
    
    static class SegTree {
        private long[] min;
        private int N;

        public SegTree(int len) {
            N = len;
            min = new long[N << 2];
            Arrays.fill(min, Long.MAX_VALUE);
        }
        
        public SegTree(int[] arr) {
            N = arr.length - 1;
            min = new long[N << 2];
            build(arr, 1, N, 1);
        }

        private void build(int[] arr, int l, int r, int i) {
            if (l == r) {
                min[i] = arr[l];
                return;
            }
            int m = (l + r) >> 1;
            build(arr, l, m, i << 1);
            build(arr, m + 1, r, i << 1 | 1);
            up(i);
        }

        private void up(int i) {
            min[i] = Math.min(min[i << 1], min[i << 1 | 1]);
        }

        public void set(int o, long v) {
            set(o, v, 1, N, 1);
        }

        private void set(int o, long v, int l, int r, int i) {
            if (l == r) {
                min[i] = v;
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
                return min[i];
            }
            int m  = (l + r) >> 1;
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
                return min[i];
            }
            int m  = (l + r) >> 1;
            if (o <= m) {
                return query(o, l, m, i << 1);
            } else {
                return query(o, m + 1, r, i << 1 | 1);
            }
        }
    }
}

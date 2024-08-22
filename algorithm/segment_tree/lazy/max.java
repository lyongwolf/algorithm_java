package algorithm.segment_tree.lazy;

import java.util.Arrays;

public class max {
    
    static class SegTree {
        private long[] max;
        private long[] lazy;
        private int N;

        public SegTree(int len) {
            N = len;
            max = new long[N << 2];
            lazy = new long[N << 2];
            Arrays.fill(max, Long.MIN_VALUE);
            Arrays.fill(lazy, Long.MIN_VALUE);
        }

        public SegTree(int[] arr) {
            N = arr.length - 1;
            max = new long[N << 2];
            lazy = new long[N << 2];
            build(arr, 1, N, 1);
        }

        private void build(int[] arr, int l, int r, int i) {
            if (l == r) {
                max[i] = arr[l];
                return;
            }
            int m = (l + r) >> 1;
            build(arr, l, m, i << 1);
            build(arr, m + 1, r, i << 1 | 1);
            up(i);
        }

        private void up(int i) {
            max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
        }

        private void down(int i) {
            if (lazy[i] != Long.MIN_VALUE) {
                lazy[i << 1] = lazy[i];
                lazy[i << 1 | 1] = lazy[i];
                max[i << 1] = lazy[i];
                max[i << 1 | 1] = lazy[i];
                lazy[i] = Long.MIN_VALUE;
            }
        }

        public void set(int l, int r, long v) {
            set(l, r, v, 1, N, 1);
        }

        private void set(int L, int R, long v, int l, int r, int i) {
            if (L <= l && r <= R) {
                lazy[i] = v;
                max[i] = v;
                return;
            }
            int m = (l + r) >> 1;
            down(i);
            if (L <= m) {
                set(L, R, v, l, m, i << 1);
            }
            if (R > m) {
                set(L, R, v, m + 1, r, i << 1 | 1);
            }
            up(i);
        }

        public long query(int l, int r) {
            return query(l, r, 1, N, 1);
        }

        private long query(int L, int R, int l, int r, int i) {
            if (L <= l && r <= R) {
                return max[i];
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
            return query(o, 1, N, 1);
        }

        private long query(int o, int l, int r, int i) {
            if (l == r) {
                return max[i];
            }
            int m  = (l + r) >> 1;
            down(i);
            if (o <= m) {
                return query(o, l, m, i << 1);
            } else {
                return query(o, m + 1, r, i << 1 | 1);
            }
        }
    }
}

package algorithm.tree.segment_tree.lazy.min;

/**
 * 区间添加值
 */
public class minAddRange {
    
    class SegTree {
        private long[] min;
        private long[] lazy;
        private int N;

        public SegTree(int len) {
            N = len;
            int tot = 1 << (33 - Integer.numberOfLeadingZeros(N - 1));
            min = new long[tot];
            lazy = new long[tot];
        }

        public SegTree(int[] arr) {
            N = arr.length - 1;
            int tot = 1 << (33 - Integer.numberOfLeadingZeros(N - 1));
            min = new long[tot];
            lazy = new long[tot];
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

        private void down(int i) {
            if (lazy[i] != 0) {
                lazy[i << 1] += lazy[i];
                lazy[i << 1 | 1] += lazy[i];
                min[i << 1] += lazy[i];
                min[i << 1 | 1] += lazy[i];
                lazy[i] = 0;
            }
        }

        public void add(int l, int r, long v) {
            add(l, r, v, 1, N, 1);
        }

        private void add(int L, int R, long v, int l, int r, int i) {
            if (L <= l && r <= R) {
                lazy[i] += v;
                min[i] += v;
                return;
            }
            int m = (l + r) >> 1;
            down(i);
            if (L <= m) {
                add(L, R, v, l, m, i << 1);
            }
            if (R > m) {
                add(L, R, v, m + 1, r, i << 1 | 1);
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
            down(i);
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
            down(i);
            if (o <= m) {
                return query(o, l, m, i << 1);
            } else {
                return query(o, m + 1, r, i << 1 | 1);
            }
        }
    }
}

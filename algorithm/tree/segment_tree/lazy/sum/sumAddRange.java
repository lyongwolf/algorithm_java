package algorithm.tree.segment_tree.lazy.sum;
/**
 * 区间添加值
 */
public class sumAddRange {

    class SegTree {
        private long[] sum;
        private long[] lazy;
        private int N;

        public SegTree(int len) {
            N = len;
            int tot = 1 << (33 - Integer.numberOfLeadingZeros(N - 1));
            sum = new long[tot];
            lazy = new long[tot];
        }

        public SegTree(int[] arr) {
            N = arr.length - 1;
            int tot = 1 << (33 - Integer.numberOfLeadingZeros(N - 1));
            sum = new long[tot];
            lazy = new long[tot];
            build(arr, 1, N, 1);
        }

        private void build(int[] arr, int l, int r, int i) {
            if (l == r) {
                sum[i] = arr[l];
                return;
            }
            int m = (l + r) >> 1;
            build(arr, l, m, i << 1);
            build(arr, m + 1, r, i << 1 | 1);
            up(i);
        }

        private void up(int i) {
            sum[i] = sum[i << 1] + sum[i << 1 | 1];
        }

        private void down(int i, int ln, int rn) {
            if (lazy[i] != 0) {
                lazy[i << 1] += lazy[i];
                lazy[i << 1 | 1] += lazy[i];
                sum[i << 1] += lazy[i] * ln;
                sum[i << 1 | 1] += lazy[i] * rn;
                lazy[i] = 0;
            }
        }

        public void add(int l, int r, long v) {
            add(l, r, v, 1, N, 1);
        }

        private void add(int L, int R, long v, int l, int r, int i) {
            if (L <= l && r <= R) {
                lazy[i] += v;
                sum[i] += v * (r - l + 1);
                return;
            }
            int m = (l + r) >> 1;
            down(i, m - l + 1, r - m);
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
                return sum[i];
            }
            int m  = (l + r) >> 1;
            down(i, m - l + 1, r - m);
            long ans = 0;
            if (L <= m) {
                ans += query(L, R, l, m, i << 1);
            }
            if (R > m) {
                ans += query(L, R, m + 1, r, i << 1 | 1);
            }
            return ans;
        }

        public long query(int o) {
            return query(o, 1, N, 1);
        }

        private long query(int o, int l, int r, int i) {
            if (l == r) {
                return sum[i];
            }
            int m  = (l + r) >> 1;
            down(i, m - l + 1, r - m);
            if (o <= m) {
                return query(o, l, m, i << 1);
            } else {
                return query(o, m + 1, r, i << 1 | 1);
            }
        }
    }

}

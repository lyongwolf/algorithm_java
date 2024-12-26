package algorithm.tree.segment_tree.dynamic;

import java.util.Arrays;

public class dynamic_segTree {
    
    class SegTree {
        private int[] sum, lc, rc;
        private final int low, high;
        private int no;
    
        public SegTree(int low, int high) {
            this.low = low;
            this.high = high;
            no = 1;
            sum = new int[2];
            lc = new int[2];
            rc = new int[2];
        }
    
        public void add(int l, int r, int v) {
            add(l, r, v, low, high, 1);
        }
    
        private void add(int L, int R, int v, int l, int r, int i) {
            if (L <= l && r <= R) {
                sum[i] += v;
                return;
            }
            int m = (l + r) >>> 1;
            down(i);
            if (L <= m) {
                add(L, R, v, l, m, lc[i]);
            }
            if (R > m) {
                add(L, R, v, m + 1, r, rc[i]);
            }
            up(i);
        }
    
        public int query(int l, int r) {
            return query(l, r, low, high, 1);
        }
    
        private int query(int L, int R, int l, int r, int i) {
            if (L <= l && r <= R) {
                return sum[i];
            }
            int m = (l + r) >>> 1;
            down(i);
            int ans = 0;
            if (L <= m) {
                ans += query(L, R, l, m, lc[i]);
            }
            if (R > m) {
                ans += query(L, R, m + 1, r, rc[i]);
            }
            return ans;
        }
    
        private void up(int i) {
            sum[i] = sum[lc[i]] + sum[rc[i]];
        }
    
        private void down(int i) {
            if (lc[i] == 0) {
                create();
                lc[i] = no;
                create();
                rc[i] = no;
            }
        }
    
        private void create() {
            if (++no == sum.length) {
                sum = Arrays.copyOf(sum, no << 1);
                lc = Arrays.copyOf(lc, no << 1);
                rc = Arrays.copyOf(rc, no << 1);
            }
        }
    }

}

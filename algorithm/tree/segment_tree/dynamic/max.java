package algorithm.tree.segment_tree.dynamic;

import java.util.*;

public class max {
    
    class SegTree {
        private int[] mx, lc, rc;
        private int no;
        private final int low, high;
    
        public SegTree(int low, int high) {
            this.low = low;
            this.high = high;
            mx = new int[2];
            lc = new int[2];
            rc = new int[2];
            no = 1;
        }
    
        public void set(int o, int v) {
            set(o, v, low, high, 1);
        }
    
        private void set(int o, int v, int l, int r, int i) {
            if (l == r) {
                mx[i] = Math.max(mx[i], v);
                return;
            }
            int m = (l + r) / 2;
            down(i);
            if (o <= m) {
                set(o, v, l, m, lc[i]);
            } else {
                set(o, v, m + 1, r, rc[i]);
            }
            mx[i] = Math.max(mx[lc[i]], mx[rc[i]]);
        }
    
        public int query(int l, int r) {
            return query(l, r, low, high, 1);
        }
    
        private int query(int L, int R, int l, int r, int i) {
            if (L <= l && r <= R) {
                return mx[i];
            }
            int m = (l + r) / 2;
            down(i);
            int ans = 0;
            if (L <= m) {
                ans = Math.max(ans, query(L, R, l, m, lc[i]));
            }
            if (R > m) {
                ans = Math.max(ans, query(L, R, m + 1, r, rc[i]));
            }
            return ans;
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
            if (++no == mx.length) {
                mx = Arrays.copyOf(mx, no << 1);
                lc = Arrays.copyOf(lc, no << 1);
                rc = Arrays.copyOf(rc, no << 1);
            }
        }
    }

}

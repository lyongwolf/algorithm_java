package algorithm.tree.segment_tree.trick;

import java.util.*;

public class lazy_bit_reverse_assign01 {
    
    class SegTree {
        private final int low, high;
        private int[] cnt, lc, rc;
        private boolean[] rev, set0, set1;
        private int no;
    
        public SegTree(int low, int high) {
            this.low = low;
            this.high = high;
            cnt = new int[2];
            lc = new int[2];
            rc = new int[2];
            rev = new boolean[2];
            set0 = new boolean[2];
            set1 = new boolean[2];
            no = 1;
        }
    
        public void assign0(int l, int r) {
            if (l > r) {
                return;
            }
            assign0(l, r, low, high, 1);
        }
    
        private void assign0(int L, int R, int l, int r, int i) {
            if (L <= l && r <= R) {
                updateSet0(i, r - l + 1);
                return;
            }
            int m = (l + r) / 2;
            down(i, m - l + 1, r - m);
            if (L <= m) {
                assign0(L, R, l, m, lc[i]);
            }
            if (R > m) {
                assign0(L, R, m + 1, r, rc[i]);
            }
            up(i);
        }
    
        public void assign1(int l, int r) {
            if (l > r) {
                return;
            }
            assign1(l, r, low, high, 1);
        }
    
        private void assign1(int L, int R, int l, int r, int i) {
            if (L <= l && r <= R) {
                updateSet1(i, r - l + 1);
                return;
            }
            int m = (l + r) / 2;
            down(i, m - l + 1, r - m);
            if (L <= m) {
                assign1(L, R, l, m, lc[i]);
            }
            if (R > m) {
                assign1(L, R, m + 1, r, rc[i]);
            }
            up(i);
        }
    
        public void reverse(int l, int r) {
            if (l > r) {
                return;
            }
            reverse(l, r, low, high, 1);
        }   
    
        private void reverse(int L, int R, int l, int r, int i) {
            int m = (l + r) / 2;
            if (L <= l && r <= R) {
                updateRev(i, r - l + 1);
                return;
            }
            down(i, m - l + 1, r - m);
            if (L <= m) {
                reverse(L, R, l, m, lc[i]);
            }
            if (R > m) {
                reverse(L, R, m + 1, r, rc[i]);
            }
            up(i);
        }
    
        public int query(int l, int r) {
            return l > r ? 0 : query(l, r, low, high, 1);
        }
    
        private int query(int L, int R, int l, int r, int i) {
            if (L <= l && r <= R) {
                return cnt[i];
            }
            int m = (l + r) / 2;
            down(i, m - l + 1, r - m);
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
            cnt[i] = cnt[lc[i]] + cnt[rc[i]];
        }
    
        private void down(int i, int ln, int rn) {
            if (lc[i] == 0) {
                create();
                lc[i] = no;
            }
            if (rc[i] == 0) {
                create();
                rc[i] = no;
            }
            if (rev[i]) {
                updateRev(lc[i], ln);
                updateRev(rc[i], rn);
                rev[i] = false;
            } else if (set0[i]) {
                updateSet0(lc[i], ln);
                updateSet0(rc[i], rn);
                set0[i] = false;
            } else if (set1[i]) {
                updateSet1(lc[i], ln);
                updateSet1(rc[i], rn);
                set1[i] = false;
            }
        }
    
        private void updateRev(int i, int n) {
            cnt[i] = n - cnt[i];
            if (set0[i]) {
                set0[i] = false;
                set1[i] = true;
            } else if (set1[i]) {
                set1[i] = false;
                set0[i] = true;
            } else {
                rev[i] ^= true;
            }
        }
    
        private void updateSet0(int i, int n) {
            cnt[i] = 0;
            set0[i] = true;
            rev[i] = set1[i] = false;
        }
    
        private void updateSet1(int i, int n) {
            cnt[i] = n;
            set1[i] = true;
            rev[i] = set0[i] = false;
        }
    
        private void create() {
            if (++no == cnt.length) {
                cnt = Arrays.copyOf(cnt, no << 1);
                lc = Arrays.copyOf(lc, no << 1);
                rc = Arrays.copyOf(rc, no << 1);
                rev = Arrays.copyOf(rev, no << 1);
                set0 = Arrays.copyOf(set0, no << 1);
                set1 = Arrays.copyOf(set1, no << 1);
            }
        }
    }

}

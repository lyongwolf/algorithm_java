package algorithm.tree.order.FHQ;

import java.util.*;

public class Basic {

    static class FHQTreap {
        private int[] key, lc, rc, sz;
        private double[] priority;
        private int head;
        private int no;

        public FHQTreap() {
            this(0);
        }

        public FHQTreap(int len) {
            len++;
            key = new int[len];
            lc = new int[len];
            rc = new int[len];
            sz = new int[len];
            priority = new double[len];
        }

        public int size() {
            return sz[head];
        }

        public boolean isEmpty() {
            return sz[head] > 0;
        }

        public boolean contains(int k) {
            return contains(k, head);
        }
    
        private boolean contains(int k, int i) {
            if (i == 0) {
                return false;
            }
            if (key[i] > k) {
                return contains(k, lc[i]);
            } else if (key[i] < k) {
                return contains(k, rc[i]);
            }
            return true;
        }

        public void add(int k) {
            split(0, 0, head, k);
            int l = rc[0], r = lc[0];
            head = merge(merge(l, create(k)), r);
        }

        public void remove(int k) {
            int l, m, r;
            split(0, 0, head, k);
            r = lc[0];
            m = rc[0];
            split(0, 0, m, k - 1);
            m = lc[0];
            l = rc[0];
            m = merge(lc[m], rc[m]);
            head = merge(merge(l, m), r);
        }

        public int floor(int k) {
            return floor(head, k);
        }

        private int floor(int i, int k) {
            if (i == 0) {
                return Integer.MIN_VALUE;
            }
            if (key[i] <= k) {
                return Math.max(key[i], floor(rc[i], k));
            } else {
                return floor(lc[i], k);
            }
        }

        public int ceiling(int k) {
            return ceiling(k, head);
        }

        private int ceiling(int k, int i) {
            if (i == 0) {
                return Integer.MAX_VALUE;
            }
            if (key[i] >= k) {
                return Math.min(key[i], ceiling(k, lc[i]));
            } else {
                return ceiling(k, rc[i]);
            }
        }

        public int rank(int k) {
            return smallCount(k, head) + 1;
        }
        
        private int smallCount(int k, int i) {
            if (i == 0) {
                return 0;
            }
            if (key[i] >= k) {
                return smallCount(k, lc[i]);
            } else {
                return sz[lc[i]] + 1 + smallCount(k, rc[i]);
            }
        }

        public int rankKey(int rk) {
            if (rk < 1 || rk > sz[head]) {
                return -1;
            }
            return rankKey(rk, head);
        }
        
        private int rankKey(int rk, int i) {
            if (sz[lc[i]] >= rk) {
                return rankKey(rk, lc[i]);
            } else if (sz[lc[i]] + 1 < rk) {
                return rankKey(rk - sz[lc[i]] - 1, rc[i]);
            }
            return key[i];
        }

        public int first() {
            return rankKey(1);
        }
    
        public int last() {
            return rankKey(sz[head]);
        }
    
        public int pollFirst() {
            int k = first();
            remove(k);
            return k;
        }
    
        public int pollLast() {
            int k = last();
            remove(k);
            return k;
        }

        public int[] copy() {
            return sub(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        // 返回所有 key ∈ [l, r] 组成的有序数组
        public int[] sub(int l, int r) {
            if (l > r) {
                return new int[0];
            }
            if (l == Integer.MIN_VALUE) {
                split(0, 0, head, r);
                int lx = rc[0], rx = lc[0];
                int[] arr = new int[sz[lx]];
                inorder(arr, lx, 0);
                head = merge(lx, rx);
                return arr;
            } else {
                split(0, 0, head, r);
                int mx = rc[0], rx = lc[0];
                split(0, 0, mx, l - 1);
                int lx = rc[0];
                mx = lc[0];
                int[] arr = new int[sz[mx]];
                inorder(arr, mx, 0);
                head = merge(merge(lx, mx), rx);
                return arr;
            }
        }
    
        private int inorder(int[] arr, int i, int c) {
            if (i == 0) {
                return c;
            }
            c = inorder(arr, lc[i], c);
            arr[c++] = key[i];
            return inorder(arr, rc[i], c);
        }

        public void clear(int l, int r) {
            if (l > r) {
                return;
            }
            int lx, mx, rx;
            split(0, 0, head, l - 1);
            lx = rc[0];
            mx = lc[0];
            split(0, 0, mx, r);
            mx = rc[0];
            rx = lc[0];
            head = merge(lx, rx);
        }

        // 按 key 分裂： <= k 在左，> k 在右
        private void split(int l, int r, int i, int k) {
            if (i == 0) {
                rc[l] = lc[r] = 0;
                return;
            }
            if (key[i] <= k) {
                rc[l] = i;
                split(i, r, rc[i], k);
            } else {
                lc[r] = i;
                split(l, i, lc[i], k);
            }
            up(i);
        }

        private int merge(int l, int r) {
            if (l == 0 || r == 0) {
                return l + r;
            }
            if (priority[l] >= priority[r]) {
                rc[l] = merge(rc[l], r);
                up(l);
                return l;
            } else {
                lc[r] = merge(l, lc[r]);
                up(r);
                return r;
            }
        }

        private void up(int i) {
            sz[i] = sz[lc[i]] + sz[rc[i]] + 1;
        }

        private int create(int k) {
            if (++no == key.length) {
                key = Arrays.copyOf(key, no << 1);
                lc = Arrays.copyOf(lc, no << 1);
                rc = Arrays.copyOf(rc, no << 1);
                sz = Arrays.copyOf(sz, no << 1);
                priority = Arrays.copyOf(priority, no << 1);
            }
            key[no] = k;
            sz[no] = 1;
            priority[no] = Math.random();
            return no;
        }
    }

}

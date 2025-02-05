package algorithm.tree.order.FHQ;

import java.util.*;
/**
 * 维护新增节点的相对顺序（key仅存储值，不再具有大小关系）
 * 区间插入，区间移动，区间翻转
 */
public class RangeUpdate {
    
    class FHQTreap {
        private int[] key, lc, rc, sz;
        private double[] priority;
        private boolean[] reverse;
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
            reverse = new boolean[len];
        }

        public int size() {
            return sz[head];
        }

        public boolean isEmpty() {
            return sz[head] == 0;
        }

        public void add(int k) {
            add(sz[head], k);
        }

        // 在 排名为 rk 的右侧插入一个值为 k 节点
        public void add(int rk, int k) {
            int l, r;
            split(0, 0, head, rk);
            l = rc[0];
            r = lc[0];
            l = merge(l, create(k));
            head = merge(l, r);
        }

        public void remove(int rk) {
            int l, m, r;
            split(0, 0, head, rk);
            r = lc[0];
            m = rc[0];
            split(0, 0, m, rk - 1);
            m = lc[0];
            l = rc[0];
            head = merge(l, r);
        }

        public int rankKey(int rk) {
            if (rk < 1 || rk > sz[head]) {
                return -1;
            }
            return rankKey(rk, head);
        }
        
        private int rankKey(int rk, int i) {
            down(i);
            if (sz[lc[i]] >= rk) {
                return rankKey(rk, lc[i]);
            } else if (sz[lc[i]] + 1 < rk) {
                return rankKey(rk - sz[lc[i]] - 1, rc[i]);
            }
            return key[i];
        }

        // 在 rk 排名之后插入 arr 这个连续区间
        public void addRange(int rk, int[] arr) {
            split(0, 0, head, rk);
            int l = rc[0], r = lc[0];
            for (int k : arr) {
                l = merge(l, create(k));
            }
            head = merge(l, r);
        }

        // 交换区间 [l1, r1] 与 [l2, r2] 的位置
        public void swapRange(int l1, int r1, int l2, int r2) {
            if (l1 > l2) {
                swapRange(l2, r2, l1, r1);
                return;
            }
            int i1, i2, i3, i4, i5;
            split(0, 0, head, r2);
            i5 = lc[0];
            i4 = rc[0];
            split(0, 0, i4, l2 - 1);
            i4 = lc[0];
            i3 = rc[0];
            split(0, 0, i3, r1);
            i3 = lc[0];
            i2 = rc[0];
            split(0, 0, i2, l1 - 1);
            i2 = lc[0];
            i1 = rc[0];
            head = merge(merge(merge(merge(i1, i4), i3), i2), i5);
        }

        // [l, r] 区间反转
        public void reverseRange(int l, int r) {
            int i1, i2, i3;
            split(0, 0, head, r);
            i3 = lc[0];
            i2 = rc[0];
            split(0, 0, i2, l - 1);
            i2 = lc[0];
            i1 = rc[0];
            reverse[i2] ^= true;
            head = merge(merge(i1, i2), i3);
        }

        public int[] copy() {
            return subRange(1, sz[head]);
        }

        // 返回区间 [l, r]代表的数组
        public int[] subRange(int l, int r) {
            if (l > r) {
                return new int[0];
            }
            if (l == 1) {
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
            down(i);
            c = inorder(arr, lc[i], c);
            arr[c++] = key[i];
            return inorder(arr, rc[i], c);
        }

        // 删除区间 [l, r]
        public void deleteRange(int l, int r) {
            if (l > r) {
                return;
            }
            int lx, mx, rx;
            split(0, 0, head, l - 1);
            lx = rc[0];
            mx = lc[0];
            split(0, 0, mx, r - l + 1);
            mx = rc[0];
            rx = lc[0];
            head = merge(lx, rx);
        }

        // 按 rank 分裂：<= rk 在左，> rk 在右
        private void split(int l, int r, int i, int rk) {
            if (i == 0) {
                rc[l] = lc[r] = 0;
                return;
            }
            down(i);
            if (sz[lc[i]] + 1 <= rk) {
                rc[l] = i;
                split(i, r, rc[i], rk - sz[lc[i]] - 1);
            } else {
                lc[r] = i;
                split(l, i, lc[i], rk);
            }
            up(i);
        }

        private int merge(int l, int r) {
            if (l == 0 || r == 0) {
                return l + r;
            }
            if (priority[l] >= priority[r]) {
                down(l);
                rc[l] = merge(rc[l], r);
                up(l);
                return l;
            } else {
                down(r);
                lc[r] = merge(l, lc[r]);
                up(r);
                return r;
            }
        }

        private void up(int i) {
            sz[i] = sz[lc[i]] + sz[rc[i]] + 1;
        }

        private void down(int i) {
            if (reverse[i]) {
                lc[i] ^= rc[i] ^ (rc[i] = lc[i]);
                reverse[lc[i]] ^= true;
                reverse[rc[i]] ^= true;
                reverse[i] = false;
            }
        }

        private int create(int k) {
            if (++no == key.length) {
                key = Arrays.copyOf(key, no << 1);
                lc = Arrays.copyOf(lc, no << 1);
                rc = Arrays.copyOf(rc, no << 1);
                sz = Arrays.copyOf(sz, no << 1);
                priority = Arrays.copyOf(priority, no << 1);
                reverse = Arrays.copyOf(reverse, no << 1);
            }
            key[no] = k;
            sz[no] = 1;
            priority[no] = Math.random();
            return no;
        }

    }

}

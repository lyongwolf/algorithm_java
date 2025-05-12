import java.util.*;
/**
 * 替罪羊树
 */
class ScapegoatTree {
    private static final double ALPHA = 0.7;// 平衡因子
    private static final int MAXT = 2000000;
    private static int[] lc = new int[MAXT], rc = new int[MAXT], key = new int[MAXT], val = new int[MAXT];
    private static int[] sz = new int[MAXT], tot = new int[MAXT], sd = new int[MAXT];// 基本信息
    private static int ab[] = new int[MAXT], nb, tb, fb, sb;// 平衡调整
    private static int no;// 已新建节点数量（节点最大编号）
    
    public static void clear() {
        for (int i = 1; i <= no; i++) {
            lc[i] = rc[i] = key[i] = val[i] = sz[i] = tot[i] = sd[i] = 0;
        }
        no = 0;
    }

    private int head;// 头节点编号
    
    public int size() {
        return tot[head];
    }

    public boolean isEmpty() {
        return tot[head] == 0;
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
        return val[i] > 0;
    }
 
    public void add(int k) {
        nb = tb = fb = sb = 0;
        add(k, 0, head, 0);
        rebuild();
    }

    private void add(int k, int f, int i, int s) {
        if (i == 0) {
            i = create(k);
            if (f == 0) {
                head = i;
            } else if (s == 1) {
                lc[f] = i;
            } else {
                rc[f] = i;
            }
            return;
        }
        if (key[i] > k) {
            add(k, i, lc[i], 1);
        } else if (key[i] < k) {
            add(k, i, rc[i], 2);
        } else {
            val[i]++;
        }
        up(i);
        if (canRbu(i)) {
            tb = i;
            fb = f;
            sb = s;
        }
    }

    public void remove(int k) {
        nb = tb = fb = sb = 0;
        remove(k, 0, head, 0);
        rebuild();
    }

    private void remove(int k, int f, int i, int s) {
        if (i == 0) {
            return;
        }
        if (key[i] > k) {
            remove(k, i, lc[i], 1);
        } else if (key[i] < k) {
            remove(k, i, rc[i], 2);
        } else if (val[i] > 0) {
            val[i]--;
        }
        up(i);
        if (canRbu(i)) {
            tb = i;
            fb = f;
            sb = s;
        }
    }

    public int floor(int k) {
        int c = smallCount(k + 1, head);
        if (c == 0) {
            return Integer.MIN_VALUE;
        }
        return rankKey(c);
    }
    
    public int ceiling(int k) {
        int c = smallCount(k, head);
        if (c == tot[head]) {
            return Integer.MAX_VALUE;
        }
        return rankKey(c + 1);
    }

    public int first() {
        return rankKey(1);
    }

    public int last() {
        return rankKey(tot[head]);
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
            return tot[lc[i]] + val[i] + smallCount(k, rc[i]);
        }
    }

    public int rankKey(int rk) {
        if (rk < 1 || rk > tot[head]) {
            return -1;
        }
        return rankKey(rk, head);
    }
    
    private int rankKey(int rk, int i) {
        if (tot[lc[i]] >= rk) {
            return rankKey(rk, lc[i]);
        } else if (tot[lc[i]] + val[i] < rk) {
            return rankKey(rk - tot[lc[i]] - val[i], rc[i]);
        }
        return key[i];
    }

    public int[] copy() {
        int[] arr = new int[tot[head]];
        copy(arr, head, 0);
        return arr;
    }

    private int copy(int[] arr, int i, int c) {
        if (i == 0) {
            return c;
        }
        c = copy(arr, lc[i], c);
        int v = val[i];
        while (v-- > 0) {
            arr[c++] = key[i];
        }
        return copy(arr, rc[i], c);
    }
    
    private void rebuild() {
        if (tb != 0) {
            inorder(tb);
            tb = rebuild(0, nb - 1);
            if (fb == 0) {
                head = tb;
            } else if (sb == 1) {
                lc[fb] = tb;
            } else {
                rc[fb] = tb;
            }
        }
    }

    private int rebuild(int l, int r) {
        if (l > r) {
            return 0;
        }
        int m = (l + r) >> 1;
        int i = ab[m];
        lc[i] = rebuild(l, m - 1);
        rc[i] = rebuild(m + 1, r);
        up(i);
        return i;
    }

    private void inorder(int i) {
        if (i == 0) {
            return;
        }
        inorder(lc[i]);
        if (val[i] > 0) {
            ab[nb++] = i;
        }
        inorder(rc[i]);
    }

    private boolean canRbu(int i) {
        return ALPHA * sz[i] <= Math.max(sz[lc[i]], sz[rc[i]]) || ALPHA * sz[i] >= sd[i];
    }

    private void up(int i) {
        sz[i] = sz[lc[i]] + sz[rc[i]] + 1;
        tot[i] = tot[lc[i]] + tot[rc[i]] + val[i];
        sd[i] = sd[lc[i]] + sd[rc[i]] + (val[i] > 0 ? 1 : 0);
    }

    private int create(int k) {
        key[++no] = k;
        val[no] = sz[no] = tot[no] = sd[no] = 1;
        return no;
    }
}
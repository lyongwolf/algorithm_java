package algorithm.tree.balance_tree.FHQ.persistent;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 可持久化文艺平衡树（区间翻转可持久化）
 * 测试链接：https://www.luogu.com.cn/problem/P5055
 */
public class Q2 {

    void solve() {
        FHQTreap fhq = new FHQTreap(200000);
        int n = ni();
        long ans = 0, p, x, l, r;
        for (int cur = 1, i, opt; cur <= n; cur++) {
            i = ni();
            opt = ni();
            switch (opt) {
                case 1:
                    p = nl() ^ ans;
                    x = nl() ^ ans;
                    fhq.add(cur, i, (int) p, (int) x);
                    break;
                case 2:
                    p = nl() ^ ans;
                    fhq.remove(cur, i, (int) p);
                    break;
                case 3:
                    l = nl() ^ ans;
                    r = nl() ^ ans;
                    fhq.reverseRange(cur, i, (int) l, (int) r);
                    break;
                default:
                    l = nl() ^ ans;
                    r = nl() ^ ans;
                    ans = fhq.rangeSum(cur, i, (int) l, (int) r);
                    println(ans);
                    break;
            }
        }
    }

}

class FHQTreap {
    private int[] lc, rc, sz;
    private int[] key;
    private long[] sum;
    private double[] priority;
    private boolean[] reverse;
    private int[] head;
    private int no;

    public FHQTreap() {
        this(0);
    }

    public FHQTreap(int len) {
        head = new int[++len];
        len *= 100;
        key = new int[len];
        lc = new int[len];
        rc = new int[len];
        sz = new int[len];
        priority = new double[len];
        reverse = new boolean[len];
        sum = new long[len];
    }

    // 在 排名为 rk 的右侧插入一个值为 k 节点
    public void add(int cur, int i, int rk, int k) {
        int l, r;
        split(0, 0, head[i], rk);
        l = rc[0];
        r = lc[0];
        lc[0] = rc[0] = 0;
        l = merge(l, create(k));
        head[cur] = merge(l, r);
    }

    public void remove(int cur, int i, int rk) {
        int l, m, r;
        split(0, 0, head[i], rk);
        r = lc[0];
        m = rc[0];
        split(0, 0, m, rk - 1);
        m = lc[0];
        l = rc[0];
        lc[0] = rc[0] = 0;
        head[cur] = merge(l, r);
    }

    // [l, r] 区间反转
    public void reverseRange(int cur, int i, int l, int r) {
        int i1, i2, i3;
        split(0, 0, head[i], r);
        i3 = lc[0];
        i2 = rc[0];
        split(0, 0, i2, l - 1);
        i2 = lc[0];
        i1 = rc[0];
        reverse[i2] ^= true;
        lc[0] = rc[0] = 0;
        head[cur] = merge(merge(i1, i2), i3);
    }

    public long rangeSum(int cur, int i, int l, int r) {
        int i1, i2, i3;
        split(0, 0, head[i], r);
        i3 = lc[0];
        i2 = rc[0];
        split(0, 0, i2, l - 1);
        i2 = lc[0];
        i1 = rc[0];
        long ans = sum[i2];
        lc[0] = rc[0] = 0;
        head[cur] = merge(merge(i1, i2), i3);
        return ans;
    }

    // 按 rank 分裂：<= rk 在左，> rk 在右
    private void split(int l, int r, int i, int rk) {
        if (i == 0) {
            rc[l] = lc[r] = 0;
            return;
        }
        i = clone(i);
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
            l = clone(l);
            down(l);
            rc[l] = merge(rc[l], r);
            up(l);
            return l;
        } else {
            r = clone(r);
            down(r);
            lc[r] = merge(l, lc[r]);
            up(r);
            return r;
        }
    }

    private void up(int i) {
        sz[i] = sz[lc[i]] + sz[rc[i]] + 1;
        sum[i] = sum[lc[i]] + sum[rc[i]] + key[i];
    }

    private void down(int i) {
        if (reverse[i]) {
            if (lc[i] != 0) {
                lc[i] = clone(lc[i]);
                reverse[lc[i]] ^= true;
            }
            if (rc[i] != 0) {
                rc[i] = clone(rc[i]);
                reverse[rc[i]] ^= true;
            }
            lc[i] ^= rc[i] ^ (rc[i] = lc[i]);
            reverse[i] = false;
        }
    }

    private int clone(int i) {
        key[++no] = key[i];
        sz[no] = sz[i];
        priority[no] = priority[i];
        sum[no] = sum[i];
        lc[no] = lc[i];
        rc[no] = rc[i];
        reverse[no] = reverse[i];
        return no;
    }

    private int create(int k) {
        key[++no] = k;
        sz[no] = 1;
        priority[no] = Math.random();
        sum[no] = k;
        return no;
    }

}
package algorithm.tree.order.FHQ.persistent;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 可持久化平衡树
 * 测试链接：https://www.luogu.com.cn/problem/P3835
 * 推荐使用静态数组（预处理空间），否则扩容开销太大
 */
public class Q1 {

    int MAXN = 500001, MAXM = MAXN * 50;
    int[] key = new int[MAXM], lc = new int[MAXM], rc = new int[MAXM], sz = new int[MAXM];
    double[] priority = new double[MAXM];
    int[] head = new int[MAXN];
    int no;

    void solve() {
        int n = sc.nextInt();
        for (int cur = 1, i, opt, x; cur <= n; cur++) {
            i = sc.nextInt();
            opt = sc.nextInt();
            x = sc.nextInt();
            switch (opt) {
                case 1:
                    add(cur, i, x);
                    break;
                case 2:
                    remove(cur, i, x);
                    break;
                case 3:
                    out.println(rank(cur, i, x));
                    break;
                case 4:
                    out.println(rankKey(cur, i, x));
                    break;
                case 5:
                    out.println(floor(cur, i, x - 1));
                    break;
                default:
                    out.println(ceiling(cur, i, x + 1));
                    break;
            }
        }
    }

    void add(int cur, int i, int k) {
        split(0, 0, head[i], k);
        int l = rc[0], r = lc[0];
        lc[0] = rc[0] = 0;
        head[cur] = merge(merge(l, create(k)), r);
    }

    void remove(int cur, int i, int k) {
        int l, m, r;
        split(0, 0, head[i], k);
        r = lc[0];
        m = rc[0];
        split(0, 0, m, k - 1);
        m = lc[0];
        l = rc[0];
        lc[0] = rc[0] = 0;
        m = merge(lc[m], rc[m]);
        head[cur] = merge(merge(l, m), r);
    }

    int floor(int cur, int i, int k) {
        head[cur] = head[i];
        return floor(head[i], k);
    }

    int floor(int i, int k) {
        if (i == 0) {
            return Integer.MIN_VALUE + 1;
        }
        if (key[i] <= k) {
            return Math.max(key[i], floor(rc[i], k));
        } else {
            return floor(lc[i], k);
        }
    }

    int ceiling(int cur, int i, int k) {
        head[cur] = head[i];
        return ceiling(k, head[i]);
    }

    int ceiling(int k, int i) {
        if (i == 0) {
            return Integer.MAX_VALUE;
        }
        if (key[i] >= k) {
            return Math.min(key[i], ceiling(k, lc[i]));
        } else {
            return ceiling(k, rc[i]);
        }
    }

    int rank(int cur, int i, int k) {
        head[cur] = head[i];
        return smallCount(k, head[i]) + 1;
    }
    
    int smallCount(int k, int i) {
        if (i == 0) {
            return 0;
        }
        if (key[i] >= k) {
            return smallCount(k, lc[i]);
        } else {
            return sz[lc[i]] + 1 + smallCount(k, rc[i]);
        }
    }

    int rankKey(int cur, int i, int rk) {
        head[cur] = head[i];
        return rankKey(rk, head[i]);
    }
    
    int rankKey(int rk, int i) {
        if (sz[lc[i]] >= rk) {
            return rankKey(rk, lc[i]);
        } else if (sz[lc[i]] + 1 < rk) {
            return rankKey(rk - sz[lc[i]] - 1, rc[i]);
        }
        return key[i];
    }

    // 按 key 分裂： <= k 在左，> k 在右
    void split(int l, int r, int i, int k) {
        if (i == 0) {
            rc[l] = lc[r] = 0;
            return;
        }
        i = clone(i);
        if (key[i] <= k) {
            rc[l] = i;
            split(i, r, rc[i], k);
        } else {
            lc[r] = i;
            split(l, i, lc[i], k);
        }
        up(i);
    }

    int merge(int l, int r) {
        if (l == 0 || r == 0) {
            return l + r;
        }
        if (priority[l] >= priority[r]) {
            l = clone(l);
            rc[l] = merge(rc[l], r);
            up(l);
            return l;
        } else {
            r = clone(r);
            lc[r] = merge(l, lc[r]);
            up(r);
            return r;
        }
    }

    void up(int i) {
        sz[i] = sz[lc[i]] + sz[rc[i]] + 1;
    }

    int clone(int i) {
        key[++no] = key[i];
        lc[no] = lc[i];
        rc[no] = rc[i];
        sz[no] = sz[i];
        priority[no] = priority[i];
        return no;
    }

    int create(int k) {
        key[++no] = k;
        sz[no] = 1;
        priority[no] = Math.random();
        return no;
    }

}

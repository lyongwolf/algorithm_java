package algorithm.tree.tree_in_tree.seg_in_bit;
import static algorithm.zz.U.*;

import java.util.*;

/**
 * 树状数组套线段树
 * 测试链接：https://www.luogu.com.cn/problem/P3380
 */

public class Q1 {

    SegInBit tree;

    void solve() {
        int n = ni(), q = ni(), m = 1, v;
        int[] a = new int[n + 1];
        int[] val = new int[n + q];
        for (int i = 1; i <= n; i++) {
            a[i] = ni();
            val[i - 1] = a[i];
        }    
        int[][] query = new int[q][4];
        for (int i = 0, j = n; i < q; i++) {
            query[i][0] = ni();
            query[i][1] = ni();
            query[i][2] = ni();
            if (query[i][0] == 3) {
                val[j++] = query[i][2];
            } else {
                query[i][3] = ni();
                val[j++] = query[i][3];
            }
        }
        Arrays.sort(val);
        for (int i = 1; i < val.length; i++) {
            if (val[i - 1] != val[i]) {
                val[m++] = val[i];
            }
        }
        tree = new SegInBit(n, 0, m - 1);
        for (int i = 1; i <= n; i++) {
            a[i] = Arrays.binarySearch(val, 0, m, a[i]);
            tree.add(i, a[i]);
        }
        for (int[] t : query) {
            switch (t[0]) {
                case 1 -> println(tree.rank(t[1], t[2], Arrays.binarySearch(val, 0, m, t[3])));
                case 2 -> println(val[tree.rankKey(t[1], t[2], t[3])]);
                case 3 -> {
                    tree.remove(t[1], a[t[1]]);
                    a[t[1]] = Arrays.binarySearch(val, 0, m, t[2]);
                    tree.add(t[1], a[t[1]]);
                }
                case 4 -> {
                    v = tree.floor(t[1], t[2], Arrays.binarySearch(val, 0, m, t[3]));
                    println(v == Integer.MIN_VALUE ? -2147483647 : val[v]);
                }
                case 5 -> {
                    v = tree.ceiling(t[1], t[2], Arrays.binarySearch(val, 0, m, t[3]));
                    println(v == Integer.MAX_VALUE ? v : val[v]);
                }
            }
        }
    }

}

class SegInBit {
    private static final int MAXN = 100000, MAXT = 10000000;
    private static int[] root = new int[MAXN + 1], cnt = new int[MAXT], lc = new int[MAXT], rc = new int[MAXT];
    private static int full[] = new int[128], rest[] = new int[128], fi, ri;// 整体查询加速部分
    private static int no;
    
    private final int N, ilow, ihigh;

    public SegInBit(int N, int ilow, int ihigh) {
        this.N = N;
        this.ilow = ilow;
        this.ihigh = ihigh;
        for (int i = 1; i <= no; i++) {
            cnt[i] = lc[i] = rc[i] = 0;
        }
        for (int i = 1; i <= N; i++) {
            root[i] = i;
        }
        no = N;
    }

    // 在 o 位置插入值 v
    public void add(int o, int v) {
        while (o <= N) {
            iadd(v, 1, ilow, ihigh, root[o]);
            o += o & -o;
        }
    }

    // 删除 o 位置的 v
    public void remove(int o, int v) {
        while (o <= N) {
            iadd(v, -1, ilow, ihigh, root[o]);
            o += o & -o;
        }
    } 

    private void iadd(int o, int v, int l, int r, int i) {
        cnt[i] += v;
        if (l == r) {
            return;
        }
        int m = (l + r) / 2;
        if (o <= m) {
            if (lc[i] == 0) {
                lc[i] = ++no;
            }
            iadd(o, v, l, m, lc[i]);
        } else {
            if (rc[i] == 0) {
                rc[i] = ++no;
            }
            iadd(o, v, m + 1, r, rc[i]);
        }
    }

    // 查询 k 在 [l, r] 中的排名
    public int rank(int l, int r, int k) {
        if (l > r) {
            return 1;
        }
        begin(l, r);
        return ismallCount(k, ilow, ihigh) + 1;
    }

    private int ismallCount(int o, int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = (l + r) / 2;
        if (o <= m) {
            for (int i = 0; i < fi; i++) {
                full[i] = lc[full[i]];
            }
            for (int i = 0; i < ri; i++) {
                rest[i] = lc[rest[i]];
            }
            return ismallCount(o, l, m);
        } else {
            int ans = 0;
            for (int i = 0; i < fi; i++) {
                ans += cnt[lc[full[i]]];
                full[i] = rc[full[i]];
            }
            for (int i = 0; i < ri; i++) {
                ans -= cnt[lc[rest[i]]];
                rest[i] = rc[rest[i]];
            }
            return ans + ismallCount(o, m + 1, r);
        }
    }

    // 查询区间 [L, R] 中排名第 rk 小的值
    public int rankKey(int l, int r, int rk) {
        begin(l, r);
        return irankKey(rk, ilow, ihigh);
    }

    private int irankKey(int rk, int l, int r) {
        if (l == r) {
            return l;
        }
        int m = (l + r) / 2;
        int c = 0;
        for (int i = 0; i < fi; i++) {
            c += cnt[lc[full[i]]];
        }
        for (int i = 0; i < ri; i++) {
            c -= cnt[lc[rest[i]]];
        }
        if (c >= rk) {
            for (int i = 0; i < fi; i++) {
                full[i] = lc[full[i]];
            }
            for (int i = 0; i < ri; i++) {
                rest[i] = lc[rest[i]];
            }
            return irankKey(rk, l, m);
        } else {
            for (int i = 0; i < fi; i++) {
                full[i] = rc[full[i]];
            }
            for (int i = 0; i < ri; i++) {
                rest[i] = rc[rest[i]];
            }
            return irankKey(rk - c, m + 1, r);
        }
    }

    // 查询区间 [l, r] 中严格小于 v ，且最大的数，若不存在返回 Integer.MIN_VALUE
    public int floor(int l, int r, int v) {
        int rk = rank(l, r, v);
        return rk == 1 ? Integer.MIN_VALUE : rankKey(l, r, rk - 1);
    }

    // 查询区间 [l, r] 中严格大于 v ，且最小的数，若不存在返回 Integer.MAX_VALUE
    public int ceiling(int l, int r, int v) {
        int rk = rank(l, r, v + 1);
        begin(l, r);
        int c = 0;
        for (int i = 0; i < fi; i++) {
            c += cnt[full[i]];
        }
        for (int i = 0; i < ri; i++) {
            c -= cnt[rest[i]];
        }
        return rk > c ? Integer.MAX_VALUE : rankKey(l, r, rk);
    }

    private void begin(int l, int r) {
        fi = ri = 0;
        while (r > 0) {
            full[fi++] = root[r];
            r -= r & -r;
        }
        l--;
        while (l > 0) {
            rest[ri++] = root[l];
            l -= l & -l;
        }
    }
}
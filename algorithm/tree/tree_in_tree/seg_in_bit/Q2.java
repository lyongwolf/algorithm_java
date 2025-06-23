package algorithm.tree.tree_in_tree.seg_in_bit;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 动态维护树上路径第 k 小
 * 测试连接：https://www.luogu.com.cn/problem/P4175
 */

public class Q2 {

    SegInBit tree;

    int[] head, nxt, to, w, dep, pa[], sz, dfn;

    void solve() {
        int n = ni(), q = ni();
        head = new int[n + 1]; nxt = new int[n << 1]; to = new int[n << 1]; w = new int[n + 1]; dep = new int[n + 1]; pa = new int[n + 1][17]; sz = new int[n + 1]; dfn = new int[n + 1];
        int[] val = new int[n + q];
        int z = 0;
        for (int i = 1; i <= n; i++) {
            w[i] = ni();
            val[z++] = w[i];
        }
        for (int i = 1, j = 2; i < n; i++) {
            int u = ni(), v = ni();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        int[][] query = new int[q][3];
        for (int i = 0; i < q; i++) {
            query[i][0] = ni();
            query[i][1] = ni();
            query[i][2] = ni();
            if (query[i][0] == 0) {
                val[z++] = query[i][2];
            }
        }
        Arrays.sort(val, 0, z);
        int m = 1;
        for (int i = 1; i < z; i++) {
            if (val[i - 1] != val[i]) {
                val[m++] = val[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            w[i] = Arrays.binarySearch(val, 0, m, w[i]);
        }
        tree = new SegInBit(n + 1, 0, m);
        init(0, 1);
        for (int[] t : query) {
            int k = t[0], u = t[1], v = t[2];
            if (k == 0) {
                v = Arrays.binarySearch(val, 0, m, v);
                tree.remove(dfn[u], w[u]);
                tree.add(dfn[u] + sz[u], w[u]);
                w[u] = v;
                tree.add(dfn[u], v);
                tree.remove(dfn[u] + sz[u], v);
            } else {
                int a = lca(u, v), f = pa[a][0], tot = dep[u] + dep[v] - dep[a] - dep[f];
                if (tot < k) {
                    println("invalid request!");
                } else {
                    println(val[tree.query(dfn[u], dfn[v], dfn[a], dfn[f], tot - k + 1)]);
                }
            }
        }
    }

    int lca(int u, int v) {
        if (dep[u] < dep[v]) {
            u ^= v ^ (v = u);
        }
        for (int i = 0, d = dep[u] - dep[v]; i < 17; i++) {
            if ((d >> i & 1) != 0) {
                u = pa[u][i];
            }
        }
        if (u == v) {
            return u;
        }
        for (int i = 16; i >= 0; i--) {
            if (pa[u][i] != pa[v][i]) {
                u = pa[u][i];
                v = pa[v][i];
            }
        }
        return pa[u][0];
    }

    void init(int f, int u) {
        int[] stk = new int[head.length];
        int z = 1, di = 0;
        stk[z] = u;
        dep[u] = 1;
        while (z > 0) {
            u = stk[z];
            f = pa[u][0];
            if (dfn[u] == 0) {
                dfn[u] = ++di;
                for (int e = head[u], v; e != 0; e = nxt[e]) {
                    if (f != (v = to[e])) {
                        stk[++z] = v;
                        pa[v][0] = u;
                        dep[v] = dep[u] + 1;
                        pa[v][0] = u;
                        for (int i = 1; i < 17; i++) {
                            pa[v][i] = pa[pa[v][i - 1]][i - 1];
                        }
                    }
                }
            } else {
                z--;
                sz[u] = 1;
                for (int e = head[u], v; e != 0; e = nxt[e]) {
                    if (f != (v = to[e])) {
                        sz[u] += sz[v];
                    }
                }
                tree.add(dfn[u], w[u]);
                tree.remove(dfn[u] + sz[u], w[u]);
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

    // 适用于题目的特殊查询
    public int query(int u, int v, int a, int f, int rk) {
        fi = ri = 0;
        while (u > 0) {
            full[fi++] = root[u];
            u -= u & -u;
        }
        while (v > 0) {
            full[fi++] = root[v];
            v -= v & -v;
        }
        while (a > 0) {
            rest[ri++] = root[a];
            a -= a & -a;
        }
        while (f > 0) {
            rest[ri++] = root[f];
            f -= f & -f;
        }
        return irankKey(rk, ilow, ihigh);
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

package algorithm.uinion_find.persistent;
import static algorithm.zz.U.*;
import java.util.*;
/**
 * 可持久化并查集（模板）
 * 测试链接：https://www.luogu.com.cn/problem/P3402
 */
public class Basic {

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        DSUPersistent dsu = new DSUPersistent(n);
        for (int i = 1, opt, k, a, b; i <= m; i++) {
            opt = sc.nextInt();
            if (opt == 1) {
                a = sc.nextInt();
                b = sc.nextInt();
                dsu.rollback(i - 1);
                dsu.union(a, b);
            } else if (opt == 2) {
                k = sc.nextInt();
                dsu.rollback(k);
            } else {
                a = sc.nextInt();
                b = sc.nextInt();
                dsu.rollback(i - 1);
                a = dsu.find(a);
                b = dsu.find(b);
                out.println(a == b ? 1 : 0);
            }
        }
    }

}

class DSUPersistent {
    private static final int MAXN = 200001, MAXT = 8000001;
    private static int[] rootPa = new int[MAXN], rootSz = new int[MAXN];
    private static int[] lc = new int[MAXT], rc = new int[MAXT], val = new int[MAXT];
    private static int no, vs;

    private int N;

    public DSUPersistent(int n) {
        for (int i = 0; i <= vs; i++) {
            rootPa[i] = rootSz[i] = 0;
        }
        for (int i = 1; i <= no; i++) {
            lc[i] = rc[i] = val[i] = 0;
        }
        vs = no = 0;
        N = n;
        rootPa[0] = buildPa(1, N);
        rootSz[0] = buildSz(1, N);
    }

    private int buildPa(int l, int r) {
        int u = ++no;
        if (l == r) {
            val[u] = l;
            return u;
        }
        int m = (l + r) >> 1;
        lc[u] = buildPa(l, m);
        rc[u] = buildPa(m + 1, r);
        return u;
    }

    private int buildSz(int l, int r) {
        int u = ++no;
        if (l == r) {
            val[u] = 1;
            return u;
        }
        int m = (l + r) >> 1;
        lc[u] = buildSz(l, m);
        rc[u] = buildSz(m + 1, r);
        return u;
    }

    // 创建新的版本，且该版本是 x 版本的拷贝
    public void rollback(int x) {
        rootPa[++vs] = rootPa[x];
        rootSz[vs] = rootSz[x];
    }

    // 对当前版本的 a 、b 两个点进行合并
    public void union(int a, int b) {
        int f1 = find(a), f2 = find(b);
        if (f1 == f2) {
            return;
        }
        int s1 = query(f1, 1, N, rootSz[vs]), s2 = query(f2, 1, N, rootSz[vs]);
        if (s1 >= s2) {
            rootPa[vs] = set(f2, f1, 1, N, rootPa[vs]);
            rootSz[vs] = set(f1, s1 + s2, 1, N, rootSz[vs]);
        } else {
            rootPa[vs] = set(f1, f2, 1, N, rootPa[vs]);
            rootSz[vs] = set(f2, s1 + s2, 1, N, rootSz[vs]);
        }
    }
    
    // 查询当前版本 a 点的 父节点编号
    public int find(int a) {
        int f;
        while ((f = query(a, 1, N, rootPa[vs])) != a) {
            a = f;
        }
        return f;
    }

    private int query(int o, int l, int r, int i) {
        if (l == r) {
            return val[i];
        }
        int m = (l + r) >> 1;
        if (o <= m) {
            return query(o, l, m, lc[i]);
        } else {
            return query(o, m + 1, r, rc[i]);
        }
    }

    private int set(int o, int v, int l, int r, int i) {
        int u = ++no;
        if (l == r) {
            val[u] = v;
            return u;
        }
        int m = (l + r) >> 1;
        if (o <= m) {
            lc[u] = set(o, v, l, m, lc[i]);
            rc[u] = rc[i];
        } else {
            lc[u] = lc[i];
            rc[u] = set(o, v, m + 1, r, rc[i]);
        }
        return u;
    }
}
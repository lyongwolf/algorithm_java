package algorithm.graph.Heavy_Light_Decomposition;

import java.util.*;
import java.io.*;

/**
 * 树链剖分
 * test link : https://www.luogu.com.cn/problem/P3384
 */

public class Main {

    static int n, m, r, p;
    static int[] head, nxt, to;
    static long[] val;
    static int[] fa, dep, siz, son;
    static int[] top, dfn, rnk;
    static int cnt;
    static long[] sum, lazy;


    static void solve() {
        n = sc.nextInt();
        m = sc.nextInt();
        r = sc.nextInt();
        p = sc.nextInt();
        head = new int[n + 1];
        nxt = new int[n << 1];
        to = new int[n << 1];
        val = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            val[i] = sc.nextLong();
        }
        for (int i = 1, j = 2; i < n; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        fa = new int[n + 1];
        dep = new int[n + 1];
        siz = new int[n + 1];
        son = new int[n + 1];
        dfs(0, r, 0);
        top = new int[n + 1];
        dfn = new int[n + 1];
        rnk = new int[n + 1];
        cnt = 0;
        dfs2(0, r, r);
        sum = new long[n << 2];
        lazy = new long[n << 2];
        build(1, n, 1);
        int x, y, z;
        while (m-- > 0) {
            switch (sc.nextInt()) {
                case 1 : 
                    x = sc.nextInt();
                    y = sc.nextInt();
                    z = sc.nextInt();
                    add(x, y, z);
                    break;
                case 2 : 
                    x = sc.nextInt();
                    y = sc.nextInt();
                    out.println(query(x, y) % p);
                    break;
                case 3 : 
                    x = sc.nextInt();
                    z = sc.nextInt();
                    add(dfn[x], dfn[x] + siz[x] - 1, z, 1, n, 1);
                    break;
                case 4 : 
                    x = sc.nextInt();
                    out.println(query(dfn[x], dfn[x] + siz[x] - 1, 1, n, 1) % p);
                    break;
            }
        }
    }

    static void up(int i) {
        sum[i] = sum[i << 1] + sum[i << 1 | 1];
    }

    static void down(int i, int ln, int rn) {
        if (lazy[i] != 0) {
            lazy[i << 1] += lazy[i];
            lazy[i << 1 | 1] += lazy[i];
            sum[i << 1] += lazy[i] * ln;
            sum[i << 1 | 1] += lazy[i] * rn;
            lazy[i] = 0;
        }
    }

    static void build(int l, int r, int i) {
        if (l == r) {
            sum[i] = val[rnk[l]];
            return;
        }
        int m = (l + r) >> 1;
        build(l, m, i << 1);
        build(m + 1, r, i << 1 | 1);
        up(i);
    }

    static void add(int u, int v, long x) {
        while (top[u] != top[v]) {
            if (dep[top[u]] < dep[top[v]]) {
                int t = u; u = v; v = t;
            }
            add(dfn[top[u]], dfn[u], x, 1, n, 1);
            u = fa[top[u]];
        }
        if (u == 0) {
            return;
        }
        if (dep[u] > dep[v]) {
            int t = u; u = v; v = t;
        }
        add(dfn[u], dfn[v], x, 1, n, 1);
    }

    static void add(int L, int R, long v, int l, int r, int i) {
        if (L <= l && r <= R) {
            lazy[i] += v;
            sum[i] += v * (r - l + 1);
            return;
        }
        int m = (l + r) >> 1;
        down(i, m - l + 1, r - m);
        if (L <= m) {
            add(L, R, v, l, m, i << 1);
        }
        if (R > m) {
            add(L, R, v, m + 1, r, i << 1 | 1);
        }
        up(i);
    }

    static long query(int u, int v) {
        long ans = 0;
        while (top[u] != top[v]) {
            if (dep[top[u]] < dep[top[v]]) {
                int t = u; u = v; v = t;
            }
            ans += query(dfn[top[u]], dfn[u], 1, n, 1);
            u = fa[top[u]];
        }
        if (dep[u] > dep[v]) {
            int t = u; u = v; v = t;
        }
        ans += query(dfn[u], dfn[v], 1, n, 1);
        return ans;
    }

    static long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return sum[i];
        }
        int m = (l + r) >> 1;
        down(i, m - l + 1, r - m);
        long ans = 0;
        if (L <= m) {
            ans += query(L, R, l, m, i << 1);
        }
        if (R > m) {
            ans += query(L, R, m + 1, r, i << 1 | 1);
        }
        return ans;
    }

    static void dfs2(int f, int u, int t) {
        top[u] = t;
        dfn[u] = ++cnt;
        rnk[cnt] = u;
        if (son[u] != 0) {
            dfs2(u, son[u], t);
        }
        for (int e = head[u], v; e != 0; e = nxt[e]) {
            if ((v = to[e]) != f && v != son[u]) {
                dfs2(u, v, v);
            }
        }
    }

    static void dfs(int f, int u, int d) {
        fa[u] = f;
        dep[u] = d;
        siz[u] = 1;
        for (int e = head[u], v; e != 0; e = nxt[e]) {
            if ((v = to[e]) != f) {
                dfs(u, v, d + 1);
                siz[u] += siz[v];
                if (siz[son[u]] < siz[v]) {
                    son[u] = v;
                }
            }
        }
    }

   
    
    static boolean retest = true;
    static FastReader sc = new FastReader();
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static void main(String[] args) {
        if (retest) {int t = sc.nextInt(); while (t-- > 0) solve();} else solve(); out.flush(); out.close();
    }
    static class FastReader {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in)); 
        StringTokenizer st;
        String next() {
            try {while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(r.readLine()); return st.nextToken();} 
            catch (Exception e) {return null;}
        }
        int nextInt() {return Integer.parseInt(next());}
        long nextLong() {return Long.parseLong(next());}
        double nextDouble() {return Double.parseDouble(next());}
    }
}

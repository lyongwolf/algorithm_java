package algorithm.graph.tarjan.directed_graph;

import java.io.*;
import java.util.*;

/**
 * 求有向图的强连通分量
 * 测试链接：https://www.luogu.com.cn/problem/P2863
 */
public class SCC {

    static int[] head, nxt, to;
    static int[] dfn, low, stk, scc, siz;
    static boolean[] vis;
    static int ts, top, no;

    static void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        head = new int[n + 1];
        nxt = new int[m + 1];
        to = new int[m + 1];
        for (int i = 0, j = 1; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            nxt[j] = head[u];
            head[u] = j;
            to[j++] = v;
        }
        dfn = new int[n + 1];
        low = new int[n + 1];
        stk = new int[n + 1];
        scc = new int[n + 1];
        siz = new int[n + 1];
        vis = new boolean[n + 1];
        ts = 0;
        top = 0;
        no = 0;
        for (int u = 1; u <= n; u++) {
            if (dfn[u] == 0) {
                tarjan(u);
            }
        }
        int cnt = 0;
        for (int u = 1; u <= n; u++) {
            if (dfn[u] == low[u] && siz[scc[u]] > 1) {
                cnt++;
            }
        }
        out.println(cnt);
    }

    static void tarjan(int u) {
        dfn[u] = low[u] = ++ts;
        stk[++top] = u;
        vis[u] = true;
        for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
            if (dfn[v] == 0) {
                tarjan(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (vis[v]) {
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
        if (dfn[u] == low[u]) {
            no++;
            int o;
            do {
                o = stk[top--];
                vis[o] = false;
                scc[o] = no;
                siz[no]++;
            } while (o != u);
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

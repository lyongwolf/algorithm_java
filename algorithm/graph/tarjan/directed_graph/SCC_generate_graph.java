package algorithm.graph.tarjan.directed_graph;

import java.util.*;
import java.io.*;

/**
 * SCC 缩点建图  有向无环图（拓扑图）
 * 测试链接：https://www.luogu.com.cn/problem/P3387
 */
public class SCC_generate_graph {

    static int[] head, nxt, to, val;
    static int[] dfn, low, stk, scc;
    static boolean[] vis;
    static int ts, top, no;
    static List<Integer>[] graph;
    static int[] val2;
    static int[] memo;

    static void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        head = new int[n + 1];
        nxt = new int[m + 1];
        to = new int[m + 1];
        val = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            val[i] = sc.nextInt();
        }
        for (int i = 1; i <= m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            nxt[i] = head[u]; head[u] = i; to[i] = v;
        }
        dfn = new int[n + 1];
        low = new int[n + 1];
        stk = new int[n + 1];
        scc = new int[n + 1];
        vis = new boolean[n + 1];
        ts = top = no = 0;
        for (int u = 1; u <= n; u++) {
            if (dfn[u] == 0) {
                tarjan(u);
            }
        }
        graph = new List[no + 1];
        val2 = new int[no + 1];
        Arrays.setAll(graph, v -> new ArrayList<>());
        for (int u = 1; u <= n; u++) {
            val2[scc[u]] += val[u];
            for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
                if (scc[u] != scc[v]) {
                    graph[scc[u]].add(scc[v]);
                }
            }
        }
        memo = new int[no + 1];
        Arrays.fill(memo, -1);
        int ans = 0;
        for (int u = 1; u <= no; u++) {
            ans = Math.max(ans, dfs(u));
        }
        out.println(ans);
    }

    static int dfs(int u) {
        if (memo[u] != -1) {
            return memo[u];
        }
        memo[u] = 0;
        for (int v : graph[u]) {
            memo[u] = Math.max(memo[u], dfs(v));
        }
        return memo[u] += val2[u];
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

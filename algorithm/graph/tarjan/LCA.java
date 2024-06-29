package algorithm.graph.tarjan;

import java.io.*;
import java.util.*;

/**
 * lca
 * test link: https://www.luogu.com.cn/problem/P3379
 */
public class LCA {

    static int[] head, nxt, to;
    static int[] parent;
    static boolean[] mark;
    static List<int[]>[] task;
    static int[] ans;

    static void solve() {
        int n = sc.nextInt(), m = sc.nextInt(), s = sc.nextInt();
        head = new int[n + 1];
        nxt = new int[n << 1];
        to = new int[n << 1];
        for (int i = 1, j = 1; i < n; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }
        mark = new boolean[n + 1];
        task = new List[n + 1];
        ans = new int[m];
        Arrays.setAll(task, v -> new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            task[u].add(new int[]{v, i});
            task[v].add(new int[]{u, i});
        }
        dfs(0, s);
        for (int v : ans) {
            out.println(v + " ");
        }
    }

    static void dfs(int f, int u) {
        mark[u] = true;
        for (int i = head[u], v; i != 0; i = nxt[i]) {
            v = to[i];
            if (f != v) {
                dfs(u, v);
                parent[v] = u;
            }
        }
        for (int[] tmp : task[u]) {
            if (mark[tmp[0]]) {
                ans[tmp[1]] = find(tmp[0]);
            }
        }
    }

    static int find(int v) {
        return parent[v] == v ? v : (parent[v] = find(parent[v]));
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

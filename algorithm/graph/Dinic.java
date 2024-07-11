package algorithm.graph;

import java.io.*;
import java.util.*;

/**
 * 网络最大流
 * 测试链接：https://www.luogu.com.cn/problem/P3376
 */

public class Dinic {
    
    static int n, m, s, t;
    static int[] head, nxt, to, es, dep;
    static long[] wt;

    static void solve() {
        n = sc.nextInt();
        m = sc.nextInt();
        s = sc.nextInt();
        t = sc.nextInt();
        head = new int[n + 1];
        nxt = new int[(m + 2) << 1];
        to = new int[(m + 2) << 1];
        wt = new long[(m + 2) << 1];
        for (int i = 0, j = 2; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt(), w = sc.nextInt();
            nxt[j] = head[u]; head[u] = j; wt[j] = w; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; wt[j] = 0; to[j++] = u;
        }
        es = new int[n + 1];
        dep = new int[n + 1];
        long ans = 0;
        while (bfs()) {
            ans += flow(s, Long.MAX_VALUE / 100);
        }
        out.println(ans);
    }

    static long flow(int u, long r) {
        if (u == t) {
            return r;
        }
        long ans = 0;
        long f;
        for (int e = es[u], v; e != 0 && r > 0; es[u] = e, e = nxt[e]) {
            v = to[e];
            if (wt[e] > 0 && dep[v] == dep[u] + 1) {
                f = flow(v, Math.min(r, wt[e]));
                if (f == 0) {
                    dep[v] = 0;
                }
                r -= f;
                wt[e] -= f;
                wt[e ^ 1] += f;
                ans += f;
            }
        }
        return ans;
    }

    static boolean bfs() {
        Arrays.fill(dep, 0);
        Deque<Integer> queue = new ArrayDeque<>();
        queue.addLast(s);
        dep[s] = 1;
        es[s] = head[s];
        while (!queue.isEmpty()) {
            int u = queue.removeFirst();
            for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
                if (dep[v] == 0 && wt[e] > 0) {
                    es[v] = head[v];
                    dep[v] = dep[u] + 1;
                    if (v == t) {
                        return true;
                    }
                    queue.addLast(v);
                }
            }
        }
        return false;
    }


    static boolean retest = false;
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

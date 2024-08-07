package algorithm.graph.tarjan.undirected_graph;

import java.io.*;
import java.util.*;
/**
 * 点双连通分量建图
 * 测试链接：https://www.luogu.com.cn/problem/P8435
 */
public class vDCC_generate_graph {

    static int[] head, nxt, to;
    static int[] dfn, low, stk, id;
    static boolean[] cut;
    static List<Integer>[] dcc;
    static int root, ts, top, no;
    static List<Integer>[] garph;

    static void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        head = new int[n + 1];
        nxt = new int[(m + 1) << 1];
        to = new int[(m + 1) << 1];
        for (int i = 0, j = 2; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        dfn = new int[n + 1];
        low = new int[n + 1];
        stk = new int[n + 1];
        id = new int[n + 1];
        cut = new boolean[n + 1];
        dcc = new List[n + 1];
        root = ts = top = no = 0;
        Arrays.setAll(dcc, v -> new ArrayList<>());
        for (int u = 1; u <= n; u++) {
            if (dfn[u] == 0) {
                root = u;
                boolean flag = true;
                for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
                    if (v != u) {
                        flag = false;
                    }
                }
                if (flag) {
                    dfn[u] = low[u] = ++ts;
                    dcc[++no].add(u);
                } else {
                    tarjan(u);
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            if (cut[i]) {
                id[i] = ++no;
            }
        }
        garph = new List[no + 1];
        Arrays.setAll(garph, v -> new ArrayList<>());
        for (int u = 1; u <= n; u++) {
            for (int v : dcc[u]) {
                if (cut[v]) {
                    garph[u].add(id[v]);
                    garph[id[v]].add(u);
                }
            }
        }

    }

    static void tarjan(int u) {
        dfn[u] = low[u] = ++ts;
        stk[++top] = u;
        int child = 0;
        for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
            if (dfn[v] == 0) {
                tarjan(v);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] >= dfn[u]) {
                    child++;
                    if (u != root || child > 1) {
                        cut[u] = true;
                    }
                    no++;
                    int o;
                    do {
                        o = stk[top--];
                        dcc[no].add(o);
                    } while (o != v);
                    dcc[no].add(u);
                }
            } else {
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
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

package algorithm.graph.tarjan.undirected_graph;
import static algorithm.zz.U.*;

import java.util.*;

/**
 * 边双连通分量缩点建图
 * 测试链接：https://codeforces.com/contest/1986/problem/F
 */
public class eDCC_generate_graph {
    
    int n, m;
    int[] head, nxt, to;
    int[] dfn, low, stk, dcc;
    boolean[] bri;
    int ts, top, no;
    List<Integer>[] garph;
    int[] val;
    long ans;

    void solve() {
        n = ni();
        m = ni();
        head = new int[n + 1];
        nxt = new int[(m + 1) << 1];
        to = new int[(m + 1) << 1];
        for (int i = 0, j = 2; i < m; i++) {
            int u = ni(), v = ni();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        dfn = new int[n + 1];
        low = new int[n + 1];
        stk = new int[n + 1];
        dcc = new int[n + 1];
        bri = new boolean[(m + 1) << 1];
        ts = top = no = 0;
        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
                tarjan(i, 0);
            }
        }
        garph = new List[no + 1];
        val = new int[no + 1];
        Arrays.setAll(garph, v -> new ArrayList<>());
        for (int u = 1; u <= n; u++) {
            val[dcc[u]]++;
        }
        for (int e = 2; e < (m + 1) << 1; e++) {
            if (bri[e]) {
                garph[dcc[to[e ^ 1]]].add(dcc[to[e]]);
            }
        }
        ans = 0;
        dfs(0, 1);
        println((long) n * (n - 1) / 2 - ans);
    }

    int dfs(int f, int u) {
        int size = val[u];
        for (int v : garph[u]) {
            if (v != f) {
                size += dfs(u, v);
            }
        }
        ans = Math.max(ans, (long) size * (n - size));
        return size;
    }

    void tarjan(int u, int ep) {
        dfn[u] = low[u] = ++ts;
        stk[++top] = u;
        for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
            if (dfn[v] == 0) {
                tarjan(v, e);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > dfn[u]) {
                    bri[e] = bri[e ^ 1] = true;
                }
            } else if (ep != (e ^ 1)) {
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
        if (dfn[u] == low[u]) {
            no++;
            int o;
            do {
                o = stk[top--];
                dcc[o] = no;
            } while (o != u);
        }
    }

}

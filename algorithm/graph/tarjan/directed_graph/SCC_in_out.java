package algorithm.graph.tarjan.directed_graph;
import static algorithm.zz.U.*;

import java.util.*;

/**
 * SCC缩点
 * 测试链接 : https://www.luogu.com.cn/problem/P2341
 */
public class SCC_in_out {

    int[] head, nxt, to;
    int[] dfn, low, stk, scc, sz;
    boolean[] vis;
    int ts, top, no;

    void solve() {
        int n = ni(), m = ni();
        head = new int[n + 1];
        nxt = new int[m + 1];
        to = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            int u = ni(), v = ni();
            nxt[i] = head[u]; head[u] = i; to[i] = v;
        }
        dfn = new int[n + 1];
        low = new int[n + 1];
        stk = new int[n + 1];
        scc = new int[n + 1];
        sz = new int[n + 1];
        vis = new boolean[n + 1];
        ts = top = no = 0;
        for (int u = 1; u <= n; u++) {
            if (dfn[u] == 0) {
                tarjan(u);
            }
        }
        int[] in = new int[no + 1], out = new int[no + 1];
        for (int u = 1; u <= n; u++) {
            for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
                if (scc[u] != scc[v]) {
                    in[scc[v]]++;
                    out[scc[u]]++;
                }
            }
        }
        int cnt = 0, sum = 0;
        for (int i = 1; i <= no; i++) {
            if (out[i] == 0) {
                cnt++;
                sum = sz[i];
            }
        }
        println(cnt == 1 ? sum : 0);
    }

    void tarjan(int u) {
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
                sz[no]++;
            } while (o != u);
        }
    }

}

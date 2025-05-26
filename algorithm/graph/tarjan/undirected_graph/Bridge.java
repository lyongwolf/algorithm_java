package algorithm.graph.tarjan.undirected_graph;
import static algorithm.zz.U.*;

import java.util.*;

/**
 * 无向图求割边（桥）
 */
public class Bridge {
    
    int[] head, nxt, to;
    int[] dfn, low;
    int ts;
    List<int[]> bridge;

    void solve() {
        int n = ni(), m = ni();
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
        ts = 0;
        bridge = new ArrayList<>();
        for (int u = 1; u <= n; u++) {
            if (dfn[u] == 0) {
                tarjan(u, 0);
            }
        }
        bridge.sort((i, j) -> i[0] == j[0] ? i[1] - j[1] : i[0] - j[0]);
        for (int[] tup : bridge) {
            println(tup[0] + " " + tup[1]);
        }
    }

    void tarjan(int u, int ep) {
        dfn[u] = low[u] = ++ts;
        for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
            if (dfn[v] == 0) {
                tarjan(v, e);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > dfn[u]) {
                    bridge.add(new int[]{Math.min(u, v), Math.max(u, v)});
                }
            } else if ((ep ^ 1) != e) {
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
    }

}

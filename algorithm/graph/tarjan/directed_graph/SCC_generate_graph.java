package algorithm.graph.tarjan.directed_graph;
import static algorithm.zz.U.*;

import java.util.*;

/**
 * SCC 缩点建图  有向无环图（拓扑图）
 * 测试链接：https://www.luogu.com.cn/problem/P3387
 */
public class SCC_generate_graph {

    int[] head, nxt, to, val;
    int[] dfn, low, stk, scc;
    boolean[] vis;
    int ts, top, no;
    List<Integer>[] graph;
    int[] val2;
    int[] memo;

    void solve() {
        int n = ni(), m = ni();
        head = new int[n + 1];
        nxt = new int[m + 1];
        to = new int[m + 1];
        val = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            val[i] = ni();
        }
        for (int i = 1; i <= m; i++) {
            int u = ni(), v = ni();
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
            ans = max(ans, dfs(u));
        }
        println(ans);
    }

    int dfs(int u) {
        if (memo[u] != -1) {
            return memo[u];
        }
        memo[u] = 0;
        for (int v : graph[u]) {
            memo[u] = max(memo[u], dfs(v));
        }
        return memo[u] += val2[u];
    }

    void tarjan(int u) {
        dfn[u] = low[u] = ++ts;
        stk[++top] = u;
        vis[u] = true;
        for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
            if (dfn[v] == 0) {
                tarjan(v);
                low[u] = min(low[u], low[v]);
            } else if (vis[v]) {
                low[u] = min(low[u], dfn[v]);
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

}

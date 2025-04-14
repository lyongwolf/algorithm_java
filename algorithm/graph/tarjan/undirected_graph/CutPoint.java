package algorithm.graph.tarjan.undirected_graph;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 求无向图割点
 * 测试链接：https://www.luogu.com.cn/problem/P3388
 */
public class CutPoint {
    
    int[] head, nxt, to;
    int[] dfn, low;
    int root;
    boolean[] cut;
    int ts;

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        head = new int[n + 1];
        nxt = new int[m << 1 | 1];
        to = new int[m << 1 | 1];
        for (int i = 0, j = 1; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        dfn = new int[n + 1];
        low = new int[n + 1];
        cut = new boolean[n + 1];
        ts = 0;
        List<Integer> list = new ArrayList<>();
        for (int u = 1; u <= n; u++) {
            if (dfn[u] == 0) {
                root = u;
                tarjan(u);
            }
            if (cut[u]) {
                list.add(u);
            }
        }
        out.println(list.size());
        list.forEach(v -> out.print(v + " "));
        out.writeln();
    }

    void tarjan(int u) {
        dfn[u] = low[u] = ++ts;
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
                }
            } else {
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
    }

}

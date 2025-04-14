package algorithm.graph.tarjan;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * lca
 * test link: https://www.luogu.com.cn/problem/P3379
 */
public class LCA {

    int[] head, nxt, to;
    int[] parent;
    boolean[] mark;
    List<int[]>[] task;
    int[] ans;

    void solve() {
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

    void dfs(int f, int u) {
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

    int find(int v) {
        return parent[v] == v ? v : (parent[v] = find(parent[v]));
    }

}

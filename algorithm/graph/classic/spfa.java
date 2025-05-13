package algorithm.graph.classic;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 图中判断负环
 * 测试链接：https://www.luogu.com.cn/problem/P5960
 */
public class spfa {

    int n;
    int[] head, nxt, to, wt;
    boolean[] vis;
    int[] dis;

    void solve() {
        n = ni();
        int m = ni();
        head = new int[n + 1];
        nxt = new int[m + 1];
        to = new int[m + 1];
        wt = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            int v = ni(), u = ni(), w = ni();
            nxt[i] = head[u];
            head[u] = i;
            wt[i] = w;
            to[i] = v;
        }

        // spfa
        
        vis = new boolean[n + 1];
        dis = new int[n + 1];
        // for (int i = 1; i <= n; i++) {
        //     if (dfs(i)) {
        //         out.println("NO");
        //         return;
        //     }
        // }
        if (bfs()) {
            println("NO");
            return;
        }
        for (int i = 1; i <= n; i++) {
            print(dis[i] + " ");
        }
        writeln();
    }

    boolean dfs(int u) {
        vis[u] = true;
        for (int e = head[u], v; e != 0; e = nxt[e]) {
            v = to[e];
            if (dis[v] > dis[u] + wt[e]) {
                dis[v] = dis[u] + wt[e];
                if (vis[v] || dfs(v)) {
                    return true;
                }
            }
        }
        vis[u] = false;
        return false;
    }

    boolean bfs() {
        int[] cnt = new int[n + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            queue.addLast(i);
            vis[i] = true;
        }
        while (!queue.isEmpty()) {
            int u = queue.removeFirst();
            vis[u] = false;
            for (int e = head[u], v; e != 0; e = nxt[e]) {
                v = to[e];
                if (dis[v] > dis[u] + wt[e]) {
                    dis[v] = dis[u] + wt[e];
                    if (++cnt[v] == n) {
                        return true;
                    }
                    if (!vis[v]) {
                        queue.addLast(v);
                        vis[v] = true;
                    }
                }
            }
        }
        return false;
    }

}
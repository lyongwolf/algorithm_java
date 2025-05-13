package algorithm.graph.Bipartite_Graph;
import static algorithm.zz.U.*;
import java.util.*;
/**
 * 测试链接：https://www.luogu.com.cn/problem/P3386
 * 二分图最大匹配（匈牙利算法）
 */
public class maximum_match {

    int[] head, nxt, to;
    boolean[] vis;
    int[] match;

    void solve() {
        int n = ni(), m = ni(), e = ni();
        head = new int[n + m + 1];
        nxt = new int[e + 1];
        to = new int[e + 1];
        for (int i = 1; i <= e; i++) {
            int u = ni(), v = ni() + n;
            nxt[i] = head[u]; head[u] = i; to[i] = v;
        }
        vis = new boolean[n + m + 1];
        match = new int[n + m + 1];
        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            Arrays.fill(vis, false);
            if (dfs(i)) {
                cnt++;
            }
        }
        println(cnt);
    }

    boolean dfs(int u) {
        for (int e = head[u], v; e != 0; e = nxt[e]) {
            v = to[e];
            if (vis[v]) {
                continue;
            }
            vis[v] = true;
            if (match[v] == 0 || dfs(match[v])) {
                match[v] = u;
                return true;
            }
        }
        return false;
    }
    
}
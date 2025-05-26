package algorithm.graph.tree_chain_decomposition;
import static algorithm.zz.U.*;

import java.util.*;

/**
 * 长链剖分（模板）
 * 测试链接：https://codeforces.com/contest/1009/problem/F
 */

public class LCD {

    int[] stk = new int[1000001];
    
    int[] head, nxt, to, fa, dep, len, son, top, dfn;

    int[] dp;
    int[] ans;

    void solve() {
        int n = ni();
        head = new int[n + 1]; nxt = new int[n << 1]; to = new int[n << 1];
        fa = new int[n + 1]; dep = new int[n + 1]; len = new int[n + 1]; son = new int[n + 1]; top = new int[n + 1]; dfn = new int[n + 1];
        for (int i = 1, j = 2; i < n; i++) {
            int u = ni(), v = ni();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        init1(0, 1);
        init2(0, 1, 1);

        dp = new int[n + 1];
        ans = new int[n + 1];
        dfs(0, 1);
        for (int i = 1; i <= n; i++) {
            println(ans[i]);
        }
    }

    void dfs(int f, int u) {
        dp[dfn[u]] = 1;
        if (son[u] == 0) {
            return;
        }
        dfs(u, son[u]);
        ans[u] = ans[son[u]] + 1;
        for (int e = head[u], v; e != 0; e = nxt[e]) {
            if (f != (v = to[e]) && v != son[u]) {
                dfs(u, v);
                for (int i = 1; i <= len[v]; i++) {
                    int c = dp[dfn[u] + i] + dp[dfn[v] + i - 1];
                    if (c > dp[dfn[u] + ans[u]] || (c == dp[dfn[u] + ans[u]] && ans[u] > i)) {
                        ans[u] = i;
                    }
                    dp[dfn[u] + i] = c;
                }
            }
        }
        if (dp[dfn[u] + ans[u]] == 1) {
            ans[u] = 0;
        }
    }

    // --------- 以下为长链剖分预处理 -----------

   void init2(int f, int u, int t) {
        int z = 0, no = 0;
        stk[++z] = u;
        top[u] = t;
        while (z > 0) {
            u = stk[z];
            f = fa[u];
            t = top[u];
            if (dfn[u] == 0) {
                dfn[u] = ++no;
                top[u] = t;
                for (int e = head[u], v; e != 0; e = nxt[e]) {
                    if ((v = to[e]) != f && v != son[u]) {
                        stk[++z] = v;
                        top[v] = v;
                    }
                }
                if (son[u] != 0) {
                    stk[++z] = son[u];
                    top[son[u]] = t;
                }
            } else {
                z--;
            }
        }
    }

    void init1(int f, int u) {
        int z = 0;
        stk[++z] = u;
        dep[u] = 1;
        while (z > 0) {
            u = stk[z];
            f = fa[u];
            if (len[u] == 0) {
                len[u] = 1;
                for (int e = head[u], v; e != 0; e = nxt[e]) {
                    if (f != (v = to[e])) {
                        stk[++z] = v;
                        fa[v] = u;
                        dep[v] = dep[u] + 1;
                    }
                }
            } else {
                z--;
                for (int e = head[u], v; e != 0; e = nxt[e]) {
                    if (f != (v = to[e])) {
                        if (len[son[u]] < len[v]) {
                            son[u] = v;
                        }
                    }
                }
                len[u] += len[son[u]];
            }
        }
    }

}
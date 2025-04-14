package algorithm.graph.network_flow;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 最小费用最大流
 * 测试链接：https://www.luogu.com.cn/problem/P3381
 */
public class CostFlow {

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt(), s = sc.nextInt(), t = sc.nextInt();
        MCMF mcmf = new MCMF(n, m, s, t);
        while (m-- > 0) {
            int u = sc.nextInt(), v = sc.nextInt(), w = sc.nextInt(), c = sc.nextInt();
            mcmf.addBiEdge(u, v, w, c);
        }
        out.print(mcmf.maxFlow());
        out.print(' ');
        out.println(mcmf.cost());
    }

}

class MCMF {
    private final long INF = 0x3f3f3f3f3f3f3f3fL;
    private int n, m, s, t, z;
    private int[] head, nxt, to, es;
    private int qn, que[];
    private long[] cap, cost;
    private boolean[] vis;
    private long[] dis;
    private long res;

    public MCMF(int n, int m, int s, int t) {
        this.n = n;
        this.m = m;
        this.s = s;
        this.t = t;
        z = 2;
        head = new int[n + 1];
        nxt = new int[m + 1 << 1];
        to = new int[m + 1 << 1];
        es = new int[n + 1];
        qn = 1 << (32 - Integer.numberOfLeadingZeros(n - 1));
        que = new int[qn--];
        cap = new long[m + 1 << 1];
        cost = new long[m + 1 << 1];
        vis = new boolean[n + 1];
        dis = new long[n + 1];
    }

    public void addBiEdge(int u, int v, long w, long c) {
        addEdge(u, v, w, c);
        addEdge(v, u, 0, -c);
    }

    private void addEdge(int u, int v, long w, long c) {
        nxt[z] = head[u];
        head[u] = z;
        cap[z] = w;
        cost[z] = c;
        to[z++] = v;
    }

    public long maxFlow() {
        res = 0;
        long ans = 0, x;
        while (spfa()) {
            System.arraycopy(head, 1, es, 1, n);
            do {
                x = flow(s, INF);
                ans += x;
            } while (x > 0);
        }
        return ans;
    }

    public long cost() {
        return res;
    }

    private long flow(int u, long r) {
        if (u == t) {
            return r;
        }
        vis[u] = true;
        long f, ans = 0;
        for (int e = es[u], v; e != 0 && r > 0; es[u] = e, e = nxt[e]) {
            v = to[e];
            if (!vis[v] && cap[e] > 0 && dis[v] == dis[u] + cost[e]) {
                f = flow(v, Math.min(r, cap[e]));
                if (f > 0) {
                    res += f * cost[e];
                    cap[e] -= f;
                    cap[e ^ 1] += f;
                    r -= f;
                    ans += f;
                }
            }
        }
        vis[u] = false;
        return ans;
    }

    private boolean spfa() {
        Arrays.fill(dis, INF);
        que[0] = s;
        dis[s] = 0;
        vis[s] = true;
        for (int l = 0, r = 0; l <= r;) {
            int u = que[l++ & qn];
            vis[u] = false;
            for (int e = head[u], v; e != 0; e = nxt[e]) {
                v = to[e];
                if (cap[e] > 0 && dis[v] > dis[u] + cost[e]) {
                    dis[v] = dis[u] + cost[e];
                    if (!vis[v]) {
                        vis[v] = true;
                        que[++r & qn] = v;
                    }
                }
            }
        }
        return dis[t] != INF;
    }
}
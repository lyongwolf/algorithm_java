package algorithm.graph.kruskal;
import static algorithm.zz.U.*;
import java.util.*;
/**
 * kruskal 重构树
 * 测试链接：https://www.luogu.com.cn/problem/P2245
 */
public class rebuild {
    
    int n, H;
    int[] head, nxt, to, dep, pa[], root, val;

    void solve() {
        n = sc.nextInt();
        H = 32 - Integer.numberOfLeadingZeros(n);
        int m = sc.nextInt();
        int[][] edge = new int[m][3]; 
        for (int i = 0; i < m; i++) {
            edge[i][0] = sc.nextInt();
            edge[i][1] = sc.nextInt();
            edge[i][2] = sc.nextInt();
        }
        Arrays.sort(edge, (i, j) -> i[2] - j[2]);
        kruskalReBuild(edge);

        for (int q = sc.nextInt(), u, v; q > 0; q--) {
            u = sc.nextInt();
            v = sc.nextInt();
            if (root[u] != root[v]) {
                out.println("impossible");
            } else {
                out.println(val[lca(u, v)]);
            }
        }
    }

    int lca(int u, int v) {
        if (dep[u] < dep[v]) {
            u ^= v ^ (v = u);
        }
        for (int i = 0, d = dep[u] - dep[v]; i < H; i++) {
            if ((d >> i & 1) != 0) {
                u = pa[u][i];
            }
        }
        if (u == v) {
            return u;
        }
        for (int i = H - 1; i >= 0; i--) {
            if (pa[u][i] != pa[v][i]) {
                u = pa[u][i];
                v = pa[v][i];
            }
        }
        return pa[u][0];
    }

    void kruskalReBuild(int[][] edge) {
        int N = n * 2 - 1;
        int[] stk = new int[N + 1];
        head = new int[N + 1]; nxt = new int[N]; to = new int[N]; val = new int[N + 1]; dep = new int[N + 1]; pa = new int[N + 1][H]; root = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            root[i] = i;
        }
        for (int i = 0, j = 1, r = n, u, v, w, f1, f2, x, z; i < edge.length; i++) {
            u = edge[i][0];
            v = edge[i][1];
            w = edge[i][2];
            x = u;
            z = 0;
            while (root[x] != x) {
                stk[++z] = x;
                x = root[x];
            }
            while (z > 0) {
                root[stk[z--]] = x;
            }
            f1 = x;
            x = v;
            z = 0;
            while (root[x] != x) {
                stk[++z] = x;
                x = root[x];
            }
            while (z > 0) {
                root[stk[z--]] = x;
            }
            f2 = x;
            if (f1 != f2) {
                root[f1] = root[f2] = ++r;
                val[r] = w;
                nxt[j] = head[r]; head[r] = j; to[j++] = f1;
                nxt[j] = head[r]; head[r] = j; to[j++] = f2;
            }
        }
        for (int i = 1, f, u, x, z; i <= N; i++) {
            x = i;
            z = 0;
            while (root[x] != x) {
                stk[++z] = x;
                x = root[x];
            }
            while (z > 0) {
                root[stk[z--]] = x;
            }
            if (root[i] == i) {
                f = 0;
                u = i;
                z = 1;
                stk[z] = u;
                while (z > 0) {
                    u = stk[z];
                    f = pa[u][0];
                    if (dep[u] == 0) {
                        dep[u] = dep[f] + 1;
                        for (int e = head[u], v; e != 0; e = nxt[e]) {
                            v = to[e];
                            stk[++z] = v;
                            pa[v][0] = u;
                            for (int j = 1; j < H; j++) {
                                pa[v][j] = pa[pa[v][j - 1]][j - 1];
                            }
                        }
                    } else {
                        z--;
                    }
                }
            }
        }
    }

}
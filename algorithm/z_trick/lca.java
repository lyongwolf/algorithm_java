import static algorithm.zz.U.*;
import java.util.*;

public class lca {
    
    int[] head, nxt, to, dep, pa[];
    int H = 17;

    void solve() {
        int n = sc.nextInt();
        head = new int[n + 1]; nxt = new int[n << 1]; to = new int[n << 1]; dep = new int[n + 1]; pa = new int[n + 1][H];
        for (int i = 1, j = 2; i < n; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        init(0, 1);
        
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

    void init(int f, int u) {
        dep[u] = dep[f] + 1;
        pa[u][0] = f;
        for (int i = 1; i < H; i++) {
            pa[u][i] = pa[pa[u][i - 1]][i - 1];
        }
        for (int e = head[u], v; e != 0; e = nxt[e]) {
            if (f != (v = to[e])) {
                init(u, v);
            }
        }
    }
}

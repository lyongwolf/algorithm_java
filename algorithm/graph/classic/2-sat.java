class TwoSat {
    // range [1, n]
    // if i ∈ [1, n] is true, then i + n must be false
    // if i ∈ [1, n] is false, then i + n must be true

    int[] head, nxt, to;
    int[] dfn, low, stk, scc;
    boolean[] vis;
    int n, z, ts, top, no;

    public TwoSat(int n) {
        this.n = n;
        z = 1;
        int m = n << 1 | 1;
        head = new int[m];
        nxt = new int[m];
        to = new int[m];
        dfn = new int[m];
        low = new int[m];
        stk = new int[m];
        scc = new int[m];
        vis = new boolean[m];
    }

    public void addEdge(int u, int v) {
        if (z == nxt.length) {
            nxt = java.util.Arrays.copyOf(nxt, z << 1);
            to = java.util.Arrays.copyOf(to, z << 1);
        }
        nxt[z] = head[u];
        head[u] = z;
        to[z++] = v;
    }

    public boolean[] match() {
        for (int u = 1; u <= n << 1; u++) {
            if (dfn[u] == 0) {
                tarjan(u);
            }
        }
        for (int i = 1; i <= n; i++) {
            if (scc[i] == scc[i + n]) {
                return null;
            }
        }
        boolean[] ans = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            ans[i] = scc[i] < scc[i + n];
        }
        return ans;
    }

    private void tarjan(int u) {
        dfn[u] = low[u] = ++ts;
        stk[++top] = u;
        vis[u] = true;
        for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
            if (dfn[v] == 0) {
                tarjan(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (vis[v]) {
                low[u] = Math.min(low[u], dfn[v]);
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
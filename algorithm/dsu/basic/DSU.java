package algorithm.dsu.basic;

class DSU {
    private int[] stk;

    int[] parent, sz;

    public DSU(int n) {
        stk = new int[++n];
        parent = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            sz[i] = 1;
        }
    }

    public int find(int v) {
        int z = 0;
        while (v != parent[v]) {
            stk[z++] = v;
            v = parent[v];
        }
        while (z-- > 0) {
            parent[stk[z]] = v;
        }
        return v;
    }

    public void union(int v0, int v1) {
        int f0 = find(v0), f1 = find(v1);
        if (f0 != f1) {
            parent[f0] = f1;
            sz[f1] += sz[f0];
        }
    }
}
package algorithm.dsu.rollback;

/**
 * 可撤销并查集（模板）
 */

class DSURollback {
    private static final int MAXN = 1000000;
    private static int[] stkF0 = new int[MAXN], stkF1 = new int[MAXN];
    private int op;
    
    int[] parent, sz;

    public DSURollback(int n) {
        n++;
        parent = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            sz[i] = 1;
        }
    }

    public int find(int v) {
        while (parent[v] != v) {
            v = parent[v];
        }
        return v;
    }

    public void union(int v0, int v1) {
        int f0 = find(v0), f1 = find(v1);
        if (sz[f0] < sz[f1]) {
            f0 ^= f1 ^ (f1 = f0);
        }
        stkF0[op] = f0;
        stkF1[op] = f1;
        if (f0 != f1) {
            parent[f1] = f0;
            sz[f0] += sz[f1];
        }
        op++;
    }

    public void undo() {
        op--;
        int f0 = stkF0[op], f1 = stkF1[op];
        if (f0 != f1) {
            parent[f1] = f1;
            sz[f0] -= sz[f1];
        }
    }
    
}
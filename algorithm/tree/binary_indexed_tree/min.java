class Fenwick {
    private static final int MAX = (int) 1e9;
    private int[] mn;
    private int n;

    public Fenwick(int len) {
        n = len + 1;
        mn = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            mn[i] = MAX;
        }
    }

    public void setMin(int i, int v) {
        while (i < n) {
            mn[i] = Math.min(mn[i], v);
            i += i & -i;
        }
    }

    public int query(int i) {
        int ans = MAX;
        while (i > 0) {
            ans = Math.min(ans, mn[i]);
            i -= i & -i;
        }
        return ans;
    }

}
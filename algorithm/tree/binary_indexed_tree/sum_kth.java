class Fenwick {
    private int[] sum;
    private int N, hb;
    
    public Fenwick(int len) {
        N = len;
        sum = new int[N + 1];
        hb = Integer.highestOneBit(N);
    }

    public void add(int o, int v) {
        for (; o <= N; o += o & -o) {
            sum[o] += v;
        }
    }

    public int smallK(int k) {
        int ans = 0;
        for (int i = hb, nxt; i > 0; i >>= 1) {
            nxt = ans | i;
            if (nxt <= N && k > sum[nxt]) {
                ans = nxt;
                k -= sum[nxt];
            }
        }
        return ans + 1;
    }
}

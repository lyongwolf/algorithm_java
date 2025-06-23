class Fenwick {
    private int[] sum;
    private int N, hb;
    
    public Fenwick(int len) {
        N = len;
        hb = Integer.highestOneBit(N);
        sum = new int[N + 1];
    }

    public void add(int o, int v) {
        for (; o <= N; o += o & -o) {
            sum[o] += v;
        }
    }

    public int pre(int i) {
        int ans = 0;
        while (i > 0) {
            ans += sum[i];
            i -= i & -i;
        }
        return ans;
    }

    public int suf(int i) {
        return range(i, N);
    }

    public int range(int l, int r) {
        return l > r ? 0 : pre(r) - pre(l - 1);
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

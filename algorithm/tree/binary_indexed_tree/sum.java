class Fenwick {
    private int[] sum;
    private int N;

    public Fenwick(int len) {
        N = len;
        sum = new int[N + 1];
    }

    public void add(int i, int v) {
        while (i <= N) {
            sum[i] += v;
            i += i & -i;
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
}
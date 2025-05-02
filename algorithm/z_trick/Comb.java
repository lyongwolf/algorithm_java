class Comb {
    private final int MOD;
    private final long[] F, IF;
    
    public Comb(int MOD, int MAXN) {
        this.MOD = MOD;
        F = new long[MAXN + 1];
        IF = new long[MAXN + 1];
        F[0] = F[1] = IF[1] = 1;
        for (int i = 2; i <= MAXN; i++) {
            F[i] = F[i - 1] * i % MOD;
        }
        IF[MAXN] = inv(F[MAXN]);
        for (int i = MAXN - 1; i >= 2; i--) {
            IF[i] = IF[i + 1] * (i + 1) % MOD;
        }
    }

    public long comb(int n, int m) {
        if (m < 0 || m > n) {
            return 0;
        }
        if (m == 0 || m == n) {
            return 1;
        }
        return F[n] * IF[n - m] % MOD * IF[m] % MOD;
    }

    public long comb2(long n, long m) {
        if (m < 0 || m > n) {
           return 0;
        }
        if (m == 0 || m == n) {
            return 1;
        }
        m = Math.min(m, n - m);
        long ans = IF[(int) m];
        for (long i = n - m + 1; i <= n; i++) {
            ans = ans * (i % MOD) % MOD;
        }
        return ans;
    }

    public long inv(long a) {
        return pow(a, MOD - 2);
    }

    public long pow(long a, long b) {
        long res = 1;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = res * a % MOD;
            }
            a = a * a % MOD;
            b >>= 1;
        }
        return res;
    }
}
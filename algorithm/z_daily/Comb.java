class C {
    private static final int MOD = 1000000007, MAXN = 100000;
    private static final long[] F = new long[MAXN + 1], IF = new long[MAXN + 1];
    static {
        F[0] = F[1] = IF[1] = 1;
        for (int i = 2; i <= MAXN; i++) {
            F[i] = F[i - 1] * i % MOD;
        }
        IF[MAXN] = inv(F[MAXN]);
        for (int i = MAXN - 1; i >= 2; i--) {
            IF[i] = IF[i + 1] * (i + 1) % MOD;
        }
    }

    public static long c(int n, int m) {
        if (m < 0 || m > n) {
            throw new ArithmeticException();
        }
        if (m == 0 || m == n) {
            return 1;
        }
        return F[n] * IF[n - m] % MOD * IF[m] % MOD;
    }

    public static long cInv(int n, int m) {
        if (m < 0 || m > n) {
            throw new ArithmeticException();
        }
        if (m == 0 || m == n) {
            return 1;
        }
        return IF[n] * F[n - m] % MOD * F[m] % MOD;
    }

    public static long c2(long n, long m) {
        if (m < 0 || m > n) {
            throw new ArithmeticException();
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

    public static long a(int n, int m) {
        if (m < 0 || m > n) {
            throw new ArithmeticException();
        }
        return F[n] * IF[n - m] % MOD;
    }
    
    public static long aInv(int n, int m) {
        if (m < 0 || m > n) {
            throw new ArithmeticException();
        }
        return IF[n] * F[n - m] % MOD;
    }

    public static long inv(long a) {
        if (a == 0) {
            throw new ArithmeticException();
        }
        return pow(a, MOD - 2);
    }

    public static long pow(long a, long b) {
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

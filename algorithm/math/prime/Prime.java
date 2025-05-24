class Prime {
    private final int[] x32 = {2, 7, 61}, x78 = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
    private final int N;
    private boolean[] isp;
    private int[] mf;
    private int[] prime;
    private int[][] factory;

    private final long i31 = 1L << 31;
    private java.util.Random rnd = new java.util.Random();
    private long[] fac = new long[64];
    private int fi;

    public Prime(int MAXN) {
        N = MAXN;
        isp = new boolean[N + 1];
        mf = new int[N + 1];
        prime = new int[N / 2 + 1];
        boolean[] vis = new boolean[N + 1];
        int j = 0;
        for (int i = 2, v; i <= N; i++) {
            if (!vis[i]) {
                isp[i] = true;
                prime[j++] = i;
                mf[i] = i;
            }
            for (int p : prime) {
                v = p * i;
                if (v > N) {
                    break;
                }
                vis[v] = true;
                mf[v] = p;
                if (i % p == 0) {
                    break;
                }
            }
        }
        prime = java.util.Arrays.copyOf(prime, j);
    }

    public Prime(int MAXN, boolean generateFactory) {
        N = MAXN;
        isp = new boolean[N + 1];
        mf = new int[N + 1];
        prime = new int[N / 2 + 1];
        int[] len = new int[N + 1];
        int[] num = new int[N + 1];
        len[1] = 1;
        boolean[] vis = new boolean[N + 1];
        int j = 0;
        for (int i = 2, v; i <= N; i++) {
            if (!vis[i]) {
                isp[i] = true;
                prime[j++] = i;
                mf[i] = i;
                len[i] = 2;
                num[i] = 1;
            }
            for (int p : prime) {
                v = p * i;
                if (v > N) {
                    break;
                }
                vis[v] = true;
                mf[v] = p;
                if (i % p == 0) {
                    len[v] = len[i] / (num[i] + 1) * (num[i] + 2);
                    num[v] = num[i] + 1;
                    break;
                } else {
                    len[v] = len[i] << 1;
                    num[v] = 1;
                }
            }
        }
        prime = Arrays.copyOf(prime, j);
        factory = new int[N + 1][];
        for (int i = 1; i <= N; i++) {
            factory[i] = new int[len[i]];
        }
        for (int i = N; i >= 1; i--) {
            for (j = i; j <= N; j += i) {
                factory[j][--len[j]] = i;
            }
        }
    }

    public boolean isP(int n) {
        return n <= N ? isp[n] : MillerRabin(n);
    }

    public boolean isP(long n) {
        return n < i31 ? isP((int) n) : MillerRabin(n);
    }
    
    public int[] primes() {
        return prime;
    }

    public int[] factories(int n) {
        return factory[n];
    }

    public int minf(int n) {
        return (int) minf((long) n);
    }

    public long minf(long n) {
        if (n <= N) {
            return mf[(int) n];
        }
        fi = 0;
        dfs(n);
        long ans = n;
        for (int i = 0; i < fi; i++) {
            ans = Math.min(ans, fac[i]);
        }
        return ans;
    }

    public int maxf(int n) {
        return (int) maxf((long) n);
    }

    public long maxf(long n) {
        fi = 0;
        dfs(n);
        long ans = 0;
        for (int i = 0; i < fi; i++) {
            ans = Math.max(ans, fac[i]);
        }
        return ans;
    }

    public int[] pFactories(int n) {
        fi = 0;
        dfs(n);
        int[] ans = new int[fi];
        for (int i = 0; i < fi; i++) {
            ans[i] = (int) fac[i];
        }
        Arrays.sort(ans);
        return ans;
    }

    public long[] pFactories(long n) {
        fi = 0;
        dfs(n);
        long[] ans = new long[fi];
        for (int i = 0; i < fi; i++) {
            ans[i] = fac[i];
        }
        java.util.Arrays.sort(ans);
        return ans;
    }
    
    private void dfs(int n) {
        if (n < 2) {
            return;
        }
        if (isP(n)) {
            fac[fi++] = n;
            return;
        }
        int d;
        do {
            d = PollardRho(n);
        } while (d == n);
        dfs(d);
        dfs(n / d);
    }

    private void dfs(long n) {
        if (n < i31) {
            dfs((int) n);
            return;
        }
        if (isP(n)) {
            fac[fi++] = n;
            return;
        }
        long d;
        do {
            d = PollardRho(n);
        } while (d == n);
        dfs(d);
        dfs(n / d);
    }

    private boolean MillerRabin(int n) {
        int a = n - 1, b = Integer.numberOfTrailingZeros(a);
        a >>= b;
        for (int x : x32) {
            if (!check(n, a, b, x)) {
                return false;
            }
        }
        return true;
    }

    private boolean MillerRabin(long n) {
        long a = n - 1;
        int b = Long.numberOfTrailingZeros(a);
        a >>= b;
        for (int s : x78) {
            if (!check2(n, a, b, s)) {
                return false;
            }
        }
        return true;
    }

    private boolean check(long n, long a, int b, int x) {
        long v = pow(x, a, n);
        if (v == 1) {
            return true;
        }
        int j = 1;
        while (j <= b) {
            if (v == n - 1) {
                return true;
            }
            v = v * v % n;
            j++;
        }
        return false;
    }
    
    private boolean check2(long n, long a, int b, int x) {
        long v = pow2(x, a, n);
        if (v == 1) {
            return true;
        }
        int j = 1;
        while (j <= b) {
            if (v == n - 1) {
                return true;
            }
            v = mul(v, v, n);
            j++;
        }
        return false;
    }
    
    private int PollardRho(int n) {
        if (n <= N) {
            return mf[n];
        }
        long s = 0, t = 0, c = rnd.nextInt(n - 1) + 1;
        int stp = 0, goal = 1;
        long val = 1;
        long d;
        for (goal = 1; ; goal <<= 1, s = t, val = 1) {
            for (stp = 1; stp <= goal; stp++) {
                t = f(t, c, n);
                val = val * Math.abs(t - s) % n;
                if (stp % 127 == 0 && (d = gcd(val, n)) > 1) {
                    return (int) d;
                }
            }
            if ((d = gcd(val, n)) > 1) {
                return (int) d;
            }
        }
    }
    
    private long PollardRho(long n) {
        if (n < i31) {
            return PollardRho((int) n);
        }
        long s = 0, t = 0, c = rnd.nextLong(n - 1) + 1;
        int stp = 0, goal = 1;
        long val = 1;
        long d;
        for (goal = 1; ; goal <<= 1, s = t, val = 1) {
            for (stp = 1; stp <= goal; stp++) {
                t = f2(t, c, n);
                val = mul(val, Math.abs(t - s), n);
                if (stp % 127 == 0 && (d = gcd(val, n)) > 1) {
                    return d;
                }
            }
            if ((d = gcd(val, n)) > 1) {
                return d;
            }
        }
    }
    
    private long f(long x, long c, long m) {
        return (x * x + c) % m;
    }
    private long f2(long x, long c, long m) {
        return (mul(x, x, m) + c) % m;
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            b = a % (a = b);
        }
        return a;
    }

    private long pow(long a, long b, long m) {
        long res = 1;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = res * a % m;
            }
            a = a * a % m;
            b >>= 1;
        }
        return res;
    }

    private long pow2(long a, long b, long m) {
        long res = 1;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = mul(res, a, m);
            }
            a = mul(a, a, m);
            b >>= 1;
        }
        return res;
    }
    
    private long mul(long a, long b, long m) {
        long res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = (res + a) % m;
            }
            a = (a + a) % m;
            b >>= 1;
        }
        return res;
    }
}
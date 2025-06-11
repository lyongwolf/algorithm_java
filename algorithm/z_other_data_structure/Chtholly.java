class Chtholly {// 区间范围 [1, n]
    private static int[] stk = new int[32];
    static {stk[0] = 1;}
    private final int N, n;
    private int[] sum, end;

    private int[] val;

    // 初始化单个区间 [1, n]，区间值默认为 0
    public Chtholly(int n) {
        this.n = n;
        int t = 1 << (33 - Integer.numberOfLeadingZeros(n - 1));
        N = t / 2 - 1;
        end = new int[t];
        sum = new int[t];
        val = new int[t];
        end[N + 1] = N + n;
        add(N + 1, 1);
    }

    // 初始化 n 个区间，i 号区间值为 arr[i]
    public Chtholly(int[] arr) {
        n = arr.length - 1;
        int t = 1 << (33 - Integer.numberOfLeadingZeros(n - 1));
        N = t / 2 - 1;
        end = new int[t];
        sum = new int[t];
        val = new int[t];
        for (int i = 1; i <= n; i++) {
            sum[N + i] = 1;
            val[N + i] = arr[i];
            end[N + i] = N + i;
        }
        for (int i = N; i >= 1; i--) {
            sum[i] = sum[i << 1] + sum[i << 1 | 1];
        }
    }

    // 推平 [l, r] -> x
    public void assign(int l, int r, int x) {
        split(l, r);
        merge(l, r);
        val[N + l] = x;
    }
 
    // 修改 [l, r] + x
    public void add(int l, int r, int x) {
        split(l, r);
        l += N;
        r += N;
        for (int i = l; i <= r; i = end[i] + 1) {
            val[i] += x;
        }
    }

    // 拆分包含 l 的区间 和 包含 r 的区间
    private void split(int l, int r) {
        if (r < n) {
            split(r + 1);
        }
        split(l);
    }

    // 将下标 m 所在的区间 [l, r] 拆分为 [l, m-1] 和 [m, r]
    private void split(int m) {
        m += N;
        if (sum[m] == 1) {
            return;
        }
        for (int i = m, p;;) {
            sum[i]++;
            while ((i & 1) == 0) {
                i >>= 1;
                sum[i]++;
            }
            p = i >> 1;
            if (sum[p << 1] > 0) {
                p <<= 1;
                while (p < N) {
                    if (sum[p << 1 | 1] > 0) {
                        p = p << 1 | 1;
                    } else {
                        p <<= 1;
                    }
                }
                while (i > 1) {
                    i >>= 1;
                    sum[i]++;
                }
                end[m] = end[p];
                end[p] = m - 1;
                val[m] = val[p];
                return;
            }
            i = p;
        }
    }

    // 将 下标 l 所在的区间 ~ 下标 r 所在的区间合并，仅保留下标 l 所在区间的信息
    private void merge(int l, int r) {
        if (l == r) {
            return;
        }
        l = pre(l + N);
        r += N;
        if (end[l] >= r) {
            return;
        }
        int s = l;
        l = end[l] + 1;
        if (end[l] >= r) {
            add(l, -1);
            end[s] = end[l];
        } else {
            // 类似虚树生成
            int z = 0, f, p, i = l;
            l = end[l] + 1;
            for (;;) {
                sum[i] = 0;
                f = i >> (32 - Integer.numberOfLeadingZeros(i ^ l));
                i >>= 1;
                p = Math.max(f, stk[z]);
                while (i > p) {
                    sum[i] = sum[i << 1] + sum[i << 1 | 1];
                    i >>= 1;
                }
                while (f < stk[z]) {
                    i = stk[z--];
                    p = Math.max(f, stk[z]);
                    while (i > p) {
                        sum[i] = sum[i << 1] + sum[i << 1 | 1];
                        i >>= 1;
                    }
                }
                if (end[l] >= r) {
                    end[s] = end[l];
                    sum[l] = 0;
                    l >>= 1;
                    while (l > 0) {
                        sum[l] = sum[l << 1] + sum[l << 1 | 1];
                        l >>= 1;
                    }
                    return;
                }
                if (f != stk[z]) {
                    stk[++z] = f;
                }
                i = l;
                l = end[l] + 1;
            }
        }
    }

    private int pre(int i) {
        if (sum[i] == 1) {
            return i;
        }
        for (int j;;) {
            i >>= Integer.numberOfTrailingZeros(i);
            j = i >> 1;
            if (sum[j << 1] > 0) {
                j <<= 1;
                while (j < N) {
                    if (sum[j << 1 | 1] > 0) {
                        j = j << 1 | 1;
                    } else {
                        j <<= 1;
                    }
                }
                return j;
            }
            i = j;
        }
    }

    private void add(int i, int v) {
        while (i > 0) {
            sum[i] += v;
            i >>= 1;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = N + 1; i <= N + n; i = end[i] + 1) {
            sb.append("[(").append(i - N).append(", ").append(end[i] - N).append("): ").append(val[i]).append(']');
        }
        sb.append('\n');
        return sb.toString();
    }

    // public String viewTree() {
    //     java.util.List<String> list = new java.util.ArrayList<>();
    //     int m = N + 1;
    //     for (int i = m >> 1, p = 4, t = p, q = 16; i >= 1; i >>= 1, p <<= 1, t += p, q <<= 1) {
    //         StringBuilder tmp = new StringBuilder();
    //         for (int j = 0; j < t; j++) {
    //             tmp.append(' ');
    //         }
    //         for (int j = i; j < i << 1; j++) {
    //             int len = ("" + sum[i]).length();
    //             tmp.append(sum[j]);
    //             while (len++ < q) {
    //                 tmp.append(' ');
    //             }
    //         }
    //         tmp.append('\n');
    //         list.add(tmp.toString());
    //     }
    //     StringBuilder sb = new StringBuilder();
    //     for (int i = list.size() - 1; i >= 0; i--) {
    //         sb.append(list.get(i)).append('\n');
    //     }
    //     for (int i = m; i < m << 1; i++) {
    //         int len = ("" + sum[i]).length();
    //         sb.append(sum[i]);
    //         while (len++ < 8) {
    //             sb.append(' ');
    //         }
    //     }
    //     sb.append('\n');
    //     return sb.toString();
    // }
}

// 不带权
class SegTree {
    private int[] sum, cnt;
    private int N;

    public SegTree(int len) {
        N = len;
        int tot = 1 << (33 - Integer.numberOfLeadingZeros(N - 1));// 预估空间
        sum = new int[tot];
        cnt = new int[tot];
    }

    public void add(int l, int r, int v) {
        add(l, r, v, 1, N, 1);
    }

    private void add(int L, int R, int v, int l, int r, int i) {
        if (L <= l && r <= R) {
            cnt[i] += v;
            if (cnt[i] > 0) {
                sum[i] = r - l + 1;// 此时区间 [l, r] 被完整覆盖次数 > 0 
            } else if (l == r) {
                sum[i] = 0;// 叶子节点（单个点）
            } else {
                sum[i] = sum[i << 1] + sum[i << 1 | 1];// 向左右儿子节点寻求答案
            }
            return;
        }
        int m = (l + r) >> 1;
        if (L <= m) {
            add(L, R, v, l, m, i << 1);
        }
        if (R > m) {
            add(L, R, v, m + 1, r, i << 1 | 1);
        }
        if (cnt[i] > 0) {
            sum[i] = r - l + 1;// 此时区间 [l, r] 被完整覆盖次数 > 0 
        } else {
            sum[i] = sum[i << 1] + sum[i << 1 | 1];// 向左右儿子节点寻求答案
        }
    }

    public int query() {
        return sum[1];// 1 代表区间 [1, n]
    }
}

// 带权
class SegTree2 {
    private int[] diff, sum, cnt;
    private int N;

    public SegTree2(int[] val) {// val[i] 即为 i 号点的权重
        N = val.length - 1;// val 下标从 1 开始，长度需要减一
        diff = new int[N + 1];// 为 val 的前缀和数组
        for (int i = 1; i <= N; i++) {
            diff[i] = diff[i - 1] + val[i];
        }
        int tot = 1 << (33 - Integer.numberOfLeadingZeros(N - 1));
        sum = new int[tot];
        cnt = new int[tot];
    }

    public void add(int l, int r, int v) {
        if (l > r) {
            return;
        }
        add(l, r, v, 1, N, 1);
    }

    private void add(int L, int R, int v, int l, int r, int i) {
        if (L <= l && r <= R) {
            cnt[i] += v;
            if (cnt[i] > 0) {
                // 此时区间 [l, r] 被完整覆盖次数 > 0 ，前缀和做差获取实际大小
                sum[i] = diff[r] - diff[l - 1];
            } else if (l == r) {
                sum[i] = 0;// 叶子节点（单个点）
            } else {
                sum[i] = sum[i << 1] + sum[i << 1 | 1];// 向左右儿子节点寻求答案
            }
            return;
        }
        int m = (l + r) >> 1;
        if (L <= m) {
            add(L, R, v, l, m, i << 1);
        }
        if (R > m) {
            add(L, R, v, m + 1, r, i << 1 | 1);
        }
        if (cnt[i] > 0) {
            // 此时区间 [l, r] 被完整覆盖次数 > 0 ，前缀和做差获取实际大小
            sum[i] = diff[r] - diff[l - 1];
        } else {
            sum[i] = sum[i << 1] + sum[i << 1 | 1];// 向左右儿子节点寻求答案
        }
    }

    public int query() {
        return sum[1];// 1 代表区间 [1, n]
    }
}

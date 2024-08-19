package algorithm.persistent_data_structure.segment_tree;
import java.util.*;
import java.io.*;
/**
 * 求区间topK累加和
 * 测试链接：https://codeforces.com/contest/1862/problem/E
 */
public class range_topk_sum {

    static void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        long d = sc.nextLong();
        int[] arr = new int[n + 1];
        int[] val = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = sc.nextInt();
            val[i] = Math.max(0, arr[i]);
        }
        Arrays.sort(val, 1, n + 1);
        HashMap<Integer, Integer> idx = new HashMap<>();
        for (int i = 1, j = i, k = 0; i <= n; i = j) {
            while (j <= n && val[j] == val[i]) {
                j++;
            }
            idx.put(val[i], ++k);
            val[k] = val[i];
        }
        SegTree tree = new SegTree(val, idx.size(), n);
        for (int i = 1; i <= n; i++) {
            int v = idx.get(Math.max(0, arr[i]));
            tree.insert(i - 1, v);
        }
        long ans = 0;
        for (int i = 1; i <= n; i++) {
            ans = Math.max(ans, tree.sumK(1, i - 1, m - 1) + arr[i] - d * i);
        }
        out.println(ans);
    }

    static class SegTree {
        private int[] root, cnt, lc, rc;
        private int N, no, version;
        private int[] val;
        private long[] sum;

        public SegTree(int[] val, int len, int version) {
            N = len;
            int tot = N * 2 + version * (33 - Integer.numberOfLeadingZeros(N));
            root = new int[version + 1];
            lc = new int[tot];
            rc = new int[tot];
            cnt = new int[tot];
            this.val = val;
            sum = new long[tot];
            root[0] = build(1, N);
        }

        public int build(int l, int r) {
            int o = ++no;
            if (l == r) {
                return o;
            }
            int m = (l + r) >> 1;
            lc[o] = build(l, m);
            rc[o] = build(m + 1, r);
            return o;
        }

        public void insert(int x, int i) {
            root[++version] = insert(root[x], i, 1, N);
        }

        private int insert(int x, int i, int l, int r) {
            int o = ++no;
            cnt[o] = cnt[x] + 1;
            if (l == r) {
                sum[o] = (long) val[i] * cnt[o];
                return o;
            }
            int m = (l + r) >> 1;
            if (i <= m) {
                lc[o] = insert(lc[x], i, l, m);
                rc[o] = rc[x];
            } else {
                rc[o] = insert(rc[x], i, m + 1, r);
                lc[o] = lc[x];
            }
            sum[o] = sum[lc[o]] + sum[rc[o]];
            return o;
        }

        public int topK(int x, int y, int k) {
            return topK(root[x - 1], root[y], k, 1, N);
        }

        private int topK(int x, int y, int k, int l, int r) {
            if (l == r) {
                return val[l];
            }
            int m = (l + r) >> 1;
            int t = cnt[rc[y]] - cnt[rc[x]];
            if (t >= k) {
                return topK(rc[x], rc[y], k, m + 1, r);
            } else {
                return topK(lc[x], lc[y], k - t, l, m);
            }
        }

        public long sumK(int x, int y, int k) {
            return sumK(root[x - 1], root[y], k, 1, N);
        }

        public long sumK(int x, int y, int k, int l, int r) {
            if (l == r) {
                return (long) val[l] * Math.min(k, cnt[y] -cnt[x]);
            }
            int m = (l + r) >> 1;
            int t = cnt[rc[y]] - cnt[rc[x]];
            long ans = 0;
            if (t >= k) {
                ans = sumK(rc[x], rc[y], k, m + 1, r);
            } else {
                ans = sumK(lc[x], lc[y], k - t, l, m) + sum[rc[y]] - sum[rc[x]];
            }
            return ans;
        }
    }

    
    static boolean retest = true;
    static FastReader sc = new FastReader();
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static void main(String[] args) {
        if (retest) {int t = sc.nextInt(); while (t-- > 0) solve();} else solve(); out.flush(); out.close();
    }
    static class FastReader {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in)); 
        StringTokenizer st;
        String next() {
            try {while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(r.readLine()); return st.nextToken();} 
            catch (Exception e) {return null;}
        }
        int nextInt() {return Integer.parseInt(next());}
        long nextLong() {return Long.parseLong(next());}
        double nextDouble() {return Double.parseDouble(next());}
    }
}

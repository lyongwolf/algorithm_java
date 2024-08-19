package algorithm.persistent_data_structure.segment_tree;

import java.io.*;
import java.util.*;
/**
 * 静态区间第 k 小
 * 测试链接：https://www.luogu.com.cn/problem/P3834
 */
public class range_smallK {
    
    static void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        int[] arr = new int[n + 1];
        int[] val = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = sc.nextInt();
            val[i] = arr[i];
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
        SegTree tree = new SegTree(n, n);
        for (int i = 1; i <= n; i++) {
            tree.insert(i - 1, idx.get(arr[i]));
        }
        while (m-- > 0) {
            int l = sc.nextInt(), r = sc.nextInt(), k = sc.nextInt();
            out.println(val[tree.smallK(l, r, k)]);
        }
    }

    static class SegTree {
        private int[] root, cnt, lc, rc;
        private int N, no, version;

        public SegTree(int len, int version) {
            N = len;
            int tot = N * 2 + version * (33 - Integer.numberOfLeadingZeros(N));
            root = new int[version + 1];
            lc = new int[tot];
            rc = new int[tot];
            cnt = new int[tot];
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
            return o;
        }

        public int smallK(int x, int y, int k) {
            return smallK(root[x - 1], root[y], k, 1, N);
        }

        private int smallK(int x, int y, int k, int l, int r) {
            if (l == r) {
                return l;
            }
            int m = (l + r) >> 1;
            int t = cnt[lc[y]] - cnt[lc[x]];
            if (t < k) {
                return smallK(rc[x], rc[y], k - t, m + 1, r);
            } else {
                return smallK(lc[x], lc[y], k, l, m);
            }
        }
    }

   

    
    static boolean retest = false;
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

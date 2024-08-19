package algorithm.persistent_data_structure.segment_tree;

import java.util.*;
import java.io.*;
/**
 * 查询数组历史版本
 * 测试链接：https://www.luogu.com.cn/problem/P3919
 */
public class history_version {

    static void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = sc.nextInt();
        }
        SegTree tree = new SegTree(arr, n, m);
        while (m-- > 0) {
            int x = sc.nextInt();
            if (sc.nextInt() == 1) {
                int i = sc.nextInt(), v = sc.nextInt();
                tree.update(x, i, v);
            } else {
                int i = sc.nextInt();
                out.println(tree.query(x, i));
            }
        }
    }

    static class SegTree {
        private int[] root, lc, rc, val;
        private int N, no, version;

        public SegTree(int[] arr, int len, int version) {
            N = len;
            int tot = N * 2 + version * (33 - Integer.numberOfLeadingZeros(N));
            root = new int[version + 1];
            lc = new int[tot];
            rc = new int[tot];
            val = new int[tot];
            root[0] = build(arr, 1, N);
        }

        private int build(int[] arr, int l, int r) {
            int o = ++no;
            if (l == r) {
                val[o] = arr[l];
                return o;
            }
            int m = (l + r) >> 1;
            lc[o] = build(arr, l, m);
            rc[o] = build(arr, m + 1, r);
            return o;
        }

        public void update(int x, int i, int v) {
            root[++version] = update(root[x], i, v, 1, N);
        }

        private int update(int x, int i, int v, int l, int r) {
            int o = ++no;
            if (l == r) {
                val[o] = v;
                return o;
            }
            int m = (l + r) >> 1;
            if (i <= m) {
                lc[o] = update(lc[x], i, v, l, m);
                rc[o] = rc[x];
            } else {
                rc[o] = update(rc[x], i, v, m + 1, r);
                lc[o] = lc[x];
            }
            return o;
        }

        public int query(int x, int i) {
            root[++version] = root[x];
            return query(root[x], i, 1, N);
        }

        private int query(int x, int i, int l, int r) {
            if (l == r) {
                return val[x];
            }
            int m = (l + r) >> 1;
            if (i <= m) {
                return query(lc[x], i, l, m);
            } else {
                return query(rc[x], i, m + 1, r);
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

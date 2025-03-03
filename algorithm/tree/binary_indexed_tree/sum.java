package algorithm.tree.binary_indexed_tree;

public class sum {
    

    class Fenwick {
        private long[] sum;
        private int N;

        public Fenwick(int len) {
            N = len + 1;
            sum = new long[N + 1];
        }

        public void add(int i, long v) {
            while (i < N) {
                sum[i] += v;
                i += i & -i;
            }
        }

        public long query(int i) {
            long ans = 0;
            while (i > 0) {
                ans += sum[i];
                i -= i & -i;
            }
            return ans;
        }

        public long range(int l, int r) {
            return l > r ? 0 : query(r) - query(l - 1);
        }
    }
}

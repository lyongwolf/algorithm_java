package algorithm.util.trick;

public class rmqs {
    
    abstract class RMQ {
        private static final int MAXN = 1000000;
        private static final int[] log = new int[MAXN + 1];
        static {
            log[0] = -1;
            for (int i = 1; i <= MAXN; i++) {
                log[i] = log[i >> 1] + 1;
            }
        }
    
        private int[][] st;
    
        public RMQ(int[] a) {
            int n = a.length, m = log[n] + 1;
            st = new int[n][m];
            for (int i = 0; i < n; i++) {
                st[i][0] = a[i];
            }
            for (int j = 1; j < m; j++) {
                for (int i = 0; i + (1 << j) - 1 < n; i++) {
                    st[i][j] = op(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
                }
            }
        }
        
        public int query(int l, int r) {
            int g = log[r - l + 1];
            return op(st[l][g], st[r - (1 << g) + 1][g]);
        }
    
        abstract int op(int a, int b);
    }
    
    abstract class RMQ2 {
        private static final int MAXN = 1000000;
        private static final int[] log = new int[MAXN + 1];
        static {
            log[0] = -1;
            for (int i = 1; i <= MAXN; i++) {
                log[i] = log[i >> 1] + 1;
            }
        }
        
        private long[][] st;
        
        public RMQ2(long[] a) {
            int n = a.length, m = log[n] + 1;
            st = new long[n][m];
            for (int i = 0; i < n; i++) {
                st[i][0] = a[i];
            }
            for (int j = 1; j < m; j++) {
                for (int i = 0; i + (1 << j) - 1 < n; i++) {
                    st[i][j] = op(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
                }
            }
        }
        
        public long query(int l, int r) {
            int g = log[r - l + 1];
            return op(st[l][g], st[r - (1 << g) + 1][g]);
        }
    
        abstract long op(long a, long b);
    }

}

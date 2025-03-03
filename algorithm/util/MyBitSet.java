package algorithm.util;

public class MyBitSet {

    class BitSet {
        private int[] mag;
        private int END;
        private int n;
        
        public BitSet(int MAXV) {
            n = MAXV / 32 + 1;
            mag = new int[n];
            END = -1 >>> (31 - MAXV % 32);
        }

        public boolean contains(int v) {
            return (mag[v / 32] & (1 << (v % 32))) != 0;
        }

        public void insert(int v) {
            mag[v / 32] |= 1 << (v % 32);
        }

        public void delete(int v) {
            mag[v / 32] &= ~(1 << (v % 32));
        }

        public void clear(int l, int r) {
            int i0 = l / 32, j0 = l % 32, i1 = r / 32, j1 = r % 32;
            int v0 = (mag[i0] >>> j0 << j0) ^ mag[i0], v1 = (mag[i1] << (31 - j1) >>> (31 - j1)) ^ mag[i1];
            if (i0 == i1) {
                mag[i0] = v0 | v1;
                return;
            }
            mag[i0] = v0;
            mag[i1] = v1;
            for (int i = i0 + 1; i < i1; i++) {
                mag[i] = 0;
            }
        }

        public void full(int l, int r) {
            int i0 = l / 32, j0 = l % 32, i1 = r / 32, j1 = r % 32;
            if (i0 == i1) {
                mag[i0] |= (j1 == 31 ? -1 : (1 << (j1 + 1) - 1)) ^ (j0 == 31 ? -1 : (1 << (j0 + 1) - 1));
                return;
            }
            mag[i0] |= -1 >>> j0 << j0;
            mag[i1] |= -1 >>> (31 - j1);
            for (int i = i0 + 1; i < i1; i++) {
                mag[i] = -1;
            }
        }

        public void flip(int l, int r) {
            int i0 = l / 32, j0 = l % 32, i1 = r / 32, j1 = r % 32;
            mag[i0] ^= -1 >>> j0 << j0;
            mag[i1] ^= -1 >>> (31 - j1);
            if (i0 == i1) {
                mag[i0] ^= -1;
                return;
            }
            for (int i = i0 + 1; i < i1; i++) {
                mag[i] ^= -1;
            }
        }

        public void shiftLeft(int b) {
            int t = b / 32, d = b % 32;
            for (int i = n - 1; i >= n - t; i--) {
                mag[i] = 0;
            }
            for (int i = n - t - 1; i >= 0; i--) {
                mag[i + t] = mag[i];
            }
            for (int i = t - 1; i >= 0; i--) {
                mag[i] = 0;
            }
            if (d > 0) {
                mag[n - 1] <<= d;
                for (int i = n - 2; i >= t; i--) {
                    mag[i + 1] |= mag[i] >>> (32 - d);
                    mag[i] <<= d;
                }
            }
            mag[n - 1] &= END;
        }

        public void shiftRight(int b) {
            int t = b / 32, d = b % 32;
            for (int i = 0; i < t; i++) {
                mag[i] = 0;
            }
            for (int i = t; i < n; i++) {
                mag[i - t] = mag[i];
            }
            for (int i = n - t; i < n; i++) {
                mag[i] = 0;
            }
            if (d > 0) {
                mag[0] >>>= d;
                for (int i = 1; i < n - t; i++) {
                    mag[i - 1] |= mag[i] << (32 - d);
                    mag[i] >>>= d;
                }
            }
        }

        public void and(int sl, int sr, int l, int r) {
            int i0 = sl / 32, j0 = sl % 32, i1 = sr / 32, j1 = sr % 32, m = i1 - i0 + 2;
            int[] tmp = new int[m];
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                tmp[i] = mag[j];
            }
            tmp[0] = tmp[0] >>> j0 << j0;
            tmp[m - 2] = tmp[m - 2] << (31 - j1) >>> (31 - j1);
            int t0 = l % 32;
            if (t0 < j0) {
                int d = j0 - t0;
                tmp[0] >>>= d;
                for (int i = 1; i < m - 1; i++) {
                    tmp[i - 1] |= tmp[i] << (32 - d);
                    tmp[i] >>>= d;
                }
            } else if (t0 > j0) {
                int d = t0 - j0;
                for (int i = m - 2; i >= 0; i--) {
                    tmp[i + 1] |= tmp[i] >>> (32 - d);
                    tmp[i] <<= d;
                }
            }
            i0 = l / 32;
            j0 = l % 32;
            i1 = r / 32;
            j1 = r % 32;
            int v0 = (mag[i0] >>> j0 << j0) ^ mag[i0], v1 = (mag[i1] << (31 - j1) >>> (31 - j1)) ^ mag[i1];
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                mag[j] &= tmp[i];
            }
            mag[i0] |= v0;
            mag[i1] |= v1;
        }

        public void or(int sl, int sr, int l, int r) {
            int i0 = sl / 32, j0 = sl % 32, i1 = sr / 32, j1 = sr % 32, m = i1 - i0 + 2;
            int[] tmp = new int[m];
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                tmp[i] = mag[j];
            }
            tmp[0] = tmp[0] >>> j0 << j0;
            tmp[m - 2] = tmp[m - 2] << (31 - j1) >>> (31 - j1);
            int t0 = l % 32;
            if (t0 < j0) {
                int d = j0 - t0;
                tmp[0] >>>= d;
                for (int i = 1; i < m - 1; i++) {
                    tmp[i - 1] |= tmp[i] << (32 - d);
                    tmp[i] >>>= d;
                }
            } else if (t0 > j0) {
                int d = t0 - j0;
                for (int i = m - 2; i >= 0; i--) {
                    tmp[i + 1] |= tmp[i] >>> (32 - d);
                    tmp[i] <<= d;
                }
            }
            i0 = l / 32;
            i1 = r / 32;
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                mag[j] |= tmp[i];
            }
        }

        public void xor(int sl, int sr, int l, int r) {
            int i0 = sl / 32, j0 = sl % 32, i1 = sr / 32, j1 = sr % 32, m = i1 - i0 + 2;
            int[] tmp = new int[m];
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                tmp[i] = mag[j];
            }
            tmp[0] = tmp[0] >>> j0 << j0;
            tmp[m - 2] = tmp[m - 2] << (31 - j1) >>> (31 - j1);
            int t0 = l % 32;
            if (t0 < j0) {
                int d = j0 - t0;
                tmp[0] >>>= d;
                for (int i = 1; i < m - 1; i++) {
                    tmp[i - 1] |= tmp[i] << (32 - d);
                    tmp[i] >>>= d;
                }
            } else if (t0 > j0) {
                int d = t0 - j0;
                for (int i = m - 2; i >= 0; i--) {
                    tmp[i + 1] |= tmp[i] >>> (32 - d);
                    tmp[i] <<= d;
                }
            }
            i0 = l / 32;
            i1 = r / 32;
            for (int i = 0, j = i0; j <= i1; i++, j++) {
                mag[j] ^= tmp[i];
            }
        }
    }
    
}
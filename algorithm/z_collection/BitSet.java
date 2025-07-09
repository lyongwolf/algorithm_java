class BitSet {
    private int[] mag;
    private int n, END, MAXV;
    
    public BitSet(int MAXV) {// [0, MAXV]
        this.MAXV = MAXV;
        n = MAXV / 32 + 1;
        END = -1 >>> (31 - MAXV % 32);
        mag = new int[n];
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

    // 生成当前位集的克隆位集
    public BitSet clone() {
        BitSet bs = new BitSet(MAXV);
        for (int i = 0; i < n; i++) {
            bs.mag[i] = mag[i];
        }
        return bs;
    }

     // 左移 b 位
     public BitSet shiftLeft(int b) {
        BitSet bs = clone();
        int[] mag = bs.mag;
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
        return bs;
    }

    // 右移 b 位
    public BitSet shiftRight(int b) {
        BitSet bs = clone();
        int[] mag = bs.mag;
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
        return bs;
    }

    // 当前位集与 st 进行与运算
    public BitSet and(BitSet st) {
        BitSet bs = clone();
        int[] mag = bs.mag;
        for (int i = 0; i < n; i++) {
            mag[i] &= st.mag[i];
        }
        return bs;
    }

    // 当前位集与 st 进行或运算
    public BitSet or(BitSet st) {
        BitSet bs = clone();
        int[] mag = bs.mag;
        for (int i = 0; i < n; i++) {
            mag[i] |= st.mag[i];
        }
        return bs;
    }

    // 当前位集与 st 进行异或运算
    public BitSet xor(BitSet st) {
        BitSet bs = clone();
        int[] mag = bs.mag;
        for (int i = 0; i < n; i++) {
             mag[i] ^= st.mag[i];
        }
        return bs;
    }

    // 清空当前位集
    public void clear() {
        for (int i = 0; i < n; i++) {
            mag[i] = 0;
        }
    }

    // 指定区间设置为 0
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

    // 填满当前位集
    public void full() {
        for (int i = 0; i < n; i++) {
            mag[i] = -1;
        }
        mag[n - 1] &= END;
    }

    // 指定区间设置为 1
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

    // 翻转当前位集
    public void flip() {
        for (int i = 0; i < n; i++) {
            mag[i] ^= -1;
        }
        mag[n - 1] &= END;
    }

    // 翻转指定区间
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

    // 将 st 的 sl ~ sr 的状态 与 当前对象的 l ~ r 的状态进行 与 运算
    public void and(BitSet st, int sl, int sr, int l, int r) {
        int i0 = sl / 32, j0 = sl % 32, i1 = sr / 32, j1 = sr % 32, m = i1 - i0 + 2;
        int[] tmp = new int[m];
        for (int i = 0, j = i0; j <= i1; i++, j++) {
            tmp[i] = st.mag[j];
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

    // 将 st 的 sl ~ sr 的状态 与 当前对象的 l ~ r 的状态进行 或 运算
    public void or(BitSet st, int sl, int sr, int l, int r) {
        int i0 = sl / 32, j0 = sl % 32, i1 = sr / 32, j1 = sr % 32, m = i1 - i0 + 2;
        int[] tmp = new int[m];
        for (int i = 0, j = i0; j <= i1; i++, j++) {
            tmp[i] = st.mag[j];
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

    // 将 st 的 sl ~ sr 的状态 与 当前对象的 l ~ r 的状态进行 异或 运算
    public void xor(BitSet st, int sl, int sr, int l, int r) {
        int i0 = sl / 32, j0 = sl % 32, i1 = sr / 32, j1 = sr % 32, m = i1 - i0 + 2;
        int[] tmp = new int[m];
        for (int i = 0, j = i0; j <= i1; i++, j++) {
            tmp[i] = st.mag[j];
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

    // 当前位集元素数量
    public int count() {
        int ans = 0;
        for (int v : mag) {
            ans += Integer.bitCount(v);
        }
        return ans;
    }

    // 判断当前位集与 st 是否存在公共元素
    public boolean intersect(BitSet st) {
        for (int i = 0; i < n; i++) {
            if ((mag[i] & st.mag[i]) != 0) {
                return true;
            }
        }
        return false;
    }
}

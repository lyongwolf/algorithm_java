package algorithm.tree.tree_in_tree.seg_in_seg;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 测试链接：https://www.luogu.com.cn/problem/P3332
 */

public class Q1 {

    SegInSeg tree = new SegInSeg(15000000);

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        int[] op = new int[m], left = new int[m], right = new int[m];
        long[] val = new long[m];
        int tot = 0;
        for (int i = 0; i < m; i++) {
            op[i] = sc.nextInt();
            if (op[i] == 1) {
                tot++;
            }
            left[i] = sc.nextInt();
            right[i] = sc.nextInt();
            val[i] = sc.nextLong();
        }
        int[] a = new int[tot];
        for (int i = 0, j = 0; i < m; i++) {
            if (op[i] == 1) {
                a[j++] = (int) val[i];
            }
        }
        Arrays.sort(a);
        tot = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] != a[i]) {
                a[tot++] = a[i];
            }
        }
        tree.init(0, tot, 1, n);
        for (int i = 0; i < m; i++) {
            if (op[i] == 1) {
                tree.add(Arrays.binarySearch(a, 0, tot, (int) val[i]), left[i], right[i]);
            } else {
                out.println(a[tree.query(val[i], left[i], right[i])]);
            }
        }
    }

}

class SegInSeg {
    private int olow, ohigh, ilow, ihigh, no;
    private int[] root, lc, rc;

    private long[] sum, lazy;

    public SegInSeg(int tot) {
        root = new int[tot];
        lc = new int[tot];
        rc = new int[tot];

        sum = new long[tot];
        lazy = new long[tot];
    }

    public void init(int olow, int ohigh, int ilow, int ihigh) {
        this.olow = olow;
        this.ohigh = ohigh;
        this.ilow = ilow;
        this.ihigh = ihigh;
        for (int i = 1; i <= no; i++) {
            lc[i] = rc[i] = root[i] = 0;

            sum[i] = 0;
            lazy[i] = 0;
        }
        no = 2;
        root[1] = 2;
    }

    public void add(int oo, int il, int ir) {
        oadd(oo, olow, ohigh, 1, il, ir);
    }

    private void oadd(int o, int l, int r, int i, int il, int ir) {
        iadd(il, ir, 1, ilow, ihigh, root[i]);
        if (l == r) {
            return;
        }
        odown(i);
        int m = (l + r) / 2;
        if (o <= m) {
            oadd(o, l, m, lc[i], il, ir);
        } else {
            oadd(o, m + 1, r, rc[i], il, ir);
        }
    }

    private void iadd(int L, int R, int v, int l, int r, int i) {
        if (L <= l && r <= R) {
            lazy[i] += v;
            sum[i] += v * (r - l + 1);
            return;
        }
        int m = (l + r) / 2;
        idown(i, m - l + 1, r - m);
        if (L <= m) {
            iadd(L, R, v, l, m, lc[i]);
        }
        if (R > m) {
            iadd(L, R, v, m + 1, r, rc[i]);
        }
        iup(i);
    }

    public int query(long ok, int il, int ir) {
        return oquery(ok, olow, ohigh, 1, il, ir);
    }

    private int oquery(long k, int l, int r, int i, int il, int ir) {
        if (l == r) {
            return l;
        }
        odown(i);
        int m = (l + r)  / 2;
        long rn = iquery(il, ir, ilow, ihigh, root[rc[i]]);
        if (rn >= k) {
            return oquery(k, m + 1, r, rc[i], il, ir);
        } else {
            return oquery(k - rn, l, m, lc[i], il, ir);
        }
    }

    private long iquery(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return sum[i];
        }
        int m = (l + r) / 2;
        idown(i, m - l + 1, r - m);
        long ans = 0;
        if (L <= m) {
            ans += iquery(L, R, l, m, lc[i]);
        }
        if (R > m) {
            ans += iquery(L, R, m + 1, r, rc[i]);
        }
        return ans;
    }

    private void oup(int i) {

    }

    private void odown(int i) {
        if (lc[i] == 0) {
            lc[i] = ++no;
            rc[i] = ++no;
            root[lc[i]] = ++no;
            root[rc[i]] = ++no;
        }
    }

    private void iup(int i) {
        sum[i] = sum[lc[i]] + sum[rc[i]];
    }

    private void idown(int i, int ln, int rn) {
        if (lc[i] == 0) {
            lc[i] = ++no;
            rc[i] = ++no;
        }
        int l = lc[i], r = rc[i];
        if (lazy[i] != 0) {
            lazy[l] += lazy[i];
            lazy[r] += lazy[i];
            sum[l] += lazy[i] * ln;
            sum[r] += lazy[i] * rn;
            lazy[i] = 0;
        }
    }
    
}
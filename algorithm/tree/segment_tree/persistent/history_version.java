package algorithm.tree.segment_tree.persistent;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 查询数组历史版本
 * 测试链接：https://www.luogu.com.cn/problem/P3919
 */
public class history_version {

    void solve() {
        int n = ni(), m = ni();
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = ni();
        }
        SegTree tree = new SegTree(arr, n, m);
        while (m-- > 0) {
            int x = ni();
            if (ni() == 1) {
                int i = ni(), v = ni();
                tree.update(x, i, v);
            } else {
                int i = ni();
                println(tree.query(x, i));
            }
        }
    }

}

class SegTree {
    private int[] root, lc, rc, val;
    private int N, no, version;

    public SegTree(int[] arr, int len, int version) {
        N = len;
        int tot = N * 2 + version * (33 - Integer.numberOfLeadingZeros(N - 1));
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
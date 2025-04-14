package algorithm.tree.segment_tree.persistent;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 静态区间第 k 小
 * 测试链接：https://www.luogu.com.cn/problem/P3834
 */
public class range_smallK {

    void solve() {
        int n = sc.nextInt(), q = sc.nextInt();
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }
        SegTree tree = new SegTree(a);
        while (q-- > 0) {
            int l = sc.nextInt(), r = sc.nextInt(), k = sc.nextInt();
            out.println(tree.smallK(l, r, k));
        }
    }

}

class SegTree {
    private int[] root, cnt, lc, rc;
    private int[] val;
    private long[] sum;
    private int N, no;

    public SegTree(int[] a) {
        int n = a.length;
        val = a.clone();
        Arrays.sort(val);
        N = 1;
        for (int i = 1; i < n; i++) {
            if (val[i - 1] != val[i]) {
                val[N++] = val[i];
            }
        }
        // 以上为值域离散化，离散化后值域为 [0, N-1] ，此时 val[i] 为 i 的实际值

        // 总共 n 个版本
        // 每个版本的生成占用空间为： N 的不含前导零的二进制表示长度 + 1
        // 此外，还有一个 0 代表的初始版本，只占用一个空间
        int tot = n * (33 - Integer.numberOfLeadingZeros(N - 1)) + 1;

        root = new int[n];// root[i] 表示 a[:i] 生成的权值线段树的根节点编号
        lc = new int[tot];// lc[i] ：i 节点左儿子编号
        rc = new int[tot];// rc[i] ：i 节点右儿子编号
        cnt = new int[tot];
        sum = new long[tot];

        // a[:i] 的权值线段树 基于 a[:i-1] 的权值线段树 来生成
        root[0] = insert(Arrays.binarySearch(val, 0, N, a[0]), 0, N - 1, 0);
        for (int i = 1; i < n; i++) {
            root[i] = insert(Arrays.binarySearch(val, 0, N, a[i]), 0, N - 1, root[i - 1]);
        }
    }

    private int insert(int o, int l, int r, int i) {
        int u = ++no;
        cnt[u] = cnt[i] + 1;
        sum[u] = sum[i] + val[o];
        if (l == r) {
            return u;
        }
        int m = (l + r) >> 1;
        if (o <= m) {
            lc[u] = insert(o, l, m, lc[i]);
            rc[u] = rc[i];
        } else {
            lc[u] = lc[i];
            rc[u] = insert(o, m + 1, r, rc[i]);
        }
        return u;
    }

    // 求 a[l:r] 中的第 k 小值
    public int smallK(int l, int r, int k) {
        return smallK(k, 0, N - 1, l == 0 ? 0 : root[l - 1], root[r]);
    }

    private int smallK(int k, int l, int r, int i, int j) {
        if (l == r) {
            return val[l];
        }
        int m = (l + r) >> 1;
        int t = cnt[lc[j]] - cnt[lc[i]];
        if (t < k) {
            return smallK(k - t, m + 1, r, rc[i], rc[j]);
        } else {
            return smallK(k, l, m, lc[i], lc[j]);
        }
    }

    // 求 a[l:r] 中的前 k 小累加和
    public long smallKSum(int l, int r, int k) {
        return smallKSum(k, 0, N - 1, l == 0 ? 0 : root[l - 1], root[r]);
    }

    private long smallKSum(int k, int l, int r, int i, int j) {
        if (l == r) {
            return (long) val[l] * k;
        }
        int m = (l + r) >> 1;
        int t = cnt[lc[j]] - cnt[lc[i]];
        if (t < k) {
            return sum[lc[j]] - sum[lc[i]] + smallKSum(k - t, m + 1, r, rc[i], rc[j]);
        } else {
            return smallKSum(k, l, m, lc[i], lc[j]);
        }
    }

    // 查询 a[l:r] 中 v 的数量
    public int count(int l, int r, int v) {
        v = Arrays.binarySearch(val, 0, N, v);
        return v < 0 ? 0 : count(v, 0, N - 1, l == 0 ? 0 : root[l - 1], root[r]);
    }

    private int count(int o, int l, int r, int i, int j) {
        if (l == r) {
            return cnt[j] - cnt[i];
        }
        int m = (l + r) >> 1;
        if (o <= m) {
            return count(o, l, m, lc[i], lc[j]);
        } else {
            return count(o, m + 1, r, rc[i], rc[j]);
        }
    }
}
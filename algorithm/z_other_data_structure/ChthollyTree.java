package algorithm.z_other_data_structure;
import static algorithm.zz.U.*;
import java.util.*;
/**
 * 测试链接：https://codeforces.com/problemset/problem/896/C
 */
public class ChthollyTree {
    
    int n, m, seed, vmax;

    void solve() {
        n = sc.nextInt();
        m = sc.nextInt();
        seed = sc.nextInt();
        vmax = sc.nextInt();
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = (rnd() % vmax) + 1;
        }
        Chtholly tree = new Chtholly(arr);
        for (int i = 1; i <= m; i++) {
            int op = (rnd() % 4) + 1;
            int l = (rnd() % n) + 1;
            int r = (rnd() % n) + 1;
            if (l > r) {
                int t = l; l = r; r = t;
            }
            if (op == 3) {
                int x = (rnd() % (r - l + 1)) + 1;
                out.println(tree.minK(l, r, x));
            } else {
                int x = (rnd() % vmax) + 1;
                if (op == 1) {
                    tree.add(l, r, x);
                } else if (op == 2) {
                    tree.assign(l, r, x);
                } else  {
                    int y = (rnd() % vmax) + 1;
                    out.println(tree.calP(l, r, x, y));
                }
            }
        }
    }

    int rnd() {
        int ret = seed;
        seed = (int) ((7L * seed + 13) % 1000000007);
        return ret;
    }

}

class Chtholly {
    private TreeSet<Node> set;
    
    public Chtholly(int[] arr) {
        set = new TreeSet<>();
        for (int i = 1; i < arr.length; i++) {
            set.add(new Node(i, i, arr[i]));
        }
    }

    private class Node implements Comparable<Node> {
        int l, r;
        long v;
    
        public Node(int l, int r, long v) {
            this.l = l;
            this.r = r;
            this.v = v;
        }

        @Override
        public int compareTo(Node other) {
            return this.l - other.l;
        }

    }

    // 拆分 [l..pos-1]  [pos..r]
    private void split(int pos) {
        Node cur = set.floor(new Node(pos, pos, 0));
        if (cur.l == pos) {
            return;
        }
        set.remove(cur);
        set.add(new Node(cur.l, pos - 1, cur.v));
        set.add(new Node(pos, cur.r, cur.v));
    }

    // 拆分包含 l 区间和包含 r 区间
    private void split(int l, int r) {
        Node tmp = set.floor(new Node(r, r, 0));
        if (tmp.r > r) {
            split(r + 1);
        }
        tmp = set.floor(new Node(l, l, 0));
        if (tmp.l < l) {
            split(l);
        }
    }

    // 推平 [l..r] -> x
    public void assign(int l, int r, long x) {
        split(l, r);
        Node tmp = new Node(r, r, 0);
        Node pre;
        while ((pre = set.floor(tmp)) != null && pre.l >= l) {
            set.remove(pre);
        }
        set.add(new Node(l, r, x));
    }

    // 修改 [l..r] + x
    public void add(int l, int r, long x) {
        split(l, r);
        for (Node tmp : set.subSet(new Node(l, l, 0), true, new Node(r, r, 0), true)) {
            tmp.v += x;
        }
    }

    // 区间第 k 小
    public long minK(int l, int r, long k) {
        split(l, r);
        List<long[]> list = new ArrayList<>();
        for (Node tmp : set.subSet(new Node(l, l, 0), true, new Node(r, r, 0), true)) {
            list.add(new long[]{tmp.v, tmp.r - tmp.l + 1});
        }
        list.sort((i, j) -> Long.compare(i[0], j[0]));
        for (long[] tmp : list) {
            k -= tmp[1];
            if (k <= 0) {
                return tmp[0];
            }
        }
        return -1;
    }

    public long calP(int l, int r, long x, long y) {
        split(l, r);
        long ans = 0;
        for (Node tmp : set.subSet(new Node(l, l, 0), true, new Node(r, r, 0), true)) {
            ans = (ans + pow(tmp.v % y, x, y) * (tmp.r - tmp.l + 1) % y) % y;
        }
        return ans;
    }

    private long pow(long a, long b, long mod) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) != 0) {
                res = res * a % mod;
            }
            a = a * a % mod;
            b >>= 1;
        }
        return res;
    }
}
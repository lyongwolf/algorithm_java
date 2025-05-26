package algorithm.tree.leftist_heap.persistent;
import static algorithm.zz.U.*;

import java.util.*;

/**
 * 测试链接：https://www.luogu.com.cn/problem/P2409
 */

public class Q1 {

    int INF = (int) 1e9;

    void solve() {
        int n = ni(), k = ni();
        int[][] mat = new int[n][];
        for (int i = 0; i < n; i++) {
            int m = ni();
            mat[i] = new int[m];
            for (int j = 0; j < m; j++) {
                mat[i][j] = ni();
            }
            Arrays.sort(mat[i]);
        }
        int[] ans = new int[k];

        PersistentHeap ph = new PersistentHeap((n + k * 2) * 10);// 节点预估数量 = 操作总次数 * log(单个堆最大节点数量)
        int h = 0;
        for (int i = 0; i < n; i++) {
            ans[0] += mat[i][0];
            h = ph.merge(h, ph.create(mat[i].length == 1 ? INF : mat[i][1] - mat[i][0], i, 0));
        }
        ph.pre[h] = ans[0];
        PriorityQueue<int[]> heap = new PriorityQueue<>((i, j) -> i[1] - j[1]);
        heap.add(new int[]{h, ph.pre[h] + ph.val[h]});
        for (int z = 1, u, d, i, j; z < k; z++) {
            int[] tup = heap.poll();
            u = tup[0];
            d = tup[1];
            ans[z] = d;

            h = ph.poll(u);
            if (h != 0) {
                ph.pre[h] = d - ph.val[u];
                heap.add(new int[]{h, ph.pre[h] + ph.val[h]});
            }

            i = ph.row[u];
            j = ph.col[u] + 1;
            if (j < mat[i].length) {
                h = ph.merge(h, ph.create(j + 1 == mat[i].length ? INF : mat[i][j + 1] - mat[i][j], i, j));
                ph.pre[h] = d;
                heap.add(new int[]{h, ph.pre[h] + ph.val[h]});
            }
        }
        for (int v : ans) {
            print(v);
            print(' ');
        }
        writeln();
    }

}

class PersistentHeap {
    private int no;
    private int[] dis;
    int[] lc, rc;

    // ...

    // 只对堆的头节点有效的参数
    int[] pre;
    
    // 节点比较逻辑参数
    int[] val;

    // 节点其它扩展属性
    int[] row, col;

    public PersistentHeap(int tot) {
        dis = new int[++tot];
        lc = new int[tot];
        rc = new int[tot];
        dis[0] = -1;
        
        // ...
        pre = new int[tot];
        
        val = new int[tot];

        row = new int[tot];
        col = new int[tot];

    }

    int merge(int i, int j) {
        if (i == 0 || j == 0) {
            return i + j;
        }
        if (val[i] > val[j]) {// 比较逻辑
            i ^= j ^ (j = i);
        }
        int h = clone(i);
        rc[h] = merge(rc[h], j);
        if (dis[lc[h]] < dis[rc[h]]) {
            lc[h] ^= rc[h] ^ (rc[h] = lc[h]);
        }
        dis[h] = dis[rc[h]] + 1;
        return h;
    }

    int poll(int i) {
        if (lc[i] == 0 && rc[i] == 0) {
            return 0;
        }
        if (lc[i] == 0 || rc[i] == 0) {
            return clone(lc[i] + rc[i]);
        }
        return merge(lc[i], rc[i]);
    }

    int create(int v, int i, int j) {
        no++;
        lc[no] = rc[no] = dis[no] = 0;

        // ...
        val[no] = v;
        row[no] = i;
        col[no] = j;

        return no;
    }

    private int clone(int i) {
        no++;
        dis[no] = dis[i];
        lc[no] = lc[i];
        rc[no] = rc[i];

        // ...
        val[no] = val[i];
        row[no] = row[i];
        col[no] = col[i];

        return no;
    }

}
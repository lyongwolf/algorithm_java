package algorithm.tree.leftist_heap.persistent;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * k 短路
 * 测试链接：https://www.luogu.com.cn/problem/P2483
 */

public class Q2 {

    double INF = 1e18;

    int[] key = new int[4150001];
	double[] val = new double[4150001];
	int[] pq = new int[4150001];
	int cntd, cnth;

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        double rest = sc.nextDouble();
        int[] head = new int[n + 1], nxt = new int[(m + 1) << 1], to = new int[(m + 1) << 1], path = new int[n + 1], rt = new int[n + 1];
        double[] wt = new double[(m + 1) << 1], dis = new double[n + 1];
        for (int i = 1, j = 2, u, v; i <= m; i++) {
            u = sc.nextInt();
            v = sc.nextInt();
            double w = sc.nextDouble();
            if (u == n) {
                continue;
            }
            nxt[j] = head[u]; head[u] = j; wt[j] = w; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; wt[j] = w; to[j++] = u;
        }
        
        Arrays.fill(dis, INF);
        dis[n] = 0;
        heapAdd(n, 0);
        while (!heapEmpty()) {
            int i = heapPop();
            int u = key[i];
            double w = val[i];
            if (dis[u] < w) {
                continue;
            }
            for (int e = head[u], v; e != 0; e = nxt[e]) {
                if ((e & 1) == 0) {
                    continue;
                }
                v = to[e];
                if (dis[v] > w + wt[e]) {
                    path[v] = e ^ 1;
                    dis[v] = w + wt[e];
                    heapAdd(v, dis[v]);
                }
            }
        }
        
        cntd = cnth = 0;
        for (int i = 1; i <= n; i++) {
            heapAdd(i, dis[i]);
        }
        PersistentHeap ph = new PersistentHeap(900000);
        while (!heapEmpty()) {
            int i = heapPop();
            int u = key[i];
            if (dis[u] == INF) {
                break;
            }
            for (int e = head[u], v; e != 0; e = nxt[e]) {
                if ((e & 1) != 0) {
                    continue;
                }
                v = to[e];
                if (e != path[u]) {
                    rt[u] = ph.merge(rt[u], ph.create(dis[v] + wt[e] - dis[u], v));
                }
            }
            if (path[u] != 0) {
                rt[u] = ph.merge(rt[u], rt[to[path[u]]]);
            }
        }

        int ans = 0;
        rest -= dis[1];
        if (rest >= 0) {
            ans++;
            if (rt[1] != 0) {
                cntd = cnth = 0;
                heapAdd(rt[1], dis[1] + ph.cost[rt[1]]);
            }
            while (!heapEmpty()) {
                int i = heapPop();
                int h = key[i];
                double w = val[i];
                rest -= w;
                if (rest < 0) {
                    break;
                }
                ans++;
                if (ph.lc[h] != 0) {
                    heapAdd(ph.lc[h], w - ph.cost[h] + ph.cost[ph.lc[h]]);
                }
                if (ph.rc[h] != 0) {
                    heapAdd(ph.rc[h], w - ph.cost[h] + ph.cost[ph.rc[h]]);
                }
                int v = ph.to[h];
                if (rt[v] != 0) {
                    heapAdd(rt[v], w + ph.cost[rt[v]]);
                }
            }
        }
        out.println(ans);
    }

    // (k, v)组成一个数据，放到堆上，根据v来组织小根堆
	void heapAdd(int k, double v) {
		key[++cntd] = k;
		val[cntd] = v;
		pq[++cnth] = cntd;
		int cur = cnth, father = cur / 2, tmp;
		while (cur > 1 && val[pq[father]] > val[pq[cur]]) {
			tmp = pq[father];
			pq[father] = pq[cur];
			pq[cur] = tmp;
			cur = father;
			father = cur / 2;
		}
	}

	// 小根堆上，堆顶的数据(k, v)弹出，并返回数据所在的下标ans
	// 根据返回值ans，key[ans]得到k，val[ans]得到v
	int heapPop() {
		int ans = pq[1];
		pq[1] = pq[cnth--];
		int cur = 1, l = cur * 2, r = l + 1, best, tmp;
		while (l <= cnth) {
			best = r <= cnth && val[pq[r]] < val[pq[l]] ? r : l;
			best = val[pq[best]] < val[pq[cur]] ? best : cur;
			if (best == cur) {
				break;
			}
			tmp = pq[best];
			pq[best] = pq[cur];
			pq[cur] = tmp;
			cur = best;
			l = cur * 2;
			r = l + 1;
		}
		return ans;
	}

	boolean heapEmpty() {
		return cnth == 0;
	}

}

class PersistentHeap {
    private int no;
    private int[] dis;
    int[] lc, rc;

    // ...
    
    // 节点比较逻辑参数
    double[] cost;

    // 节点其它扩展属性
    int[] to;

    public PersistentHeap(int tot) {
        dis = new int[++tot];
        lc = new int[tot];
        rc = new int[tot];
        dis[0] = -1;
        
        // ...
        cost = new double[tot];

        to = new int[tot];

    }

    int merge(int i, int j) {
        if (i == 0 || j == 0) {
            return i + j;
        }
        if (cost[i] > cost[j]) {// 比较逻辑
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

    int create(double v, int i) {
        no++;
        lc[no] = rc[no] = dis[no] = 0;

        // ...
        cost[no] = v;
        to[no] = i;

        return no;
    }

    private int clone(int i) {
        no++;
        dis[no] = dis[i];
        lc[no] = lc[i];
        rc[no] = rc[i];

        // ...
        cost[no] = cost[i];
        to[no] = to[i];

        return no;
    }

}
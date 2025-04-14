package algorithm.graph.classic;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 最小生成树
 */
public class Kruskal {

    int[] parent, sizeMap;

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        parent = new int[n + 1];
        sizeMap = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            sizeMap[i] = 1;
        }
        int[][] edge = new int[m][3];
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt(), w = sc.nextInt();
            edge[i][0] = u;
            edge[i][1] = v;
            edge[i][2] = w;
        }
        Arrays.sort(edge, (i, j) -> i[2] - j[2]);
        int s = 0, max = 0;
        for (int[] e : edge) {
            if (find(e[0]) != find(e[1])) {
                union(e[0], e[1]);
                s++;
                max = Math.max(max, e[2]);
            }
        }
        out.println(s + " " + max);
    }

    int find(int v) {
        return parent[v] == v ? v : (parent[v] = find(parent[v]));
    }

    void union(int f1, int f2) {
        f1 = find(f1); f2 = find(f2);
        if (f1 != f2) {
            if (sizeMap[f1] > sizeMap[f2]) {
                parent[f2] = f1;
                sizeMap[f1] += sizeMap[f2];
            } else {
                parent[f1] = f2;
                sizeMap[f2] += sizeMap[f1];
            }
        }
    }

}

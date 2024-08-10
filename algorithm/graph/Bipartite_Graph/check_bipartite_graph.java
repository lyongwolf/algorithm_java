package algorithm.graph.Bipartite_Graph;

/**
 * 测试链接：https://leetcode.cn/problems/is-graph-bipartite/
 * 如果存在奇环，则不是二分图（染色法）
 */
public class check_bipartite_graph {
    
    int[][] graph;
    int[] color;

    public boolean isBipartite(int[][] graph) {
        this.graph = graph;
        color = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (color[i] == 0 && !dfs(i, 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean dfs(int u, int c) {
        color[u] = c;
        for (int v : graph[u]) {
            if (color[v] == 0) {
                if (!dfs(v, 3 - c)) {
                    return false;
                }
            } else if (color[v] == c) {
                return false;
            }
        }
        return true;
    }

}

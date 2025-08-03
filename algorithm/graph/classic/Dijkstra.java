import java.util.*;

/**
 * 最短路
 */
public class Dijkstra {

   public int[] dijkstra(List<int[]>[] graph, int start) {
       int[] dis = new int[graph.length];
       Arrays.fill(dis, Integer.MAX_VALUE);
       dis[start] = 0;
       PriorityQueue<int[]> heap = new PriorityQueue<>((i, j) -> i[1] - j[1]);
       heap.add(new int[]{start, 0});
       while (!heap.isEmpty()) {
           int[] tup = heap.poll();
           int u = tup[0], w = tup[1];
           if (dis[u] < w) {
               continue;
           }
           for (int[] next : graph[u]) {
               int v = next[0], d = w + next[1];
               if (dis[v] > d) {
                   dis[v] = d;
                   heap.add(new int[]{v, d});
               }
           }
       }
       return dis;
   }

    public long[] dijkstra2(List<int[]>[] graph, int start) {
        long[] dis = new long[graph.length];
        Arrays.fill(dis, Long.MAX_VALUE);
        dis[start] = 0;
        PriorityQueue<long[]> heap = new PriorityQueue<>((i, j) -> Long.compare(i[1], j[1]));
        heap.add(new long[]{start, 0});
        while (!heap.isEmpty()) {
            long[] tup = heap.poll();
            int u = (int) tup[0];
            long w = tup[1];
            if (dis[u] < w) {
                continue;
            }
            for (int[] next : graph[u]) {
                int v = next[0];
                long d = w + next[1];
                if (dis[v] > d) {
                    dis[v] = d;
                    heap.add(new long[]{v, d});
                }
            }
        }
        return dis;
    }

    // 链式前向星
    public int[] dijkstra(int[] head, int[] nxt, int[] to, int[] wt, int start) {
        int[] dis = new int[head.length];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[start] = 0;
        PriorityQueue<int[]> heap = new PriorityQueue<>((i, j) -> i[1] - j[1]);
        heap.add(new int[]{start, 0});
        while (!heap.isEmpty()) {
            int[] tup = heap.poll();
            int u = tup[0], w = tup[1];
            if (dis[u] < w) {
                continue;
            }
            for (int i = head[u]; i != 0; i = nxt[i]) {
                int v = to[i], d = w + wt[i];
                if (dis[v] > d) {
                    dis[v] = d;
                    heap.add(new int[]{v, d});
                }
            }
        }
        return dis;
    }

    // 链式前向星
    public long[] dijkstra2(int[] head, int[] nxt, int[] to, long[] wt, int start) {
        long[] dis = new long[head.length];
        Arrays.fill(dis, Long.MAX_VALUE);
        dis[start] = 0;
        PriorityQueue<long[]> heap = new PriorityQueue<>((i, j) -> Long.compare(i[1], j[1]));
        heap.add(new long[]{start, 0});
        while (!heap.isEmpty()) {
            long[] tup = heap.poll();
            int u = (int) tup[0];
            long w = tup[1];
            if (w > dis[u]) {
                continue;
            }
            for (int i = head[u]; i != 0; i = nxt[i]) {
                int v = to[i];
                long d = w + wt[i];
                if (dis[v] > d) {
                    dis[v] = d;
                    heap.add(new long[]{v, d});
                }
            }
        }
        return dis;
    }

}

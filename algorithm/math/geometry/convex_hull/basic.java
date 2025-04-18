package algorithm.math.geometry.convex_hull;
import java.util.*;

/**
 * 二维凸包模板：
 * 测试链接：https://leetcode.cn/problems/erect-the-fence/
 */
public class basic {
    
    record Vec(long x, long y) implements Comparable<Vec> {
        public int compareTo(Vec o) { return x < o.x ? -1 : x > o.x ? 1 : Long.compare(y, o.y); }
        Vec  sub(Vec o) { return new Vec(x - o.x, y - o.y); }
        long dot(Vec o) { return x * o.x + y * o.y; }
        long det(Vec o) { return x * o.y - y * o.x; }
    }

    // dot: 求向量 a 和 b 的点积。几何意义为：b 在 a 方向上的投影长度，再乘以 a 的模长

    // det: 求向量 a 和 b 的叉积。叉积绝对值大小为向量构成的平行四边形面积。
    // 叉积为 0 则共线
    // 叉积小于 0 则 b 相对 a 顺时针旋转
    // 叉积大于 0 则 b 相对 a 逆时针旋转

    // 在使用 Andrew 算法求凸包时， 通常情况下不需要保留位于凸包边上的点。因此，如果叉积的结果等于 0 则依然可以弹出栈顶元素

    public int[][] outerTrees(int[][] trees) {
        int n = trees.length;
        Vec[] a = new Vec[n];
        for (int i = 0; i < n; i++) {
            a[i] = new Vec(trees[i][0], trees[i][1]);
        }
        Arrays.sort(a);
        int[] stk = new int[n + 1];
        int z = 0;
        boolean[] use = new boolean[n];
        for (int i = 1; i < n; i++) {
            while (z >= 1 && a[stk[z]].sub(a[stk[z - 1]]).det(a[i].sub(a[stk[z]])) < 0) {
                use[stk[z--]] = false;
            }
            use[i] = true;
            stk[++z] = i;
        }
        for (int i = n - 1, t = z; i >= 0; i--) {
            if (use[i]) {
                continue;
            }
            while (z > t && a[stk[z]].sub(a[stk[z - 1]]).det(a[i].sub(a[stk[z]])) < 0) {
                use[stk[z--]] = false;
            }
            use[i] = true;
            stk[++z] = i;
        }
        int[][] ans = new int[z][];
        for (int i = 0; i < z; i++) {
            ans[i] = new int[]{(int) a[stk[i]].x, (int) a[stk[i]].y};
        }
        return ans;
    }

}

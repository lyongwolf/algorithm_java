package algorithm.math.geometry.nearest_points;
import static algorithm.zz.U.*;
import java.util.*;
/**
 * 平面最近点对
 * 测试链接：https://codeforces.com/contest/429/problem/D
 */
public class basic {

    record Point(double x, double y) {
        static int cmpx(Point p1, Point p2) { return p1.x < p2.x ? -1 : p1.x > p2.x ? 1 : Double.compare(p1.y, p2.y); }
        static int cmpy(Point p1, Point p2) { return p1.y < p2.y ? -1 : p1.y > p2.y ? 1 : Double.compare(p1.x, p2.x); }
        static double Euclidean(Point p1, Point p2) { return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)); }
        static double Manhattan(Point p1, Point p2) { return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y); }
    }

    double minDis;
    Point ans1, ans2;

    void solve() {
        int n = ni();
        Point[] arr = new Point[n];
        for (int i = 0, s = 0; i < n; i++) {
            s += ni();
            arr[i] = new Point(i, s);
        }
        Arrays.sort(arr, Point::cmpx);
        minDis = Double.POSITIVE_INFINITY;
        dfs(new Point[n], arr, 0, n - 1);
        println((long) ((ans1.x - ans2.x) * (ans1.x - ans2.x) + (ans1.y - ans2.y) * (ans1.y - ans2.y)));
    }

    // 分治
    void dfs(Point[] tmp, Point[] arr, int l, int r) {
        if (r - l <= 3) {
            for (int i = l; i < r; i++) {
                for (int j = i + 1; j <= r; j++) {
                    updateAns(arr[i], arr[j]);
                }
            }
            Arrays.sort(arr, l, r + 1, (i, j) -> Double.compare(i.y, j.y));
            return;
        }
        int m = (l + r) >> 1;
        double minx = arr[m].x;
        dfs(tmp, arr, l, m);
        dfs(tmp, arr, m + 1, r);
        System.arraycopy(arr, l, tmp, l, r - l + 1);
        for (int i = l, i1 = l, i2 = m + 1; i <= r; i++) {
            arr[i] = i1 > m ? tmp[i2++] : i2 > r ? tmp[i1++] : tmp[i1].y < tmp[i2].y ? tmp[i1++] : tmp[i2++];
        }
        int z = 0;
        for (int i = l; i <= r; i++) {
            if (Math.abs(arr[i].x - minx) < minDis) {
                tmp[z++] = arr[i];
            }
        }
        for (int i = 0; i < z; i++) {
            for (int j = i - 1; j >= 0 && tmp[i].y - tmp[j].y < minDis; j--) {
                updateAns(tmp[j], tmp[i]);
            }
        }
    }

    void updateAns(Point a, Point b) {
        double d = Point.Euclidean(a, b);
        if (minDis > d) {
            minDis = d;
            ans1 = a;
            ans2 = b;
        }
    }
}

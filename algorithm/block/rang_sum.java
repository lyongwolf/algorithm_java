package algorithm.block;
import static algorithm.zz.U.*;

/**
 * 分块思想：
 * 区间求和，区间增加值
 * 测试链接：https://www.luogu.com.cn/problem/P3372
 */

public class rang_sum {
    
    void solve() {
        int n = ni(), m = ni();
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = ni();
        }
        Block block = new Block(arr);
        while (m-- > 0) {
            if (ni() == 1) {
                int x = ni(), y = ni(), k = ni();
                block.add(x, y, k);
            } else {
                int x = ni(), y = ni();
                println(block.query(x, y));
            }
        }
    }
   
}

class Block {
    private int[] l, r, id;
    private long[] arr, sum, lazy;

    public Block(int[] nums) {
        int n = nums.length - 1;
        int N = (int) Math.sqrt(n);
        int tot = (n + N - 1) / N;
        l = new int[tot + 1];
        r = new int[tot + 1];
        id = new int[n + 1];
        arr = new long[n + 1];
        sum = new long[tot + 1];
        lazy = new long[tot + 1];
        for (int i = 1, x = 1, y; i <= tot; i++, x += N) {
            y = Math.min(n, x + N - 1);
            l[i] = x;
            r[i] = y;
            for (int j = x; j <= y; j++) {
                id[j] = i;
                arr[j] = nums[j];
                sum[i] += arr[j];
            }
        }
    }

    public void add(int L, int R, long V) {
        for (int i = L; i <= R;) {
            int j = id[i];
            int ri = Math.min(R, r[j]);
            long v = (long) V * (ri - i + 1);
            sum[j] += v;
            if (i == l[j] && ri == r[j]) {
                lazy[j] += V;
            } else {
                for (int t = i; t <= ri; t++) {
                    arr[t] += V;
                }
            }
            i = ri + 1;
        }
    }

    public long query(int L, int R) {
        long ans = 0;
        for (int i = L; i <= R;) {
            int j = id[i];
            int ri = Math.min(R, r[j]);
            if (i == l[j] && ri == r[j]) {
                ans += sum[j];
            } else {
                for (int t = i; t <= ri; t++) {
                    ans += arr[t] + lazy[j];
                }
            }
            i = ri + 1;
        }
        return ans;
    }
}
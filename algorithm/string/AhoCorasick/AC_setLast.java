package algorithm.string.AhoCorasick;
import static algorithm.zz.U.*;
import java.util.*;
/**
 * https://www.luogu.com.cn/problem/P3311
 * 新增last指针，指向上一个模式串的终点，且该模式串为当前节点的后缀
 */
public class AC_setLast {

    int MOD = (int) 1e9 + 7;

    void solve() {
        char[] str = ns().toCharArray();
        int n = str.length;
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = str[i] - '0';
        }
        AhoCorasick ac = new AhoCorasick();
        int m = ni();
        for (int i = 0; i < m; i++) {
            ac.insert(ns());
        }
        ac.setFail();
        m = ac.no;
        long[][][][] dp = new long[n + 1][2][2][m + 1];
        for (int i = 0; i <= m; i++) {
            dp[n][1][0][i] = dp[n][1][1][i] = 1;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int u = 0; u <= m; u++) {
                        if (j == 0) {
                            dp[i][j][k][u] += dp[i + 1][j][1][u];
                        }
                        for (int v = j == 0 ? 1 : 0; v <= (k == 0 ? num[i] : 9); v++) {
                            int p = ac.nxt[u][v];
                            if (!ac.end[p] && ac.last[p] == 0) {
                                dp[i][j][k][u] += dp[i + 1][1][k == 0 && v == num[i] ? 0 : 1][p];
                            }
                        }
                        dp[i][j][k][u] %= MOD;
                    }
                }
            }
        }
        println(dp[0][0][0][0]);
    }

}

class AhoCorasick {
    public int[][] nxt;
    public boolean[] end;
    public int[] fail, last;
    public int no;

    public AhoCorasick() {
        nxt = new int[1][10];
        end = new boolean[1];
    }

    public void insert(String s) {
        int u = 0;
        for (char c : s.toCharArray()) {
            c -= '0';
            if (nxt[u][c] == 0) {
                if (++no == nxt.length) {
                    int[][] tmp = new int[no << 1][];
                    for (int i = 0; i < no; i++) {
                        tmp[i] = nxt[i];
                    }
                    for (int i = no; i < no << 1; i++) {
                        tmp[i] = new int[10];
                    }
                    nxt = tmp;
                    end = Arrays.copyOf(end, no << 1);
                }
                nxt[u][c] = no;
            }
            u = nxt[u][c];
        }
        end[u] = true;
    }

    public void setFail() {
        fail = new int[no + 1];
        last = new int[no + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            if (nxt[0][i] != 0) {
                queue.addLast(nxt[0][i]);
            }
        }
        while (!queue.isEmpty()) {
            int u = queue.removeFirst();
            for (int i = 0; i < 10; i++) {
                int v = nxt[u][i];
                if (v != 0) {
                    queue.addLast(v);
                    fail[v] = nxt[fail[u]][i];
                    last[v] = end[fail[v]] ? fail[v] : last[fail[v]];
                } else {
                    nxt[u][i] = nxt[fail[u]][i];
                }
            }
        }
    }
}
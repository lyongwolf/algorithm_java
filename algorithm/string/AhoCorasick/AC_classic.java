package algorithm.string.AhoCorasick;
import static algorithm.zz.U.*;

import java.util.*;

/**
 * 测试链接：https://www.luogu.com.cn/problem/P5357
 */
public class AC_classic {

    int[] head, nxt, to, freq;
    boolean[] vis;

    void solve() {
        int n = ni();
        String[] pattern = new String[n];
        AhoCorasick ac = new AhoCorasick();
        for (int i = 0; i < n; i++) {
            pattern[i] = ns();
            ac.insert(pattern[i]);
        }
        ac.setFail();
        int tot = ac.no + 1;
        char[] str = ns().toCharArray();
        freq = new int[tot];
        for (int i = 0, u = 0; i < str.length; i++) {
            u = ac.nxt[u][str[i] - 'a'];
            freq[u]++;
        }
        head = new int[tot];
        nxt = new int[tot];
        to = new int[tot];
        Arrays.fill(head, -1);
        for (int v = 1, j = 0; v < tot; v++) {
            int u = ac.fail[v];
            nxt[j] = head[u];
            head[u] = j;
            to[j++] = v;
        }
        vis = new boolean[tot];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.add(0);
        while (!stack.isEmpty()) {
            int u = stack.peek();
            if (!vis[u]) {
                vis[u] = true;
                for (int e = head[u]; e != -1; e = nxt[e]) {
                    stack.push(to[e]);
                }
            } else {
                stack.pop();
                for (int e = head[u]; e != -1; e = nxt[e]) {
                    freq[u] += freq[to[e]];
                }
            }
        }
        for (String s : pattern) {
            int u = 0;
            for (char c : s.toCharArray()) {
                u = ac.nxt[u][c - 'a'];
            }
            println(freq[u]);
        }
    }

}

class AhoCorasick {
    public int[][] nxt;
    public int[] fail;
    public int no;

    public AhoCorasick() {
        nxt = new int[1][26];
    }

    public void insert(String s) {
        int u = 0;
        for (char c : s.toCharArray()) {
            c -= 'a';
            if (nxt[u][c] == 0) {
                if (++no == nxt.length) {
                    int[][] tmp = new int[no << 1][];
                    for (int i = 0; i < no; i++) {
                        tmp[i] = nxt[i];
                    }
                    for (int i = no; i < no << 1; i++) {
                        tmp[i] = new int[26];
                    }
                    nxt = tmp;
                }
                nxt[u][c] = no;
            }
            u = nxt[u][c];
        }
    }

    public void setFail() {
        fail = new int[no + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < 26; i++) {
            if (nxt[0][i] != 0) {
                queue.addLast(nxt[0][i]);
            }
        }
        while (!queue.isEmpty()) {
            int u = queue.removeFirst();
            for (int i = 0; i < 26; i++) {
                int v = nxt[u][i];
                if (v != 0) {
                    queue.addLast(v);
                    fail[v] = nxt[fail[u]][i];
                } else {
                    nxt[u][i] = nxt[fail[u]][i];
                }
            }
        }
    }
}

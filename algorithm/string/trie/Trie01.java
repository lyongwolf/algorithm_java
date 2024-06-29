package algorithm.string.trie;

/**
 * 0-1 trie
 * 测试链接：https://leetcode.cn/problems/ms70jA/
 */
public class Trie01 {
    
    static int MAXN = 200001 * 31;
    static int[][] trie = new int[MAXN][2];
    static int[] pass = new int[MAXN], end = new int[MAXN];
    static int no;

    static void insert(int v) {
        int cur = 1;
        for (int i = 30; i >= 0; i--) {
            int j = v >> i & 1;
            if (trie[cur][j] == 0) {
                trie[cur][j] = ++no;
            }
            cur = trie[cur][j];
            pass[cur]++;
        }
        end[cur]++;
    }

    // v 一定在字典树中
    static void delete(int v) {
        int cur = 1;
        for (int i = 30; i >= 0; i--) {
            int j = v >> i & 1;
            if (--pass[trie[cur][j]] == 0) {
                trie[cur][j] = 0;
                return;
            }
            cur = trie[cur][j];
        }
        end[cur]--;
    }

    static boolean search(int v) {
        int cur = 1;
        for (int i = 30; i >= 0; i--) {
            cur = trie[cur][v >> i & 1];
            if (cur == 0) {
                return false;
            }
        }
        return end[cur] > 0;
    }

    static void clear() {
        for (int i = 1; i <= no; i++) {
            trie[i][0] = trie[i][1] = pass[i] = end[i] = 0;
        }
        no = 1;
    }

    public int findMaximumXOR(int[] nums) {
        clear();
        for (int v : nums) {
            insert(v);
        }
        int ans = 0;
        for (int v : nums) {
            int cur = 1;
            int best = 0;
            for (int i = 30; i >= 0; i--) {
                int j = v >> i & 1;
                if (trie[cur][j ^ 1] != 0) {
                    best |= (j ^ 1) << i;
                    cur = trie[cur][j ^ 1];
                } else {
                    best |= j << i;
                    cur = trie[cur][j];
                }
            }
            ans = Math.max(ans, v ^ best);
        }
        return ans;
    }
}

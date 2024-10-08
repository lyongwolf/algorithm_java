package algorithm.string.trie.array;

/**
 * 0-1 trie
 * 测试链接：https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/
 */
public class Trie01Array {
    
    static class Trie01 {
        private int[][] nxt;
        private int[] cnt;
        private int no, high;

        public Trie01(int tot, int high) {
            tot += 2;
            nxt = new int[tot][2];
            cnt = new int[tot];
            no = 1;
            this.high = high;
        }

        public void insert(int v) {
            int u = 1;
            for (int i = high; i >= 0; i--) {
                int j = v >> i & 1;
                if (nxt[u][j] == 0) {
                    nxt[u][j] = ++no;
                }
                u = nxt[u][j];
                cnt[u]--;
            }
        }

        public void delete(int v) {
            int u = 1;
            for (int i = high; i >= 0; i--) {
                int j = v >> i & 1;
                if (cnt[nxt[u][j]] == 1) {
                    nxt[u][j] = 0;
                    return;
                }
                u = nxt[u][j];
                cnt[u]--;
            }
        }

        public int maxXor(int v) {
            int u = 1;
            int ans = 0;
            for (int i = high; i >= 0; i--) {
                int j = v >> i & 1;
                if (nxt[u][j ^ 1] != 0) {
                    ans |= 1 << i;
                    u = nxt[u][j ^ 1];
                } else {
                    u = nxt[u][j];
                }
            }
            return ans;
        }

        public int minXor(int v) {
            int u = 1;
            int ans = 0;
            for (int i = high; i >= 0; i--) {
                int j = v >> i & 1;
                if (nxt[u][j] != 0) {
                    u = nxt[u][j];
                } else {
                    ans |= 1 << i;
                    u = nxt[u][j ^ 1];
                }
            }
            return ans;
        }

    }
}

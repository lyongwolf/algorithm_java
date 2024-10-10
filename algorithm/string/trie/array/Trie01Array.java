package algorithm.string.trie.array;

import java.util.Arrays;

/**
 * 0-1 trie
 * 测试链接：https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/
 */
public class Trie01Array {
    
    static class Trie01 {
        private int[][] nxt;
        private int[] cnt;
        private int no, high;

        public Trie01(int high) {
            nxt = new int[16][2];
            cnt = new int[16];
            this.high = high;
        }

        public void insert(int v) {
            int u = 0;
            for (int i = high; i >= 0; i--) {
                int j = v >> i & 1;
                if (nxt[u][j] == 0) {
                    if (++no == nxt.length) {
                        int[][] tmp = new int[no << 1][2];
                        for (int k = 0; k < no; k++) {
                            tmp[k] = nxt[k];
                        }
                        for (int k = no; k < no << 1; k++) {
                            tmp[k] = new int[2];
                        }
                        nxt = tmp;
                        cnt = Arrays.copyOf(cnt, no << 1);
                    }
                    nxt[u][j] = no;
                }
                u = nxt[u][j];
                cnt[u]++;
            }
        }

        public void delete(int v) {
            int u = 0;
            for (int i = high; i >= 0; i--) {
                int j = v >> i & 1;
                if (--cnt[nxt[u][j]] == 0) {
                    nxt[u][j] = 0;
                    return;
                }
                u = nxt[u][j];
            }
        }

        public int maxXor(int v) {
            int u = 0;
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
            int u = 0;
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

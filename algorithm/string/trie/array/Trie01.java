package algorithm.string.trie.array;

/**
 * 0-1 trie
 * 测试链接：https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/
 */
class Trie01 {
    private static final int H = 30, MAXN = 200000, MAXT = MAXN * (H + 2);
    private static int[] nxt0 = new int[MAXT], nxt1 = new int[MAXT];
    private static int[] cnt = new int[MAXT];
    private static int no;

    private int root;

    public Trie01() {
        root = ++no;
    }

    public void insert(int v) {
        int u = 0;
        for (int i = H; i >= 0; i--) {
            if ((v >> i & 1) == 0) {
                if (nxt0[u] == 0) {
                    nxt0[u] = ++no;
                }
                u = nxt0[u];
            } else {
                if (nxt1[u] == 0) {
                    nxt1[u] = ++no;
                }
                u = nxt1[u];
            }
            cnt[u]++;
        }
    }

    public void delete(int v) {
        int u = root;
        for (int i = H; i >= 0; i--) {
            if ((v >> i & 1) == 0) {
                if (--cnt[nxt0[u]] == 0) {
                    nxt0[u] = 0;
                    return;
                }
                u = nxt0[u];
            } else {
                if (--cnt[nxt1[u]] == 0) {
                    nxt1[u] = 0;
                    return;
                }
                u = nxt1[u];
            }
        }
    }

    public int maxXor(int v) {
        int u = root;
        int ans = 0;
        for (int i = H; i >= 0; i--) {
            if ((v >> i & 1) == 0) {
                if (nxt1[u] != 0) {
                    ans |= 1 << i;
                    u = nxt1[u];
                } else {
                    u = nxt0[u];
                }
            } else {
                if (nxt0[u] != 0) {
                    ans |= 1 << i;
                    u = nxt0[u];
                } else {
                    u = nxt1[u];
                }
            }
        }
        return ans;
    }

    public int minXor(int v) {
        int u = root;
        int ans = 0;
        for (int i = H; i >= 0; i--) {
            if ((v >> i & 1) == 0) {
                if (nxt0[u] != 0) {
                    u = nxt0[u];
                } else {
                    ans |= 1 << i;
                    u = nxt1[u];
                }
            } else {
                if (nxt1[u] != 0) {
                    u = nxt1[u];
                } else {
                    ans |= 1 << i;
                    u = nxt0[u];
                }
            }
        }
        return ans;
    }
}
package algorithm.string.trie;

/**
 * 0-1 trie
 */
public class Trie01 {
    
    static class Trie {
        private Node root = new Node();

        private class Node {
            int cnt;
            Node[] nxt = new Node[2];
        }

        private void insert(int v) {
            Node cur = root;
            for (int i = 30; i >= 0; i--) {
                int j = v >> i & 1;
                if (cur.nxt[j] == null) {
                    cur.nxt[j] = new Node();
                }
                cur = cur.nxt[j];
                cur.cnt++;
            }
        }

        private void delete(int v) {
            Node cur = root;
            for (int i = 30; i >= 0; i--) {
                int j = v >> i & 1;
                if (cur.nxt[j].cnt == 1) {
                    cur.nxt[j] = null;
                    return;
                }
                cur = cur.nxt[j];
                cur.cnt--;
            }
        }

        private int maxXor(int v) {
            Node cur = root;
            int ans = 0;
            for (int i = 30; i >= 0; i--) {
                int j = v >> i & 1, k = j ^ 1;
                if (cur.nxt[k] != null) {
                    ans |= 1 << i;
                    cur = cur.nxt[k];
                } else {
                    cur = cur.nxt[j];
                }
            }
            return ans;
        }

    }
}

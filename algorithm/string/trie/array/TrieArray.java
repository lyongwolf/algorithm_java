package algorithm.string.trie.array;

/**
 * 测试链接：https://www.nowcoder.com/practice/a55a584bc0ca4a83a272680174be113b
 */
public class TrieArray {

    static class Trie {
        int[][] nxt;
        int[] end, cnt; 
        int no;

        public Trie(int tot) {
            tot++;
            nxt = new int[tot][26];
            end = new int[tot];
            cnt = new int[tot];
        }

        public void insert(String word) {
            int u = 0;
            for (char c : word.toCharArray()) {
                c -= 'a';
                if (nxt[u][c] == 0) {
                    nxt[u][c] = ++no;
                }
                u = nxt[u][c];
                cnt[u]++;
            }
            end[u]++;
        }

        // 字符串在前缀树中
        public void delete(String word) {
            int u = 0;
            for (char c : word.toCharArray()) {
                c -= 'a';
                if (--cnt[nxt[u][c]] == 0) {
                    nxt[u][c] = 0;
                    return;
                }
                u = nxt[u][c];
            }
            end[u]--;
        }

        public boolean search(String word) {
            int u = 0;
            for (char c : word.toCharArray()) {
                u = nxt[u][c - 'a'];
                if (u == 0) {
                    return false;
                }
            }
            return end[u] > 0;
        }

        public int prefixNumber(String pre) {
            int u = 0;
            for (char c : pre.toCharArray()) {
                u = nxt[u][c - 'a'];
                if (u == 0) {
                    return 0;
                }
            }
            return cnt[u];
        }
    }

    public String[] trieU(String[][] operators) {
        Trie trie = new Trie(100000 * 20);
        int n = 0;
        for (String[] op : operators) {
            if (op[0].equals("3") || op[0].equals("4")) {
                n++;
            }
        }
        String[] ans = new String[n];
        int i = 0;
        for (String[] op : operators) {
            switch (op[0]) {
                case "1" : 
                    trie.insert(op[1]);
                    break;
                case "2" : 
                    trie.delete(op[1]);
                    break;
                case "3" : 
                    ans[i++] = trie.search(op[1]) ? "YES" : "NO";
                    break;
                case "4" : 
                    ans[i++] = String.valueOf(trie.prefixNumber(op[1]));
                    break;
            }
        }
        return ans;
    }

}
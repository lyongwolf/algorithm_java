package algorithm.string.trie.node;

/**
 * 测试链接：https://www.nowcoder.com/practice/a55a584bc0ca4a83a272680174be113b
 */
public class Trie {

    static class Node {
        int end;
        int cnt;
        Node[] nxt = new Node[26];
    }
    static Node root;

    static void insert(String word) {
        Node cur = root;
        for (char c : word.toCharArray()) {
            int j = c - 'a';
            if (cur.nxt[j] == null) {
                cur.nxt[j] = new Node();
            }
            cur = cur.nxt[j];
            cur.cnt++;
        }
        cur.end++;
    }

    // 字符串在前缀树中
    static void delete(String word) {
        Node cur = root;
        for (char c : word.toCharArray()) {
            int j = c - 'a';
            if (--cur.nxt[j].cnt == 0) {
                cur.nxt[j] = null;
                return;
            }
            cur = cur.nxt[j];
        }
        cur.end--;
    }

    static boolean search(String word) {
        Node cur = root;
        for (char c : word.toCharArray()) {
            cur = cur.nxt[c - 'a'];
            if (cur == null) {
                return false;
            }
        }
        return cur.end > 0;
    }

    static int prefixNumber(String pre) {
        Node cur = root;
        for (char c : pre.toCharArray()) {
            cur = cur.nxt[c - 'a'];
            if (cur == null) {
                return 0;
            }
        }
        return cur.cnt;
    }

    public String[] trieU(String[][] operators) {
        root = new Node();
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
                    insert(op[1]);
                    break;
                case "2" : 
                    delete(op[1]);
                    break;
                case "3" : 
                    ans[i++] = search(op[1]) ? "YES" : "NO";
                    break;
                case "4" : 
                    ans[i++] = String.valueOf(prefixNumber(op[1]));
                    break;
            }
        }
        return ans;
    }

}
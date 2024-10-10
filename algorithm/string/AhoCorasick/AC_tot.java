package algorithm.string.AhoCorasick;

import java.util.*;

public class AC_tot {
    
    static class AhoCorasick {
        public int[][] nxt;
        public int[] len;
        public boolean[] end;
        public int[] fail, last;
        public int no;

        public AhoCorasick() {
            nxt = new int[16][26];
            len = new int[16];
            end = new boolean[16];
        }

        public void insert(String s) {
            int u = 0, l = 0;
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
                        len = Arrays.copyOf(len, no << 1);
                        end = Arrays.copyOf(end, no << 1);
                    }
                    nxt[u][c] = no;
                }
                u = nxt[u][c];
                len[u] = ++l;
            }
            end[u] = true;
        }

        public void setFail() {
            fail = new int[no + 1];
            last = new int[no + 1];
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
                        last[v] = end[fail[v]] ? fail[v] : last[fail[v]];
                    } else {
                        nxt[u][i] = nxt[fail[u]][i];
                    }
                }
            }
        }
    }

}

package algorithm.string;

import java.util.*;


public class KMP {
    
    public List<Integer> kmp(String s, String t) {
        char[] str = s.toCharArray(), tar = t.toCharArray();
        int n = str.length, m = tar.length, x = 0, y = 0;
        int[] next = next(tar);
        List<Integer> ans = new ArrayList<>();
        while (x < n) {
            if (str[x] == tar[y]) {
                x++;
                y++;
                if (y == m) {
                    ans.add(x - m);
                    y = next[y];
                }
            } else if (y == 0) {
                x++;
            } else {
                y = next[y];
            }
        }
        return ans;
    }

    public int[] next(char[] str) {
        int n = str.length;
        int[] next = new int[n + 1];
        next[0] = -1;
        for (int i = 2, j = 0; i <= n; i++) {
            while (j >= 0 && str[i - 1] != str[j]) {
                j = next[j];
            }
            next[i] = ++j;
        }
        return next;
    }
}

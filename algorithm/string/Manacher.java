package algorithm.string;

import java.util.*;

public class Manacher {
    
    // 若s[i..j]为回文串，则  (j - i + 1) < pArr[i + j + 1]
    public int[] pArr(String s) {
        char[] str = manacherString(s).toCharArray();
        System.out.println(Arrays.toString(str));
        int n = str.length;
        int[] pArr = new int[n];
        for (int i = 0, r = 0, c = 0; i < n; i++) {
            pArr[i] = i >= r ? 1 : Math.min(r - i, pArr[c * 2 - i]);
            while (i - pArr[i] >= 0 && i + pArr[i] < n && str[i - pArr[i]] == str[i + pArr[i]]) {
                pArr[i]++;
            }
            if (i + pArr[i] > r) {
                c = i;
                r = i + pArr[i];
            }
        }
        return pArr;
    }

    public String manacherString(String s) {
        StringBuilder sb = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            sb.append(c).append('#');
        }
        return sb.toString();
    }
}

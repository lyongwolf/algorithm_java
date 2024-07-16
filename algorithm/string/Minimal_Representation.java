package algorithm.string;

import java.util.*;
import java.io.*;

/**
 * test link：https://www.luogu.com.cn/problem/P1368
 */

public class Minimal_Representation {
    
    static void solve() {
        int n = sc.nextInt();
        int[] arr = new int[n << 1];
        for (int i = 0; i < n; i++) {
            arr[i] = arr[i + n] = sc.nextInt();
        }
        int i = 0, j = 1, k = 0;
        for (; i < n && j < n; k = 0) {
            while (k < n && arr[i + k] == arr[j + k]) {
                k++;
            }
            if (arr[i + k] > arr[j + k]) {
                i += k + 1;
            } else {
                j += k + 1;
            }
            if (i == j) {
                j++;
            }
        }
        i = Math.min(i, j);
        for (j = 0; j < n; j++) {
            out.print(arr[i++] + " ");
        }   
        out.println();
    }



    static boolean retest = false;
    static FastReader sc = new FastReader();
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static void main(String[] args) {
        if (retest) {int t = sc.nextInt(); while (t-- > 0) solve();} else solve(); out.flush(); out.close();
    }
    static class FastReader {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in)); 
        StringTokenizer st;
        String next() {
            try {while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(r.readLine()); return st.nextToken();} 
            catch (Exception e) {return null;}
        }
        int nextInt() {return Integer.parseInt(next());}
        long nextLong() {return Long.parseLong(next());}
        double nextDouble() {return Double.parseDouble(next());}
    }
}

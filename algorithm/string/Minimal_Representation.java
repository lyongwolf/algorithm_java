package algorithm.string;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * test link：https://www.luogu.com.cn/problem/P1368
 */

public class Minimal_Representation {
    
    void solve() {
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
        out.writeln();
    }

}

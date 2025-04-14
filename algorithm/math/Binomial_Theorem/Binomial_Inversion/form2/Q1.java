package algorithm.math.Binomial_Theorem.Binomial_Inversion.form2;
import static algorithm.zz.U.*;
import java.util.*;

/**
 * 测试链接：https://www.luogu.com.cn/problem/P1595
 */

public class Q1 {     

    void solve() {
        int n = sc.nextInt();
        long f = 1;
        for (int i = 1; i <= n; i++) {
            f *= i;
        }
        long ans = f;
        long fi = 1;
        for (int i = 1; i <= n; i++) {
            fi *= i;
            if ((i & 1) == 0) {
                ans += f / fi;
            } else {
                ans -= f / fi;
            }
        }
        out.println(ans);
    }
}

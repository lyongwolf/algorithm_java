package algorithm.util.trick;

public class Bit {
    
    // 下一个大于 x 的 二进制1个数与 x 等同的数
    int g(int x){
        int o = x & -x;
        int t = x + o;
        int c = t & -t;
        int m = (c / o >> 1) - 1;
        return t | m;
    }

}

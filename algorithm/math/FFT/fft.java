package algorithm.math.FFT;

import java.util.*;

/**
 * 多项式乘法
 * 测试链接：https://www.luogu.com.cn/problem/P3803
 */
public class fft {                                                                                                                                                                                             static class FastReader{java.io.InputStream is=System.in;byte[]inbuf=new byte[1024];char[]str=new char[16];int lenbuf,ptrbuf,b;int readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=(char)b;b=readByte();}return new String(str,0,i);}int nextInt(){return (int)nextLong();}long nextLong(){while((b=readByte())<33);boolean minus=b=='-';long num=minus?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return minus?-num:num;}double nextDouble(){return Double.parseDouble(next());}}static class FastWriter{java.io.OutputStream out=System.out;int tr=0,BUF_SIZE=8192;byte[]buf=new byte[BUF_SIZE];int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}FastWriter write(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}FastWriter write(char c){return write((byte)c);}FastWriter write(int x){if(x==Integer.MIN_VALUE){return write((long)x);}if(tr+12>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(long x){if(x==Long.MIN_VALUE){return write(""+x);}if(tr+21>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(double x,int precision){if(x<0){write('-');x=-x;}x+=Math.pow(10,-precision)/2;write((long)x).write(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;write((char)('0'+(int)x));x-=(int)x;}return this;}FastWriter write(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.charAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void print(char c){write(c);}void print(String s){write(s);}void print(int x){write(x);}void print(long x){write(x);}void print(double x,int precision){write(x,precision);}void writeln(){write((byte)'\n');}void println(char c){write(c).writeln();}void println(int x){write(x).writeln();}void println(long x){write(x).writeln();}void println(double x,int precision){write(x,precision).writeln();}void println(String s){write(s).writeln();}void println(Object obj){write(obj.toString()).writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){}}void flush(){innerflush();try{out.flush();}catch(Exception e){}}}static FastReader sc=new FastReader();static FastWriter out=new FastWriter();static int min(int a,int b){return a>b?b:a;}static int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}static long min(long a,long b){return a>b?b:a;}static long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}static double min(double a,double b){return a>b?b:a;}static double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}static int max(int a,int b){return a<b?b:a;}static int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}static long max(long a,long b){return a<b?b:a;}static long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}static double max(double a,double b){return a<b?b:a;}static double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}static int abs(int a){return a<0?-a:a;}static long abs(long a){return a<0?-a:a;}static double abs(double a){return a<0?-a:a;}
    public static void main(String[] args) {/* int t = sc.nextInt(); while (t-- > 0) */ solve(); out.flush();}         

    static void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        int tot = 1 << (32 - Integer.numberOfLeadingZeros(n + m));
        Complex[] A = new Complex[tot], B = new Complex[tot];
        for (int i = 0; i <= n; i++) {
            A[i] = new Complex(sc.nextInt(), 0);
        }
        for (int i = 0; i <= m; i++) {
            B[i] = new Complex(sc.nextInt(), 0);
        }
        for (int i = n + 1; i < tot; i++) {
            A[i] = Complex.ZERO;
        }
        for (int i = m + 1; i < tot; i++) {
            B[i] = Complex.ZERO;
        }
        FFT(A, tot, 1);
        FFT(B, tot, 1);
        for (int i = 0; i < tot; i++) {
            A[i] = A[i].mul(B[i]);
        }
        FFT(A, tot, -1);
        for (int i = 0; i <= n + m; i++) {
            out.print((int) (A[i].x / tot + 0.5) + " ");
        }
        out.writeln();
    }

    // 迭代版 + 位逆序变换（蝴蝶变换）
    static void FFT(Complex[] A, int n, int op) {
        int[] r = new int[n];
        for (int i = 0; i < n; i++) {
            r[i] = r[i >> 1] >> 1 | ((i & 1) == 0 ? 0 : n >> 1);
        }
        for (int i = 0; i < n; i++) {
            if (i < r[i]) {
                Complex t = A[i];
                A[i] = A[r[i]];
                A[r[i]] = t;
            }
        }
        for (int m = 2; m <= n; m <<= 1) {
            Complex w1 = new Complex(Math.cos(Math.PI * 2 / m), Math.sin(Math.PI * 2 / m) * op);
            for (int j = 0; j < n; j += m) {
                Complex wk = Complex.ONE;
                for (int i = j; i < j + m / 2; i++, wk = wk.mul(w1)) {
                    Complex a = A[i], b = A[i + m / 2].mul(wk);
                    A[i] = a.add(b);
                    A[i + m / 2] = a.sub(b);
                }
            }
        }
    }

    // 递归版
    // static void FFT(Complex[] A, int n, int op) {
    //     if (n == 1) {
    //         return;
    //     }
    //     Complex[] A1 = new Complex[n / 2], A2 = new Complex[n / 2];
    //     for (int i = 0; i < n / 2; i++) {
    //         A1[i] = A[i * 2];
    //         A2[i] = A[i * 2 + 1];
    //     }
    //     FFT(A1, n / 2, op);
    //     FFT(A2, n / 2, op);
    //     Complex w1 = new Complex(Math.cos(Math.PI * 2 / n), Math.sin(Math.PI * 2 / n) * op), wk = Complex.ONE;
    //     for (int i = 0; i < n / 2; i++, wk = wk.mul(w1)) {
    //         Complex tmp = A2[i].mul(wk);
    //         A[i] = A1[i].add(tmp);
    //         A[i + n / 2] = A1[i].sub(tmp);
    //     }
    // }

    // 复数类
    static class Complex {
        static final Complex ZERO = new Complex(0, 0), ONE = new Complex(1, 0);
        double x, y;

        public Complex(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Complex add(Complex o) {
            return new Complex(x + o.x, y + o.y);
        }

        public Complex sub(Complex o) {
            return new Complex(x - o.x, y - o.y);
        }

        public Complex mul(Complex o) {
            return new Complex(x * o.x - y * o.y, x * o.y + y * o.x);
        }

    }
}

// FFT 封装类
class FFT {
    private double x, y, t, w1x, w1y, wkx, wky, ax, ay, bx, by;
    private int[] r;

    public int[] mul(int[] a, int[] b) {
        int n = a.length - 1, m = b.length - 1;
        int N = 1 << (32 - Integer.numberOfLeadingZeros(n + m));
        r = new int[N];
        for (int i = 0; i < N; i++) {
            r[i] = r[i >> 1] >> 1 | ((i & 1) == 0 ? 0 : N >> 1);
        }
        double[] Ax = new double[N], Ay = new double[N], Bx = new double[N], By = new double[N];
        for (int i = 0; i <= n; i++) {
            Ax[i] = a[i];
        }
        for (int i = 0; i <= m; i++) {
            Bx[i] = b[i];
        }
        fft(Ax, Ay, N, 1);
        fft(Bx, By, N, 1);
        for (int i = 0; i < N; i++) {
            x = Ax[i] * Bx[i] - Ay[i] * By[i];
            y = Ax[i] * By[i] + Ay[i] * Bx[i];
            Ax[i] = x;
            Ay[i] = y;
        }
        fft(Ax, Ay, N, -1);
        int[] ans = new int[n + m + 1];
        for (int i = 0; i <= n + m; i++) {
            ans[i] = (int) (Ax[i] / N + 0.5);
        }
        return ans;
    }

    private void fft(double[] Ax, double[] Ay, int n, int op) {
        for (int i = 0; i < n; i++) {
            if (i < r[i]) {
                t = Ax[i];
                Ax[i] = Ax[r[i]];
                Ax[r[i]] = t;
                t = Ay[i];
                Ay[i] = Ay[r[i]];
                Ay[r[i]] = t;
            }
        }
        for (int m = 2; m <= n; m <<= 1) {
            w1x = Math.cos(Math.PI * 2 / m);
            w1y = Math.sin(Math.PI * 2 / m) * op;
            for (int k = 0; k < n; k += m) {
                wkx = 1;
                wky = 0;
                for (int i = k, j = k + m / 2; i < k + m / 2; i++, j++) {
                    ax = Ax[i];
                    ay = Ay[i];
                    bx = Ax[j] * wkx - Ay[j] * wky;
                    by = Ax[j] * wky + Ay[j] * wkx;
                    Ax[i] = ax + bx;
                    Ay[i] = ay + by;
                    Ax[j] = ax - bx;
                    Ay[j] = ay - by;
                    x = w1x * wkx - w1y * wky;
                    y = w1x * wky + w1y * wkx;
                    wkx = x;
                    wky = y;
                }
            }
        }
    }
}
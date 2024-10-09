package algorithm.string.AhoCorasick;

import java.util.*;
/**
 * https://www.luogu.com.cn/problem/P3311
 * 新增last指针，指向上一个模式串的终点，且该模式串为当前节点的后缀
 */
public class AC_setLast {                                                                                                                                                                                             static class FastReader{java.io.InputStream is=System.in;byte[]inbuf=new byte[1024];char[]str=new char[16];int lenbuf,ptrbuf,b;int readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=(char)b;b=readByte();}return new String(str,0,i);}int nextInt(){return (int)nextLong();}long nextLong(){while((b=readByte())<33);boolean minus=b=='-';long num=minus?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return minus?-num:num;}double nextDouble(){return Double.parseDouble(next());}}static class FastWriter{java.io.OutputStream out=System.out;int tr=0,BUF_SIZE=8192;byte[]buf=new byte[BUF_SIZE];int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}FastWriter write(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}FastWriter write(char c){return write((byte)c);}FastWriter write(int x){if(x==Integer.MIN_VALUE){return write((long)x);}if(tr+12>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(long x){if(x==Long.MIN_VALUE){return write(""+x);}if(tr+21>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(double x,int precision){if(x<0){write('-');x=-x;}x+=Math.pow(10,-precision)/2;write((long)x).write(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;write((char)('0'+(int)x));x-=(int)x;}return this;}FastWriter write(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.charAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void print(char c){write(c);}void print(String s){write(s);}void print(int x){write(x);}void print(long x){write(x);}void print(double x,int precision){write(x,precision);}void writeln(){write((byte)'\n');}void println(char c){write(c).writeln();}void println(int x){write(x).writeln();}void println(long x){write(x).writeln();}void println(double x,int precision){write(x,precision).writeln();}void println(String s){write(s).writeln();}void println(Object obj){write(obj.toString()).writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){}}void flush(){innerflush();try{out.flush();}catch(Exception e){}}}static FastReader sc=new FastReader();static FastWriter out=new FastWriter();static int min(int a,int b){return a>b?b:a;}static int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}static long min(long a,long b){return a>b?b:a;}static long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}static double min(double a,double b){return a>b?b:a;}static double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}static int max(int a,int b){return a<b?b:a;}static int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}static long max(long a,long b){return a<b?b:a;}static long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}static double max(double a,double b){return a<b?b:a;}static double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}static int abs(int a){return a<0?-a:a;}static long abs(long a){return a<0?-a:a;}static double abs(double a){return a<0?-a:a;}static long sqrt(long a){long v=(long)Math.sqrt(a);return v*v>a?v-1:(v+1)*(v+1)<=a?v+1:v;}static int sqrt(int a){int v=(int)Math.sqrt(a);return v*v>a?v-1:(v+1)*(v+1)<=a?v+1:v;}static long pow(long a,long b,long m){long res=1;a%=m;while(b>0){if((b&1)!=0)res=res*a%m;a=a*a%m;b>>=1;}return res;}static int gcd(int a,int b){return b==0?a:gcd(b,a%b);}static long gcd(long a,long b){return b==0?a:gcd(b,a%b);}static long lcm(long a,long b){return a/gcd(a,b)*b;}
    public static void main(String[] args) {/* int t = sc.nextInt(); while (t-- > 0) */ solve(); out.flush();}

    static int MOD = (int) 1e9 + 7;

    static void solve() {
        char[] str = sc.next().toCharArray();
        int n = str.length;
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = str[i] - '0';
        }
        AhoCorasick ac = new AhoCorasick(1500);
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            ac.insert(sc.next());
        }
        ac.setFail();
        m = ac.no;
        long[][][][] dp = new long[n + 1][2][2][m + 1];
        for (int i = 0; i <= m; i++) {
            dp[n][1][0][i] = dp[n][1][1][i] = 1;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int u = 0; u <= m; u++) {
                        if (j == 0) {
                            dp[i][j][k][u] += dp[i + 1][j][1][u];
                        }
                        for (int v = j == 0 ? 1 : 0; v <= (k == 0 ? num[i] : 9); v++) {
                            int p = ac.nxt[u][v];
                            if (!ac.end[p] && ac.last[p] == 0) {
                                dp[i][j][k][u] += dp[i + 1][1][k == 0 && v == num[i] ? 0 : 1][p];
                            }
                        }
                        dp[i][j][k][u] %= MOD;
                    }
                }
            }
        }
        out.println(dp[0][0][0][0]);
    }

    static class AhoCorasick {
        public int[][] nxt;
        public int[] fail;
        public int[] last;
        public boolean[] end;
        public int no;

        public AhoCorasick(int tot) {
            tot++;
            nxt = new int[tot][10];
            fail = new int[tot];
            last = new int[tot];
            end = new boolean[tot];
        }

        public void insert(String s) {
            int u = 0;
            for (char c : s.toCharArray()) {
                c -= '0';
                if (nxt[u][c] == 0) {
                    nxt[u][c] = ++no;
                }
                u = nxt[u][c];
            }
            end[u] = true;
        }

        public void setFail() {
            Deque<Integer> queue = new ArrayDeque<>();
            for (int i = 0; i < 10; i++) {
                if (nxt[0][i] != 0) {
                    queue.addLast(nxt[0][i]);
                }
            }
            while (!queue.isEmpty()) {
                int u = queue.removeFirst();
                for (int i = 0; i < 10; i++) {
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

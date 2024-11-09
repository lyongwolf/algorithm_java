package algorithm.graph.classic;

import java.util.*;

/**
 * 图中判断负环
 * 测试链接：https://www.luogu.com.cn/problem/P5960
 */
public class spfa {                                                                                                                                                                                             static class FastReader{java.io.InputStream is=System.in;byte[]inbuf=new byte[1024];char[]str=new char[16];int lenbuf,ptrbuf,b;int readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=(char)b;b=readByte();}return new String(str,0,i);}int nextInt(){return (int)nextLong();}long nextLong(){while((b=readByte())<33);boolean minus=b=='-';long num=minus?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return minus?-num:num;}double nextDouble(){return Double.parseDouble(next());}}static class FastWriter{java.io.OutputStream out=System.out;int tr=0,BUF_SIZE=8192;byte[]buf=new byte[BUF_SIZE];int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}FastWriter write(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}FastWriter write(char c){return write((byte)c);}FastWriter write(int x){if(x==Integer.MIN_VALUE){return write((long)x);}if(tr+12>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(long x){if(x==Long.MIN_VALUE){return write(""+x);}if(tr+21>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(double x,int precision){if(x<0){write('-');x=-x;}x+=Math.pow(10,-precision)/2;write((long)x).write(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;write((char)('0'+(int)x));x-=(int)x;}return this;}FastWriter write(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.charAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void print(char c){write(c);}void print(String s){write(s);}void print(int x){write(x);}void print(long x){write(x);}void print(double x,int precision){write(x,precision);}void writeln(){write((byte)'\n');}void println(char c){write(c).writeln();}void println(int x){write(x).writeln();}void println(long x){write(x).writeln();}void println(double x,int precision){write(x,precision).writeln();}void println(String s){write(s).writeln();}void println(Object obj){write(obj.toString()).writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){}}void flush(){innerflush();try{out.flush();}catch(Exception e){}}}static FastReader sc=new FastReader();static FastWriter out=new FastWriter();static int min(int a,int b){return a>b?b:a;}static int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}static long min(long a,long b){return a>b?b:a;}static long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}static double min(double a,double b){return a>b?b:a;}static double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}static int max(int a,int b){return a<b?b:a;}static int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}static long max(long a,long b){return a<b?b:a;}static long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}static double max(double a,double b){return a<b?b:a;}static double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}static int abs(int a){return a<0?-a:a;}static long abs(long a){return a<0?-a:a;}static double abs(double a){return a<0?-a:a;}
    public static void main(String[] args) {/* int t = sc.nextInt(); while (t-- > 0) */ solve(); out.flush();}      

    static int n;
    static int[] head, nxt, to, wt;
    static boolean[] vis;
    static int[] dis;

    static void solve() {
        n = sc.nextInt();
        int m = sc.nextInt();
        head = new int[n + 1];
        nxt = new int[m + 1];
        to = new int[m + 1];
        wt = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            int v = sc.nextInt(), u = sc.nextInt(), w = sc.nextInt();
            nxt[i] = head[u];
            head[u] = i;
            wt[i] = w;
            to[i] = v;
        }

        // spfa
        
        vis = new boolean[n + 1];
        dis = new int[n + 1];
        // for (int i = 1; i <= n; i++) {
        //     if (dfs(i)) {
        //         out.println("NO");
        //         return;
        //     }
        // }
        if (bfs()) {
            out.println("NO");
            return;
        }
        for (int i = 1; i <= n; i++) {
            out.print(dis[i] + " ");
        }
        out.writeln();
    }

    static boolean dfs(int u) {
        vis[u] = true;
        for (int e = head[u], v; e != 0; e = nxt[e]) {
            v = to[e];
            if (dis[v] > dis[u] + wt[e]) {
                dis[v] = dis[u] + wt[e];
                if (vis[v] || dfs(v)) {
                    return true;
                }
            }
        }
        vis[u] = false;
        return false;
    }

    static boolean bfs() {
        int[] cnt = new int[n + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            queue.addLast(i);
            vis[i] = true;
        }
        while (!queue.isEmpty()) {
            int u = queue.removeFirst();
            vis[u] = false;
            for (int e = head[u], v; e != 0; e = nxt[e]) {
                v = to[e];
                if (dis[v] > dis[u] + wt[e]) {
                    dis[v] = dis[u] + wt[e];
                    if (++cnt[v] == n) {
                        return true;
                    }
                    if (!vis[v]) {
                        queue.addLast(v);
                        vis[v] = true;
                    }
                }
            }
        }
        return false;
    }

}
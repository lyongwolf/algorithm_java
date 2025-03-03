package algorithm.graph.Heavy_Light_Decomposition;

import java.util.*;

/**
 * 树链剖分
 * test link : https://codeforces.com/problemset/problem/343/D
 */

public class HLD extends U {

    int n, root;
    int[] head, nxt, to;
    int[] fa, dep, sz, son;
    int[] top, dfn, rk;
    int no;
    long[] sum, lazy;

    void solve() {
        n = sc.nextInt();
        root = 1;
        head = new int[n + 1];
        nxt = new int[n << 1];
        to = new int[n << 1];
        for (int i = 1, j = 2; i < n; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        fa = new int[n + 1];
        dep = new int[n + 1];
        sz = new int[n + 1];
        son = new int[n + 1];
        init(0, root, 0);
        top = new int[n + 1];
        dfn = new int[n + 1];
        rk = new int[n + 1];
        no = 0;
        init2(0, root, root);
        int tot = 1 << (33 - Integer.numberOfLeadingZeros(n - 1));
        sum = new long[tot];
        lazy = new long[tot];
        Arrays.fill(lazy, -1);
        for (int q = sc.nextInt(), c, v; q > 0; q--) {
            c = sc.nextInt();
            v = sc.nextInt();
            switch (c) {
                case 1 -> {
                    set(dfn[v], dfn[v] + sz[v] - 1, 1, 1, n, 1);
                }
                case 2 -> {
                    set(v, root, 0);
                }
                default -> {
                    out.println(query(dfn[v], dfn[v], 1, n, 1));
                }
            }
        }
    }

    void maintain(int i, int n, long v) {
        lazy[i] = v;
        sum[i] = v * n;
    }

    void down(int i, int ln, int rn) {
        if (lazy[i] != -1) {
            maintain(i << 1, ln, lazy[i]);
            maintain(i << 1 | 1, rn, lazy[i]);
            lazy[i] = -1;
        }
    }

    void up(int i) {
        sum[i] = sum[i << 1] + sum[i << 1 | 1];
    }

    void set(int u, int v, long x) {
        while (top[u] != top[v]) {
            if (dep[top[u]] < dep[top[v]]) {
                int t = u; u = v; v = t;
            }
            set(dfn[top[u]], dfn[u], x, 1, n, 1);
            u = fa[top[u]];
        }
        if (u == 0) {
            return;
        }
        if (dep[u] > dep[v]) {
            int t = u; u = v; v = t;
        }
        set(dfn[u], dfn[v], x, 1, n, 1);
    }

    void set(int L, int R, long v, int l, int r, int i) {
        if (L <= l && r <= R) {
            maintain(i, r - l + 1, v);
            return;
        }
        int m = (l + r) >> 1;
        down(i, m - l + 1, r - m);
        if (L <= m) {
            set(L, R, v, l, m, i << 1);
        }
        if (R > m) {
            set(L, R, v, m + 1, r, i << 1 | 1);
        }
        up(i);
    }

    long query(int u, int v) {
        long ans = 0;
        while (top[u] != top[v]) {
            if (dep[top[u]] < dep[top[v]]) {
                int t = u; u = v; v = t;
            }
            ans += query(dfn[top[u]], dfn[u], 1, n, 1);
            u = fa[top[u]];
        }
        if (dep[u] > dep[v]) {
            int t = u; u = v; v = t;
        }
        ans += query(dfn[u], dfn[v], 1, n, 1);
        return ans;
    }

    long query(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return sum[i];
        }
        int m = (l + r) >> 1;
        down(i, m - l + 1, r - m);
        long ans = 0;
        if (L <= m) {
            ans += query(L, R, l, m, i << 1);
        }
        if (R > m) {
            ans += query(L, R, m + 1, r, i << 1 | 1);
        }
        return ans;
    }

    void init2(int f, int u, int t) {
        top[u] = t;
        dfn[u] = ++no;
        rk[no] = u;
        if (son[u] != 0) {
            init2(u, son[u], t);
        }
        for (int e = head[u], v; e != 0; e = nxt[e]) {
            if ((v = to[e]) != f && v != son[u]) {
                init2(u, v, v);
            }
        }
    }

    void init(int f, int u, int d) {
        fa[u] = f;
        dep[u] = d;
        sz[u] = 1;
        for (int e = head[u], v; e != 0; e = nxt[e]) {
            if ((v = to[e]) != f) {
                init(u, v, d + 1);
                sz[u] += sz[v];
                if (sz[son[u]] < sz[v]) {
                    son[u] = v;
                }
            }
        }
    }


    public static void main(String[] args) {
        HLD o = new HLD();
        // int t = o.sc.nextInt(); while (t-- > 0)
        o.solve();
        o.out.flush();
    }
}

class U {                                                                                                                                                                                                       FastReader sc=new FastReader();FastWriter out=new FastWriter();class FastReader{private java.io.InputStream is=System.in;private byte[] inbuf=new byte[8192];private byte[] str=new byte[16];private byte b;private int lenbuf,ptrbuf;private byte readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){e.printStackTrace();}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=b;b=readByte();}return new String(str,0,i);}int nextInt(){return(int)nextLong();}long nextLong(){while((b=readByte())<33);boolean neg=b=='-';long num=neg?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return neg?-num:num;}double nextDouble(){double num=0,div=1;while((b=readByte())<33);boolean neg=false;if(b=='-'){neg=true;b=readByte();}while(b>32&&b!='.'){num=num*10+(b-'0');b=readByte();}if(b=='.'){b=readByte();while(b>32){num+=(b-'0')/(div*=10);b=readByte();}}return neg?-num:num;}}class FastWriter{private java.io.OutputStream out=System.out;private int tr=0,BUF_SIZE=8192;private byte[] buf=new byte[BUF_SIZE];private int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}private int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}FastWriter print(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}FastWriter print(char c){return print((byte)c);}FastWriter print(int x){if(x==Integer.MIN_VALUE){return print((long)x);}if(tr+12>=BUF_SIZE)innerflush();if(x<0){print((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter print(long x){if(x==Long.MIN_VALUE){return print(""+x);}if(tr+21>=BUF_SIZE)innerflush();if(x<0){print((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter print(double x,int precision){if(x<0){print('-');x=-x;}x+=Math.pow(10,-precision)/2;print((long)x).print(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;print((char)('0'+(int)x));x-=(int)x;}return this;}FastWriter print(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.codePointAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void writeln(){print((byte)'\n');}void println(byte b){print(b).writeln();}void println(char c){print(c).writeln();}void println(int x){print(x).writeln();}void println(long x){print(x).writeln();}void println(double x,int precision){print(x,precision).writeln();}void println(String s){print(s).writeln();}void println(Object o){deepPrint(o,true);}private void deepPrint(Object o,boolean f){if(o==null){print(f?"null\n":"null");return;}Class<?>c=o.getClass();if(c.isArray()){if(o instanceof Object[]){Object[]t=(Object[])o;int n=t.length;boolean b=f&&n>0&&t[0]!=null;print(b?"[\n":"[");for(int i=0;i<n;i++){deepPrint(t[i],false);if(i<n-1)print(b?", \n":", ");else if(b)writeln();}print("]");}else{print(o instanceof byte[]?Arrays.toString((byte[])o):o instanceof short[]?Arrays.toString((short[])o):o instanceof int[]?Arrays.toString((int[])o):o instanceof long[]?Arrays.toString((long[])o):o instanceof char[]?Arrays.toString((char[])o):o instanceof float[]?Arrays.toString((float[])o):o instanceof double[]?Arrays.toString((double[])o):Arrays.toString((boolean[])o));}}else if(o instanceof Collection){Collection<?>t=(Collection<?>)o;print("[");int i=0;for(Object v:t){deepPrint(v,false);if(i<t.size()-1)print(", ");i++;}print("]");}else if(o instanceof Map){Map<?,?>t=(Map<?,?>)o;print(!t.isEmpty()&&f?"{\n":"{");int i=0;for(Map.Entry<?,?>v:t.entrySet()){deepPrint(v.getKey(),false);print(" = ");deepPrint(v.getValue(),false);if(++i<t.size())print(f?", \n":", ");else if(f)writeln();}print("}");}else{print(o.toString());}if(f)writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){e.printStackTrace();}}void flush(){innerflush();try{out.flush();}catch(Exception e){e.printStackTrace();}}}int min(int a, int b){return a>b?b:a;}int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}long min(long a, long b){return a>b?b:a;}long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}double min(double a, double b){return a>b?b:a;}double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}int max(int a, int b){return a<b?b:a;}int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}long max(long a, long b){return a<b?b:a;}long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}double max(double a, double b){return a<b?b:a;}double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}int abs(int a){return a<0?-a:a;}long abs(long a){return a<0?-a:a;}double abs(double a){return a<0?-a:a;}
}
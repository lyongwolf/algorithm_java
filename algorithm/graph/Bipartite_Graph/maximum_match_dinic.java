package algorithm.graph.Bipartite_Graph;

import java.util.*;

/**
 * 测试链接：https://www.luogu.com.cn/problem/P3386
 * 二分图最大匹配（dinic algorithm）
 */
public class maximum_match_dinic  extends U {

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt(), e = sc.nextInt();
        MF mf = new MF(n, m, e);
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            mf.addBiEdge(u, v);
        }
        out.println(mf.maxFlow());
    }

    public static void main(String[] args) {
        maximum_match_dinic o = new maximum_match_dinic();
        // int t = o.sc.nextInt(); while (t-- > 0)
        o.solve();
        o.out.flush();
    }
}

class U {                                                                                                                                                                                                 FastReader sc=new FastReader();FastWriter out=new FastWriter();class FastReader{private java.io.InputStream is=System.in;private byte[] inbuf=new byte[1024];private char[] str=new char[16];private int lenbuf,ptrbuf,b;int readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=(char)b;b=readByte();}return new String(str,0,i);}int nextInt(){return(int)nextLong();}long nextLong(){while((b=readByte())<33);boolean minus=b=='-';long num=minus?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return minus?-num:num;}double nextDouble(){return Double.parseDouble(next());}}class FastWriter{private java.io.OutputStream out=System.out;private int tr=0,BUF_SIZE=8192;private byte[] buf=new byte[BUF_SIZE];private int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}private int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}private FastWriter write(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}private FastWriter write(char c){return write((byte)c);}private FastWriter write(int x){if(x==Integer.MIN_VALUE){return write((long)x);}if(tr+12>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}private FastWriter write(long x){if(x==Long.MIN_VALUE){return write(""+x);}if(tr+21>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}private FastWriter write(double x,int precision){if(x<0){write('-');x=-x;}x+=Math.pow(10,-precision)/2;write((long)x).write(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;write((char)('0'+(int)x));x-=(int)x;}return this;}private FastWriter write(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.charAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void print(char c){write(c);}void print(String s){write(s);}void print(int x){write(x);}void print(long x){write(x);}void print(double x,int precision){write(x,precision);}void writeln(){write((byte)'\n');}void println(char c){write(c).writeln();}void println(int x){write(x).writeln();}void println(long x){write(x).writeln();}void println(double x,int precision){write(x,precision).writeln();}void println(String s){write(s).writeln();}void println(Object o){deepPrint(o,true);}private void deepPrint(Object o,boolean f){if(o==null){write(f?"null\n":"null");return;}Class<?>c=o.getClass();if(c.isArray()){if(o instanceof Object[]){Object[]t=(Object[])o;int n=t.length;boolean b=f&&n>0&&t[0]!=null&&(t[0].getClass().isArray()||t[0]instanceof Collection||t[0]instanceof Map);write(b?"[\n":"[");for(int i=0;i<n;i++){deepPrint(t[i],false);if(i<n-1)write(b?", \n":", ");else if(b)writeln();}write("]");}else{write(o instanceof byte[]?Arrays.toString((byte[])o):o instanceof short[]?Arrays.toString((short[])o):o instanceof int[]?Arrays.toString((int[])o):o instanceof long[]?Arrays.toString((long[])o):o instanceof char[]?Arrays.toString((char[])o):o instanceof float[]?Arrays.toString((float[])o):o instanceof double[]?Arrays.toString((double[])o):Arrays.toString((boolean[])o));}}else if(o instanceof Collection){Collection<?>t=(Collection<?>)o;write("[");int i=0;for(Object v:t){deepPrint(v,false);if(i<t.size()-1)write(", ");i++;}write("]");}else if(o instanceof Map){Map<?,?>t=(Map<?,?>)o;write(!t.isEmpty()&&f?"{\n":"{");int i=0;for(Map.Entry<?,?>v:t.entrySet()){deepPrint(v.getKey(),false);write(" = ");deepPrint(v.getValue(),false);if(++i<t.size())write(f?", \n":", ");else if(f)writeln();}write("}");}else{write(o.toString());}if(f)writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){}}void flush(){innerflush();try{out.flush();}catch(Exception e){}}}int min(int a,int b){return a>b?b:a;}int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}long min(long a,long b){return a>b?b:a;}long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}double min(double a,double b){return a>b?b:a;}double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}int max(int a,int b){return a<b?b:a;}int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}long max(long a,long b){return a<b?b:a;}long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}double max(double a,double b){return a<b?b:a;}double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}int abs(int a){return a<0?-a:a;}long abs(long a){return a<0?-a:a;}double abs(double a){return a<0?-a:a;}
}

class MF {
    private final int INF = 0x3f3f3f3f;
    private int n, m, s, t, z;
    private int[] head, nxt, to, cap, es, dep, que;

    public MF(int n, int m, int e) {
        this.n = n;
        this.m = m;
        s = n + m + 1;
        t = s + 1;
        z = 2;
        head = new int[n + m + 3];
        int tot = (n + m + e + 1) << 1;
        nxt = new int[tot];
        to = new int[tot];
        cap = new int[tot];
        for (int i = 1; i <= n; i++) {
            addEdge(s, i, 1);
            addEdge(i, s, 0);
        }
        for (int i = 1 + n; i <= m + n; i++) {
            addEdge(i, t, 1);
            addEdge(t, i, 0);
        }
        es = new int[n + m + 3];
        dep = new int[n + m + 3];
        que = new int[n + m + 2];
    }

    public void addBiEdge(int u, int v) {
        v += n;
        addEdge(u, v, 1);
        addEdge(v, u, 0);
    }

    private void addEdge(int u, int v, int w) {
        nxt[z] = head[u];
        head[u] = z;
        cap[z] = w;
        to[z++] = v;
    }

    public int maxFlow() {
        int ans = 0;
        while (bfs()) {
            ans += flow(s, INF);
        }
        return ans;
    }

    public int[] match() {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int e = head[i]; e != 0; e = nxt[e]) {
                if (cap[e] == 0) {
                    ans[i] = to[e] - n;
                    break;
                }
            }
        }
        return ans;
    }

    private int flow(int u, int r) {
        if (u == t) {
            return r;
        }
        int ans = 0;
        int f;
        for (int e = es[u], v; e != 0 && r > 0; es[u] = e, e = nxt[e]) {
            v = to[e];
            if (cap[e] > 0 && dep[v] == dep[u] + 1) {
                f = flow(v, Math.min(r, cap[e]));
                if (f == 0) {
                    dep[v] = 0;
                }
                r -= f;
                cap[e] -= f;
                cap[e ^ 1] += f;
                ans += f;
            }
        }
        return ans;
    }

    private boolean bfs() {
        Arrays.fill(dep, 0);
        que[0] = s;
        dep[s] = 1;
        es[s] = head[s];
        for (int l = 0, r = 0; l <= r;) {
            int u = que[l++];
            for (int e = head[u], v; e != 0; e = nxt[e]) {
                v = to[e];
                if (dep[v] == 0 && cap[e] > 0) {
                    es[v] = head[v];
                    dep[v] = dep[u] + 1;
                    if (v == t) {
                        return true;
                    }
                    que[++r] = v;
                }
            }
        }
        return false;
    }
}
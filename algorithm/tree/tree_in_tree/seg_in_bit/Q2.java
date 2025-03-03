package algorithm.tree.tree_in_tree.seg_in_bit;

import java.util.*;

/**
 * 动态维护树上路径第 k 小
 * 测试连接：https://www.luogu.com.cn/problem/P4175
 */

public class Q2 extends U {

    SegInBit tree = new SegInBit(80001, 18000000);

    int[] head, nxt, to, w, dep, pa[], sz, dfn;

    void solve() {
        int n = sc.nextInt(), q = sc.nextInt();
        head = new int[n + 1]; nxt = new int[n << 1]; to = new int[n << 1]; w = new int[n + 1]; dep = new int[n + 1]; pa = new int[n + 1][17]; sz = new int[n + 1]; dfn = new int[n + 1];
        int[] val = new int[n + q];
        int z = 0;
        for (int i = 1; i <= n; i++) {
            w[i] = sc.nextInt();
            val[z++] = w[i];
        }
        for (int i = 1, j = 2; i < n; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
        }
        int[][] query = new int[q][3];
        for (int i = 0; i < q; i++) {
            query[i][0] = sc.nextInt();
            query[i][1] = sc.nextInt();
            query[i][2] = sc.nextInt();
            if (query[i][0] == 0) {
                val[z++] = query[i][2];
            }
        }
        Arrays.sort(val, 0, z);
        int m = 1;
        for (int i = 1; i < z; i++) {
            if (val[i - 1] != val[i]) {
                val[m++] = val[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            w[i] = Arrays.binarySearch(val, 0, m, w[i]);
        }
        tree.init(n + 1, 0, m);
        init(0, 1);
        for (int[] t : query) {
            int k = t[0], u = t[1], v = t[2];
            if (k == 0) {
                v = Arrays.binarySearch(val, 0, m, v);
                tree.remove(dfn[u], w[u]);
                tree.add(dfn[u] + sz[u], w[u]);
                w[u] = v;
                tree.add(dfn[u], v);
                tree.remove(dfn[u] + sz[u], v);
            } else {
                int a = lca(u, v), f = pa[a][0], tot = dep[u] + dep[v] - dep[a] - dep[f];
                if (tot < k) {
                    out.println("invalid request!");
                } else {
                    out.println(val[tree.query(dfn[u], dfn[v], dfn[a], dfn[f], tot - k + 1)]);
                }
            }
        }
    }

    int lca(int u, int v) {
        if (dep[u] < dep[v]) {
            u ^= v ^ (v = u);
        }
        for (int i = 0, d = dep[u] - dep[v]; i < 17; i++) {
            if ((d >> i & 1) != 0) {
                u = pa[u][i];
            }
        }
        if (u == v) {
            return u;
        }
        for (int i = 16; i >= 0; i--) {
            if (pa[u][i] != pa[v][i]) {
                u = pa[u][i];
                v = pa[v][i];
            }
        }
        return pa[u][0];
    }

    void init(int f, int u) {
        int[] stk = new int[head.length];
        int z = 1, di = 0;
        stk[z] = u;
        dep[u] = 1;
        while (z > 0) {
            u = stk[z];
            f = pa[u][0];
            if (dfn[u] == 0) {
                dfn[u] = ++di;
                for (int e = head[u], v; e != 0; e = nxt[e]) {
                    if (f != (v = to[e])) {
                        stk[++z] = v;
                        pa[v][0] = u;
                        dep[v] = dep[u] + 1;
                        pa[v][0] = u;
                        for (int i = 1; i < 17; i++) {
                            pa[v][i] = pa[pa[v][i - 1]][i - 1];
                        }
                    }
                }
            } else {
                z--;
                sz[u] = 1;
                for (int e = head[u], v; e != 0; e = nxt[e]) {
                    if (f != (v = to[e])) {
                        sz[u] += sz[v];
                    }
                }
                tree.add(dfn[u], w[u]);
                tree.remove(dfn[u] + sz[u], w[u]);
            }
        }
    }

    public static void main(String[] args) {
        Q2 o = new Q2();
        // int t = o.sc.nextInt(); while (t-- > 0)
        o.solve();
        o.out.flush();
    }
}

class U {                                                                                                                                                                                                       FastReader sc=new FastReader();FastWriter out=new FastWriter();class FastReader{private java.io.InputStream is=System.in;private byte[] inbuf=new byte[8192];private byte[] str=new byte[16];private byte b;private int lenbuf,ptrbuf;private byte readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){e.printStackTrace();}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=b;b=readByte();}return new String(str,0,i);}int nextInt(){return(int)nextLong();}long nextLong(){while((b=readByte())<33);boolean neg=b=='-';long num=neg?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return neg?-num:num;}double nextDouble(){double num=0,div=1;while((b=readByte())<33);boolean neg=false;if(b=='-'){neg=true;b=readByte();}while(b>32&&b!='.'){num=num*10+(b-'0');b=readByte();}if(b=='.'){b=readByte();while(b>32){num+=(b-'0')/(div*=10);b=readByte();}}return neg?-num:num;}}class FastWriter{private java.io.OutputStream out=System.out;private int tr=0,BUF_SIZE=8192;private byte[] buf=new byte[BUF_SIZE];private int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}private int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}FastWriter print(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}FastWriter print(char c){return print((byte)c);}FastWriter print(int x){if(x==Integer.MIN_VALUE){return print((long)x);}if(tr+12>=BUF_SIZE)innerflush();if(x<0){print((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter print(long x){if(x==Long.MIN_VALUE){return print(""+x);}if(tr+21>=BUF_SIZE)innerflush();if(x<0){print((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter print(double x,int precision){if(x<0){print('-');x=-x;}x+=Math.pow(10,-precision)/2;print((long)x).print(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;print((char)('0'+(int)x));x-=(int)x;}return this;}FastWriter print(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.codePointAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void writeln(){print((byte)'\n');}void println(byte b){print(b).writeln();}void println(char c){print(c).writeln();}void println(int x){print(x).writeln();}void println(long x){print(x).writeln();}void println(double x,int precision){print(x,precision).writeln();}void println(String s){print(s).writeln();}void println(Object o){deepPrint(o,true);}private void deepPrint(Object o,boolean f){if(o==null){print(f?"null\n":"null");return;}Class<?>c=o.getClass();if(c.isArray()){if(o instanceof Object[]){Object[]t=(Object[])o;int n=t.length;boolean b=f&&n>0&&t[0]!=null;print(b?"[\n":"[");for(int i=0;i<n;i++){deepPrint(t[i],false);if(i<n-1)print(b?", \n":", ");else if(b)writeln();}print("]");}else{print(o instanceof byte[]?Arrays.toString((byte[])o):o instanceof short[]?Arrays.toString((short[])o):o instanceof int[]?Arrays.toString((int[])o):o instanceof long[]?Arrays.toString((long[])o):o instanceof char[]?Arrays.toString((char[])o):o instanceof float[]?Arrays.toString((float[])o):o instanceof double[]?Arrays.toString((double[])o):Arrays.toString((boolean[])o));}}else if(o instanceof Collection){Collection<?>t=(Collection<?>)o;print("[");int i=0;for(Object v:t){deepPrint(v,false);if(i<t.size()-1)print(", ");i++;}print("]");}else if(o instanceof Map){Map<?,?>t=(Map<?,?>)o;print(!t.isEmpty()&&f?"{\n":"{");int i=0;for(Map.Entry<?,?>v:t.entrySet()){deepPrint(v.getKey(),false);print(" = ");deepPrint(v.getValue(),false);if(++i<t.size())print(f?", \n":", ");else if(f)writeln();}print("}");}else{print(o.toString());}if(f)writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){e.printStackTrace();}}void flush(){innerflush();try{out.flush();}catch(Exception e){e.printStackTrace();}}}int min(int a, int b){return a>b?b:a;}int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}long min(long a, long b){return a>b?b:a;}long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}double min(double a, double b){return a>b?b:a;}double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}int max(int a, int b){return a<b?b:a;}int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}long max(long a, long b){return a<b?b:a;}long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}double max(double a, double b){return a<b?b:a;}double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}int abs(int a){return a<0?-a:a;}long abs(long a){return a<0?-a:a;}double abs(double a){return a<0?-a:a;}
}

class SegInBit {
    private int N, ilow, ihigh, no;
    private int[] root;
    
    // 动态开点线段树部分
    private int[] cnt, lc, rc;

    // 整体查询加速部分
    private int full[] = new int[128], rest[] = new int[128], fi, ri;

    public SegInBit(int MAXN, int tot) {
        root = new int[MAXN + 1];

        cnt = new int[tot];
        lc = new int[tot];
        rc = new int[tot];
    }

    public void init(int N, int ilow, int ihigh) {
        this.N = N;
        this.ilow = ilow;
        this.ihigh = ihigh;
        for (int i = 1; i <= no; i++) {
            cnt[i] = lc[i] = rc[i] = 0;
        }
        for (int i = 1; i <= N; i++) {
            root[i] = i;
        }
        no = N;
    }

    // 适用于题目的特殊查询
    public int query(int u, int v, int a, int f, int rk) {
        fi = ri = 0;
        while (u > 0) {
            full[fi++] = root[u];
            u -= u & -u;
        }
        while (v > 0) {
            full[fi++] = root[v];
            v -= v & -v;
        }
        while (a > 0) {
            rest[ri++] = root[a];
            a -= a & -a;
        }
        while (f > 0) {
            rest[ri++] = root[f];
            f -= f & -f;
        }
        return irankKey(rk, ilow, ihigh);
    }

    // 在 o 位置插入值 v
    public void add(int o, int v) {
        while (o <= N) {
            iadd(v, 1, ilow, ihigh, root[o]);
            o += o & -o;
        }
    }

    // 删除 o 位置的 v
    public void remove(int o, int v) {
        while (o <= N) {
            iadd(v, -1, ilow, ihigh, root[o]);
            o += o & -o;
        }
    } 

    private void iadd(int o, int v, int l, int r, int i) {
        if (l == r) {
            cnt[i] += v;
            return;
        }
        idown(i);
        int m = (l + r) / 2;
        if (o <= m) {
            iadd(o, v, l, m, lc[i]);
        } else {
            iadd(o, v, m + 1, r, rc[i]);
        }
        iup(i);
    }

    // 查询 k 在 [l, r] 中的排名
    public int rank(int l, int r, int k) {
        if (l > r) {
            return 1;
        }
        begin(l, r);
        return ismallCount(k, ilow, ihigh) + 1;
    }

    private int ismallCount(int o, int l, int r) {
        if (l == r) {
            return 0;
        }
        for (int i = 0; i < fi; i++) {
            idown(full[i]);
        }
        for (int i = 0; i < ri; i++) {
            idown(rest[i]);
        }
        int m = (l + r) / 2;
        if (o <= m) {
            for (int i = 0; i < fi; i++) {
                full[i] = lc[full[i]];
            }
            for (int i = 0; i < ri; i++) {
                rest[i] = lc[rest[i]];
            }
            return ismallCount(o, l, m);
        } else {
            int ans = 0;
            for (int i = 0; i < fi; i++) {
                ans += cnt[lc[full[i]]];
                full[i] = rc[full[i]];
            }
            for (int i = 0; i < ri; i++) {
                ans -= cnt[lc[rest[i]]];
                rest[i] = rc[rest[i]];
            }
            return ans + ismallCount(o, m + 1, r);
        }
    }

    // 查询区间 [L, R] 中排名第 rk 小的值
    public int rankKey(int l, int r, int rk) {
        begin(l, r);
        return irankKey(rk, ilow, ihigh);
    }

    private int irankKey(int rk, int l, int r) {
        if (l == r) {
            return l;
        }
        for (int i = 0; i < fi; i++) {
            idown(full[i]);
        }
        for (int i = 0; i < ri; i++) {
            idown(rest[i]);
        }
        int m = (l + r) / 2;
        int c = 0;
        for (int i = 0; i < fi; i++) {
            c += cnt[lc[full[i]]];
        }
        for (int i = 0; i < ri; i++) {
            c -= cnt[lc[rest[i]]];
        }
        if (c >= rk) {
            for (int i = 0; i < fi; i++) {
                full[i] = lc[full[i]];
            }
            for (int i = 0; i < ri; i++) {
                rest[i] = lc[rest[i]];
            }
            return irankKey(rk, l, m);
        } else {
            for (int i = 0; i < fi; i++) {
                full[i] = rc[full[i]];
            }
            for (int i = 0; i < ri; i++) {
                rest[i] = rc[rest[i]];
            }
            return irankKey(rk - c, m + 1, r);
        }
    }

    // 查询区间 [l, r] 中严格小于 v ，且最大的数，若不存在返回 Integer.MIN_VALUE
    public int floor(int l, int r, int v) {
        int rk = rank(l, r, v);
        return rk == 1 ? Integer.MIN_VALUE : rankKey(l, r, rk - 1);
    }

    // 查询区间 [l, r] 中严格大于 v ，且最小的数，若不存在返回 Integer.MAX_VALUE
    public int ceiling(int l, int r, int v) {
        int rk = rank(l, r, v + 1);
        begin(l, r);
        int c = 0;
        for (int i = 0; i < fi; i++) {
            c += cnt[full[i]];
        }
        for (int i = 0; i < ri; i++) {
            c -= cnt[rest[i]];
        }
        return rk > c ? Integer.MAX_VALUE : rankKey(l, r, rk);
    }

    private void begin(int l, int r) {
        fi = ri = 0;
        while (r > 0) {
            full[fi++] = root[r];
            r -= r & -r;
        }
        l--;
        while (l > 0) {
            rest[ri++] = root[l];
            l -= l & -l;
        }
    }

    private void iup(int i) {
        cnt[i] = cnt[lc[i]] + cnt[rc[i]];
    }

    private void idown(int i) {
        if (lc[i] == 0) {
            lc[i] = ++no;
            rc[i] = ++no;
        }
    }
}


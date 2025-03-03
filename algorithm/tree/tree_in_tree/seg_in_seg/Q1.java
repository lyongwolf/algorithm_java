package algorithm.tree.tree_in_tree.seg_in_seg;

import java.util.*;

/**
 * 测试链接：https://www.luogu.com.cn/problem/P3332
 */

public class Q1 extends U {

    SegInSeg tree = new SegInSeg(15000000);

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        int[] op = new int[m], left = new int[m], right = new int[m];
        long[] val = new long[m];
        int tot = 0;
        for (int i = 0; i < m; i++) {
            op[i] = sc.nextInt();
            if (op[i] == 1) {
                tot++;
            }
            left[i] = sc.nextInt();
            right[i] = sc.nextInt();
            val[i] = sc.nextLong();
        }
        int[] a = new int[tot];
        for (int i = 0, j = 0; i < m; i++) {
            if (op[i] == 1) {
                a[j++] = (int) val[i];
            }
        }
        Arrays.sort(a);
        tot = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] != a[i]) {
                a[tot++] = a[i];
            }
        }
        tree.init(0, tot, 1, n);
        for (int i = 0; i < m; i++) {
            if (op[i] == 1) {
                tree.add(Arrays.binarySearch(a, 0, tot, (int) val[i]), left[i], right[i]);
            } else {
                out.println(a[tree.query(val[i], left[i], right[i])]);
            }
        }
    }

    public static void main(String[] args) {
        Q1 o = new Q1();
        // int t = o.sc.nextInt(); while (t-- > 0)
        o.solve();
        o.out.flush();
    }
}

class U {                                                                                                                                                                                                       FastReader sc=new FastReader();FastWriter out=new FastWriter();class FastReader{private java.io.InputStream is=System.in;private byte[] inbuf=new byte[8192];private byte[] str=new byte[16];private byte b;private int lenbuf,ptrbuf;private byte readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){e.printStackTrace();}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=b;b=readByte();}return new String(str,0,i);}int nextInt(){return(int)nextLong();}long nextLong(){while((b=readByte())<33);boolean neg=b=='-';long num=neg?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return neg?-num:num;}double nextDouble(){double num=0,div=1;while((b=readByte())<33);boolean neg=false;if(b=='-'){neg=true;b=readByte();}while(b>32&&b!='.'){num=num*10+(b-'0');b=readByte();}if(b=='.'){b=readByte();while(b>32){num+=(b-'0')/(div*=10);b=readByte();}}return neg?-num:num;}}class FastWriter{private java.io.OutputStream out=System.out;private int tr=0,BUF_SIZE=8192;private byte[] buf=new byte[BUF_SIZE];private int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}private int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}FastWriter print(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}FastWriter print(char c){return print((byte)c);}FastWriter print(int x){if(x==Integer.MIN_VALUE){return print((long)x);}if(tr+12>=BUF_SIZE)innerflush();if(x<0){print((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter print(long x){if(x==Long.MIN_VALUE){return print(""+x);}if(tr+21>=BUF_SIZE)innerflush();if(x<0){print((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter print(double x,int precision){if(x<0){print('-');x=-x;}x+=Math.pow(10,-precision)/2;print((long)x).print(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;print((char)('0'+(int)x));x-=(int)x;}return this;}FastWriter print(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.codePointAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void writeln(){print((byte)'\n');}void println(byte b){print(b).writeln();}void println(char c){print(c).writeln();}void println(int x){print(x).writeln();}void println(long x){print(x).writeln();}void println(double x,int precision){print(x,precision).writeln();}void println(String s){print(s).writeln();}void println(Object o){deepPrint(o,true);}private void deepPrint(Object o,boolean f){if(o==null){print(f?"null\n":"null");return;}Class<?>c=o.getClass();if(c.isArray()){if(o instanceof Object[]){Object[]t=(Object[])o;int n=t.length;boolean b=f&&n>0&&t[0]!=null;print(b?"[\n":"[");for(int i=0;i<n;i++){deepPrint(t[i],false);if(i<n-1)print(b?", \n":", ");else if(b)writeln();}print("]");}else{print(o instanceof byte[]?Arrays.toString((byte[])o):o instanceof short[]?Arrays.toString((short[])o):o instanceof int[]?Arrays.toString((int[])o):o instanceof long[]?Arrays.toString((long[])o):o instanceof char[]?Arrays.toString((char[])o):o instanceof float[]?Arrays.toString((float[])o):o instanceof double[]?Arrays.toString((double[])o):Arrays.toString((boolean[])o));}}else if(o instanceof Collection){Collection<?>t=(Collection<?>)o;print("[");int i=0;for(Object v:t){deepPrint(v,false);if(i<t.size()-1)print(", ");i++;}print("]");}else if(o instanceof Map){Map<?,?>t=(Map<?,?>)o;print(!t.isEmpty()&&f?"{\n":"{");int i=0;for(Map.Entry<?,?>v:t.entrySet()){deepPrint(v.getKey(),false);print(" = ");deepPrint(v.getValue(),false);if(++i<t.size())print(f?", \n":", ");else if(f)writeln();}print("}");}else{print(o.toString());}if(f)writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){e.printStackTrace();}}void flush(){innerflush();try{out.flush();}catch(Exception e){e.printStackTrace();}}}int min(int a, int b){return a>b?b:a;}int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}long min(long a, long b){return a>b?b:a;}long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}double min(double a, double b){return a>b?b:a;}double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}int max(int a, int b){return a<b?b:a;}int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}long max(long a, long b){return a<b?b:a;}long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}double max(double a, double b){return a<b?b:a;}double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}int abs(int a){return a<0?-a:a;}long abs(long a){return a<0?-a:a;}double abs(double a){return a<0?-a:a;}
}

class SegInSeg {
    private int olow, ohigh, ilow, ihigh, no;
    private int[] root, lc, rc;

    private long[] sum, lazy;

    public SegInSeg(int tot) {
        root = new int[tot];
        lc = new int[tot];
        rc = new int[tot];

        sum = new long[tot];
        lazy = new long[tot];
    }

    public void init(int olow, int ohigh, int ilow, int ihigh) {
        this.olow = olow;
        this.ohigh = ohigh;
        this.ilow = ilow;
        this.ihigh = ihigh;
        for (int i = 1; i <= no; i++) {
            lc[i] = rc[i] = root[i] = 0;

            sum[i] = 0;
            lazy[i] = 0;
        }
        no = 2;
        root[1] = 2;
    }

    public void add(int oo, int il, int ir) {
        oadd(oo, olow, ohigh, 1, il, ir);
    }

    private void oadd(int o, int l, int r, int i, int il, int ir) {
        iadd(il, ir, 1, ilow, ihigh, root[i]);
        if (l == r) {
            return;
        }
        odown(i);
        int m = (l + r) / 2;
        if (o <= m) {
            oadd(o, l, m, lc[i], il, ir);
        } else {
            oadd(o, m + 1, r, rc[i], il, ir);
        }
    }

    private void iadd(int L, int R, int v, int l, int r, int i) {
        if (L <= l && r <= R) {
            lazy[i] += v;
            sum[i] += v * (r - l + 1);
            return;
        }
        int m = (l + r) / 2;
        idown(i, m - l + 1, r - m);
        if (L <= m) {
            iadd(L, R, v, l, m, lc[i]);
        }
        if (R > m) {
            iadd(L, R, v, m + 1, r, rc[i]);
        }
        iup(i);
    }

    public int query(long ok, int il, int ir) {
        return oquery(ok, olow, ohigh, 1, il, ir);
    }

    private int oquery(long k, int l, int r, int i, int il, int ir) {
        if (l == r) {
            return l;
        }
        odown(i);
        int m = (l + r)  / 2;
        long rn = iquery(il, ir, ilow, ihigh, root[rc[i]]);
        if (rn >= k) {
            return oquery(k, m + 1, r, rc[i], il, ir);
        } else {
            return oquery(k - rn, l, m, lc[i], il, ir);
        }
    }

    private long iquery(int L, int R, int l, int r, int i) {
        if (L <= l && r <= R) {
            return sum[i];
        }
        int m = (l + r) / 2;
        idown(i, m - l + 1, r - m);
        long ans = 0;
        if (L <= m) {
            ans += iquery(L, R, l, m, lc[i]);
        }
        if (R > m) {
            ans += iquery(L, R, m + 1, r, rc[i]);
        }
        return ans;
    }

    private void oup(int i) {

    }

    private void odown(int i) {
        if (lc[i] == 0) {
            lc[i] = ++no;
            rc[i] = ++no;
            root[lc[i]] = ++no;
            root[rc[i]] = ++no;
        }
    }

    private void iup(int i) {
        sum[i] = sum[lc[i]] + sum[rc[i]];
    }

    private void idown(int i, int ln, int rn) {
        if (lc[i] == 0) {
            lc[i] = ++no;
            rc[i] = ++no;
        }
        int l = lc[i], r = rc[i];
        if (lazy[i] != 0) {
            lazy[l] += lazy[i];
            lazy[r] += lazy[i];
            sum[l] += lazy[i] * ln;
            sum[r] += lazy[i] * rn;
            lazy[i] = 0;
        }
    }
    
}
package algorithm.tree.order.FHQ;

import java.util.*;

/**
 * 可持久化平衡树
 * 测试链接：https://www.luogu.com.cn/problem/P3835
 * 推荐使用静态数组（预处理空间），否则扩容开销太大
 */
public class Persistent {                                                                                                                                                                                             static class FastReader{java.io.InputStream is=System.in;byte[] inbuf=new byte[1024];char[] str=new char[16];int lenbuf,ptrbuf,b;int readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=(char)b;b=readByte();}return new String(str,0,i);}int nextInt(){return(int)nextLong();}long nextLong(){while((b=readByte())<33);boolean minus=b=='-';long num=minus?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return minus?-num:num;}double nextDouble(){return Double.parseDouble(next());} } static class FastWriter{java.io.OutputStream out=System.out;int tr=0,BUF_SIZE=8192;byte[] buf=new byte[BUF_SIZE];int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}FastWriter write(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}FastWriter write(char c){return write((byte)c);}FastWriter write(int x){if(x==Integer.MIN_VALUE)return write((long)x);if(tr+12>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(long x){if(x==Long.MIN_VALUE)return write(""+x);if(tr+21>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(double x,int precision){if(x<0){write('-');x=-x;}x+=Math.pow(10,-precision)/2;write((long)x).write(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;write((char)('0'+(int)x));x-=(int)x;}return this;}FastWriter write(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.charAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void print(char c){write(c);}void print(String s){write(s);}void print(int x){write(x);}void print(long x){write(x);}void print(double x,int precision){write(x,precision);}void writeln(){write((byte)'\n');}void println(char c){write(c).writeln();}void println(int x){write(x).writeln();}void println(long x){write(x).writeln();}void println(double x,int precision){write(x,precision).writeln();}void println(String s){write(s).writeln();}void println(Object o){deepPrint(o,true);}void deepPrint(Object o,boolean f){if(o==null){write(f?"null\n":"null");return;}Class<?> c=o.getClass();if(c.isArray()){if(o instanceof Object[]){Object[] t=(Object[])o;int n=t.length;boolean b=f&&n>0&&t[0]!=null&&(t[0].getClass().isArray()||t[0] instanceof Collection||t[0] instanceof Map);write(b?"[\n":"[");for(int i=0;i<n;i++){deepPrint(t[i],false);if(i<n-1)write(b?", \n":", ");else if(b)writeln();}write("]");}else{write(o instanceof byte[]?Arrays.toString((byte[])o):o instanceof short[]?Arrays.toString((short[])o):o instanceof int[]?Arrays.toString((int[])o):o instanceof long[]?Arrays.toString((long[])o):o instanceof char[]?Arrays.toString((char[])o):o instanceof float[]?Arrays.toString((float[])o):o instanceof double[]?Arrays.toString((double[])o):Arrays.toString((boolean[])o));}}else if(o instanceof Collection){Collection<?> t=(Collection<?>)o;write("[");int i=0;for(Object v:t){deepPrint(v,false);if(i<t.size()-1)write(", ");i++;}write("]");}else if(o instanceof Map){Map<?,?> t=(Map<?,?>)o;write(!t.isEmpty()&&f?"{\n":"{");int i=0;for(Map.Entry<?,?> v:t.entrySet()){deepPrint(v.getKey(),false);write(" = ");deepPrint(v.getValue(),false);if(++i<t.size())write(f?", \n":", ");else if(f)writeln();}write("}");}else{write(o.toString());}if(f)writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){}}void flush(){innerflush();try{out.flush();}catch(Exception e){}}}static FastReader sc=new FastReader();static FastWriter out=new FastWriter();static int min(int a,int b){return a>b?b:a;}static int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}static long min(long a,long b){return a>b?b:a;}static long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}static double min(double a,double b){return a>b?b:a;}static double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}static int max(int a,int b){return a<b?b:a;}static int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}static long max(long a,long b){return a<b?b:a;}static long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}static double max(double a,double b){return a<b?b:a;}static double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}static int abs(int a){return a<0?-a:a;}static long abs(long a){return a<0?-a:a;}static double abs(double a){return a<0?-a:a;}
    public static void main(String[] args) {/* int t = sc.nextInt(); while (t-- > 0) */ solve(); out.flush();}

    static int MAXN = 500001, MAXM = MAXN * 50;
    static int[] key = new int[MAXM], lc = new int[MAXM], rc = new int[MAXM], sz = new int[MAXM];
    static double[] priority = new double[MAXM];
    static int[] head = new int[MAXN];
    static int no;

    static void solve() {
        int n = sc.nextInt();
        for (int cur = 1, i, opt, x; cur <= n; cur++) {
            i = sc.nextInt();
            opt = sc.nextInt();
            x = sc.nextInt();
            switch (opt) {
                case 1:
                    add(cur, i, x);
                    break;
                case 2:
                    remove(cur, i, x);
                    break;
                case 3:
                    out.println(rank(cur, i, x));
                    break;
                case 4:
                    out.println(rankKey(cur, i, x));
                    break;
                case 5:
                    out.println(floor(cur, i, x - 1));
                    break;
                default:
                    out.println(ceiling(cur, i, x + 1));
                    break;
            }
        }
    }

    static void add(int cur, int i, int k) {
        split(0, 0, head[i], k);
        int l = rc[0], r = lc[0];
        lc[0] = rc[0] = 0;
        head[cur] = merge(merge(l, create(k)), r);
    }

    static void remove(int cur, int i, int k) {
        int l, m, r;
        split(0, 0, head[i], k);
        r = lc[0];
        m = rc[0];
        split(0, 0, m, k - 1);
        m = lc[0];
        l = rc[0];
        lc[0] = rc[0] = 0;
        m = merge(lc[m], rc[m]);
        head[cur] = merge(merge(l, m), r);
    }

    static int floor(int cur, int i, int k) {
        head[cur] = head[i];
        return floor(head[i], k);
    }

    static int floor(int i, int k) {
        if (i == 0) {
            return Integer.MIN_VALUE + 1;
        }
        if (key[i] <= k) {
            return Math.max(key[i], floor(rc[i], k));
        } else {
            return floor(lc[i], k);
        }
    }

    static int ceiling(int cur, int i, int k) {
        head[cur] = head[i];
        return ceiling(k, head[i]);
    }

    static int ceiling(int k, int i) {
        if (i == 0) {
            return Integer.MAX_VALUE;
        }
        if (key[i] >= k) {
            return Math.min(key[i], ceiling(k, lc[i]));
        } else {
            return ceiling(k, rc[i]);
        }
    }

    static int rank(int cur, int i, int k) {
        head[cur] = head[i];
        return smallCount(k, head[i]) + 1;
    }
    
    static int smallCount(int k, int i) {
        if (i == 0) {
            return 0;
        }
        if (key[i] >= k) {
            return smallCount(k, lc[i]);
        } else {
            return sz[lc[i]] + 1 + smallCount(k, rc[i]);
        }
    }

    static int rankKey(int cur, int i, int rk) {
        head[cur] = head[i];
        return rankKey(rk, head[i]);
    }
    
    static int rankKey(int rk, int i) {
        if (sz[lc[i]] >= rk) {
            return rankKey(rk, lc[i]);
        } else if (sz[lc[i]] + 1 < rk) {
            return rankKey(rk - sz[lc[i]] - 1, rc[i]);
        }
        return key[i];
    }

    // 按 key 分裂： <= k 在左，> k 在右
    static void split(int l, int r, int i, int k) {
        if (i == 0) {
            rc[l] = lc[r] = 0;
            return;
        }
        i = clone(i);
        if (key[i] <= k) {
            rc[l] = i;
            split(i, r, rc[i], k);
        } else {
            lc[r] = i;
            split(l, i, lc[i], k);
        }
        up(i);
    }

    static int merge(int l, int r) {
        if (l == 0 || r == 0) {
            return l + r;
        }
        if (priority[l] >= priority[r]) {
            l = clone(l);
            rc[l] = merge(rc[l], r);
            up(l);
            return l;
        } else {
            r = clone(r);
            lc[r] = merge(l, lc[r]);
            up(r);
            return r;
        }
    }

    static void up(int i) {
        sz[i] = sz[lc[i]] + sz[rc[i]] + 1;
    }

    static int clone(int i) {
        key[++no] = key[i];
        lc[no] = lc[i];
        rc[no] = rc[i];
        sz[no] = sz[i];
        priority[no] = priority[i];
        return no;
    }

    static int create(int k) {
        key[++no] = k;
        sz[no] = 1;
        priority[no] = Math.random();
        return no;
    }

}

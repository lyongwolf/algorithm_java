package algorithm.tree.order.FHQ.persistent;

import java.util.*;

/**
 * 可持久化文艺平衡树（区间翻转可持久化）
 * 测试链接：https://www.luogu.com.cn/problem/P5055
 */
public class Q2 {                                                                                                                                                                                             static class FastReader{java.io.InputStream is=System.in;byte[] inbuf=new byte[1024];char[] str=new char[16];int lenbuf,ptrbuf,b;int readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=(char)b;b=readByte();}return new String(str,0,i);}int nextInt(){return(int)nextLong();}long nextLong(){while((b=readByte())<33);boolean minus=b=='-';long num=minus?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return minus?-num:num;}double nextDouble(){return Double.parseDouble(next());} } static class FastWriter{java.io.OutputStream out=System.out;int tr=0,BUF_SIZE=8192;byte[] buf=new byte[BUF_SIZE];int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}FastWriter write(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}FastWriter write(char c){return write((byte)c);}FastWriter write(int x){if(x==Integer.MIN_VALUE)return write((long)x);if(tr+12>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(long x){if(x==Long.MIN_VALUE)return write(""+x);if(tr+21>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}FastWriter write(double x,int precision){if(x<0){write('-');x=-x;}x+=Math.pow(10,-precision)/2;write((long)x).write(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;write((char)('0'+(int)x));x-=(int)x;}return this;}FastWriter write(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.charAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void print(char c){write(c);}void print(String s){write(s);}void print(int x){write(x);}void print(long x){write(x);}void print(double x,int precision){write(x,precision);}void writeln(){write((byte)'\n');}void println(char c){write(c).writeln();}void println(int x){write(x).writeln();}void println(long x){write(x).writeln();}void println(double x,int precision){write(x,precision).writeln();}void println(String s){write(s).writeln();}void println(Object o){deepPrint(o,true);}void deepPrint(Object o,boolean f){if(o==null){write(f?"null\n":"null");return;}Class<?> c=o.getClass();if(c.isArray()){if(o instanceof Object[]){Object[] t=(Object[])o;int n=t.length;boolean b=f&&n>0&&t[0]!=null&&(t[0].getClass().isArray()||t[0] instanceof Collection||t[0] instanceof Map);write(b?"[\n":"[");for(int i=0;i<n;i++){deepPrint(t[i],false);if(i<n-1)write(b?", \n":", ");else if(b)writeln();}write("]");}else{write(o instanceof byte[]?Arrays.toString((byte[])o):o instanceof short[]?Arrays.toString((short[])o):o instanceof int[]?Arrays.toString((int[])o):o instanceof long[]?Arrays.toString((long[])o):o instanceof char[]?Arrays.toString((char[])o):o instanceof float[]?Arrays.toString((float[])o):o instanceof double[]?Arrays.toString((double[])o):Arrays.toString((boolean[])o));}}else if(o instanceof Collection){Collection<?> t=(Collection<?>)o;write("[");int i=0;for(Object v:t){deepPrint(v,false);if(i<t.size()-1)write(", ");i++;}write("]");}else if(o instanceof Map){Map<?,?> t=(Map<?,?>)o;write(!t.isEmpty()&&f?"{\n":"{");int i=0;for(Map.Entry<?,?> v:t.entrySet()){deepPrint(v.getKey(),false);write(" = ");deepPrint(v.getValue(),false);if(++i<t.size())write(f?", \n":", ");else if(f)writeln();}write("}");}else{write(o.toString());}if(f)writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){}}void flush(){innerflush();try{out.flush();}catch(Exception e){}}}static FastReader sc=new FastReader();static FastWriter out=new FastWriter();static int min(int a,int b){return a>b?b:a;}static int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}static long min(long a,long b){return a>b?b:a;}static long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}static double min(double a,double b){return a>b?b:a;}static double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}static int max(int a,int b){return a<b?b:a;}static int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}static long max(long a,long b){return a<b?b:a;}static long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}static double max(double a,double b){return a<b?b:a;}static double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}static int abs(int a){return a<0?-a:a;}static long abs(long a){return a<0?-a:a;}static double abs(double a){return a<0?-a:a;}
    public static void main(String[] args) {/* int t = sc.nextInt(); while (t-- > 0) */ solve(); out.flush();}

    static void solve() {
        FHQTreap fhq = new FHQTreap(200000);
        int n = sc.nextInt();
        long ans = 0, p, x, l, r;
        for (int cur = 1, i, opt; cur <= n; cur++) {
            i = sc.nextInt();
            opt = sc.nextInt();
            switch (opt) {
                case 1:
                    p = sc.nextLong() ^ ans;
                    x = sc.nextLong() ^ ans;
                    fhq.add(cur, i, (int) p, (int) x);
                    break;
                case 2:
                    p = sc.nextLong() ^ ans;
                    fhq.remove(cur, i, (int) p);
                    break;
                case 3:
                    l = sc.nextLong() ^ ans;
                    r = sc.nextLong() ^ ans;
                    fhq.reverseRange(cur, i, (int) l, (int) r);
                    break;
                default:
                    l = sc.nextLong() ^ ans;
                    r = sc.nextLong() ^ ans;
                    ans = fhq.rangeSum(cur, i, (int) l, (int) r);
                    out.println(ans);
                    break;
            }
        }
    }

    static class FHQTreap {
        private int[] lc, rc, sz;
        private int[] key;
        private long[] sum;
        private double[] priority;
        private boolean[] reverse;
        private int[] head;
        private int no;

        public FHQTreap() {
            this(0);
        }

        public FHQTreap(int len) {
            head = new int[++len];
            len *= 100;
            key = new int[len];
            lc = new int[len];
            rc = new int[len];
            sz = new int[len];
            priority = new double[len];
            reverse = new boolean[len];
            sum = new long[len];
        }

        // 在 排名为 rk 的右侧插入一个值为 k 节点
        public void add(int cur, int i, int rk, int k) {
            int l, r;
            split(0, 0, head[i], rk);
            l = rc[0];
            r = lc[0];
            lc[0] = rc[0] = 0;
            l = merge(l, create(k));
            head[cur] = merge(l, r);
        }

        public void remove(int cur, int i, int rk) {
            int l, m, r;
            split(0, 0, head[i], rk);
            r = lc[0];
            m = rc[0];
            split(0, 0, m, rk - 1);
            m = lc[0];
            l = rc[0];
            lc[0] = rc[0] = 0;
            head[cur] = merge(l, r);
        }

        // [l, r] 区间反转
        public void reverseRange(int cur, int i, int l, int r) {
            int i1, i2, i3;
            split(0, 0, head[i], r);
            i3 = lc[0];
            i2 = rc[0];
            split(0, 0, i2, l - 1);
            i2 = lc[0];
            i1 = rc[0];
            reverse[i2] ^= true;
            lc[0] = rc[0] = 0;
            head[cur] = merge(merge(i1, i2), i3);
        }

        public long rangeSum(int cur, int i, int l, int r) {
            int i1, i2, i3;
            split(0, 0, head[i], r);
            i3 = lc[0];
            i2 = rc[0];
            split(0, 0, i2, l - 1);
            i2 = lc[0];
            i1 = rc[0];
            long ans = sum[i2];
            lc[0] = rc[0] = 0;
            head[cur] = merge(merge(i1, i2), i3);
            return ans;
        }

        // 按 rank 分裂：<= rk 在左，> rk 在右
        private void split(int l, int r, int i, int rk) {
            if (i == 0) {
                rc[l] = lc[r] = 0;
                return;
            }
            i = clone(i);
            down(i);
            if (sz[lc[i]] + 1 <= rk) {
                rc[l] = i;
                split(i, r, rc[i], rk - sz[lc[i]] - 1);
            } else {
                lc[r] = i;
                split(l, i, lc[i], rk);
            }
            up(i);
        }

        private int merge(int l, int r) {
            if (l == 0 || r == 0) {
                return l + r;
            }
            if (priority[l] >= priority[r]) {
                l = clone(l);
                down(l);
                rc[l] = merge(rc[l], r);
                up(l);
                return l;
            } else {
                r = clone(r);
                down(r);
                lc[r] = merge(l, lc[r]);
                up(r);
                return r;
            }
        }

        private void up(int i) {
            sz[i] = sz[lc[i]] + sz[rc[i]] + 1;
            sum[i] = sum[lc[i]] + sum[rc[i]] + key[i];
        }

        private void down(int i) {
            if (reverse[i]) {
                if (lc[i] != 0) {
                    lc[i] = clone(lc[i]);
                    reverse[lc[i]] ^= true;
                }
                if (rc[i] != 0) {
                    rc[i] = clone(rc[i]);
                    reverse[rc[i]] ^= true;
                }
                lc[i] ^= rc[i] ^ (rc[i] = lc[i]);
                reverse[i] = false;
            }
        }

        private int clone(int i) {
            key[++no] = key[i];
            sz[no] = sz[i];
            priority[no] = priority[i];
            sum[no] = sum[i];
            lc[no] = lc[i];
            rc[no] = rc[i];
            reverse[no] = reverse[i];
            return no;
        }

        private int create(int k) {
            key[++no] = k;
            sz[no] = 1;
            priority[no] = Math.random();
            sum[no] = k;
            return no;
        }

    }

}
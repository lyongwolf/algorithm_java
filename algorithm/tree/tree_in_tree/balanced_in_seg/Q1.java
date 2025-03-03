package algorithm.tree.tree_in_tree.balanced_in_seg;

import java.util.*;

/**
 * 线段树套平衡树
 * 测试连接：https://www.luogu.com.cn/problem/P3380
 */

public class Q1 extends U {

    BalancedInSeg tree = new BalancedInSeg(1900000);

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt(), l, r, k, pos, v;
        tree.init(1, n, 0, 100000000);
        for (int i = 1; i <= n; i++) {
            tree.add(i, sc.nextInt());
        }
        while (m-- > 0) {
            switch (sc.nextInt()) {
                case 1 -> {
                    l = sc.nextInt();
                    r = sc.nextInt();
                    k = sc.nextInt();
                    out.println(tree.rank(l, r, k));
                }
                case 2 -> {
                    l = sc.nextInt();
                    r = sc.nextInt();
                    k = sc.nextInt();
                    out.println(tree.rankKey(l, r, k));
                }
                case 3 -> {
                    pos = sc.nextInt();
                    k = sc.nextInt();
                    tree.set(pos, k);
                }
                case 4 -> {
                    l = sc.nextInt();
                    r = sc.nextInt();
                    k = sc.nextInt();
                    v = tree.floor(l, r, k);
                    out.println(v == Integer.MIN_VALUE ? -2147483647 : v);
                }
                case 5 -> {
                    l = sc.nextInt();
                    r = sc.nextInt();
                    k = sc.nextInt();
                    out.println(tree.ceiling(l, r, k));
                }
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

class BalancedInSeg {
    private int olow, ohigh, ilow, ihigh, no;
    private int[] root, lc, rc;
    
    // 平衡树部分
    private static final double ALPHA = 0.7;// 平衡因子
    private int[] key, val, sz, tot, sd;// 基本信息
    private int ab[], nb, tb, fb, sb;// 平衡调整

    public BalancedInSeg(int tot) {
        root = new int[tot];
        lc = new int[tot];
        rc = new int[tot];

        key = new int[tot];
        val = new int[tot];
        sz = new int[tot];
        this.tot = new int[tot];
        sd = new int[tot];
        ab = new int[tot];
    }

    public void init(int olow, int ohigh, int ilow, int ihigh) {
        this.olow = olow;
        this.ohigh = ohigh;
        this.ilow = ilow;
        this.ihigh = ihigh;
        for (int i = 1; i <= no; i++) {
            root[i] = lc[i] = rc[i] = 0;
        }
        no = 1;
    }

    // 在 o 位置插入值 v
    public void add(int o, int v) {
        oadd(o, v, olow, ohigh, 1);
    }

    private void oadd(int o, int v, int l, int r, int i) {
        iadd(i, v);
        if (l == r) {
            return;
        }
        odown(i);
        int m = (l + r) / 2;
        if (o <= m) {
            oadd(o, v, l, m, lc[i]);
        } else {
            oadd(o, v, m + 1, r, rc[i]);
        }
    }

    // 将 o 位置的值设置为 v
    public void set(int o, int v) {
        oset(o, v, olow, ohigh, 1);
    }

    private int oset(int o, int v, int l, int r, int i) {
        if (l == r) {
            int val = key[root[i]];
            iremove(i, val);
            iadd(i, v);
            return val;
        }
        odown(i);
        int m = (l + r) / 2;
        int val = o <= m ? oset(o, v, l, m, lc[i]) : oset(o, v, m + 1, r, rc[i]);
        iremove(i, val);
        iadd(i, v);
        return val;
    }

    // 查询 k 在 [l, r] 中的排名
    public int rank(int l, int r, int k) {
        return osmallCount(l, r, k, olow, ohigh, 1) + 1;
    }

    private int osmallCount(int L, int R, int k, int l, int r, int i) {
        if (L <= l && r <= R) {
            return smallCount(k, root[i]);
        }
        odown(i);
        int m = (l + r) / 2;
        int ans = 0;
        if (L <= m) {
            ans += osmallCount(L, R, k, l, m, lc[i]);
        }
        if (R > m) {
            ans += osmallCount(L, R, k, m + 1, r, rc[i]);
        }
        return ans;
    }

    // 查询区间 [L, R] 中排名第 rk 小的值
    public int rankKey(int L, int R, int rk) {
        int l = ilow, r = ihigh, k;
        while (l <= r) {
            k = (l + r) / 2;
            if (rank(L, R, k) <= rk) {
                l = k + 1;
            } else {
                r = k - 1;
            }
        }
        return l - 1;
    }

    // 查询区间 [l, r] 中严格小于 v ，且最大的数，若不存在返回 Integer.MIN_VALUE
    public int floor(int l, int r, int v) {
        return ofloor(l, r, v, olow, ohigh, 1);
    }

    private int ofloor(int L, int R, int v, int l, int r, int i) {
        if (L <= l && r <= R) {
            return floor(i, v);
        }
        odown(i);
        int m = (l + r) / 2;
        int ans = Integer.MIN_VALUE;
        if (L <= m) {
            ans = Math.max(ans, ofloor(L, R, v, l, m, lc[i]));
        }
        if (R > m) {
            ans = Math.max(ans, ofloor(L, R, v, m + 1, r, rc[i]));
        }
        return ans;
    }

    // 查询区间 [l, r] 中严格大于 v ，且最小的数，若不存在返回 Integer.MAX_VALUE
     public int ceiling(int l, int r, int v) {
        return oceiling(l, r, v, olow, ohigh, 1);
    }

    private int oceiling(int L, int R, int v, int l, int r, int i) {
        if (L <= l && r <= R) {
            return ceiling(i, v);
        }
        odown(i);
        int m = (l + r) / 2;
        int ans = Integer.MAX_VALUE;
        if (L <= m) {
            ans = Math.min(ans, oceiling(L, R, v, l, m, lc[i]));
        }
        if (R > m) {
            ans = Math.min(ans, oceiling(L, R, v, m + 1, r, rc[i]));
        }
        return ans;
    }

    private void oup(int i) {

    }

    private void odown(int i) {
        if (lc[i] == 0) {
            lc[i] = ++no;
            rc[i] = ++no;
        }
    }

    // ------------------ 以下为平衡树部分 ------------------ 
    private void iadd(int h, int k) {
        nb = tb = fb = sb = 0;
        iadd(h, k, 0, root[h], 0);
        rebuild(h);
    }

    private void iadd(int h, int k, int f, int i, int s) {
        if (i == 0) {
            i = create(k);
            if (f == 0) {
                root[h] = i;
            } else if (s == 1) {
                lc[f] = i;
            } else {
                rc[f] = i;
            }
            return;
        }
        if (key[i] > k) {
            iadd(h, k, i, lc[i], 1);
        } else if (key[i] < k) {
            iadd(h, k, i, rc[i], 2);
        } else {
            val[i]++;
        }
        iup(i);
        if (canRbu(i)) {
            tb = i;
            fb = f;
            sb = s;
        }
    }

    private void iremove(int h, int k) {
        nb = tb = fb = sb = 0;
        iremove(k, 0, root[h], 0);
        rebuild(h);
    }

    private void iremove(int k, int f, int i, int s) {
        if (i == 0) {
            return;
        }
        if (key[i] > k) {
            iremove(k, i, lc[i], 1);
        } else if (key[i] < k) {
            iremove(k, i, rc[i], 2);
        } else if (val[i] > 0) {
            val[i]--;
        }
        iup(i);
        if (canRbu(i)) {
            tb = i;
            fb = f;
            sb = s;
        }
    }

    private int floor(int h, int k) {
        int rk = rank(h, k);
        if (rk == 1) {
            return Integer.MIN_VALUE;
        }
        return rankKey(h, rk - 1);
    }
    
    private int ceiling(int h, int k) {
        int rk = rank(h, k + 1);
        if (rk > tot[root[h]]) {
            return Integer.MAX_VALUE;
        }
        return rankKey(h, rk);
    }

    private int first(int h) {
        return rankKey(h, 1);
    }

    private int last(int h) {
        return rankKey(h, tot[root[h]]);
    }

    private int pollFirst(int h) {
        int k = first(h);
        iremove(h, k);
        return k;
    }

    private int pollLast(int h) {
        int k = last(h);
        iremove(h, k);
        return k;
    }
    
    private int rank(int h, int k) {
        return smallCount(k, root[h]) + 1;
    }
    
    private int smallCount(int k, int i) {
        if (i == 0) {
            return 0;
        }
        if (key[i] >= k) {
            return smallCount(k, lc[i]);
        } else {
            return tot[lc[i]] + val[i] + smallCount(k, rc[i]);
        }
    }

    private int rankKey(int h, int rk) {
        if (rk < 1 || rk > tot[root[h]]) {
            return -1;
        }
        return rankKey2(rk, root[h]);
    }
    
    private int rankKey2(int rk, int i) {
        if (tot[lc[i]] >= rk) {
            return rankKey2(rk, lc[i]);
        } else if (tot[lc[i]] + val[i] < rk) {
            return rankKey2(rk - tot[lc[i]] - val[i], rc[i]);
        }
        return key[i];
    }

    private int[] copy(int h) {
        int[] arr = new int[tot[root[h]]];
        copy(arr, root[h], 0);
        return arr;
    }

    private int copy(int[] arr, int i, int c) {
        if (i == 0) {
            return c;
        }
        c = copy(arr, lc[i], c);
        int v = val[i];
        while (v-- > 0) {
            arr[c++] = key[i];
        }
        return copy(arr, rc[i], c);
    }
    
    private void rebuild(int h) {
        if (tb != 0) {
            inorder(tb);
            tb = rebuild(0, nb - 1);
            if (fb == 0) {
                root[h] = tb;
            } else if (sb == 1) {
                lc[fb] = tb;
            } else {
                rc[fb] = tb;
            }
        }
    }

    private int rebuild(int l, int r) {
        if (l > r) {
            return 0;
        }
        int m = (l + r) >> 1;
        int i = ab[m];
        lc[i] = rebuild(l, m - 1);
        rc[i] = rebuild(m + 1, r);
        iup(i);
        return i;
    }

    private void inorder(int i) {
        if (i == 0) {
            return;
        }
        inorder(lc[i]);
        if (val[i] > 0) {
            ab[nb++] = i;
        }
        inorder(rc[i]);
    }

    private boolean canRbu(int i) {
        return ALPHA * sz[i] <= Math.max(sz[lc[i]], sz[rc[i]]) || ALPHA * sz[i] >= sd[i];
    }

    private void iup(int i) {
        sz[i] = sz[lc[i]] + sz[rc[i]] + 1;
        tot[i] = tot[lc[i]] + tot[rc[i]] + val[i];
        sd[i] = sd[lc[i]] + sd[rc[i]] + (val[i] > 0 ? 1 : 0);
    }

    private int create(int k) {
        key[++no] = k;
        val[no] = sz[no] = tot[no] = sd[no] = 1;
        return no;
    }
}
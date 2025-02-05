package algorithm.tree.leftist_heap.persistent;

import java.util.*;

/**
 * 测试链接：https://www.luogu.com.cn/problem/P2409
 */

public class Q1 extends U {

    int INF = (int) 1e9;

    void solve() {
        int n = sc.nextInt(), k = sc.nextInt();
        int[][] mat = new int[n][];
        for (int i = 0; i < n; i++) {
            int m = sc.nextInt();
            mat[i] = new int[m];
            for (int j = 0; j < m; j++) {
                mat[i][j] = sc.nextInt();
            }
            Arrays.sort(mat[i]);
        }
        int[] ans = new int[k];

        PersistentHeap ph = new PersistentHeap((n + k * 2) * 10);// 节点预估数量 = 操作总次数 * log(单个堆最大节点数量)
        int h = 0;
        for (int i = 0; i < n; i++) {
            ans[0] += mat[i][0];
            h = ph.merge(h, ph.create(mat[i].length == 1 ? INF : mat[i][1] - mat[i][0], i, 0));
        }
        ph.pre[h] = ans[0];
        PriorityQueue<int[]> heap = new PriorityQueue<>((i, j) -> i[1] - j[1]);
        heap.add(new int[]{h, ph.pre[h] + ph.val[h]});
        for (int z = 1, u, d, i, j; z < k; z++) {
            int[] tup = heap.poll();
            u = tup[0];
            d = tup[1];
            ans[z] = d;

            h = ph.poll(u);
            if (h != 0) {
                ph.pre[h] = d - ph.val[u];
                heap.add(new int[]{h, ph.pre[h] + ph.val[h]});
            }

            i = ph.row[u];
            j = ph.col[u] + 1;
            if (j < mat[i].length) {
                h = ph.merge(h, ph.create(j + 1 == mat[i].length ? INF : mat[i][j + 1] - mat[i][j], i, j));
                ph.pre[h] = d;
                heap.add(new int[]{h, ph.pre[h] + ph.val[h]});
            }
        }
        for (int v : ans) {
            out.print(v);
            out.print(' ');
        }
        out.writeln();
    }

    public static void main(String[] args) {
        Q1 o = new Q1();
        // int t = o.sc.nextInt(); while (t-- > 0)
        o.solve();
        o.out.flush();
    }
}

class U {                                                                                                                                                                                                   FastReader sc=new FastReader();FastWriter out=new FastWriter();class FastReader{private java.io.InputStream is=System.in;private byte[] inbuf=new byte[1024];private byte[] str=new byte[16];private byte b;private int lenbuf,ptrbuf;private byte readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=b;b=readByte();}return new String(str,0,i);}int nextInt(){return(int)nextLong();}long nextLong(){while((b=readByte())<33);boolean neg=b=='-';long num=neg?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return neg?-num:num;}double nextDouble(){return Double.parseDouble(next());}}class FastWriter{private java.io.OutputStream out=System.out;private int tr=0,BUF_SIZE=8192;private byte[] buf=new byte[BUF_SIZE];private int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}private int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}private FastWriter write(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}private FastWriter write(char c){return write((byte)c);}private FastWriter write(int x){if(x==Integer.MIN_VALUE){return write((long)x);}if(tr+12>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}private FastWriter write(long x){if(x==Long.MIN_VALUE){return write(""+x);}if(tr+21>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}private FastWriter write(double x,int precision){if(x<0){write('-');x=-x;}x+=Math.pow(10,-precision)/2;write((long)x).write(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;write((char)('0'+(int)x));x-=(int)x;}return this;}private FastWriter write(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.codePointAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void print(char c){write(c);}void print(String s){write(s);}void print(int x){write(x);}void print(long x){write(x);}void print(double x,int precision){write(x,precision);}void writeln(){write((byte)'\n');}void println(char c){write(c).writeln();}void println(int x){write(x).writeln();}void println(long x){write(x).writeln();}void println(double x,int precision){write(x,precision).writeln();}void println(String s){write(s).writeln();}void println(Object o){deepPrint(o,true);}private void deepPrint(Object o,boolean f){if(o==null){write(f?"null\n":"null");return;}Class<?>c=o.getClass();if(c.isArray()){if(o instanceof Object[]){Object[]t=(Object[])o;int n=t.length;boolean b=f&&n>0&&t[0]!=null&&(t[0].getClass().isArray()||t[0]instanceof Collection||t[0]instanceof Map);write(b?"[\n":"[");for(int i=0;i<n;i++){deepPrint(t[i],false);if(i<n-1)write(b?", \n":", ");else if(b)writeln();}write("]");}else{write(o instanceof byte[]?Arrays.toString((byte[])o):o instanceof short[]?Arrays.toString((short[])o):o instanceof int[]?Arrays.toString((int[])o):o instanceof long[]?Arrays.toString((long[])o):o instanceof char[]?Arrays.toString((char[])o):o instanceof float[]?Arrays.toString((float[])o):o instanceof double[]?Arrays.toString((double[])o):Arrays.toString((boolean[])o));}}else if(o instanceof Collection){Collection<?>t=(Collection<?>)o;write("[");int i=0;for(Object v:t){deepPrint(v,false);if(i<t.size()-1)write(", ");i++;}write("]");}else if(o instanceof Map){Map<?,?>t=(Map<?,?>)o;write(!t.isEmpty()&&f?"{\n":"{");int i=0;for(Map.Entry<?,?>v:t.entrySet()){deepPrint(v.getKey(),false);write(" = ");deepPrint(v.getValue(),false);if(++i<t.size())write(f?", \n":", ");else if(f)writeln();}write("}");}else{write(o.toString());}if(f)writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){}}void flush(){innerflush();try{out.flush();}catch(Exception e){}}}int min(int a,int b){return a>b?b:a;}int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}long min(long a,long b){return a>b?b:a;}long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}double min(double a,double b){return a>b?b:a;}double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}int max(int a,int b){return a<b?b:a;}int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}long max(long a,long b){return a<b?b:a;}long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}double max(double a,double b){return a<b?b:a;}double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}int abs(int a){return a<0?-a:a;}long abs(long a){return a<0?-a:a;}double abs(double a){return a<0?-a:a;}
}

class PersistentHeap {
    private int no;
    private int[] dis;
    int[] lc, rc;

    // ...

    // 只对堆的头节点有效的参数
    int[] pre;
    
    // 节点比较逻辑参数
    int[] val;

    // 节点其它扩展属性
    int[] row, col;

    public PersistentHeap(int tot) {
        dis = new int[++tot];
        lc = new int[tot];
        rc = new int[tot];
        dis[0] = -1;
        
        // ...
        pre = new int[tot];
        
        val = new int[tot];

        row = new int[tot];
        col = new int[tot];

    }

    int merge(int i, int j) {
        if (i == 0 || j == 0) {
            return i + j;
        }
        if (val[i] > val[j]) {// 比较逻辑
            i ^= j ^ (j = i);
        }
        int h = clone(i);
        rc[h] = merge(rc[h], j);
        if (dis[lc[h]] < dis[rc[h]]) {
            lc[h] ^= rc[h] ^ (rc[h] = lc[h]);
        }
        dis[h] = dis[rc[h]] + 1;
        return h;
    }

    int poll(int i) {
        if (lc[i] == 0 && rc[i] == 0) {
            return 0;
        }
        if (lc[i] == 0 || rc[i] == 0) {
            return clone(lc[i] + rc[i]);
        }
        return merge(lc[i], rc[i]);
    }

    int create(int v, int i, int j) {
        no++;
        lc[no] = rc[no] = dis[no] = 0;

        // ...
        val[no] = v;
        row[no] = i;
        col[no] = j;

        return no;
    }

    private int clone(int i) {
        no++;
        dis[no] = dis[i];
        lc[no] = lc[i];
        rc[no] = rc[i];

        // ...
        val[no] = val[i];
        row[no] = row[i];
        col[no] = col[i];

        return no;
    }

}
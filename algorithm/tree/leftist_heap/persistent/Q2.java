package algorithm.tree.leftist_heap.persistent;

import java.util.*;

/**
 * k 短路
 * 测试链接：https://www.luogu.com.cn/problem/P2483
 */

public class Q2 extends U {

    double INF = 1e18;

    int[] key = new int[4150001];
	double[] val = new double[4150001];
	int[] pq = new int[4150001];
	int cntd, cnth;

    void solve() {
        int n = sc.nextInt(), m = sc.nextInt();
        double rest = sc.nextDouble();
        int[] head = new int[n + 1], nxt = new int[(m + 1) << 1], to = new int[(m + 1) << 1], path = new int[n + 1], rt = new int[n + 1];
        double[] wt = new double[(m + 1) << 1], dis = new double[n + 1];
        for (int i = 1, j = 2, u, v; i <= m; i++) {
            u = sc.nextInt();
            v = sc.nextInt();
            double w = sc.nextDouble();
            if (u == n) {
                continue;
            }
            nxt[j] = head[u]; head[u] = j; wt[j] = w; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; wt[j] = w; to[j++] = u;
        }
        
        Arrays.fill(dis, INF);
        dis[n] = 0;
        heapAdd(n, 0);
        while (!heapEmpty()) {
            int i = heapPop();
            int u = key[i];
            double w = val[i];
            if (dis[u] < w) {
                continue;
            }
            for (int e = head[u], v; e != 0; e = nxt[e]) {
                if ((e & 1) == 0) {
                    continue;
                }
                v = to[e];
                if (dis[v] > w + wt[e]) {
                    path[v] = e ^ 1;
                    dis[v] = w + wt[e];
                    heapAdd(v, dis[v]);
                }
            }
        }
        
        cntd = cnth = 0;
        for (int i = 1; i <= n; i++) {
            heapAdd(i, dis[i]);
        }
        PersistentHeap ph = new PersistentHeap(900000);
        while (!heapEmpty()) {
            int i = heapPop();
            int u = key[i];
            if (dis[u] == INF) {
                break;
            }
            for (int e = head[u], v; e != 0; e = nxt[e]) {
                if ((e & 1) != 0) {
                    continue;
                }
                v = to[e];
                if (e != path[u]) {
                    rt[u] = ph.merge(rt[u], ph.create(dis[v] + wt[e] - dis[u], v));
                }
            }
            if (path[u] != 0) {
                rt[u] = ph.merge(rt[u], rt[to[path[u]]]);
            }
        }

        int ans = 0;
        rest -= dis[1];
        if (rest >= 0) {
            ans++;
            if (rt[1] != 0) {
                cntd = cnth = 0;
                heapAdd(rt[1], dis[1] + ph.cost[rt[1]]);
            }
            while (!heapEmpty()) {
                int i = heapPop();
                int h = key[i];
                double w = val[i];
                rest -= w;
                if (rest < 0) {
                    break;
                }
                ans++;
                if (ph.lc[h] != 0) {
                    heapAdd(ph.lc[h], w - ph.cost[h] + ph.cost[ph.lc[h]]);
                }
                if (ph.rc[h] != 0) {
                    heapAdd(ph.rc[h], w - ph.cost[h] + ph.cost[ph.rc[h]]);
                }
                int v = ph.to[h];
                if (rt[v] != 0) {
                    heapAdd(rt[v], w + ph.cost[rt[v]]);
                }
            }
        }
        out.println(ans);
    }

    // (k, v)组成一个数据，放到堆上，根据v来组织小根堆
	void heapAdd(int k, double v) {
		key[++cntd] = k;
		val[cntd] = v;
		pq[++cnth] = cntd;
		int cur = cnth, father = cur / 2, tmp;
		while (cur > 1 && val[pq[father]] > val[pq[cur]]) {
			tmp = pq[father];
			pq[father] = pq[cur];
			pq[cur] = tmp;
			cur = father;
			father = cur / 2;
		}
	}

	// 小根堆上，堆顶的数据(k, v)弹出，并返回数据所在的下标ans
	// 根据返回值ans，key[ans]得到k，val[ans]得到v
	int heapPop() {
		int ans = pq[1];
		pq[1] = pq[cnth--];
		int cur = 1, l = cur * 2, r = l + 1, best, tmp;
		while (l <= cnth) {
			best = r <= cnth && val[pq[r]] < val[pq[l]] ? r : l;
			best = val[pq[best]] < val[pq[cur]] ? best : cur;
			if (best == cur) {
				break;
			}
			tmp = pq[best];
			pq[best] = pq[cur];
			pq[cur] = tmp;
			cur = best;
			l = cur * 2;
			r = l + 1;
		}
		return ans;
	}

	boolean heapEmpty() {
		return cnth == 0;
	}

    public static void main(String[] args) {
        Q2 o = new Q2();
        // int t = o.sc.nextInt(); while (t-- > 0)
        o.solve();
        o.out.flush();
    }
}

class U {                                                                                                                                                                                                   FastReader sc=new FastReader();FastWriter out=new FastWriter();class FastReader{private java.io.InputStream is=System.in;private byte[] inbuf=new byte[8192];private byte[] str=new byte[16];private byte b;private int lenbuf,ptrbuf;private byte readByte(){if(ptrbuf==lenbuf){ptrbuf=0;try{lenbuf=is.read(inbuf);}catch(Exception e){}if(lenbuf<=0)return-1;}return inbuf[ptrbuf++];}String next(){while((b=readByte())<33);int i=0;while(b>32){if(i==str.length)str=Arrays.copyOf(str,str.length<<1);str[i++]=b;b=readByte();}return new String(str,0,i);}int nextInt(){return(int)nextLong();}long nextLong(){while((b=readByte())<33);boolean neg=b=='-';long num=neg?0:b-'0';while((b=readByte())>32)num=num*10+(b-'0');return neg?-num:num;}double nextDouble(){double num=0,div=1;while((b=readByte())<33);boolean minus=false;if(b=='-'){minus=true;b=readByte();}while(b>32&&b!='.'){num=num*10+(b-'0');b=readByte();}if(b=='.'){b=readByte();while(b>32){num+=(b-'0')/(div*=10);b=readByte();}}return minus?-num:num;}}class FastWriter{private java.io.OutputStream out=System.out;private int tr=0,BUF_SIZE=8192;private byte[] buf=new byte[BUF_SIZE];private int countDigits(int v){return v>=100000?v>=10000000?v>=100000000?v>=1000000000?10:9:8:v>=1000000?7:6:v>=1000?v>=10000?5:4:v>=100?3:v>=10?2:1;}private int countDigits(long v){return v>=10000000000L?10+countDigits((int)(v/10000000000L)):v>=1000000000?10:countDigits((int)v);}private FastWriter write(byte b){buf[tr++]=b;if(tr==BUF_SIZE)innerflush();return this;}private FastWriter write(char c){return write((byte)c);}private FastWriter write(int x){if(x==Integer.MIN_VALUE){return write((long)x);}if(tr+12>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}private FastWriter write(long x){if(x==Long.MIN_VALUE){return write(""+x);}if(tr+21>=BUF_SIZE)innerflush();if(x<0){write((byte)'-');x=-x;}int d=countDigits(x);for(int i=tr+d-1;i>=tr;i--){buf[i]=(byte)('0'+x%10);x/=10;}tr+=d;return this;}private FastWriter write(double x,int precision){if(x<0){write('-');x=-x;}x+=Math.pow(10,-precision)/2;write((long)x).write(".");x-=(long)x;for(int i=0;i<precision;i++){x*=10;write((char)('0'+(int)x));x-=(int)x;}return this;}private FastWriter write(String s){for(int i=0;i<s.length();i++){buf[tr++]=(byte)s.codePointAt(i);if(tr==BUF_SIZE)innerflush();}return this;}void print(char c){write(c);}void print(String s){write(s);}void print(int x){write(x);}void print(long x){write(x);}void print(double x,int precision){write(x,precision);}void writeln(){write((byte)'\n');}void println(char c){write(c).writeln();}void println(int x){write(x).writeln();}void println(long x){write(x).writeln();}void println(double x,int precision){write(x,precision).writeln();}void println(String s){write(s).writeln();}void println(Object o){deepPrint(o,true);}private void deepPrint(Object o,boolean f){if(o==null){write(f?"null\n":"null");return;}Class<?>c=o.getClass();if(c.isArray()){if(o instanceof Object[]){Object[]t=(Object[])o;int n=t.length;boolean b=f&&n>0&&t[0]!=null&&(t[0].getClass().isArray()||t[0]instanceof Collection||t[0]instanceof Map);write(b?"[\n":"[");for(int i=0;i<n;i++){deepPrint(t[i],false);if(i<n-1)write(b?", \n":", ");else if(b)writeln();}write("]");}else{write(o instanceof byte[]?Arrays.toString((byte[])o):o instanceof short[]?Arrays.toString((short[])o):o instanceof int[]?Arrays.toString((int[])o):o instanceof long[]?Arrays.toString((long[])o):o instanceof char[]?Arrays.toString((char[])o):o instanceof float[]?Arrays.toString((float[])o):o instanceof double[]?Arrays.toString((double[])o):Arrays.toString((boolean[])o));}}else if(o instanceof Collection){Collection<?>t=(Collection<?>)o;write("[");int i=0;for(Object v:t){deepPrint(v,false);if(i<t.size()-1)write(", ");i++;}write("]");}else if(o instanceof Map){Map<?,?>t=(Map<?,?>)o;write(!t.isEmpty()&&f?"{\n":"{");int i=0;for(Map.Entry<?,?>v:t.entrySet()){deepPrint(v.getKey(),false);write(" = ");deepPrint(v.getValue(),false);if(++i<t.size())write(f?", \n":", ");else if(f)writeln();}write("}");}else{write(o.toString());}if(f)writeln();}private void innerflush(){try{out.write(buf,0,tr);tr=0;}catch(Exception e){}}void flush(){innerflush();try{out.flush();}catch(Exception e){}}}int min(int a,int b){return a>b?b:a;}int min(int...args){int ans=Integer.MAX_VALUE;for(int v:args)if(ans>v)ans=v;return ans;}long min(long a,long b){return a>b?b:a;}long min(long...args){long ans=Long.MAX_VALUE;for(long v:args)if(ans>v)ans=v;return ans;}double min(double a,double b){return a>b?b:a;}double min(double...args){double ans=Double.MAX_VALUE;for(double v:args)if(ans>v)ans=v;return ans;}int max(int a,int b){return a<b?b:a;}int max(int...args){int ans=Integer.MIN_VALUE;for(int v:args)if(ans<v)ans=v;return ans;}long max(long a,long b){return a<b?b:a;}long max(long...args){long ans=Long.MIN_VALUE;for(long v:args)if(ans<v)ans=v;return ans;}double max(double a,double b){return a<b?b:a;}double max(double...args){double ans=Double.MIN_VALUE;for(double v:args)if(ans<v)ans=v;return ans;}int abs(int a){return a<0?-a:a;}long abs(long a){return a<0?-a:a;}double abs(double a){return a<0?-a:a;}
}

class PersistentHeap {
    private int no;
    private int[] dis;
    int[] lc, rc;

    // ...
    
    // 节点比较逻辑参数
    double[] cost;

    // 节点其它扩展属性
    int[] to;

    public PersistentHeap(int tot) {
        dis = new int[++tot];
        lc = new int[tot];
        rc = new int[tot];
        dis[0] = -1;
        
        // ...
        cost = new double[tot];

        to = new int[tot];

    }

    int merge(int i, int j) {
        if (i == 0 || j == 0) {
            return i + j;
        }
        if (cost[i] > cost[j]) {// 比较逻辑
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

    int create(double v, int i) {
        no++;
        lc[no] = rc[no] = dis[no] = 0;

        // ...
        cost[no] = v;
        to[no] = i;

        return no;
    }

    private int clone(int i) {
        no++;
        dis[no] = dis[i];
        lc[no] = lc[i];
        rc[no] = rc[i];

        // ...
        cost[no] = cost[i];
        to[no] = to[i];

        return no;
    }

}
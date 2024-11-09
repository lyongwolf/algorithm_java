package algorithm.graph.Bipartite_Graph;
import java.io.*;
import java.util.*;

/**
 * 测试链接：https://www.luogu.com.cn/problem/P3386
 * 二分图最大匹配（dinic algorithm）
 */
public class maximum_match_bipartite_graph_dinic {                                                                                                                                                                                        static class FastReader{InputStream is = System.in;byte[] inbuf = new byte[1024];int lenbuf = 0,ptrbuf = 0;int readByte(){if(lenbuf == -1) throw new InputMismatchException();if(ptrbuf >= lenbuf){ptrbuf = 0;try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException();}if (lenbuf <= 0) return -1;}return inbuf[ptrbuf++];}boolean isSpaceChar(int c){return !(c >= 33 && c <= 126);}int skip(){int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b;}String next(){int b = skip();StringBuilder sb = new StringBuilder();while(!(isSpaceChar(b))){sb.appendCodePoint(b);b = readByte();}return sb.toString();}int nextInt(){return (int)nextLong();}long nextLong(){long num = 0;int b;boolean minus = false;while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));if(b == '-'){minus = true;b = readByte();}while(true){if(b >= '0' && b <= '9'){num = num * 10 + (b - '0');}else{return minus ? -num : num;}b = readByte();}}double nextDouble(){return Double.parseDouble(next());}}static class FastWriter{OutputStream out = System.out;int BUF_SIZE = 1<<13;byte[] buf = new byte[BUF_SIZE];int tr = 0;int countDigits(int l){if (l >= 1000000000) return 10;if (l >= 100000000) return 9;if (l >= 10000000) return 8;if (l >= 1000000) return 7;if (l >= 100000) return 6;if (l >= 10000) return 5;if (l >= 1000) return 4;if (l >= 100) return 3;if (l >= 10) return 2;return 1;}int countDigits(long l){if (l >= 1000000000000000000L) return 19;if (l >= 100000000000000000L) return 18;if (l >= 10000000000000000L) return 17;if (l >= 1000000000000000L) return 16;if (l >= 100000000000000L) return 15;if (l >= 10000000000000L) return 14;if (l >= 1000000000000L) return 13;if (l >= 100000000000L) return 12;if (l >= 10000000000L) return 11;if (l >= 1000000000L) return 10;if (l >= 100000000L) return 9;if (l >= 10000000L) return 8;if (l >= 1000000L) return 7;if (l >= 100000L) return 6;if (l >= 10000L) return 5;if (l >= 1000L) return 4;if (l >= 100L) return 3;if (l >= 10L) return 2;return 1;}FastWriter write(byte b){buf[tr++] = b;if(tr == BUF_SIZE)innerflush();return this;}FastWriter write(char c){return write((byte)c);}FastWriter write(int x){if(x == Integer.MIN_VALUE){return write((long)x);}if(tr + 12 >= BUF_SIZE)innerflush();if(x < 0){write((byte)'-');x = -x;}int d = countDigits(x);for(int i = tr + d - 1;i >= tr;i--){buf[i] = (byte)('0'+x%10);x /= 10;}tr += d;return this;}FastWriter write(long x){if(x == Long.MIN_VALUE){return write("" + x);}if(tr + 21 >= BUF_SIZE)innerflush();if(x < 0){write((byte)'-');x = -x;}int d = countDigits(x);for(int i = tr + d - 1;i >= tr;i--){buf[i] = (byte)('0'+x%10);x /= 10;}tr += d;return this;}FastWriter write(double x, int precision){if(x < 0){write('-');x = -x;}x += Math.pow(10, -precision)/2;write((long)x).write(".");x -= (long)x;for(int i = 0;i < precision;i++){x *= 10;write((char)('0'+(int)x));x -= (int)x;}return this;}FastWriter write(String s) {s.chars().forEach(c -> {buf[tr++] = (byte)c;if(tr == BUF_SIZE)innerflush();});return this;}FastWriter print(char c){return write(c);}FastWriter print(String s){return write(s);}FastWriter print(int x){return write(x);}FastWriter print(long x){return write(x);}FastWriter print(double x, int precision){return write(x, precision);}FastWriter writeln() {return write((byte)'\n');}FastWriter println(char c){return write(c).writeln();}FastWriter println(int x){return write(x).writeln();}FastWriter println(long x){return write(x).writeln();}FastWriter println(double x, int precision){return write(x, precision).writeln();}FastWriter println(String s){return write(s).writeln();} FastWriter println(Object obj) {return write(obj.toString()).writeln();} void innerflush(){try{out.write(buf, 0, tr);tr = 0;}catch (IOException e){throw new RuntimeException("innerflush");}}void flush(){innerflush();try{out.flush();}catch (IOException e){throw new RuntimeException("flush");}}}static FastReader sc = new FastReader();static FastWriter out = new FastWriter();
    public static void main(String[] args) {/* int t = sc.nextInt(); while (t-- > 0) */ solve(); out.flush();}

    static void solve() {
        int n = sc.nextInt(), m = sc.nextInt(), e = sc.nextInt();
        MF mf = new MF(n, m, e);
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            mf.addBiEdge(u, v);
        }
        out.println(mf.maxFlow());
    }

    static class MF {
        private final int INF = 0x3f3f3f3f;
        private int n, m, s, t, z;
        private int[] head, nxt, to, wt, es, dep;

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
            wt = new int[tot];
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
        }

        public void addBiEdge(int u, int v) {
            v += n;
            addEdge(u, v, 1);
            addEdge(v, u, 0);
        }

        private void addEdge(int u, int v, int w) {
            nxt[z] = head[u];
            head[u] = z;
            wt[z] = w;
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
                    if (wt[e] == 0) {
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
                if (wt[e] > 0 && dep[v] == dep[u] + 1) {
                    f = flow(v, Math.min(r, wt[e]));
                    if (f == 0) {
                        dep[v] = 0;
                    }
                    r -= f;
                    wt[e] -= f;
                    wt[e ^ 1] += f;
                    ans += f;
                }
            }
            return ans;
        }
    
        private boolean bfs() {
            Arrays.fill(dep, 0);
            Deque<Integer> queue = new ArrayDeque<>();
            queue.addLast(s);
            dep[s] = 1;
            es[s] = head[s];
            while (!queue.isEmpty()) {
                int u = queue.removeFirst();
                for (int e = head[u], v = to[e]; e != 0; e = nxt[e], v = to[e]) {
                    if (dep[v] == 0 && wt[e] > 0) {
                        es[v] = head[v];
                        dep[v] = dep[u] + 1;
                        if (v == t) {
                            return true;
                        }
                        queue.addLast(v);
                    }
                }
            }
            return false;
        }
    }
    
}
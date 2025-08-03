class Euler {
    // Hierholzer algorithm
    // graph must be connected
    
    private static int[] head, nxt, to, deg, in, out;
    private static boolean[] vis;
    private static int[] ans;
    private static int z;

    public static int[] path(int[][] edge) {
        int n = prepare0(edge), m = edge.length, start = n, cnt = 0;
        for (int i = 0; i <= n; i++) {
            if ((deg[i] & 1) != 0) {
                start = i;
                cnt++;
            }
        }
        if (cnt > 2) {
            return null;
        }
        vis = new boolean[m];
        ans = new int[m + 1];
        z = m;
        dfs0(start);
        return ans;
    }

    public static int[] lexPath(int[][] edge) {
        int n = prepare0(edge), m = edge.length, start = n, cnt = 0, s = n;
        for (int i = n; i >= 0; i--) {
            if ((deg[i] & 1) != 0) {
                start = i;
                cnt++;
            } else if (deg[i] > 0) {
                s = i;
            }
        }
        if (cnt > 2) {
            return null;
        }
        if (cnt == 0) {
            start = s;
        }
        sort0(n);
        vis = new boolean[m];
        ans = new int[m + 1];
        z = m;
        dfs0(start);
        return ans;
    }

    public static int[] digraphPath(int[][] edge) {
        int n = prepare1(edge), m = edge.length, start = n, c0 = 0, c1 = 0;
        for (int i = 0; i <= n; i++) {
            if (in[i] != out[i]) {
                int d = in[i] - out[i];
                if (Math.abs(d) > 1) {
                    return null;
                }
                if (d == 1) {
                    c0++;
                } else {
                    start = i;
                    c1++;
                }
            }
        }
        if (c0 != c1 || c0 > 1) {
            return null;
        }
        vis = new boolean[m];
        ans = new int[m + 1];
        z = m;
        dfs1(start);
        return ans;
    }

    public static int[] digraphLexPath(int[][] edge) {
        int n = prepare1(edge), m = edge.length, start = n, c0 = 0, c1 = 0, s = n;
        for (int i = n; i >= 0; i--) {
            if (in[i] != out[i]) {
                int d = in[i] - out[i];
                if (Math.abs(d) > 1) {
                    return null;
                }
                if (d == 1) {
                    c0++;
                } else {
                    start = i;
                    c1++;
                }
            } else if (in[i] > 0) {
                s = i;
            }
        }
        if (c0 != c1 || c0 > 1) {
            return null;
        }
        if (c0 == 0) {
            start = s;
        }
        sort1(n);
        vis = new boolean[m];
        ans = new int[m + 1];
        z = m;
        dfs1(start);
        return ans;
    }

    private static int prepare0(int[][] edge) {
        int n = 0, m = edge.length;
        for (int[] e : edge) {
            n = Math.max(n, Math.max(e[0], e[1]));
        }
        head = new int[n + 1]; nxt = new int[m + 1 << 1]; to = new int[m + 1 << 1]; deg = new int[n + 1];
        java.util.Arrays.fill(head, -1);
        int j = 0;
        for (int[] e : edge) {
            int u = e[0], v = e[1];
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            nxt[j] = head[v]; head[v] = j; to[j++] = u;
            deg[u]++;
            deg[v]++;
        }
        return n;
    }

    private static int prepare1(int[][] edge) {
        int n = 0, m = edge.length;
        for (int[] e : edge) {
            n = Math.max(n, Math.max(e[0], e[1]));
        }
        head = new int[n + 1]; nxt = new int[m]; to = new int[m]; in = new int[n + 1]; out = new int[n + 1];
        java.util.Arrays.fill(head, -1);
        int j = 0;
        for (int[] e : edge) {
            int u = e[0], v = e[1];
            nxt[j] = head[u]; head[u] = j; to[j++] = v;
            in[v]++;
            out[u]++;
        }
        return n;
    }

    private static void sort0(int n) {
        for (int u = 0; u <= n; u++) {
            if (deg[u] == 0) {
                continue;
            }
            int[][] a = new int[deg[u]][2];
            int z = 0;
            for (int e = head[u]; e != -1; e = nxt[e]) {
                a[z][0] = e;
                a[z++][1] = to[e];
            }
            java.util.Arrays.sort(a, (x, y) -> x[1] - y[1]);
            head[u] = a[0][0];
            nxt[a[z - 1][0]] = -1;
            for (int i = 0; i < z - 1; i++) {
                nxt[a[i][0]] = a[i + 1][0];
            }
        }
    }

    private static void sort1(int n) {
        for (int u = 0; u <= n; u++) {
            if (out[u] == 0) {
                continue;
            }
            int[][] a = new int[out[u]][2];
            int z = 0;
            for (int e = head[u]; e != -1; e = nxt[e]) {
                a[z][0] = e;
                a[z++][1] = to[e];
            }
            java.util.Arrays.sort(a, (x, y) -> x[1] - y[1]);
            head[u] = a[0][0];
            nxt[a[z - 1][0]] = -1;
            for (int i = 0; i < z - 1; i++) {
                nxt[a[i][0]] = a[i + 1][0];
            }
        }
    }

    private static void dfs0(int u) {
        for (int e = head[u]; e != -1; e = head[u]) {
            head[u] = nxt[e];
            if (!vis[e >> 1]) {
                vis[e >> 1] = true;
                dfs0(to[e]);
            }
        }
        ans[z--] = u;
    }

    private static void dfs1(int u) {
        for (int e = head[u]; e != -1; e = head[u]) {
            head[u] = nxt[e];
            if (!vis[e]) {
                vis[e] = true;
                dfs1(to[e]);
            }
        }
        ans[z--] = u;
    }
}
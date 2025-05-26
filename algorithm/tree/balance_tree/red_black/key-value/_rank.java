class RedBlackTree {
    private static final boolean RED = false, BLACK = true;
    private static final int MAXT = 1000000;
    private static int[] key = new int[MAXT], val = new int[MAXT];
    private static int[] lc = new int[MAXT], rc = new int[MAXT], fa = new int[MAXT], sz = new int[MAXT];
    private static boolean[] color = new boolean[MAXT];
    private static int no;
    static {
        color[0] = BLACK;// 空节点颜色为黑
    }

    public static void clear() {
        for (int i = 1; i <= no; i++) {
            lc[i] = rc[i] = fa[i] = 0;
        }
        no = 0;
    }

    private int head;// 头节点编号

    public int size() {
        return sz[head];
    }

    public boolean isEmpty() {
        return sz[head] == 0;
    }

    public boolean containsKey(int k) {
        int n = head;
        while (n != 0) {
            if (key[n] > k) {
                n = lc[n];
            } else if (key[n] < k) {
                n = rc[n];
            } else {
                return true;
            }
        }
        return false;
    }

    public int get(int k) {
        int n = head;
        while (n != 0) {
            if (key[n] > k) {
                n = lc[n];
            } else if (key[n] < k) {
                n = rc[n];
            } else {
                break;
            }
        }
        return val[n];
    }

    public int getOrDefault(int k, int defaultValue) {
        int n = head;
        while (n != 0) {
            if (key[n] > k) {
                n = lc[n];
            } else if (key[n] < k) {
                n = rc[n];
            } else {
                return val[n];
            }
        }
        return defaultValue;
    }

    public int floorKey(int k) {
        int ans = Integer.MIN_VALUE;
        int n = head;
        while (n != 0) {
            if (key[n] > k) {
                n = lc[n];
            } else {
                ans = Math.max(ans, key[n]);
                n = rc[n];
            }
        }
        return ans;
    }

    public int ceilingKey(int k) {
        int ans = Integer.MAX_VALUE;
        int n = head;
        while (n != 0) {
            if (key[n] < k) {
                n = rc[n];
            } else {
                ans = Math.min(ans, key[n]);
                n = lc[n];
            }
        }
        return ans;
    }

    public int firstKey() {
        int n = head;
        while (lc[n] != 0) {
            n = lc[n];
        }
        return key[n];
    }

    public int lastKey() {
        int n = head;
        while (rc[n] != 0) {
            n = rc[n];
        }
        return key[n];
    }

    public int rank(int k) {
        return lessCount(k) + 1;
    }

    public int rankKey(int rk) {
        int i = head;
        while (i != 0) {
            if (sz[lc[i]] >= rk) {
                i = lc[i];
            } else if (sz[lc[i]] + 1 < rk) {
                rk -= sz[lc[i]] + 1;
                i = rc[i];
            } else {
                return key[i];
            }
        }
        return Integer.MIN_VALUE;
    }

    public int lessCount(int k) {
        int cnt = 0;
        int i = head;
        while (i != 0) {
            if (key[i] < k) {
                cnt += sz[lc[i]] + 1;
                i = rc[i];
            } else {
                i = lc[i];
            }
        }
        return cnt;
    }

    public int[][] view() {
        int[][] arr = new int[sz[head]][2];
        inorder(arr, head, 0);
        return arr;
    }

    private int inorder(int[][] arr, int i, int c) {
        if (i == 0) {
            return c;
        }
        c = inorder(arr, lc[i], c);
        arr[c][0] = key[i];
        arr[c++][1] = val[i];
        return inorder(arr, rc[i], c);
    }

    public void put(int k, int v) {
        int p = 0, n = head;
        while (n != 0) {
            if (key[n] > k) {
                p = n;
                n = lc[n];
            } else if (key[n] < k) {
                p = n;
                n = rc[n];
            } else {
                val[n] = v;
                return;
            }
        }
        n = create(k, v);
        if (p == 0) { // 新插入节点是根节点
            head = n;
            color[head] = BLACK;
            return;
        }
        if (key[p] > k) {
            lc[p] = n;
        } else {
            rc[p] = n;
        }
        fa[n] = p;
        maintainFa(n, 1);
        insertFixup(n);
    }

    public void add(int k, int v) {
        int p = 0, n = head;
        while (n != 0) {
            if (key[n] > k) {
                p = n;
                n = lc[n];
            } else if (key[n] < k) {
                p = n;
                n = rc[n];
            } else {
                val[n] += v;
                return;
            }
        }
        n = create(k, v);
        if (p == 0) { // 新插入节点是根节点
            head = n;
            color[head] = BLACK;
            return;
        }
        if (key[p] > k) {
            lc[p] = n;
        } else {
            rc[p] = n;
        }
        fa[n] = p;
        maintainFa(n, 1);
        insertFixup(n);
    }

    public void setMax(int k, int v) {
        int p = 0, n = head;
        while (n != 0) {
            if (key[n] > k) {
                p = n;
                n = lc[n];
            } else if (key[n] < k) {
                p = n;
                n = rc[n];
            } else {
                val[n] = Math.max(val[n], v);
                return;
            }
        }
        n = create(k, v);
        if (p == 0) { // 新插入节点是根节点
            head = n;
            color[head] = BLACK;
            return;
        }
        if (key[p] > k) {
            lc[p] = n;
        } else {
            rc[p] = n;
        }
        fa[n] = p;
        maintainFa(n, 1);
        insertFixup(n);
    }

    public void setMin(int k, int v) {
        int p = 0, n = head;
        while (n != 0) {
            if (key[n] > k) {
                p = n;
                n = lc[n];
            } else if (key[n] < k) {
                p = n;
                n = rc[n];
            } else {
                val[n] = Math.min(val[n], v);
                return;
            }
        }
        n = create(k, v);
        if (p == 0) { // 新插入节点是根节点
            head = n;
            color[head] = BLACK;
            return;
        }
        if (key[p] > k) {
            lc[p] = n;
        } else {
            rc[p] = n;
        }
        fa[n] = p;
        maintainFa(n, 1);
        insertFixup(n);
    }

    private void insertFixup(int n) {
        for (int p, g, u; color[p = fa[n]] == RED;) {
            g = fa[p];
            u = lc[g] == p ? rc[g] : lc[g];
            if (color[u] == RED) {
                // u 是红色
                color[p] = color[u] = BLACK;
                color[g] = RED;
                n = g;
            } else if (lc[g] == p) {
                if (lc[p] == n) {
                    // u 是黑色，p 是 g 的左儿子，n 是 p 的左儿子（左左）
                    color[p] = BLACK;
                    color[g] = RED;
                    rotateRight(g);
                    break;
                }
                // u 是黑色，p 是 g 的左儿子，n 是 p 的右儿子（左右）
                rotateLeft(p);
                n = p;
            } else if (rc[g] == p) {
                if (rc[p] == n) {
                    // u 是黑色，p 是 g 的右儿子，n 是 p 的右儿子（右右）
                    color[p] = BLACK;
                    color[g] = RED;
                    rotateLeft(g);
                    break;
                }
                // u 是黑色，p 是 g 的右儿子，n 是 p 的左儿子（右左）
                rotateRight(p);
                n = p;
            } 
        }
        color[head] = BLACK;
    }

    public void remove(int k) {
        int x = head;
        while (x != 0 && key[x] != k) {
            x = key[x] > k ? lc[x] : rc[x];
        }
        if (x == 0) {
            return;
        }
        if (lc[x] != 0 && rc[x] != 0) {
            int nx = rc[x];
            while (lc[nx] != 0) {
                nx = lc[nx];
            }
            key[x] = key[nx];
            x = nx;
        }
        int n = lc[x] != 0 ? lc[x] : rc[x], p = fa[x];
        if (n != 0) {
            fa[n] = p;
            if (p == 0) {
                head = n;
            } else if (x == lc[p]) {
                lc[p] = n;
            } else {
                rc[p] = n;
            }
            maintainFa(n, -1);
            if (color[x] == BLACK) {
                deleteFixUp(n);
            }
        } else if (fa[x] == 0) {
            // 删除根节点
            head = 0;
        } else {
            if (color[x] == BLACK) {
                deleteFixUp(x);
            }
            maintainFa(x, -1);
            p = fa[x];
            if (lc[p] == x) {
                lc[p] = 0;
            } else {
                rc[p] = 0;
            }
        }
    }

    private void deleteFixUp(int x) {
        while (x != head && color[x] == BLACK) {
            if (x == lc[fa[x]]) {
                int sib = rc[fa[x]];
                if (color[sib] == RED) {
                    color[sib] = BLACK;
                    color[fa[x]] = RED;
                    rotateLeft(fa[x]);
                    sib = rc[fa[x]];
                }
                if (color[lc[sib]] == BLACK && color[rc[sib]] == BLACK) {
                    color[sib] = RED;
                    x = fa[x];
                } else {
                    if (color[rc[sib]] == BLACK) {
                        color[lc[sib]] = BLACK;
                        color[sib] = RED;
                        rotateRight(sib);
                        sib = rc[fa[x]];
                    }
                    color[sib] = color[fa[x]];
                    color[fa[x]] = BLACK;
                    color[rc[sib]] = BLACK;
                    rotateLeft(fa[x]);
                    x = head;
                }
            } else {
                int sib = lc[fa[x]];
                if (color[sib] == RED) {
                    color[sib] = BLACK;
                    color[fa[x]] = RED;
                    rotateRight(fa[x]);
                    sib = lc[fa[x]];
                }
                if (color[rc[sib]] == BLACK && color[lc[sib]] == BLACK) {
                    color[sib] = RED;
                    x = fa[x];
                } else {
                    if (color[lc[sib]] == BLACK) {
                        color[rc[sib]] = BLACK;
                        color[sib] = RED;
                        rotateLeft(sib);
                        sib = lc[fa[x]];
                    }
                    color[sib] = color[fa[x]];
                    color[fa[x]] = BLACK;
                    color[lc[sib]] = BLACK;
                    rotateRight(fa[x]);
                    x = head;
                }
            }
        }
        color[x] = BLACK;
    }

    private void maintainFa(int o, int v) {
        o = fa[o];
        while (o != 0) {
            sz[o] += v;
            o = fa[o];
        }
    }

    private void rotateLeft(int o) {
        int f = fa[o], r = rc[o], rl = lc[r];
        if (f == 0) {
            head = r;
        } else if (lc[f] == o) {
            lc[f] = r;
        } else {
            rc[f] = r;
        }
        fa[r] = f;
        lc[r] = o;
        fa[o] = r;
        rc[o] = rl;
        fa[rl] = o;
        sz[r] = sz[o];
        sz[o] = sz[lc[o]] + sz[rl] + 1;
    }

    private void rotateRight(int o) {
        int f = fa[o], l = lc[o], lr = rc[l];
        if (f == 0) {
            head = l;
        } else if (lc[f] == o) {
            lc[f] = l;
        } else {
            rc[f] = l;
        }
        fa[l] = f;
        rc[l] = o;
        fa[o] = l;
        lc[o] = lr;
        fa[lr] = o;
        sz[l] = sz[o];
        sz[o] = sz[lr] + sz[rc[o]] + 1;
    }

    private int create(int k, int v) {
        key[++no] = k;
        val[no] = v;
        color[no] = RED;
        sz[no] = 1;
        return no;
    }
}

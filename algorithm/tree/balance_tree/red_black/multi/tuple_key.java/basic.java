class RedBlackTree2 {
    private static final boolean RED = false, BLACK = true;
    private static final int MAXT = 1000000;
    private static int[] lc = new int[MAXT], rc = new int[MAXT], fa = new int[MAXT];
    private static boolean[] color = new boolean[MAXT];
    static {
        color[0] = BLACK;// 空节点颜色为黑
    }

    static int[] key1 = new int[MAXT + 1], key2 = new int[MAXT + 1];
    static int no;

    private static int cmp(int o1, int o2) {
        return key1[o1] != key1[o2] ? key1[o2] - key1[o1] : key2[o2] - key2[o1];
    }

    private static void load(int k1, int k2) {
        key1[MAXT] = k1;
        key2[MAXT] = k2;
    }

    public static void clear() {
        for (int i = 1; i <= no; i++) {
            lc[i] = rc[i] = fa[i] = 0;
        }
        no = 0;
    }

    private int head;// 头节点编号

    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(int k1, int k2) {
        load(k1, k2);
        int n = head;
        while (n != 0) {
            if (cmp(n, MAXT) > 0) {
                n = lc[n];
            } else if (cmp(n, MAXT) < 0) {
                n = rc[n];
            } else {
                return true;
            }
        }
        return false;
    }

    public int ceiling(int k1, int k2) {
        load(k1, k2);
        int n = head;
        int ans = 0;
        while (n != 0) {
            if (cmp(n, MAXT) >= 0) {
                ans = n;
                n = lc[n];
            } else {
                n = rc[n];
            }
        }
        return ans;
    }

    public int floor(int k1, int k2) {
        load(k1, k2);
        int n = head;
        int ans = 0;
        while (n != 0) {
            if (cmp(n, MAXT) <= 0) {
                ans = n;
                n = rc[n];
            } else {
                n = lc[n];
            }
        }
        return ans;
    }

    public int first() {
        int n = head;
        while (lc[n] != 0) {
            n = lc[n];
        }
        return n;
    }

    public int last() {
        int n = head;
        while (rc[n] != 0) {
            n = rc[n];
        }
        return n;
    }

    public int pollFirst() {
        int o = first();
        remove(key1[o], key2[o]);
        return MAXT;
    }

    public int pollLast() {
        int o = last();
        remove(key1[o], key2[o]);
        return MAXT;
    }

    public int[] view() {
        int[] arr = new int[size];
        inorder(arr, head, 0);
        return arr;
    }

    private int inorder(int[] arr, int i, int c) {
        if (i == 0) {
            return c;
        }
        c = inorder(arr, lc[i], c);
        arr[c++] = i;
        return inorder(arr, rc[i], c);
    }

    public void add(int k1, int k2) {
        load(k1, k2);
        int p = 0, n = head;
        while (n != 0) {
            if (cmp(n, MAXT) > 0) {
                p = n;
                n = lc[n];
            } else {
                p = n;
                n = rc[n];
            }
        }
        size++;
        n = create(k1, k2);
        if (p == 0) { // 新插入节点是根节点
            head = n;
            color[head] = BLACK;
            return;
        }
        if (cmp(p, MAXT) > 0) {
            lc[p] = n;
        } else {
            rc[p] = n;
        }
        fa[n] = p;
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

    public void remove(int k1, int k2) {
        load(k1, k2);
        int x = head;
        while (x != 0) {
            int c = cmp(x, MAXT);
            if (c == 0) {
                break;
            }
            x = c > 0 ? lc[x] : rc[x];
        }
        if (x == 0) {
            return;
        }
        size--;
        if (lc[x] != 0 && rc[x] != 0) {
            int nx = rc[x];
            while (lc[nx] != 0) {
                nx = lc[nx];
            }
            key1[x] = key1[nx];
            key2[x] = key2[nx];
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
    }

    private int create(int k1, int k2) {
        key1[++no] = k1;
        key2[no] = k2;
        color[no] = RED;
        return no;
    }
}

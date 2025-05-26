class Map {
    private static final int NULL = Integer.MIN_VALUE, MAXN = 1 << 20, MOD = MAXN - 1;
    private static int[] keys = new int[MAXN], vals = new int[MAXN];
    private static int[] head = new int[MAXN], nxt = new int[MAXN];
    private static int no, size;
    static {
        for (int i = 0; i < MAXN; i++) {
            head[i] = -1;
            keys[i] = NULL;
        }
    }

    private final int RND;

    public Map() {
        RND = (int) (Integer.MAX_VALUE * Math.random());
        clear();
    }

    public int size() {
        return size;
    }
    
    public boolean containsKey(int key) {
        return get(key) != NULL; 
    }
    
    public void remove(int key) {
        int u = hash(key);
        for (int i = head[u], p = -1; i != -1; p = i, i = nxt[i]) {
            if (keys[i] == key) {
                keys[i] = NULL;
                size--;
                if (p != -1) {
                    nxt[p] = nxt[i];
                } else {
                    head[u] = nxt[i];
                }
                return;
            }
        }
    }

    public int get(int key) {
        for (int i = head[hash(key)]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                return vals[i];
            }
        }
        return NULL;
    }

    public int getOrDefault(int key, int defaultValue) {
        for (int i = head[hash(key)]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                return vals[i];
            }
        }
        return defaultValue;
    }

    public void put(int key, int val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                vals[i] = val;
                return;
            }
        }
        create(u, key, val);
    }

    public void putIfAbsent(int key, int val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                return;
            }
        }
        create(u, key, val);
    }

    public void setMax(int key, int val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                vals[i] = Math.max(vals[i], val);
                return;
            }
        }
        create(u, key, val);
    }

    public void setMin(int key, int val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                vals[i] = Math.min(vals[i], val);
                return;
            }
        }
        create(u, key, val);
    }
    
    public void add(int key, int val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                vals[i] += val;
                return;
            }
        }
        create(u, key, val);
    }

    public int[][] view() {
        int[][] tup = new int[size][2];
        for (int i = 0, j = 0; i < no; i++) {
            if (keys[i] != NULL) {
                tup[j][0] = keys[i];
                tup[j++][1] = vals[i];
            }
        }
        return tup;
    }

    public void clear() {
        for (int i = 0; i < no; i++) {
            if (keys[i] != NULL) {
                head[hash(keys[i])] = -1;
                keys[i] = NULL;
            }
        }
        no = 0;
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int[][] tup = view();
        sb.append("(" + tup[0][0] + ", " + tup[0][1]);
        for (int i = 1; i < size; i++) {
            sb.append("), (" + tup[i][0] + ", " + tup[i][1]);
        }
        sb.append(")}");
        return sb.toString();
    }
    
    private void create(int u, int k, int v) {
        nxt[no] = head[u];
        head[u] = no;
        keys[no] = k;
        vals[no] = v;
        size++;
        no++;
    }

    private int hash(int key) {
        key ^= RND;
        return (key ^ (key >>> 16)) & MOD;
    }
}

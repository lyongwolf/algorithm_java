class MapLong {
    private static final long NULL = Long.MIN_VALUE;
    private static final int MAXN = 1 << 20, MOD = MAXN - 1;
    private static long[] keys = new long[MAXN], vals = new long[MAXN];
    private static int[] head = new int[MAXN], nxt = new int[MAXN];
    private static int no, size;
    static {
        for (int i = 0; i < MAXN; i++) {
            head[i] = -1;
            keys[i] = NULL;
        }
    }

    private final long RND;

    public MapLong() {
        RND = (long) (Long.MAX_VALUE * Math.random());
        clear();
    }

    public int size() {
        return size;
    }
    
    public boolean containsKey(long key) {
        return get(key) != NULL; 
    }
    
    public void remove(long key) {
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

    public long get(long key) {
        for (int i = head[hash(key)]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                return vals[i];
            }
        }
        return NULL;
    }

    public long getOrDefault(long key, long defaultValue) {
        for (int i = head[hash(key)]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                return vals[i];
            }
        }
        return defaultValue;
    }

    public void put(long key, long val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                vals[i] = val;
                return;
            }
        }
        create(u, key, val);
    }

    public void putIfAbsent(long key, long val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                return;
            }
        }
        create(u, key, val);
    }

    public void merge(long key, long val, java.util.function.LongBinaryOperator op) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                vals[i] = op.applyAsLong(vals[i], val);
                return;
            }
        }
        create(u, key, val);
    }

    public long[][] view() {
        long[][] tup = new long[size][2];
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
        sb.append("{\n");
        long[][] tup = view();
        sb.append("" + tup[0][0] + " = " + tup[0][1]);
        for (int i = 1; i < size; i++) {
            sb.append(",\n" + tup[i][0] + " = " + tup[i][1]);
        }
        sb.append("\n}");
        return sb.toString();
    }
    
    private void create(int u, long k, long v) {
        nxt[no] = head[u];
        head[u] = no;
        keys[no] = k;
        vals[no] = v;
        size++;
        no++;
    }

    private int hash(long key) {
        key ^= RND;
        return (int) (key ^ (key >>> 32)) & MOD;
    }
}

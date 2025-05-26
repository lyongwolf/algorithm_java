class MultiSet {
    private int[] keys;
    private int[] vals;
    private int[] head, nxt;
    private int size, type, no;
    private final int m;

    public MultiSet(int n) {
        keys = new int[n];
        vals = new int[n];
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        m = n++;
        head = new int[n];
        nxt = new int[n];
        java.util.Arrays.fill(head, -1);
    }

    public int size() {
        return size;
    }

    public int type() {
        return type;
    }
    
    public boolean contains(int key) {
        int u = hash(key);
        for (int i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                return vals[i] > 0;
            }
        }
        return false;
    }
    
    public void remove(int key) {
        int u = hash(key);
        for (int i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                if (vals[i] > 0) {
                    size--;
                    if (--vals[i] == 0) {
                        type--;
                    }
                }
                return;
            }
        }
    }
    
    public void add(int key) {
        int u = hash(key);
        for (int i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                size++;
                if (++vals[i] == 1) {
                    type++;
                }
                return;
            }
        }
        add(u, key);
    }

    public int get(int key) {
        int u = hash(key);
        for (int i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                return vals[i];
            }
        }
        return 0;
    }
    
    private int hash(int key) {
        return (key ^ (key >>> 16)) & m;
    }

    private void add(int u, int key) {
        keys[no] = key;
        vals[no] = 1;
        nxt[no] = head[u];
        head[u] = no++;
        size++;
        type++;
    }
}

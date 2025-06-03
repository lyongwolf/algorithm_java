import java.util.*;

// 拉链法
class HashMap<K, V> {
    private static class Node<K, V> {
        K key;
        V val;
        Node<K, V> nxt;
        
        Node(K key, V val, Node<K, V> nxt) {
            this.key = key;
            this.val = val;
            this.nxt = nxt;
        }
    }
    private Node<K, V>[] table;
    private final int m;
    private int size;

    public HashMap(int n) {
        n--;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        m = n;
        table = new Node[n + 1];
    }

    public int size() {
        return size;
    }
    
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    public void put(K key, V val) {
        int idx = hash(key);
        Node<K, V> cur = table[idx];
        while (cur != null) {
            if (cur.key.equals(key)) {
                cur.val = val;
                return;
            }
            cur = cur.nxt;
        }
        table[idx] = new Node<>(key, val, table[idx]);
        size++;
    }
    
    public void putIfAbsent(K key, V val) {
        int idx = hash(key);
        Node<K, V> cur = table[idx];
        while (cur != null) {
            if (cur.key.equals(key)) {
                return;
            }
            cur = cur.nxt;
        }
        table[idx] = new Node<>(key, val, table[idx]);
        size++;
    }

    public V get(K key) {
        int idx = hash(key);
        Node<K, V> cur = table[idx];
        while (cur != null) {
            if (cur.key.equals(key)) {
                return cur.val;
            }
            cur = cur.nxt;
        }
        return null;
    }
    
    public V getOrDefault(K key, V dVal) {
        V val = get(key);
        return val != null ? val : dVal;
    }
    
    public V remove(K key) {
        int idx = hash(key);
        Node<K, V> cur = table[idx];
        Node<K, V> pre = null;
        while (cur != null) {
            if (cur.key.equals(key)) {
                if (pre == null) {
                    table[idx] = cur.nxt;
                } else {
                    pre.nxt = cur.nxt;
                }
                size--;
                return cur.val;
            }
            pre = cur;
            cur = cur.nxt;
        }
        return null;
    }
    
    public V merge(K key, V val, java.util.function.BiFunction<V, V, V> func) {
        int idx = hash(key);
        Node<K, V> cur = table[idx];
        while (cur != null) {
            if (cur.key.equals(key)) {
                return cur.val = func.apply(cur.val, val);
            }
            cur = cur.nxt;
        }
        table[idx] = new Node<>(key, val, table[idx]);
        size++;
        return val;
    }
    
    public V computeIfAbsent(K key, java.util.function.Function<K, V> func) {
        V value = get(key);
        if (value == null) {
            int h = hash(key);
            table[h] = new Node<>(key, value = func.apply(key), table[h]);
            size++;
        }
        return value;
    }

    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                keySet.add(node.key);
                node = node.nxt;
            }
        }
        return keySet;
    }
    
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (Node<K, V> cur : table) {
            while (cur != null) {
                values.add(cur.val);
                cur = cur.nxt;
            }
        }
        return values;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    private int hash(K key) {
        int h = key.hashCode();
        return (h ^ (h >>> 16)) & m;
    }
}

// --------------------------------------------------------------------------------

// 拉链法 + 前向星
class HashMap2<K, V> {
    private K[] keys;
    private V[] vals;
    private int[] head, nxt;
    private int size, no;
    private final int m;

    public HashMap2(int n) {
        keys = (K[]) new Object[n];
        vals = (V[]) new Object[n];
        n--;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        m = n++;
        head = new int[n];
        nxt = new int[n];
        Arrays.fill(head, -1);
    }

    public int size() {
        return size;
    }
    
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    public void put(K key, V val) {
        int u = hash(key);
        for (int i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        add(u, key, val);
    }
    
    public void putIfAbsent(K key, V val) {
        int u = hash(key);
        for (int i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i].equals(key)) {
                return;
            }
        }
        add(u, key, val);
    }

    public V get(K key) {
        int u = hash(key);
        for (int i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }
    
    public V getOrDefault(K key, V dVal) {
        V val = get(key);
        return val != null ? val : dVal;
    }
    
    public V remove(K key) {
        int u = hash(key);
        for (int i = head[u], p = -1; i != -1; p = i, i = nxt[i]) {
            if (keys[i].equals(key)) {
                keys[i] = null;
                vals[i] = null;
                if (p == -1) {
                    head[u] = nxt[i];
                } else {
                    nxt[p] = nxt[i];
                }
                size--;
                return vals[i];
            }
        }
        return null;
    }
    
    public V merge(K key, V val, java.util.function.BiFunction<V, V, V> func) {
        int u = hash(key);
        for (int i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i].equals(key)) {
                return vals[i] = func.apply(vals[i], val);
            }
        }
        add(u, key, val);
        return val;
    }
    
    public V computeIfAbsent(K key, java.util.function.Function<K, V> func) {
        V val = get(key);
        if (val == null) {
            add(hash(key), key, val = func.apply(key));
        }
        return val;
    }

    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (int i = 0; i < no; i++) {
            if (keys[i] != null) {
                keySet.add(keys[i]);
            }
        }
        return keySet;
    }
    
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (int i = 0; i < no; i++) {
            if (keys[i] != null) {
                values.add(vals[i]);
            }
        }
        return values;
    }

    public void clear() {
        Arrays.fill(head, -1);
        no = 0;
        size = 0;
    }

    private int hash(K key) {
        int h = key.hashCode();
        return (h ^ (h >>> 16)) & m;
    }

    private void add(int u, K key, V val) {
        keys[no] = key;
        vals[no] = val;
        nxt[no] = head[u];
        head[u] = no++;
        size++;
    }
}

class MapInt {
    private static final int NULL = Integer.MIN_VALUE;
    private static final float FACTOR = 0.75f;
    private int[] keys, vals;
    private int[] head, nxt;
    private int threshold;
    private int no;
    private int size;

    public MapInt() {
        this(15);
    }

    public MapInt(int n) {
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        n++;
        threshold = (int) (n * FACTOR);
        keys = new int[n];
        vals = new int[n];
        head = new int[n];
        nxt = new int[n];
        Arrays.fill(head, -1);
        Arrays.fill(keys, NULL);
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
        if (++no > threshold) {
            int n = head.length << 1;
            int[] tkeys = new int[n], tvals = new int[n];
            head = new int[n];
            nxt = new int[n];
            Arrays.fill(head, -1);
            for (int i = 0, j = 0; i < no; i++) {
                if (keys[i] != NULL) {
                    u = hash(keys[i]);
                    nxt[j] = head[u];
                    head[u] = j;
                    tkeys[j] = keys[i];
                    tvals[j] = vals[i];
                    j++;
                }
            }
            no = size;
            for (int i = no; i < n; i++) {
                tkeys[i] = NULL;
            }
            keys = tkeys;
            vals = tvals;
            threshold = (int) (n * FACTOR);
        }
    }

    private int hash(int key) {
        return (key ^ (key >>> 16)) & (head.length - 1);
    }
}

class MapLong {
    private static final long NULL = Long.MIN_VALUE;
    private static final float FACTOR = 0.75f;
    private long[] keys, vals;
    private int[] head, nxt;
    private int threshold;
    private int no;
    private int size;

    public MapLong() {
        this(15);
    }

    public MapLong(int n) {
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        n++;
        threshold = (int) (n * FACTOR);
        keys = new long[n];
        vals = new long[n];
        head = new int[n];
        nxt = new int[n];
        Arrays.fill(head, -1);
        Arrays.fill(keys, NULL);
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

    public void setMax(long key, long val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                vals[i] = Math.max(vals[i], val);
                return;
            }
        }
        create(u, key, val);
    }

    public void setMin(long key, long val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                vals[i] = Math.min(vals[i], val);
                return;
            }
        }
        create(u, key, val);
    }
    
    public void add(long key, long val) {
        int u = hash(key), i;
        for (i = head[u]; i != -1; i = nxt[i]) {
            if (keys[i] == key) {
                vals[i] += val;
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
        sb.append("{");
        long[][] tup = view();
        sb.append("(" + tup[0][0] + ", " + tup[0][1]);
        for (int i = 1; i < size; i++) {
            sb.append("), (" + tup[i][0] + ", " + tup[i][1]);
        }
        sb.append(")}");
        return sb.toString();
    }
    
    private void create(int u, long k, long v) {
        nxt[no] = head[u];
        head[u] = no;
        keys[no] = k;
        vals[no] = v;
        size++;
        if (++no > threshold) {
            int n = head.length << 1;
            long[] tkeys = new long[n], tvals = new long[n];
            head = new int[n];
            nxt = new int[n];
            Arrays.fill(head, -1);
            for (int i = 0, j = 0; i < no; i++) {
                if (keys[i] != NULL) {
                    u = hash(keys[i]);
                    nxt[j] = head[u];
                    head[u] = j;
                    tkeys[j] = keys[i];
                    tvals[j] = vals[i];
                    j++;
                }
            }
            no = size;
            for (int i = no; i < n; i++) {
                tkeys[i] = NULL;
            }
            keys = tkeys;
            vals = tvals;
            threshold = (int) (n * FACTOR);
        }
    }

    private int hash(long key) {
        int h = (int) (key ^ (key >>> 32));
        return (h ^ (h >>> 16)) & (head.length - 1);
    }
}
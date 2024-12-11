package algorithm.util;

import java.util.*;

public class MyHashMap {

    // 拉链法
    static class HashMap<K, V> {
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
    static class HashMap2<K, V> {
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

}
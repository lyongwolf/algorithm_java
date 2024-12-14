package algorithm.util.set;

import java.util.Arrays;

public class MultiSet {
    
    class MultisetInteger {
        private int[] keys;
        private int[] vals;
        private int[] head, nxt;
        private int size, type, no;
        private final int m;

        public MultisetInteger(int n) {
            keys = new int[n];
            vals = new int[n];
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
        
        private int hash(Integer key) {
            int h = key.hashCode();
            return (h ^ (h >>> 16)) & m;
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

    class MultisetLong {
        private long[] keys;
        private int[] vals;
        private int[] head, nxt;
        private int size, type, no;
        private final int m;

        public MultisetLong(int n) {
            keys = new long[n];
            vals = new int[n];
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

        public int type() {
            return type;
        }
        
        public boolean contains(long key) {
            int u = hash(key);
            for (int i = head[u]; i != -1; i = nxt[i]) {
                if (keys[i] == key) {
                    return vals[i] > 0;
                }
            }
            return false;
        }
        
        public void remove(long key) {
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
        
        public void add(long key) {
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
        
        private int hash(Long key) {
            int h = key.hashCode();
            return (h ^ (h >>> 16)) & m;
        }

        private void add(int u, long key) {
            keys[no] = key;
            vals[no] = 1;
            nxt[no] = head[u];
            head[u] = no++;
            size++;
            type++;
        }
    }

}

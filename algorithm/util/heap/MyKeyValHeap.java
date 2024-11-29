import java.util.Arrays;

public class MyKeyValHeap {
    
    static class KVHeap {
        private int[] key, val, idx;
        private int size;
        
        // 按照 val 大小维护堆
        // key 为该 val 对应的主键
        // 堆中的 key 不重复，key 对应的 val 总是最新的
        // n 为 key 的值域 [0, n-1]
        // idx[k] 表示 k 在堆数组中的下标
        public KVHeap(int n) {
            val = new int[n];
            key = new int[n];
            idx = new int[n];
            Arrays.fill(idx, -1);
        }
 
        public boolean isEmpty() {
            return size == 0;
        }

        public boolean containsKey(int k) {
            return idx[k] != -1;
        }
 
        public int size() {
            return size;
        }
 
        public void clear() {
            size = 0;
        }
 
        public int[] peek() {
            return new int[]{key[0], val[0]};
        }
 
        public int[] poll() {
            swap(0, --size);
            heapify(0);
            idx[key[size]] = -1;
            return new int[]{key[size], val[size]};
        }

        public int topKey() {
            return key[0];
        }

        public int topVal() {
            return val[0];
        }

        public int get(int k) {
            return val[idx[k]];
        }
 
        public void set(int k, int v) {
            if (idx[k] != -1) {
                int i = idx[k];
                val[i] = v;
                heapify(i);
                heapInsert(i);
                return;
            }
            idx[k] = size;
            key[size] = k;
            val[size++] = v;
            heapInsert(size - 1);
        }
 
        public void remove(int k) {
            int i = idx[k];
            if (i != -1) {
                swap(i, --size);
                heapify(i);
                heapInsert(i);
                idx[k] = -1;
            }
        }
 
        private void heapInsert(int i) {
            while (val[i] < val[(i - 1) / 2]) {
                swap(i, (i - 1) / 2);
                i = (i - 1) / 2;
            }
        }
 
        private void heapify(int i) {
            int l = i * 2 + 1;
            while (l < size) {
                int b = l + 1 < size && val[l + 1] < val[l] ? l + 1 : l;
                b = val[b] < val[i] ? b : i;
                if (b == i) {
                    break;
                }
                swap(b, i);
                i = b;
                l = i * 2 + 1;
            }
        }
 
        private void swap(int i, int j) {
            val[i] ^= val[j] ^ (val[j] = val[i]);
            key[i] ^= key[j] ^ (key[j] = key[i]);
            idx[key[i]] = i;
            idx[key[j]] = j;
        }
    }

}

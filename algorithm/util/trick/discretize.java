package algorithm.util.trick;

import java.util.Arrays;

public class discretize {
    
    class Discretize {
        private int[] keys, vals, head, nxt;
        private int m, no;

        public int discretize(int[] nums) {
            int n = nums.length, m = 0;
            init(n);
            int[] val = nums.clone();
            Arrays.sort(val);
            for (int i = 0, j = i; i < n; i = j) {
                while (j < n && val[i] == val[j]) {
                    j++;
                }
            put(val[i], m++);
            }
            for (int i = 0; i < n; i++) {
                nums[i] = get(nums[i]);
            }
            return m;
        }

        private void init(int n) {
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

        private int get(int k) {
            for (int i = head[hash(k)]; ; i = nxt[i]) {
                if (keys[i] == k) {
                    return vals[i];
                }
            }
        }
        
        private void put(int k, int v) {
            int u = hash(k);
            keys[no] = k;
            vals[no] = v;
            nxt[no] = head[u];
            head[u] = no++;
        }
        
        private int hash(Integer key) {
            int h = key.hashCode();
            return (h ^ (h >>> 16)) & m;
        }
    }

}

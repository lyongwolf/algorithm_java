package algorithm.util.heap;

import java.util.*;

public class MyDualHeap {

    class DualHeap<E> {
        private TreeSet<E> topK, rest;
        private Comparator<E> cmp;
        private int k;
    
        public DualHeap(int k, Comparator<E> cmp) {
            this.k = k;
            this.cmp = cmp;
            topK = new TreeSet<>((a, b) -> cmp.compare(a, b));
            rest = new TreeSet<>((a, b) -> cmp.compare(a, b));
        }
    
        public void add(E e) {
            if (topK.size() < k) {
                topK.add(e);
                return;
            }
            E last = topK.last();
            if (cmp.compare(e, last) < 0) {
                rest.add(topK.pollLast());
                topK.add(e);
            } else {
                rest.add(e);
            }
        }
    
        public void remove(E e) {
            if (rest.contains(e)) {
                rest.remove(e);
                return;
            }
            if (!topK.contains(e)) {
                return;
            }
            topK.remove(e);
            if (!rest.isEmpty()) {
                topK.add(rest.pollFirst());
            }
        }
    }

    static class DualHeapInteger {
        private TreeSet<Integer> topK, rest;
        private Comparator<Integer> cmp;
        private int k;
    
        public DualHeapInteger(int k, Comparator<Integer> cmp) {
            this.k = k;
            this.cmp = cmp;
            topK = new TreeSet<>((a, b) -> cmp.compare(a, b));
            rest = new TreeSet<>((a, b) -> cmp.compare(a, b));
        }
    
        public void add(int e) {
            if (topK.size() < k) {
                topK.add(e);
                return;
            }
            int last = topK.last();
            if (cmp.compare(e, last) < 0) {
                rest.add(topK.pollLast());
                topK.add(e);
            } else {
                rest.add(e);
            }
        }
    
        public void remove(int e) {
            if (rest.contains(e)) {
                rest.remove(e);
                return;
            }
            if (!topK.contains(e)) {
                return;
            }
            topK.remove(e);
            if (!rest.isEmpty()) {
                topK.add(rest.pollFirst());
            }
        }
    }

    class DualHeapLong {
        private TreeSet<Long> topK, rest;
        private Comparator<Long> cmp;
        private int k;
    
        public DualHeapLong(int k, Comparator<Long> cmp) {
            this.k = k;
            this.cmp = cmp;
            topK = new TreeSet<>((a, b) -> cmp.compare(a, b));
            rest = new TreeSet<>((a, b) -> cmp.compare(a, b));
        }
    
        public void add(long e) {
            if (topK.size() < k) {
                topK.add(e);
                return;
            }
            long last = topK.last();
            if (cmp.compare(e, last) < 0) {
                rest.add(topK.pollLast());
                topK.add(e);
            } else {
                rest.add(e);
            }
        }
    
        public void remove(long e) {
            if (rest.contains(e)) {
                rest.remove(e);
                return;
            }
            if (!topK.contains(e)) {
                return;
            }
            topK.remove(e);
            if (!rest.isEmpty()) {
                topK.add(rest.pollFirst());
            }
        }
    }

    class DualHeapIntegerArray {
        private TreeSet<int[]> topK, rest;
        private Comparator<int[]> cmp;
        private int k;
    
        public DualHeapIntegerArray(int k, Comparator<int[]> cmp) {
            this.k = k;
            this.cmp = cmp;
            topK = new TreeSet<>((a, b) -> cmp.compare(a, b));
            rest = new TreeSet<>((a, b) -> cmp.compare(a, b));
        }
    
        public void add(int[] e) {
            if (topK.size() < k) {
                topK.add(e);
                return;
            }
            int[] last = topK.last();
            if (cmp.compare(e, last) < 0) {
                rest.add(topK.pollLast());
                topK.add(e);
            } else {
                rest.add(e);
            }
        }
    
        public void remove(int[] e) {
            if (rest.contains(e)) {
                rest.remove(e);
                return;
            }
            if (!topK.contains(e)) {
                return;
            }
            topK.remove(e);
            if (!rest.isEmpty()) {
                topK.add(rest.pollFirst());
            }
        }
    }

    class DualHeapLongArray {
        private TreeSet<long[]> topK, rest;
        private Comparator<long[]> cmp;
        private int k;
    
        public DualHeapLongArray(int k, Comparator<long[]> cmp) {
            this.k = k;
            this.cmp = cmp;
            topK = new TreeSet<>((a, b) -> cmp.compare(a, b));
            rest = new TreeSet<>((a, b) -> cmp.compare(a, b));
        }
    
        public void add(long[] e) {
            if (topK.size() < k) {
                topK.add(e);
                return;
            }
            long[] last = topK.last();
            if (cmp.compare(e, last) < 0) {
                rest.add(topK.pollLast());
                topK.add(e);
            } else {
                rest.add(e);
            }
        }
    
        public void remove(long[] e) {
            if (rest.contains(e)) {
                rest.remove(e);
                return;
            }
            if (!topK.contains(e)) {
                return;
            }
            topK.remove(e);
            if (!rest.isEmpty()) {
                topK.add(rest.pollFirst());
            }
        }
    }
}
package algorithm.util;

public class MyDeque {
    
    // 32 位 Deque
    static class DequeInteger {
        private int[] arr;
        private int head;
        private int tail;
        private int size;

        public DequeInteger(int len) {
            arr = new int[len << 1 | 1];
            head = len;
            tail = len - 1;
        }

        public void clear() {
            head = arr.length >> 1;
            tail = head - 1;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void addLast(int v) {
            size++;
            arr[++tail] = v;
        }

        public void addFirst(int v) {
            size++;
            arr[--head] = v;
        }

        public int removeFirst() {
            size--;
            return arr[head++];
        }

        public int removeLast() {
            size--;
            return arr[tail--];
        }
    }

     // 64 位 Deque
     static class DequeLong {
        private long[] arr;
        private int head;
        private int tail;
        private int size;

        public DequeLong(int len) {
            arr = new long[len << 1 | 1];
            head = len;
            tail = len - 1;
        }

        public void clear() {
            head = arr.length >> 1;
            tail = head - 1;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void addLast(long v) {
            size++;
            arr[++tail] = v;
        }

        public void addFirst(long v) {
            size++;
            arr[--head] = v;
        }

        public long removeFirst() {
            size--;
            return arr[head++];
        }

        public long removeLast() {
            size--;
            return arr[tail--];
        }
    }

    // 元组类型 Deque
    static class DequeTup {
        private int[][] arr;
        private int head;
        private int tail;
        private int size;

        public DequeTup(int len) {
            arr = new int[len << 1 | 1][];
            head = len;
            tail = len - 1;
        }

        public void clear() {
            head = arr.length >> 1;
            tail = head - 1;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void addLast(int[] v) {
            size++;
            arr[++tail] = v;
        }

        public void addFirst(int[] v) {
            size++;
            arr[--head] = v;
        }

        public int[] removeFirst() {
            size--;
            return arr[head++];
        }

        public int[] removeLast() {
            size--;
            return arr[tail--];
        }
    }

    // 泛型 Deque
    static class DequeGeneric<E> {
        private E[] arr;
        private int head;
        private int tail;
        private int size;

        public DequeGeneric(int len) {
            arr = (E[]) new Object[len];
            head = len;
            tail = len - 1;
        }

        public void clear() {
            head = arr.length >> 1;
            tail = head - 1;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void addLast(E v) {
            size++;
            arr[++tail] = v;
        }

        public void addFirst(E v) {
            size++;
            arr[--head] = v;
        }

        public E removeFirst() {
            size--;
            return arr[head++];
        }

        public E removeLast() {
            size--;
            return arr[tail--];
        }
    }
}

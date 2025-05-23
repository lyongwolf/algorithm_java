package algorithm.util.heap;

import java.util.*;

// 小根堆，32位整型
class HeapIntegerMin {
    private int[] arr;
    private int size;

    public HeapIntegerMin() {
        arr = new int[16];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    public int peek() {
        return arr[0];
    }

    public int poll() {
        swap(0, --size);
        heapify(0);
        return arr[size];
    }

    public void add(int v) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length << 1);
        }
        arr[size++] = v;
        heapInsert(size - 1);
    }

    private void heapInsert(int i) {
        while (arr[i] < arr[(i - 1) / 2]) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void heapify(int i) {
        int l = i * 2 + 1;
        while (l < size) {
            int b = l + 1 < size && arr[l + 1] < arr[l] ? l + 1 : l;
            b = arr[b] < arr[i] ? b : i;
            if (b == i) {
                break;
            }
            swap(b, i);
            i = b;
            l = i * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}

// 大根堆，32位整型
class HeapIntegerMax {
    private int[] arr;
    private int size;

    public HeapIntegerMax() {
        arr = new int[16];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    public int peek() {
        return arr[0];
    }

    public int poll() {
        swap(0, --size);
        heapify(0);
        return arr[size];
    }

    public void add(int v) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length << 1);
        }
        arr[size++] = v;
        heapInsert(size - 1);
    }

    private void heapInsert(int i) {
        while (arr[i] > arr[(i - 1) / 2]) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void heapify(int i) {
        int l = i * 2 + 1;
        while (l < size) {
            int b = l + 1 < size && arr[l + 1] > arr[l] ? l + 1 : l;
            b = arr[b] > arr[i] ? b : i;
            if (b == i) {
                break;
            }
            swap(b, i);
            i = b;
            l = i * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}

// 小根堆，64位整型
class HeapLongMin {
    private long[] arr;
    private int size;

    public HeapLongMin() {
        arr = new long[16];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    public long peek() {
        return arr[0];
    }

    public long poll() {
        swap(0, --size);
        heapify(0);
        return arr[size];
    }

    public void add(long v) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length << 1);
        }
        arr[size++] = v;
        heapInsert(size - 1);
    }

    private void heapInsert(int i) {
        while (arr[i] < arr[(i - 1) / 2]) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void heapify(int i) {
        int l = i * 2 + 1;
        while (l < size) {
            int b = l + 1 < size && arr[l + 1] < arr[l] ? l + 1 : l;
            b = arr[b] < arr[i] ? b : i;
            if (b == i) {
                break;
            }
            swap(b, i);
            i = b;
            l = i * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        long t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}

// 大根堆，64位整型
class HeapLongMax {
    private long[] arr;
    private int size;

    public HeapLongMax() {
        arr = new long[16];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    public long peek() {
        return arr[0];
    }

    public long poll() {
        swap(0, --size);
        heapify(0);
        return arr[size];
    }

    public void add(long v) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length << 1);
        }
        arr[size++] = v;
        heapInsert(size - 1);
    }

    private void heapInsert(int i) {
        while (arr[i] > arr[(i - 1) / 2]) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void heapify(int i) {
        int l = i * 2 + 1;
        while (l < size) {
            int b = l + 1 < size && arr[l + 1] > arr[l] ? l + 1 : l;
            b = arr[b] > arr[i] ? b : i;
            if (b == i) {
                break;
            }
            swap(b, i);
            i = b;
            l = i * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        long t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}

// 一般堆
class Heap<E> {
    private Comparator<? super E> comparator;
    private E[] arr;
    private int size;

    public Heap(Comparator<? super E> comparator) {
        this.comparator = comparator;
        arr = (E[]) new Object[16];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    public E peek() {
        return arr[0];
    }

    public E poll() {
        swap(0, --size);
        heapify(0);
        return arr[size];
    }

    public void add(E v) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length << 1);
        }
        arr[size++] = v;
        heapInsert(size - 1);
    }

    private void heapInsert(int i) {
        while (comparator.compare(arr[i], arr[(i - 1) / 2]) < 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void heapify(int i) {
        int l = i * 2 + 1;
        while (l < size) {
            int b = l + 1 < size && comparator.compare(arr[l + 1], arr[l]) < 0 ? l + 1 : l;
            b = comparator.compare(arr[b], arr[i]) < 0 ? b : i;
            if (b == i) {
                break;
            }
            swap(b, i);
            i = b;
            l = i * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        E t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
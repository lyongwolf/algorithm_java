class Heap2 {
    int[] first = new int[1], second = new int[1];// 0 下标为堆顶

    private int cmp(int i, int j) {
        return first[i] != first[j] ? first[i] - first[j] : second[i] - second[j];
    }
    
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    // 弹出堆顶，返回堆顶下标
    public int poll() {
        swap(0, --size);
        heapify(0);
        return size;
    }

    // 插入 {k1, k2}
    public void add(int k1, int k2) {
        if (size == first.length) {
            first = java.util.Arrays.copyOf(first, size << 1);
            second = java.util.Arrays.copyOf(second, size << 1);
        }
        first[size++] = k1;
        first[size] = k2;
        heapInsert(size - 1);
    }

    private void heapInsert(int i) {
        while (cmp(i, (i - 1) / 2) < 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void heapify(int i) {
        int l = i * 2 + 1;
        while (l < size) {
            int b = l + 1 < size && cmp(l + 1, l) < 0 ? l + 1 : l;
            b = cmp(b, l) < 0 ? b : i;
            if (b == i) {
                break;
            }
            swap(b, i);
            i = b;
            l = i * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        int t = first[i]; first[i] = first[j]; first[j] = t;
        t = second[i]; second[i] = second[j]; second[j] = t;
    }
}

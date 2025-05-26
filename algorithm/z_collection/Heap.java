class Heap {
    private int[] key = new int[1];
    private int size;

    private java.util.function.IntBinaryOperator cmp;

    public Heap() {
        cmp = Integer::compare;
    }

    public Heap(java.util.function.IntBinaryOperator cmp) {
        this.cmp = cmp;
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
        return key[0];
    }

    public int poll() {
        swap(0, --size);
        heapify(0);
        return key[size];
    }

    public void add(int k) {
        if (size == key.length) {
            key = java.util.Arrays.copyOf(key, key.length << 1);
        }
        key[size++] = k;
        heapInsert(size - 1);
    }

    private void heapInsert(int i) {
        while (cmp.applyAsInt(key[i], key[(i - 1) / 2]) < 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void heapify(int i) {
        int l = i * 2 + 1;
        while (l < size) {
            int b = l + 1 < size && cmp.applyAsInt(key[l + 1], key[l]) < 0 ? l + 1 : l;
            b = cmp.applyAsInt(key[b], key[i]) < 0 ? b : i;
            if (b == i) {
                break;
            }
            swap(b, i);
            i = b;
            l = i * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        int t = key[i];
        key[i] = key[j];
        key[j] = t;
    }
}

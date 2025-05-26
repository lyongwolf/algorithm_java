class Heap {
    private int[] arr;
    private int size;

    public Heap() {
        arr = new int[1];
        size = 0;
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
            arr = java.util.Arrays.copyOf(arr, arr.length << 1);
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

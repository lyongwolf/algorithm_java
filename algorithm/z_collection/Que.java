class Que {
    private int[] arr;
    private int head;
    private int tail;
    private int size;

    public Que(int len) {
        arr = new int[len << 1 | 1];
        head = len;
        tail = len - 1;
    }

    public void clear() {
        head = arr.length >> 1;
        tail = head - 1;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int peekFirst() {
        return arr[head];
    }

    public int peekLast() {
        return arr[tail];
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

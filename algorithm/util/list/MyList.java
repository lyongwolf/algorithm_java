package algorithm.util.list;
import java.util.*;

class ListInt {
    private int[] arr;
    private int sz;

    public ListInt() {
        this(1);
    }

    public ListInt(int len) {
        arr = new int[len];
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public boolean contains(int v) {
        return index(v) != -1;
    }

    public int size() {
        return sz;
    }

    public int get(int i) {
        if (i < 0 || i >= sz) {
            throw new IndexOutOfBoundsException();
        }
        return arr[i];
    }

    public void set(int i, int v) {
        if (i < 0 || i >= sz) {
            throw new IndexOutOfBoundsException();
        }
        arr[i] = v;
    }

    public void add(int v) {
        if (sz == arr.length) {
            arr = Arrays.copyOf(arr, sz << 1);
        }
        arr[sz++] = v;
    }

    public void remove(int v) {
        int i = index(v);
        if (i != -1 && i != --sz) {
            System.arraycopy(arr, i + 1, arr, i, sz - i);
        }
    }

    private int index(int v) {
        int i = sz - 1;
        while (i >= 0 && arr[i] != v) {
            i--;
        }
        return i;
    }

    public void clear() {
        sz = 0;
    }

    public int[] copy() {
        return Arrays.copyOf(arr, sz);
    }

    public void reverse() {
        for (int i = 0, j = sz - 1; i < j; i++, j--) {
            arr[i] ^= arr[j] ^ (arr[j] = arr[i]);
        }
    }

    public void ascending() {
        Arrays.sort(arr, 0, sz);
    }

    // list must be ascending
    public int floorIndex(int v) {
        int l = 0, r = sz - 1, m, ans = -1;
        while (l <= r) {
            m = (l + r) >> 1;
            if (arr[m] <= v) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }

    // list must be ascending
    public int ceilingIndex(int v) {
        int l = 0, r = sz - 1, m, ans = -1;
        while (l <= r) {
            m = (l + r) >> 1;
            if (arr[m] >= v) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (sz > 0) {
            sb.append(arr[0]);
        }
        for (int i = 1; i < sz; i++) {
            sb.append(", " + arr[i]);
        }
        sb.append(']');
        return sb.toString();
    }
    
}

class ListLong {
    private long[] arr;
    private int sz;

    public ListLong() {
        this(1);
    }

    public ListLong(int len) {
        arr = new long[len];
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public boolean contains(long v) {
        return index(v) != -1;
    }

    public int size() {
        return sz;
    }

    public long get(int i) {
        if (i < 0 || i >= sz) {
            throw new IndexOutOfBoundsException();
        }
        return arr[i];
    }

    public void set(int i, long v) {
        if (i < 0 || i >= sz) {
            throw new IndexOutOfBoundsException();
        }
        arr[i] = v;
    }

    public void add(long v) {
        if (sz == arr.length) {
            arr = Arrays.copyOf(arr, sz << 1);
        }
        arr[sz++] = v;
    }

    public void remove(long v) {
        int i = index(v);
        if (i != -1 && i != --sz) {
            System.arraycopy(arr, i + 1, arr, i, sz - i);
        }
    }

    private int index(long v) {
        int i = sz - 1;
        while (i >= 0 && arr[i] != v) {
            i--;
        }
        return i;
    }

    public void clear() {
        sz = 0;
    }

    public long[] copy() {
        return Arrays.copyOf(arr, sz);
    }

    public void reverse() {
        for (int i = 0, j = sz - 1; i < j; i++, j--) {
            arr[i] ^= arr[j] ^ (arr[j] = arr[i]);
        }
    }

    public void ascending() {
        Arrays.sort(arr, 0, sz);
    }

   // list must be ascending
    public int floorIndex(long v) {
        int l = 0, r = sz - 1, m, ans = -1;
        while (l <= r) {
            m = (l + r) >> 1;
            if (arr[m] <= v) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }

    // list must be ascending
    public int ceilingIndex(long v) {
        int l = 0, r = sz - 1, m, ans = -1;
        while (l <= r) {
            m = (l + r) >> 1;
            if (arr[m] >= v) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (sz > 0) {
            sb.append(arr[0]);
        }
        for (int i = 1; i < sz; i++) {
            sb.append(", " + arr[i]);
        }
        sb.append(']');
        return sb.toString();
    }
    
}
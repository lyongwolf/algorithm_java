class Sort {

    public static void radix(int[] arr) {
        radix(arr, 0, arr.length - 1);
    }
    
    public static void radix(int[] arr, int l, int r) {
        int len = r - l + 1, b = 4, n = 1 << b, m, mx = 0, x, i, j;
        while ((n <<= 1) <= len) b++;
        m = (n >>= 1) - 1;
        int[] cnt = new int[n], tmp = new int[len];
        for (i = l; i <= r; i++) cnt[arr[i] & m]++;
        for (i = 1; i < n; i++) cnt[i] = cnt[i - 1] + cnt[i];
        for (i = r; i >= l; i--) tmp[--cnt[arr[i] & m]] = arr[i];
        for (i = l, j = 0; i <= r; arr[i++] = tmp[j++]) mx = Math.max(mx, 32 - Integer.numberOfLeadingZeros(arr[i]));
        if (mx > b * b) {java.util.Arrays.sort(arr, l, r + 1); return;}
        for (x = b; x < mx; x += b) {
            java.util.Arrays.fill(cnt, 0);
            for (i = l; i <= r; i++) cnt[arr[i] >> x & m]++;
            for (i = 1; i < n; i++) cnt[i] = cnt[i - 1] + cnt[i];
            for (i = r; i >= l; i--) tmp[--cnt[arr[i] >> x & m]] = arr[i];
            for (i = l, j = 0; i <= r; i++) arr[i] = tmp[j++];
        }
    }

    public static void merge(int[] a, java.util.function.IntBinaryOperator cmp) {merge(a.clone(), a, 0, a.length, 0, cmp);}

    public static void merge(int[] a, int l, int r, java.util.function.IntBinaryOperator cmp) {merge(java.util.Arrays.copyOfRange(a, l, r), a, l, r, -l, cmp);}

    private static void merge(int[] src, int[] dest, int low, int high, int off, java.util.function.IntBinaryOperator c) {
        int length = high - low;
        if (length < 7) {
            for (int i=low; i<high; i++) for (int j=i; j>low && c.applyAsInt(dest[j-1], dest[j])>0; j--) swap(dest, j, j-1);
            return;
        }
        int destLow  = low, destHigh = high;
        low  += off;
        high += off;
        int mid = (low + high) >>> 1;
        merge(dest, src, low, mid, -off, c);
        merge(dest, src, mid, high, -off, c);
        if (c.applyAsInt(src[mid-1], src[mid]) <= 0) {
           System.arraycopy(src, low, dest, destLow, length);
           return;
        }
        for(int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && c.applyAsInt(src[p], src[q]) <= 0) dest[i] = src[p++];
            else dest[i] = src[q++];
        }
    }

    private static void swap(int[] x, int a, int b) {int t = x[a]; x[a] = x[b]; x[b] = t;}

    public static void merge(long[] a, java.util.function.LongBinaryOperator cmp) {merge(a.clone(), a, 0, a.length, 0, cmp);}
    
    public static void merge(long[] a, int l, int r, java.util.function.LongBinaryOperator cmp) {merge(java.util.Arrays.copyOfRange(a, l, r), a, l, r, -l, cmp);}
    
    private static void merge(long[] src, long[] dest, int low, int high, int off, java.util.function.LongBinaryOperator c) {
        int length = high - low;
        if (length < 7) {
            for (int i=low; i<high; i++) for (int j=i; j>low && c.applyAsLong(dest[j-1], dest[j])>0; j--) swap(dest, j, j-1);
            return;
        }
        int destLow  = low, destHigh = high;
        low  += off;
        high += off;
        int mid = (low + high) >>> 1;
        merge(dest, src, low, mid, -off, c);
        merge(dest, src, mid, high, -off, c);
        if (c.applyAsLong(src[mid-1], src[mid]) <= 0) {
            System.arraycopy(src, low, dest, destLow, length);
            return;
        }
        for(int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && c.applyAsLong(src[p], src[q]) <= 0) dest[i] = src[p++];
            else dest[i] = src[q++];
        }
    }

    private static void swap(long[] x, int a, int b) {long t = x[a]; x[a] = x[b]; x[b] = t;}
}

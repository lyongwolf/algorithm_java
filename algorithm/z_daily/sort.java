import java.util.*;

class sort {
    
    void radixSort(int[] arr) {
        radixSort(arr, 0, arr.length - 1);
    }
    
    void radixSort(int[] arr, int l, int r) {
        int len = r - l + 1, b = 4, n = 1 << b, m, mx = 0, x, i, j;
        while ((n <<= 1) <= len) b++;
        m = (n >>= 1) - 1;
        int[] cnt = new int[n], tmp = new int[len];
        for (i = l; i <= r; i++) cnt[arr[i] & m]++;
        for (i = 1; i < n; i++) cnt[i] = cnt[i - 1] + cnt[i];
        for (i = r; i >= l; i--) tmp[--cnt[arr[i] & m]] = arr[i];
        for (i = l, j = 0; i <= r; arr[i++] = tmp[j++]) mx = Math.max(mx, 32 - Integer.numberOfLeadingZeros(arr[i]));
        if (mx > b * b) {Arrays.sort(arr, l, r + 1); return;}
        for (x = b; x < mx; x += b) {
            Arrays.fill(cnt, 0);
            for (i = l; i <= r; i++) cnt[arr[i] >> x & m]++;
            for (i = 1; i < n; i++) cnt[i] = cnt[i - 1] + cnt[i];
            for (i = r; i >= l; i--) tmp[--cnt[arr[i] >> x & m]] = arr[i];
            for (i = l, j = 0; i <= r; i++) arr[i] = tmp[j++];
        }
    }

}

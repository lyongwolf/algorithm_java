package algorithm.zz;

import java.util.*;
/**
 * 读写工具类+常用基本方法
 */
class MyUtil {
    
    private byte[] inbuf = new byte[8192], str = new byte[16];
    private int lenbuf, ptrbuf;

    private byte readByte() {
        if (ptrbuf == lenbuf) {
            ptrbuf = 0;
            try {
                lenbuf = System.in.read(inbuf);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (lenbuf <= 0) return -1;
        }
        return inbuf[ptrbuf++];
    }

    String ns() {
        byte b;
        while ((b = readByte()) < 33);
        int i = 0;
        while (b > 32) {
            if (i == str.length) {
                byte[] tmp = new byte[i << 1];
                System.arraycopy(str, 0, tmp, 0, i);
                str = tmp;
            }
            str[i++] = b;
            b = readByte();
        }
        return new String(str, 0, i);
    }

    char nc() {
        byte b;
        while ((b = readByte()) < 33);
        return (char) b;
    }

    int ni() {
        return (int) nl();
    }

    long nl() {
        byte b;
        while ((b = readByte()) < 33);
        boolean neg = b == '-';
        long num = neg ? 0 : b - '0';
        while ((b = readByte()) > 32) num = num * 10 + (b - '0');
        return neg ? -num : num;
    }

    double nd() {
        byte b;
        double num = 0, div = 1;
        while ((b = readByte()) < 33);
        boolean neg = false;
        if (b == '-') {
            neg = true;
            b = readByte();
        }
        while (b > 32 && b != '.') {
            num = num * 10 + (b - '0');
            b = readByte();
        }
        if (b == '.') {
            b = readByte();
            while (b > 32) {
                num += (b - '0') / (div *= 10);
                b = readByte();
            }
        }
        return neg ? -num : num;
    }

    private int tr = 0, BUF_SIZE = 8192;
    private byte[] buf = new byte[BUF_SIZE];

    private int countDigits(int v) {
        return v >= 100000 ? v >= 10000000 ? v >= 100000000 ? v >= 1000000000 ? 10 : 9 : 8 : v >= 1000000 ? 7 : 6 : v >= 1000 ? v >= 10000 ? 5 : 4 : v >= 100 ? 3 : v >= 10 ? 2 : 1;
    }

    private int countDigits(long v) {
        return v >= 10000000000L ? 10 + countDigits((int) (v / 10000000000L)) : v >= 1000000000 ? 10 : countDigits((int) v);
    }

    void print(byte b) {
        buf[tr++] = b;
        if (tr == BUF_SIZE) innerflush();
    }

    void print(char c) {
        print((byte) c);
    }

    void print(int x) {
        if (x == Integer.MIN_VALUE) {
            print((long) x);
        }
        if (tr + 12 >= BUF_SIZE) innerflush();
        if (x < 0) {
            print((byte) '-');
            x = -x;
        }
        int d = countDigits(x);
        for (int i = tr + d - 1; i >= tr; i--) {
            buf[i] = (byte) ('0' + x % 10);
            x /= 10;
        }
        tr += d;
    }

    void print(long x) {
        if (x == Long.MIN_VALUE) {
            print("" + x);
        }
        if (tr + 21 >= BUF_SIZE) innerflush();
        if (x < 0) {
            print((byte) '-');
            x = -x;
        }
        int d = countDigits(x);
        for (int i = tr + d - 1; i >= tr; i--) {
            buf[i] = (byte) ('0' + x % 10);
            x /= 10;
        }
        tr += d;
    }

    void print(double x) {
        print(String.valueOf(x));
    }

    void print(double x, int precision) {
        if (x < 0) {
            print('-');
            x = -x;
        }
        x += Math.pow(10, -precision) / 2;
        print((long) x);
        print(".");
        x -= (long) x;
        for (int i = 0; i < precision; i++) {
            x *= 10;
            print((byte) ('0' + (int) x));
            x -= (int) x;
        }
    }

    void print(String s) {
        for (int i = 0; i < s.length(); i++) {
            buf[tr++] = (byte) s.codePointAt(i);
            if (tr == BUF_SIZE) innerflush();
        }
    }

    void writeln() {
        print((byte) '\n');
    }

    void println(byte b) {
        print(b);
        writeln();
    }

    void println(char c) {
        print(c);
        writeln();
    }

    void println(int x) {
        print(x);
        writeln();
    }

    void println(long x) {
        print(x);
        writeln();
    }

    void println(double x) {
        print(x);
        writeln();
    }

    void println(double x, int precision) {
        print(x, precision);
        writeln();
    }

    void println(String s) {
        print(s);
        writeln();
    }

    void println(Object o) {
        deepPrint(o, true);
        writeln();
    }

    void deepPrint(Object o, boolean f) {
        if (o == null) {
            print("null");
            return;
        }
        Class<?> c = o.getClass();
        int[] j = new int[1];
        boolean b;
        if (c.isArray()) {
            int n = java.lang.reflect.Array.getLength(o);
            b = f && n > 0 && o instanceof Object[];
            print(b ? "[\n" : "[");
            for (int i = 0; i < n;) {
                deepPrint(java.lang.reflect.Array.get(o, i), false);
                if (++i < n) print(b ? ", \n" : ", ");
            }
            print(b ? "\n]" : "]");
        } else if (o instanceof Collection) {
            Collection<?> t = (Collection<?>) o;
            b = f && !t.isEmpty();
            print(b ? "[\n" : "[");
            t.forEach(v -> {
                deepPrint(v, false);
                if (++j[0] < t.size()) print(b ? ", \n" : ", ");
            });
            print(b ? "\n]" : "]");
        } else if (o instanceof Map) {
            Map<?, ?> t = (Map<?, ?>) o;
            b = f && !t.isEmpty();
            print(b ? "{\n" : "{");
            t.forEach((k, v) -> {
                deepPrint(k, false);
                print(" = ");
                deepPrint(v, false);
                if (++j[0] < t.size()) print(b ? ", \n" : ", ");
            });
            print(b ? "\n}" : "}");
        } else {
            print(o.toString());
        }
    }

    private void innerflush() {
        System.out.write(buf, 0, tr);
        tr = 0;
    }

    void flush() {
        innerflush();
        System.out.flush();
    }

    int min(int a, int b) {
        return a > b ? b : a;
    }

    int min(int... args) {
        int ans = Integer.MAX_VALUE;
        for (int v : args) if (ans > v) ans = v;
        return ans;
    }

    long min(long a, long b) {
        return a > b ? b : a;
    }

    long min(long... args) {
        long ans = Long.MAX_VALUE;
        for (long v : args) if (ans > v) ans = v;
        return ans;
    }

    double min(double a, double b) {
        return a > b ? b : a;
    }

    double min(double... args) {
        double ans = Double.POSITIVE_INFINITY;
        for (double v : args) if (ans > v) ans = v;
        return ans;
    }

    int max(int a, int b) {
        return a < b ? b : a;
    }

    int max(int... args) {
        int ans = Integer.MIN_VALUE;
        for (int v : args) if (ans < v) ans = v;
        return ans;
    }

    long max(long a, long b) {
        return a < b ? b : a;
    }

    long max(long... args) {
        long ans = Long.MIN_VALUE;
        for (long v : args) if (ans < v) ans = v;
        return ans;
    }

    double max(double a, double b) {
        return a < b ? b : a;
    }

    double max(double... args) {
        double ans = Double.NEGATIVE_INFINITY;
        for (double v : args) if (ans < v) ans = v;
        return ans;
    }

    int abs(int a) {
        return a < 0 ? -a : a;
    }

    long abs(long a) {
        return a < 0 ? -a : a;
    }

    double abs(double a) {
        return a < 0 ? -a : a;
    }

}

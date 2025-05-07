package algorithm.zz;

import java.util.*;
/**
 * 读写工具类+常用基本方法
 */
class MyUtil {
    
    private java.io.InputStream is = System.in;
    private byte[] inbuf = new byte[8192], str = new byte[16];
    private int lenbuf, ptrbuf;
    private byte b;

    private byte readByte() {
        if (ptrbuf == lenbuf) {
            ptrbuf = 0;
            try {
                lenbuf = is.read(inbuf);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (lenbuf <= 0) return -1;
        }
        return inbuf[ptrbuf++];
    }

    String ns() {
        while ((b = readByte()) < 33);
        int i = 0;
        while (b > 32) {
            if (i == str.length) str = Arrays.copyOf(str, str.length << 1);
            str[i++] = b;
            b = readByte();
        }
        return new String(str, 0, i);
    }

    char nc() {
        while ((b = readByte()) < 33);
        return (char) b;
    }

    int ni() {
        return (int) nl();
    }

    long nl() {
        while ((b = readByte()) < 33);
        boolean neg = b == '-';
        long num = neg ? 0 : b - '0';
        while ((b = readByte()) > 32) num = num * 10 + (b - '0');
        return neg ? -num : num;
    }

    double nd() {
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

    private java.io.OutputStream out = System.out;
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
            print((char) ('0' + (int) x));
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
    }

    private void deepPrint(Object o, boolean f) {
        if (o == null) {
            print(f ? "null\n" : "null");
            return;
        }
        Class<?> c = o.getClass();
        if (c.isArray()) {
            if (o instanceof Object[]) {
                Object[] t = (Object[]) o;
                int n = t.length;
                boolean b = f && n > 0 && t[0] != null;
                print(b ? "[\n" : "[");
                for (int i = 0; i < n; i++) {
                    deepPrint(t[i], false);
                    if (i < n - 1) print(b ? ", \n" : ", ");
                    else if (b) writeln();
                }
                print("]");
            } else {
                print(o instanceof byte[] ? Arrays.toString((byte[]) o)
                        : o instanceof short[] ? Arrays.toString((short[]) o)
                        : o instanceof int[] ? Arrays.toString((int[]) o)
                        : o instanceof long[] ? Arrays.toString((long[]) o)
                        : o instanceof char[] ? Arrays.toString((char[]) o)
                        : o instanceof float[] ? Arrays.toString((float[]) o)
                        : o instanceof double[] ? Arrays.toString((double[]) o)
                        : Arrays.toString((boolean[]) o));
            }
        } else if (o instanceof Collection) {
            Collection<?> t = (Collection<?>) o;
            print("[");
            int i = 0;
            for (Object v : t) {
                deepPrint(v, false);
                if (i < t.size() - 1) print(", ");
                i++;
            }
            print("]");
        } else if (o instanceof Map) {
            Map<?, ?> t = (Map<?, ?>) o;
            print(!t.isEmpty() && f ? "{\n" : "{");
            int i = 0;
            for (Map.Entry<?, ?> v : t.entrySet()) {
                deepPrint(v.getKey(), false);
                print(" = ");
                deepPrint(v.getValue(), false);
                if (++i < t.size()) print(f ? ", \n" : ", ");
                else if (f) writeln();
            }
            print("}");
        } else {
            print(o.toString());
        }
        if (f) writeln();
    }

    private void innerflush() {
        try {
            out.write(buf, 0, tr);
            tr = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void flush() {
        innerflush();
        try {
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

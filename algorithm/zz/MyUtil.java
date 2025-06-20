package algorithm.zz;

import java.util.*;
/**
 * 读写工具类+常用基本方法
 */
class MyUtil {
    
    byte[] inBuf = new byte[8192], outBuf = new byte[8192];
    int lenBuf, ptrBuf, tr;

    byte readByte() {
        if (ptrBuf == lenBuf) {
            ptrBuf = 0;
            try {
                lenBuf = System.in.read(inBuf);
            } catch (Exception e) {}
            if (lenBuf <= 0) return -1;
        }
        return inBuf[ptrBuf++];
    }

    String nextString() {
        byte b;
        while ((b = readByte()) < 33);
        StringBuilder sb = new StringBuilder();
        while (b > 32) {
            sb.append((char) b);
            b = readByte();
        }
        return sb.toString();
    }

    char nextChar() {
        byte b;
        while ((b = readByte()) < 33);
        return (char) b;
    }

    int nextInt() {
        byte b;
        while ((b = readByte()) < 33);
        boolean neg = b == '-';
        int num = neg ? 0 : b - '0';
        while ((b = readByte()) > 32)
            num = num * 10 + (b - '0');
        return neg ? -num : num;
    }

    long nextLong() {
        byte b;
        while ((b = readByte()) < 33);
        boolean neg = b == '-';
        long num = neg ? 0 : b - '0';
        while ((b = readByte()) > 32)
            num = num * 10 + (b - '0');
        return neg ? -num : num;
    }

    double nextDouble() {
        byte b;
        while ((b = readByte()) < 33);
        boolean neg = false;
        if (b == '-') {
            neg = true;
            b = readByte();
        }
        double num = 0, div = 1;
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

    int countDigits(int v) {
        return v >= 100000 ?
                v >= 10000000 ?
                        v >= 100000000 ?
                                v >= 1000000000 ? 10 : 9 : 8 :
                        v >= 1000000 ? 7 : 6 :
                v >= 1000 ?
                        v >= 10000 ? 5 : 4 :
                        v >= 100 ? 3 :
                                v >= 10 ? 2 : 1;
    }

    int countDigits(long v) {
        return v >= 10000000000L ? 10 + countDigits((int)(v / 10000000000L)) :
                v >= 1000000000 ? 10 : countDigits((int)v);
    }

    MyUtil print(byte b) {
        outBuf[tr++] = b;
        if (tr == 8192) innerFlush();
        return this;
    }

    MyUtil print(char c) {
        return print((byte) c);
    }

    MyUtil print(int x) {
        if (x == 0x80000000) {
            return print((long) x);
        }
        if (tr >= 8180) innerFlush();
        if (x < 0) {
            print((byte) '-');
            x = -x;
        }
        int d = countDigits(x);
        for (int i = tr + d - 1; i >= tr; i--) {
            outBuf[i] = (byte) ('0' + x % 10);
            x /= 10;
        }
        tr += d;
        return this;
    }

    MyUtil print(long x) {
        if (x == Long.MIN_VALUE) {
            return print("" + x);
        }
        if (tr >= 8171) innerFlush();
        if (x < 0) {
            print((byte) '-');
            x = -x;
        }
        int d = countDigits(x);
        for (int i = tr + d - 1; i >= tr; i--) {
            outBuf[i] = (byte) ('0' + x % 10);
            x /= 10;
        }
        tr += d;
        return this;
    }

    MyUtil print(double x) {
        return print(String.valueOf(x));
    }

    MyUtil print(double x, int precision) {
        if (x < 0) {
            print('-');
            x = -x;
        }
        x += Math.pow(10, -precision) / 2;
        print((long) x).print('.');
        x -= (long) x;
        for (int i = 0; i < precision; i++) {
            x *= 10;
            print((byte) ('0' + (int) x));
            x -= (int) x;
        }
        return this;
    }

    MyUtil print(String s) {
        for (int i = 0; i < s.length(); i++) {
            outBuf[tr++] = (byte) s.codePointAt(i);
            if (tr == 8192) innerFlush();
        }
        return this;
    }

    void writeln() {
        print((byte) '\n');
    }

    void println(byte b) {
        print(b).writeln();
    }

    void println(char c) {
        print(c).writeln();
    }

    void println(int x) {
        print(x).writeln();
    }

    void println(long x) {
        print(x).writeln();
    }

    void println(double x) {
        print(x).writeln();
    }

    void println(double x, int precision) {
        print(x, precision).writeln();
    }

    void println(String s) {
        print(s).writeln();
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

    void innerFlush() {
        System.out.write(outBuf, 0, tr);
        tr = 0;
    }

    void flush() {
        innerFlush();
        System.out.flush();
    }

    int min(int a, int b) {
        return a > b ? b : a;
    }

    int min(int... g) {
        int z = 0x7fffffff;
        for (int v : g)
            if (z > v) z = v;
        return z;
    }

    long min(long a, long b) {
        return a > b ? b : a;
    }

    long min(long... g) {
        long z = Long.MAX_VALUE;
        for (long v : g)
            if (z > v) z = v;
        return z;
    }

    double min(double a, double b) {
        return a > b ? b : a;
    }

    double min(double... g) {
        double z = 1.0 / 0.0;
        for (double v : g)
            if (z > v) z = v;
        return z;
    }

    int max(int a, int b) {
        return a < b ? b : a;
    }

    int max(int... g) {
        int z = 0x80000000;
        for (int v : g)
            if (z < v) z = v;
        return z;
    }

    long max(long a, long b) {
        return a < b ? b : a;
    }

    long max(long... g) {
        long z = Long.MIN_VALUE;
        for (long v : g)
            if (z < v) z = v;
        return z;
    }

    double max(double a, double b) {
        return a < b ? b : a;
    }

    double max(double... g) {
        double z = -1.0 / 0.0;
        for (double v : g)
            if (z < v) z = v;
        return z;
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

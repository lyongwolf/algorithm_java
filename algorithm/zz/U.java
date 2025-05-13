package algorithm.zz;
import java.util.*;

public class U {
    private static java.io.InputStream is = System.in;
    private static byte[] inbuf = new byte[8192], str = new byte[16];
    private static int lenbuf, ptrbuf;
    private static byte b;

    private static byte readByte() {
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

    public static String ns() {
        while ((b = readByte()) < 33);
        int i = 0;
        while (b > 32) {
            if (i == str.length) str = Arrays.copyOf(str, str.length << 1);
            str[i++] = b;
            b = readByte();
        }
        return new String(str, 0, i);
    }

    public static char nc() {
        while ((b = readByte()) < 33);
        return (char) b;
    }

    public static int ni() {
        return (int) nl();
    }

    public static long nl() {
        while ((b = readByte()) < 33);
        boolean neg = b == '-';
        long num = neg ? 0 : b - '0';
        while ((b = readByte()) > 32) num = num * 10 + (b - '0');
        return neg ? -num : num;
    }

    public static double nd() {
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

    private static java.io.OutputStream out = System.out;
    private static int tr = 0, BUF_SIZE = 8192;
    private static byte[] buf = new byte[BUF_SIZE];

    private static int countDigits(int v) {
        return v >= 100000 ? v >= 10000000 ? v >= 100000000 ? v >= 1000000000 ? 10 : 9 : 8 : v >= 1000000 ? 7 : 6 : v >= 1000 ? v >= 10000 ? 5 : 4 : v >= 100 ? 3 : v >= 10 ? 2 : 1;
    }

    private static  int countDigits(long v) {
        return v >= 10000000000L ? 10 + countDigits((int) (v / 10000000000L)) : v >= 1000000000 ? 10 : countDigits((int) v);
    }

    public static void print(byte b) {
        buf[tr++] = b;
        if (tr == BUF_SIZE) innerflush();
    }

    public static void print(char c) {
        print((byte) c);
    }

    public static void print(int x) {
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

    public static void print(long x) {
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

    public static void print(double x) {
        print(String.valueOf(x));
    }

    public static void print(double x, int precision) {
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

    public static void print(String s) {
        for (int i = 0; i < s.length(); i++) {
            buf[tr++] = (byte) s.codePointAt(i);
            if (tr == BUF_SIZE) innerflush();
        }
    }

    public static void writeln() {
        print((byte) '\n');
    }

    public static void println(byte b) {
        print(b);
        writeln();
    }

    public static void println(char c) {
        print(c);
        writeln();
    }

    public static void println(int x) {
        print(x);
        writeln();
    }

    public static void println(long x) {
        print(x);
        writeln();
    }

    public static void println(double x) {
        print(x);
        writeln();
    }

    public static void println(double x, int precision) {
        print(x, precision);
        writeln();
    }

    public static void println(String s) {
        print(s);
        writeln();
    }

    public static void println(Object o) {
        deepPrint(o, true);
    }

    private static void deepPrint(Object o, boolean f) {
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

    private static void innerflush() {
        try {
            out.write(buf, 0, tr);
            tr = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void flush() {
        innerflush();
        try {
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int min(int a, int b) {
        return a > b ? b : a;
    }

    public static int min(int... args) {
        int ans = Integer.MAX_VALUE;
        for (int v : args) if (ans > v) ans = v;
        return ans;
    }

    public static long min(long a, long b) {
        return a > b ? b : a;
    }

    public static long min(long... args) {
        long ans = Long.MAX_VALUE;
        for (long v : args) if (ans > v) ans = v;
        return ans;
    }

    public static double min(double a, double b) {
        return a > b ? b : a;
    }

    public static double min(double... args) {
        double ans = Double.MAX_VALUE;
        for (double v : args) if (ans > v) ans = v;
        return ans;
    }

    public static int max(int a, int b) {
        return a < b ? b : a;
    }

    public static int max(int... args) {
        int ans = Integer.MIN_VALUE;
        for (int v : args) if (ans < v) ans = v;
        return ans;
    }

    public static long max(long a, long b) {
        return a < b ? b : a;
    }

    public static long max(long... args) {
        long ans = Long.MIN_VALUE;
        for (long v : args) if (ans < v) ans = v;
        return ans;
    }

    public static double max(double a, double b) {
        return a < b ? b : a;
    }

    public static double max(double... args) {
        double ans = Double.MIN_VALUE;
        for (double v : args) if (ans < v) ans = v;
        return ans;
    }

    public static int abs(int a) {
        return a < 0 ? -a : a;
    }

    public static long abs(long a) {
        return a < 0 ? -a : a;
    }

    public static double abs(double a) {
        return a < 0 ? -a : a;
    }
}

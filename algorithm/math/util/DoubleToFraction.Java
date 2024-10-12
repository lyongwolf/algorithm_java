package algorithm.math.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DoubleToFraction {

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return b.equals(BigInteger.ZERO) ? a : gcd(b, a.mod(b));
    }

    public static String doubleToFraction(double num) {
        final double EPS = 1e-6;
        BigDecimal decimal = BigDecimal.valueOf(num);

        for (int denominator = 1; denominator <= 1000000; denominator++) {
            double numerator = num * denominator;
            if (Math.abs(numerator - Math.round(numerator)) < EPS) {
                long n = Math.round(numerator);
                BigInteger g = gcd(BigInteger.valueOf(n), BigInteger.valueOf(denominator));
                return (n / g.longValue()) + "/" + (denominator / g.longValue());
            }
        }

        int scale = decimal.scale();
        BigInteger denominator = BigInteger.TEN.pow(scale);
        BigInteger numerator = decimal.multiply(BigDecimal.valueOf(Math.pow(10, scale))).toBigInteger();
        BigInteger g = gcd(numerator, denominator);
        numerator = numerator.divide(g);
        denominator = denominator.divide(g);
        return numerator + "/" + denominator;
    }

    public static void main(String[] args) {
        System.out.println(1);
    }

}

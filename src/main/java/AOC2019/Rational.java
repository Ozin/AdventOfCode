package AOC2019;

import lombok.Value;

@Value
public class Rational {
    final int dividend;
    final int divisor;

    public Rational reduce() {
        final int gcd = gcd();
        return new Rational(dividend / gcd, divisor / gcd);
    }

    public Rational expand(final int factor) {
        return new Rational(dividend * factor, divisor * factor);
    }

    private int gcd() {
        int a = Math.abs(dividend);
        int b = Math.abs(divisor);

        while(b != 0) {
            final int t = b;
            b = a % b;
            a = t;
        }

        return a;
    }

    // function gcd(a, b)
    //    while b ≠ 0
    //       t := b;
    //       b := a mod b;
    //       a := t;
    //    return a;
}

package utils;

import lombok.Value;

@Value
public class Range {
    int lower;
    int upper;

    public Range(final int lower, final int upper) {
        this.upper = Math.max(lower, upper);
        this.lower = Math.min(lower, upper);
    }

    public static Range empty() {
        return new Range(0, 0);
    }

    public boolean inBetween(final int value) {
        return lower <= value && value <= upper;
    }

    public Range extendWith(final int integer) {
        return new Range(Math.min(lower, integer), Math.max(upper, integer));
    }

    public Range union(final Range other) {
        return new Range(Math.min(getLower(), other.getLower()), Math.max(getUpper(), other.getUpper()));
    }
}

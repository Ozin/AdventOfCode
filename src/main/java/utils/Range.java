package utils;

import lombok.Value;

@Value
public class Range {
    int lower;
    int upper;

    public Range(final String range) {
        this(range.split("-"));
    }

    public Range(final String[] strings) {
        this.lower = Integer.parseInt(strings[0]);
        this.upper = Integer.parseInt(strings[1]);
    }

    public boolean inBetween(final int value) {
        return lower <= value && value <= upper;
    }
}

package AOC2021;


import one.util.streamex.StreamEx;

import java.util.stream.IntStream;

public class Day10 {

    protected String a(final String[] input) throws Exception {
        return "" + StreamEx.of(input)
                .map(this::reduce)
                .mapToInt(this::scoreSyntaxChecker)
                .sum();
    }

    protected String b(final String[] input) throws Exception {
        final long[] scores = StreamEx.of(input)
                .map(this::reduce)
                .filter(s -> scoreSyntaxChecker(s) == 0)
                .mapToLong(this::scoreAutoCompletion)
                .sorted()
                .toArray();
        return "" + scores[scores.length / 2];
    }

    private long scoreAutoCompletion(String s) {
        return IntStream.iterate(s.length() - 1, i -> i >= 0, i -> i - 1)
                .map(s::charAt)
                .mapToLong(c -> switch (c) {
                    case '(' -> 1;
                    case '[' -> 2;
                    case '{' -> 3;
                    case '<' -> 4;
                    default -> throw new IllegalArgumentException("unknown char: " + s);
                })
                .reduce((acc, cur) -> acc * 5 + cur)
                .getAsLong();
    }

    private int scoreSyntaxChecker(String s) {
        final String cleanedString = s.replaceAll("(\\(|\\{|\\[|<)+", "");

        if (cleanedString.isEmpty()) {
            return 0;
        }

        return switch (cleanedString.charAt(0)) {
            case ')' -> 3;
            case ']' -> 57;
            case '}' -> 1197;
            case '>' -> 25137;
            default -> throw new IllegalArgumentException("Can't find illegal char in " + s);
        };
    }

    private String reduce(String current) {
        String previous;
        do {
            previous = current;

            current = current
                    .replaceAll("(\\(\\)|\\{}|\\[]|<>)+", "");

        } while (!previous.equals(current));

        return current;
    }
}

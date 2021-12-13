package AOC2021;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day07 {

    protected String a(final String[] input) throws Exception {
        int[] positions = Stream.of(input[0].split(",")).mapToInt(Integer::parseInt).sorted().toArray();
        int bestVal = Integer.MAX_VALUE;

        for (int i = positions[0]; i < positions[positions.length - 1]; i++) {
            int finalI = i;
            final int sum = IntStream.of(positions)
                    .map(p -> p - finalI)
                    .map(Math::abs)
                    .sum();

            if (sum < bestVal) {
                bestVal = sum;
            }
        }

        return "" + bestVal;
    }

    protected String b(final String[] input) throws Exception {
        int[] positions = Stream.of(input[0].split(",")).mapToInt(Integer::parseInt).sorted().toArray();
        int bestVal = Integer.MAX_VALUE;

        for (int i = positions[0]; i < positions[positions.length - 1]; i++) {
            int finalI = i;
            final int sum = IntStream.of(positions)
                    .map(p -> p - finalI)
                    .map(Math::abs)
                    .map(n -> n * (n + 1) / 2)
                    .sum();

            if (sum < bestVal) {
                bestVal = sum;
            }

            // System.out.printf("Pos %4d costs %d%n", i, sum);
        }

        return "" + bestVal;
    }
}

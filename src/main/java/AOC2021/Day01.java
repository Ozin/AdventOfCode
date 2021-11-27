package AOC2021;

import utils.AbstractDay;

import java.util.stream.Stream;

public class Day01 extends AbstractDay<int[]> {
    public static void main(final String[] args) {
        new Day01().run();
    }

    @Override
    protected int[] parseInput(final String[] rawInput) throws Exception {
        return Stream.of(rawInput).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    protected Object a(final int[] input) throws Exception {
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (input[i] + input[j] == 2020) {
                    return input[i] * input[j];
                }
            }
        }

        throw new IllegalStateException("Did not find solution");
    }

    @Override
    protected Object b(final int[] input) throws Exception {
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                for (int k = j + 1; k < input.length; k++) {
                    final int a = input[i];
                    final int b = input[j];
                    final int c = input[k];

                    if (a + b + c == 2020) {
                        return a * b * c;
                    }
                }
            }
        }

        throw new IllegalStateException("Did not find solution");
    }
}

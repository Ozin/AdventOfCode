package adventOfCode2019;

import java.util.Arrays;
import one.util.streamex.IntStreamEx;

public class Day16 extends AbstractDay<int[]> {

    public static final int[] REPEATING_PATTERN = {1, 0, -1, 0};

    @Override
    protected int[] parseInput(final String[] rawInput) throws Exception {
        return Arrays.stream(rawInput[0].split("")).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    protected Object a(int[] input) throws Exception {
        // System.out.println("Input signal: " + IntStreamEx.of(input).joining(""));
        for (int i = 0; i < 100; i++) {
            input = runPhase(input);
            // System.out.printf("After %d phase: %s%n", i + 1, IntStreamEx.of(input).joining(""));
        }
        return IntStreamEx.of(input).joining("").substring(0, 8);
    }

    @Override
    protected Object b(final int[] input) throws Exception {
        final int multiplicator = 10000;
        int[] wip = new int[input.length * multiplicator];
        for (int i = 0; i < multiplicator; i++) {
            System.arraycopy(input, 0, wip, i * input.length, input.length);
        }

        for (int i = 0; i < 100; i++) {
            wip = runPhase(wip);
            // System.out.printf("After %d phase: %s%n", i + 1, IntStreamEx.of(input).joining(""));
        }

        final String joining = IntStreamEx.of(input).joining("");
        final int offset = Integer.parseInt(joining.substring(0, 7));
        return joining.substring(offset, offset + 8);
    }

    private int[] runPhase(final int[] input) {
        final int[] newArray = new int[input.length];

        for (int index = 0; index < input.length; index++) {
            newArray[index] = processDigit(index, input);
        }

        return newArray;
    }

    private int processDigit(final int index, final int[] input) {
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            final int value = input[i];
            final int factor = getFactor(index, i);
            sum += (value * factor);
        }

        return Math.abs((sum) % 10);
    }

    private int getFactor(final int row, final int column) {
        if (row > column) {
            return 0;
        }

        final int adjustedColumn = (column - row) / (row + 1);
        final int adjustedIndex = adjustedColumn % 4;

        return REPEATING_PATTERN[adjustedIndex];
    }
}

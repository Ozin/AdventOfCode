package AOC2020;

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

public class Day09 extends AbstractDay<long[]> {
    public static void main(final String[] args) {
        new Day09().run();
    }

    @Override
    protected long[] parseInput(final String[] rawInput) throws Exception {
        return StreamEx.of(rawInput).mapToLong(Long::parseLong).toArray();
    }

    @Override
    protected Object a(final long[] input) throws Exception {
        return findFirstWeakNumber(input, 25);
    }

    @Override
    protected Object b(final long[] input) throws Exception {
        return findWeakness(input, 25);
    }

    public long findWeakness(final long[] input, final int preambleLength) {
        final long firstWeakNumber = findFirstWeakNumber(input, preambleLength);

        for (int i = 0; i < input.length; i++) {
            long sum = input[i];
            for (int j = i + 1; j < input.length; j++) {
                sum += input[j];
                if (sum == firstWeakNumber) {
                    return sumOfSmallestAndBiggest(input, i, j);
                } else if (sum > firstWeakNumber) {
                    break;
                }
            }
        }

        return 0;
    }

    private long sumOfSmallestAndBiggest(final long[] input, final int i, final int j) {
        final long min = IntStreamEx.range(i, j + 1).mapToLong(k -> input[k]).min().orElseThrow();
        final long max = IntStreamEx.range(i, j + 1).mapToLong(k -> input[k]).max().orElseThrow();
        return min + max;
    }

    public long findFirstWeakNumber(final long[] input, final int preambleLength) {
        for (int i = preambleLength; i < input.length; i++) {
            if (!findSumInPrevious(input, i, preambleLength)) {
                return input[i];
            }
        }

        return -1;
    }

    private boolean findSumInPrevious(final long[] input, final int i, final int preambleLength) {
        for (int j = i - preambleLength; j < i; j++) {
            for (int k = j + 1; k < i; k++) {
                if (input[i] == input[j] + input[k]) {
                    return true;
                }
            }
        }

        return false;
    }
}

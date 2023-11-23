package AOC2020;

import utils.AbstractDay;

import java.util.stream.Stream;

public class Day25 extends AbstractDay<long[]> {
    public static void main(final String[] args) {
        new Day25().run();
    }

    @Override
    protected long[] parseInput(final String[] rawInput) throws Exception {
        return Stream.of(rawInput).mapToLong(Long::parseLong).toArray();
    }

    @Override
    protected Object a(final long[] input) throws Exception {
        final long cardLoopSize = getLoopSize(input[0], 7);
        //final long doorLoopSize = getLoopSize(input[1]);
        return transform(input[1], cardLoopSize);
    }

    private long getLoopSize(final long publicKey, final int subjectNumber) {
        long loopSize = 0;
        long value = 1;
        while (publicKey != value) {
            value = iterate(subjectNumber, value);
            loopSize += 1;
        }
        return loopSize;
    }

    @Override
    protected Object b(final long[] input) throws Exception {
        return null;
    }

    private long transform(final long subjectNumber, final long loopSize) {
        long value = 1;

        for (long i = 0; i < loopSize; i++) {
            value = iterate(subjectNumber, value);
        }

        return value;
    }

    private long iterate(final long subjectNumber, final long value) {
        return (value * subjectNumber) % 20201227;
    }
}

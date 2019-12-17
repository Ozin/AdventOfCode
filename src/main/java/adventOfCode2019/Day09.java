package adventOfCode2019;

import java.util.Arrays;

public class Day09 extends AbstractIntcodePuzzle {

    public static void main(String[] args) {
        new Day09().run();
    }

    @Override
    protected Object a(final long[] input) throws Exception {
        final var intcodeComputer = new IntcodeComputer(input, 100000);

        long[] result = intcodeComputer.finishProgram(1);

        if(result.length > 1) {
            throw new IllegalStateException("Result must have only one output");
        }

        return result[0];
    }

    @Override
    protected Object b(final long[] input) throws Exception {
        return null;
    }
}

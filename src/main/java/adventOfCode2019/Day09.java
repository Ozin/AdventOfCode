package adventOfCode2019;

import java.util.Arrays;

public class Day09 extends AbstractIntcodePuzzle {

    public static void main(String[] args) {
        new Day09().run();
    }

    @Override
    protected Object a(final long[] input) throws Exception {
        final var intcodeComputer = new IntcodeComputer(input);

        return Arrays.toString(intcodeComputer.finishProgram(1));
    }

    @Override
    protected Object b(final long[] input) throws Exception {
        return null;
    }
}

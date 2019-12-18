package adventOfCode2019;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Day02 extends AbstractIntcodePuzzle {
    public static void main(final String[] args) throws IOException {
        new Day02().run();
    }

    private static long runProgram(final long[] input, final int noun, final int verb) {

        final long[] program = Arrays.copyOf(input, input.length);

        program[1] = noun;
        program[2] = verb;

        final IntcodeComputer intcodeComputer = new IntcodeComputer(program, new long[0]);
        intcodeComputer.nextOutput();
        return intcodeComputer.getState()[0];
    }

    @Override
    protected Object a(final long[] input) throws Exception {
        return runProgram(input, 12, 2);
    }

    @Override
    protected Object b(final long[] input) throws Exception {
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                final long result = runProgram(input, noun, verb);
                if (result == 19690720) {
                    return 100 * noun + verb;
                }
            }
        }

        throw new IllegalStateException("Did not find result");
    }
}

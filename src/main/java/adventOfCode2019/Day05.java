package adventOfCode2019;

import java.util.Map;
import java.util.Optional;

public class Day05 extends AbstractIntcodePuzzle {

    public static void main(final String[] args) {
        new Day05().run();
    }

    @Override
    protected Object a(final long[] program) throws Exception {
        return runToEnd(program, 1);
    }

    @Override
    protected Object b(final long[] program) throws Exception {
        return runToEnd(program, 5);
    }

    private Object runToEnd(final long[] program, final int input) {
        final IntcodeComputer intcodeComputer = new IntcodeComputer(program);
        Optional<Long> lastOutput = Optional.empty();
        long nextOutput = intcodeComputer.nextOutput(input);
        while (!intcodeComputer.isDone()) {
            lastOutput = Optional.of(nextOutput);
            nextOutput = intcodeComputer.nextOutput();
        }
        return lastOutput.get();
    }
}

package adventOfCode2019;

public class Day09 extends AbstractIntcodePuzzle {

    public static void main(String[] args) {
        new Day09().run();
    }

    @Override
    protected Object a(final long[] input) throws Exception {
        return runSensorMode(input, 1);
    }

    @Override
    protected Object b(final long[] input) throws Exception {
        return runSensorMode(input, 2);
    }

    private Object runSensorMode(final long[] input, final int mode) {
        final var intcodeComputer = new IntcodeComputer(input, new long[]{mode});

        final long[] result = intcodeComputer.finishProgram();

        if (result.length > 1) {
            throw new IllegalStateException("Result must have only one output");
        }

        return result[0];
    }
}

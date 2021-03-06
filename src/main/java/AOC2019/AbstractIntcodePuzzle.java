package AOC2019;

import utils.AbstractDay;

import java.util.Arrays;

public abstract class AbstractIntcodePuzzle extends AbstractDay<long[]> {

    private long[] program;

    @Override
    protected long[] parseInput(final String[] rawInput) throws Exception {
        return Arrays.stream(rawInput)
            .map(line -> line.split(","))
            .flatMap(Arrays::stream)
            .mapToLong(Long::parseLong)
            .toArray();
    }

    public long[] getProgram() {
        if (this.program == null) {
            this.program = getInput().clone();
        }

        return this.program.clone();


    }
}

package adventOfCode2019;

import java.util.Arrays;

public abstract class AbstractIntcodePuzzle extends AbstractDay<long[]> {

    @Override
    protected long[] parseInput(final String[] rawInput) throws Exception {
        return Arrays.stream(rawInput)
                .map(line -> line.split(","))
                .flatMap(Arrays::stream)
                .mapToLong(Long::parseLong)
                .toArray();
    }

}

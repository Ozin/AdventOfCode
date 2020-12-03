package AOC2020;

import java.util.Map;
import one.util.streamex.IntStreamEx;
import one.util.streamex.LongStreamEx;

public class Day03 extends Abstract2DPuzzle {
    public static void main(final String[] args) {
        new Day03().run();
    }

    @Override
    protected Object a(final Map<Point, Character> input) throws Exception {
        return countOnSlope(input, 3, 1);
    }

    @Override
    protected Object b(final Map<Point, Character> input) throws Exception {
        return LongStreamEx.of(
            countOnSlope(input, 1, 1),
            countOnSlope(input, 3, 1),
            countOnSlope(input, 5, 1),
            countOnSlope(input, 7, 1),
            countOnSlope(input, 1, 2)
        ).reduce((a, b) -> a * b)
            .orElse(0);
    }

    private long countOnSlope(final Map<Point, Character> input, final int deltaX, final int deltaY) {
        int currentX = 0;
        int count = 0;

        for (int y = 0; y < getMaxY(); y += deltaY) {
            if (input.get(new Point(currentX, y)) == '#') {
                count++;
            }
            currentX += deltaX;
            currentX %= getMaxX();
        }

        return count;
    }
}

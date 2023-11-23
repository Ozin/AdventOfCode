package AOC2020;

import one.util.streamex.EntryStream;
import utils.Abstract2DPuzzle;
import utils.Point;

import java.util.Map;

public class Day11 extends Abstract2DPuzzle {
    public static void main(final String[] args) {
        new Day11().run();
    }

    @Override
    protected Object a(final Map<Point, Character> input) throws Exception {
        return countFinalSeating(input, this::decideSeatA);
    }

    @Override
    protected Object b(final Map<Point, Character> input) throws Exception {
        return countFinalSeating(input, this::decideSeatB);
    }

    private Object countFinalSeating(final Map<Point, Character> input, final SeatingStrategy seatingStrategy) {
        Map<Point, Character> old = input;
        Map<Point, Character> next = input;
        do {
            old = next;
            final Map<Point, Character> finalOld = old;

            next = EntryStream.of(finalOld)
                .mapToValue((p, c) -> seatingStrategy.decideSeat(finalOld, p))
                .toMap();

        } while (!old.equals(next));

        return next.values().stream()
            .filter(Character.valueOf('#')::equals)
            .count();
    }

    private Character decideSeatA(final Map<Point, Character> map, final Point point) {
        if (map.get(point) == '.') {
            return map.get(point);
        }

        final var neighbours = point.getNeighboursIncludingDiagonal()
            .map(map::get)
            .filter(Character.valueOf('#')::equals)
            .count();

        switch (Math.toIntExact(neighbours)) {
            case 0:
                return '#';
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return 'L';
        }

        return map.get(point);
    }

    private Character decideSeatB(final Map<Point, Character> map, final Point point) {
        if (map.get(point) == '.') {
            return map.get(point);
        }

        var neighbours = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }

                Point curPoint = point;
                while (true) {
                    curPoint = curPoint.addX(x).addY(y);

                    if (map.get(curPoint) == null) {
                        break;
                    }

                    if (map.get(curPoint) == '#') {
                        neighbours++;
                        break;
                    }

                    if (map.get(curPoint) == 'L') {
                        break;
                    }
                }
            }
        }

        switch (Math.toIntExact(neighbours)) {
            case 0:
                return '#';
            case 5:
            case 6:
            case 7:
            case 8:
                return 'L';
        }

        return map.get(point);
    }

    @FunctionalInterface
    interface SeatingStrategy {
        Character decideSeat(final Map<Point, Character> map, final Point point);
    }
}

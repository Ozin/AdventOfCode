package AOC2021;

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Day15 {

    protected String a(final String[] input) throws Exception {
        final Map<Point, Integer> map = new HashMap<>();
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length(); x++) {
                map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }

        return solve(map::get, input[0].length(), input.length);
    }

    protected String b(final String[] input) throws Exception {
        final Map<Point, Integer> map = new HashMap<>();
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length(); x++) {
                map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }

        final int maxX = input[0].length();
        final int maxY = input.length;

        final Function<Point, Integer> calculatedMap = point -> {
            if (point.getX() < 0 || point.getY() < 0) return null;

            final int originalX = point.getX() % maxX;
            final int originalY = point.getY() % maxY;
            final int originalValue = map.get(new Point(originalX, originalY)) - 1;

            final int newValue = (point.getX() / maxX + point.getY() / maxY + originalValue) % 9;

            return newValue + 1;
        };

        return solve(calculatedMap, maxX * 5, maxY * 5);
    }

    private String solve(final Function<Point, Integer> map, final int maxX, final int maxY) {
        IntStreamEx.range(maxX)
                .flatMapToObj(sum -> IntStreamEx.range(sum + 1).mapToObj(x -> new Point(x, sum - x)));

        final Map<Point, Integer> tentativeDistance = new HashMap<>();
        tentativeDistance.put(new Point(0, 0), 0);

        for (int sum = 1; sum < 2 * maxX; sum++) {
            for (int x = 0; x <= sum; x++) {
                final Point current = new Point(x, sum - x);
                final Integer currentValue = map.apply(current);

                if (currentValue == null) continue;

                tentativeDistance.put(current, currentValue + minOf(tentativeDistance.get(current.addX(-1)), tentativeDistance.get(current.addY(-1))));
            }
        }

        return "" + tentativeDistance.get(new Point(maxX - 1, maxY - 1));
    }

    private Integer minOf(final Integer x, final Integer y) {
        return StreamEx.of(x, y)
                .filter(Objects::nonNull)
                .minByInt(i -> i)
                .orElse(0);
    }

}

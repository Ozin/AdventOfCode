package AOC2021;

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day15 {

    protected String a(final String[] input) throws Exception {
        final Map<Point, Integer> map = new HashMap<>();
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length(); x++) {
                map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }

        return solveA(map, input[0].length() - 1, input.length - 1);
    }

    private String solveA(final Map<Point, Integer> map, final int maxX, final int maxY) {
        IntStreamEx.range(maxX + 1)
                .flatMapToObj(sum -> IntStreamEx.range(sum + 1).mapToObj(x -> new Point(x, sum - x)));

        final Map<Point, Integer> tentativeDistance = new HashMap<>();
        tentativeDistance.put(new Point(0, 0), 0);

        for (int sum = 1; sum < 2 * maxX + 1; sum++) {
            for (int x = 0; x <= sum; x++) {
                final Point current = new Point(x, sum - x);
                final Integer currentValue = map.get(current);

                if (currentValue == null) continue;

                tentativeDistance.put(current, currentValue + minOf(tentativeDistance.get(current.addX(-1)), tentativeDistance.get(current.addY(-1))));
            }
        }

        return "" + tentativeDistance.get(new Point(maxX, maxY));
    }

    private Integer minOf(final Integer x, final Integer y) {
        return StreamEx.of(x, y)
                .filter(Objects::nonNull)
                .minByInt(i -> i)
                .orElse(0);
    }

    protected String b(final String[] input) throws Exception {
        return "";
    }

}

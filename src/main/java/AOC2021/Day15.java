package AOC2021;


import io.vavr.API;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class Day15 {

    protected String a(final String[] input) throws Exception {
        final Map<Point, Integer> map = new HashMap<>(input.length * input[0].length());
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length(); x++) {
                map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }

        return solve(p -> Optional.ofNullable(map.get(p)), input[0].length() - 1, input.length - 1);
    }

    protected String b(final String[] input) throws Exception {
        final Map<Point, Integer> map = new HashMap<>(input.length * input[0].length());
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length(); x++) {
                map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }

        final Function<Point, Optional<Integer>> getMap = API.Function(this::calculatedValue).curried()
                .apply(map)
                .apply(input[0].length())
                .apply(input.length);

        return solve(getMap, input[0].length() * 5 - 1, input.length * 5 - 1);
    }

    private String solve(final Function<Point, Optional<Integer>> map, final int maxX, final int maxY) {
        final Set<Point> unvisited = new HashSet<>(maxX * maxY);

        IntStreamEx.range(maxX + 1).boxed()
                .cross(IntStreamEx.range(maxY + 1).boxed().toSet())
                .mapKeyValue(Point::new)
                .forEach(unvisited::add);

        final Map<Point, Integer> tmpDistances = new HashMap<>(maxX * maxY);
        tmpDistances.put(new Point(0, 0), 0);

        Point current = new Point(0, 0);

        while (!unvisited.isEmpty()) {
            final Map<Point, Integer> newNeighbours = current.getNeighbours().values()
                    .mapToEntry(map)
                    .filterKeys(unvisited::contains)
                    .filterValues(Optional::isPresent)
                    .mapValues(Optional::get)
                    .mapValues(updateDistance(tmpDistances, current))
                    .toMap();

            unvisited.remove(current);

            newNeighbours.forEach((p, v) -> tmpDistances.merge(p, v, Math::min));

            if (!unvisited.isEmpty()) {
                current = StreamEx.of(unvisited)
                        .mapToEntry(tmpDistances::get)
                        .filterValues(Objects::nonNull)
                        .minByInt(Map.Entry::getValue)
                        .get()
                        .getKey();
            }

            if (unvisited.size() % 100 == 0)
                System.out.printf("%6s, %23s, %6s%n", unvisited.size(), current, tmpDistances.size());
        }

        // Abstract2DPuzzle.printMap(tmpDistances.filterValues(v -> Integer.MAX_VALUE != v).mapValues(String::valueOf).mapValues(s -> s.charAt(0)).toJavaMap());

        return "" + tmpDistances.get(new Point(maxX, maxY));
    }

    private Function<Integer, Integer> updateDistance(final Map<Point, Integer> tentativeDistance, final Point current) {
        return v -> tentativeDistance.get(current) + v;
    }

    private Optional<Integer> calculatedValue(final Map<Point, Integer> map, final Integer originalMaxX, final Integer originalMaxY, final Point p) {
        if (p.getX() < 0 || p.getY() < 0) return Optional.empty();

        final int originalX = p.getX() % originalMaxX;
        final int originalY = p.getY() % originalMaxY;
        final int originalValue = map.get(new Point(originalX, originalY)) - 1;

        final int newValue = (p.getX() / originalMaxX + p.getY() / originalMaxY + originalValue) % 9;

        return Optional.of(newValue + 1);
    }
}

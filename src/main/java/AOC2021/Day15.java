package AOC2021;


import io.vavr.API;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import one.util.streamex.IntStreamEx;
import utils.Point;

import java.util.Comparator;
import java.util.function.Function;

public class Day15 {

    protected String a(final String[] input) throws Exception {
        Map<Point, Integer> map = HashMap.empty();
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length(); x++) {
                map = map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }

        return solveA(map.lift(), input[0].length() - 1, input.length - 1);
    }

    private String solveA(final Function1<Point, Option<Integer>> map, final int maxX, final int maxY) {
        Set<Point> unvisited = IntStreamEx.range(maxX + 1).boxed()
                .cross(IntStreamEx.range(maxY + 1).boxed().toSet())
                .mapKeyValue(Point::new)
                .collect(HashSet.collector());

        Map<Point, Integer> tentativeDistance = HashMap.of(new Point(0, 0), 0);
        Point current = new Point(0, 0);
        while (!unvisited.isEmpty()) {
            final Map<Point, Integer> newNeighbours = current.getNeighbours().values()
                    .collect(HashSet.collector())
                    .toMap(p -> p, map)
                    .filterValues(Option::isDefined)
                    .mapValues(Option::get)
                    .mapValues(updateDistance(tentativeDistance, current));

            unvisited = unvisited.remove(current);

            tentativeDistance = newNeighbours
                    .merge(tentativeDistance, Math::min);

            if (!unvisited.isEmpty()) {
                final Comparator<Tuple2<Point, Integer>> comparator1 = Comparator.comparingInt(t -> t._2);
                final Comparator<Tuple2<Point, Integer>> comparator2 = Comparator.comparingInt(t -> t._1.getX() + t._1.getY());
                current = unvisited.toMap(p -> p, tentativeDistance::get)
                        .filterValues(Option::isDefined)
                        .mapValues(Option::get)
                        .minBy(comparator1.thenComparing(comparator2.reversed()))
                        .get()._1;
            }

            if (unvisited.length() % 100 == 0)
                System.out.printf("%6s, %23s, %s%n", unvisited.length(), current, tentativeDistance.size());
        }

        // Abstract2DPuzzle.printMap(tentativeDistance.filterValues(v -> Integer.MAX_VALUE != v).mapValues(String::valueOf).mapValues(s -> s.charAt(0)).toJavaMap());

        return "" + tentativeDistance.get(new Point(maxX, maxY)).get();
    }

    private Function<Integer, Integer> updateDistance(final Map<Point, Integer> tentativeDistance, final Point current) {
        return v -> tentativeDistance.get(current).map(i -> v + i).get();
    }

    protected String b(final String[] input) throws Exception {
        Map<Point, Integer> map = HashMap.empty();
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length(); x++) {
                map = map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }

        final Function1<Point, Option<Integer>> getMap = API.Function(this::calculatedValue).curried()
                .apply(map)
                .apply(input[0].length())
                .apply(input.length);

        return solveA(getMap, input[0].length() * 5 - 1, input.length * 5 - 1);
    }

    private Option<Integer> calculatedValue(final Map<Point, Integer> map, final Integer originalMaxX, final Integer originalMaxY, final Point p) {
        if (p.getX() < 0 || p.getY() < 0) return Option.none();

        final int originalX = p.getX() % originalMaxX;
        final int originalY = p.getY() % originalMaxY;
        final int originalValue = map.get(new Point(originalX, originalY)).get() - 1;

        final int newValue = (p.getX() / originalMaxX + p.getY() / originalMaxY + originalValue) % 9;

        return Option.some(newValue + 1);
    }
}

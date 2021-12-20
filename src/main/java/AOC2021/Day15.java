package AOC2021;


import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;
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

        Set<Point> unvisited = map.keySet();
        Map<Point, Integer> tentativeDistance = map.mapValues(ignored -> Integer.MAX_VALUE).put(new Point(0, 0), 0);
        Point current = new Point(0, 0);

        while (!unvisited.isEmpty()) {
            if (current.equals(new Point(input[0].length() - 1, input.length - 1))) break;

            final Map<Point, Integer> newNeighbours = current.getNeighbours().values()
                    .filter(map.keySet()::contains)
                    .collect(HashSet.collector())
                    .toMap(p -> p, map::get)
                    .mapValues(Option::get)
                    .mapValues(updateDistance(tentativeDistance, current));

            unvisited = unvisited.remove(current);

            tentativeDistance = newNeighbours
                    .merge(tentativeDistance, Math::min);

            if (!unvisited.isEmpty())
                current = unvisited.toMap(p -> p, tentativeDistance::get)
                        .mapValues(Option::get)
                        .minBy(Comparator.comparingInt(t -> t._2))
                        .get()._1;

            if (unvisited.length() % 100 == 0)
                System.out.println(unvisited.length());
        }

        // Abstract2DPuzzle.printMap(tentativeDistance.filterValues(v -> Integer.MAX_VALUE != v).mapValues(String::valueOf).mapValues(s -> s.charAt(0)).toJavaMap());

        return "" + tentativeDistance.get(new Point(input[0].length() - 1, input.length - 1)).get();
    }

    private Function<Integer, Integer> updateDistance(final Map<Point, Integer> tentativeDistance, final Point current) {
        return v -> v + tentativeDistance.get(current).get();
    }

    protected String b(final String[] input) throws Exception {
        return "" + null;
    }
}

package AOC2021;


import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import utils.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day09 {

    protected String a(final String[] input) throws Exception {
        Map<Point, Integer> map = new HashMap<>();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }

        return "" + EntryStream.of(map)
                .filterKeys(point -> isLowPoint(map, point))
                .values()
                .mapToInt(i -> i + 1)
                .sum();
    }

    private boolean isLowPoint(Map<Point, Integer> heatMap, Point curPoint) {
        return curPoint.getNeighbours()
                .values()
                .map(heatMap::get)
                .filter(Objects::nonNull)
                .allMatch(neighbourHeat -> heatMap.get(curPoint) < neighbourHeat);
    }

    protected String b(final String[] input) throws Exception {
        Map<Point, Integer> map = new HashMap<>();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }

        final Set<Point> lowPoints = EntryStream.of(map)
                .filterKeys(point -> isLowPoint(map, point))
                .keys()
                .toSet();

        /*
        final Set<Point> basins = StreamEx.of(lowPoints)
                .flatCollection(lowPoint -> findBasin(lowPoint, map))
                .toSet();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                if (basins.contains(new Point(x, y))) {
                    System.out.print(" ");
                } else {
                    System.out.print(map.get(new Point(x, y)));
                }
            }
            System.out.println();
        }
        */

        return "" + StreamEx.of(lowPoints)
                .map(lowPoint -> findBasin(lowPoint, map))
                .mapToInt(Set::size)
                .reverseSorted()
                .limit(3)
                .reduce((a, b) -> a * b)
                .getAsInt();
    }

    private Set<Point> findBasin(Point lowPoint, Map<Point, Integer> map) {
        final Set<Point> neighborBasin = lowPoint
                .getNeighbours()
                .values()
                .mapToEntry(map::get)
                .filterValues(Objects::nonNull)
                .filterValues(localValue -> localValue > map.get(lowPoint) && localValue != 9)
                .keys()
                .flatCollection(newLowPoint -> this.findBasin(newLowPoint, map))
                .toSet();

        return Stream.concat(Stream.of(lowPoint), neighborBasin.stream()).collect(Collectors.toSet());
    }
}

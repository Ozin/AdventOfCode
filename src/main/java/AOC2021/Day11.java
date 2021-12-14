package AOC2021;


import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import utils.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day11 {

    protected String a(final String[] input) throws Exception {
        Map<Point, Integer> octupuses = get2DPuzzle(input);

        int flashes = 0;
        for (int i = 0; i < 100; i++) {
            octupuses = iterate(octupuses);
            flashes += StreamEx.of(octupuses.values())
                    .filter(o -> o == 0)
                    .count();
        }

        return "" + flashes;
    }

    protected String b(final String[] input) throws Exception {
        Map<Point, Integer> octupuses = get2DPuzzle(input);

        for (int i = 1; ; i++) {
            octupuses = iterate(octupuses);

            if (octupuses.values().stream().allMatch(v -> v == 0))
                return "" + i;
        }
    }

    private Map<Point, Integer> iterate(Map<Point, Integer> octupuses) {
        Map<Point, Integer> afterCharge = EntryStream.of(octupuses)
                .mapValues(v -> v + 1)
                .toMap();

        while (true) {

            Set<Point> willFlash = EntryStream.of(afterCharge)
                    .filterValues(v -> v > 9)
                    .keys()
                    .filter(octupuses.keySet()::contains)
                    .toSet();

            if (willFlash.isEmpty()) break;

            List<Point> willBeCharged = StreamEx.of(willFlash)
                    .flatMap(Point::getNeighboursIncludingDiagonal)
                    .filter(octupuses.keySet()::contains)
                    .filter(p -> afterCharge.get(p) > 0)
                    .toList();

            willBeCharged.forEach(p -> afterCharge.compute(p, (point, val) -> val + 1));
            willFlash.forEach(p -> afterCharge.put(p, 0));
        }

        return afterCharge;
    }

    private Map<Point, Integer> get2DPuzzle(String[] input) {
        Map<Point, Integer> map = new HashMap<>();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                map.put(new Point(x, y), Integer.parseInt(input[y].substring(x, x + 1)));
            }
        }
        return map;
    }

}

package AOC2018.day11;

import one.util.streamex.IntStreamEx;

import java.util.Comparator;
import java.util.Map;

public class Day11 {
    public static int INPUT = 8141;

    public static void main(String[] args) {
        Map<Point, Integer> powerMap = IntStreamEx.range(0, 300).boxed()
            .cross(IntStreamEx.range(0, 300).boxed().toList())
            .parallel()
            .mapKeyValue(Point::new)
            .mapToEntry(Day11::calculateCellPower)
            .toMap();

        SummedAreaTable sat = new SummedAreaTable(powerMap);

        System.out.printf("Result of 11 A: %s%n", a(sat));
        System.out.printf("Result of 11 B: %s%n", b(sat));
    }

    private static Map.Entry<Point, Integer> b(SummedAreaTable sat) {
        Comparator<Map.Entry<Point, Integer>> comparator = Comparator.comparingInt(entry -> sat.get(entry.getKey(), entry.getValue()));
        return IntStreamEx.range(1, 300).boxed()
            .mapToEntry(gridSize -> getBestPointForGridSize(sat, gridSize))
            .invert()
            .sorted(comparator.reversed())
            .mapKeys(p -> p.addY(1).addX(1))
            .findFirst()
            .get();


    }

    private static Point a(SummedAreaTable sat) {
        final int gridSize = 3;

        return getBestPointForGridSize(sat, gridSize)
            .addY(1)
            .addX(1);
    }

    private static Point getBestPointForGridSize(SummedAreaTable sat, int gridSize) {
        Comparator<Map.Entry<Point, Integer>> comparator = Comparator.comparingInt(Map.Entry::getValue);
        Map.Entry<Point, Integer> pointIntegerEntry = IntStreamEx.range(0, 301 - gridSize).boxed()
            .cross(IntStreamEx.range(0, 301 - gridSize).boxed().toList())
            .mapKeyValue(Point::new)
            .mapToEntry(p -> sat.get(p, gridSize))
            .sorted(comparator.reversed())
            .findFirst()
            .get();

        System.out.printf("GridSize: %s, Point: %s, Value: %s%n", gridSize, pointIntegerEntry.getKey(), pointIntegerEntry.getValue());
        return pointIntegerEntry
            .getKey();
    }
//
//    private static Point getBestPointForSubGrid(int gridSize) {
//        Comparator<Point> pointComparator = Comparator.comparingInt(p -> calculateSubGridPower(p.getX(), p.getY(), gridSize));
//
//        Optional<Point> first = IntStreamEx.range(1, 301 - gridSize).boxed()
//                .cross(IntStreamEx.range(1, 301 - gridSize).boxed().toList())
//                .parallel()
//                .mapKeyValue(Point::new)
//                .sorted(pointComparator.reversed())
//                .findFirst();
//        return first.get();
//    }
//
//    public static int calculateSubGridPower(int x, int y, int gridSize) {
//        return IntStreamEx.range(x, x + gridSize).boxed()
//                .cross(IntStreamEx.range(y, y + gridSize).boxed().toList())
//                .mapKeyValue(Day11::calculateCellPower)
//                .mapToInt(value -> value)
//                .sum();
//    }

    public static int calculateCellPower(Point p) {
        return calculateCellPower(p.getX(), p.getY());
    }

    public static int calculateCellPower(int x, int y) {
        int rackId = x + 10 + 1;
        int power = rackId;
        power *= y + 1;
        power += INPUT;
        power *= rackId;
        power /= 100;
        power %= 10;
        power -= 5;

        return power;
    }
}

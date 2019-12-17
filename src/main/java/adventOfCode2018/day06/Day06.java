package adventOfCode2018.day06;

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day06 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day06.class.getResourceAsStream("/2018/input06")))) {
            var points = br.lines().map(Point::new).toArray(Point[]::new);

            a(points);
            b(points);
        }
    }

    private static void b(Point[] importantPoints) {
        long sizeOfArea = getCoordinateStream(importantPoints)
                .mapToInt(point -> sumOfAllDistances(point, importantPoints))
                .filter(distance -> distance < 10000)
                .count();

        System.out.printf("Result of 06 B: %d%n", sizeOfArea);

    }

    private static void a(Point[] importantPoints) {
        List<Point> hull = ConvexHull.convexHull(importantPoints);

        var pointWithMostNearest = getCoordinateStream(importantPoints)
                .mapToEntry(gridPoint -> getNearestPoints(importantPoints, gridPoint))
                .filterValues(points -> points.size() == 1)
                .mapValues(l -> l.get(0))
                .invert()
                .sortedBy(Map.Entry::getKey)
                .collapseKeys()
                .removeKeys(hull::contains)
                .mapValues(List::size)
                .reverseSorted(Comparator.comparingInt(Map.Entry::getValue))
                .findFirst()
                .get();
        System.out.printf("Result of 06 A: %s%n", pointWithMostNearest);
    }

    private static StreamEx<Point> getCoordinateStream(Point[] importantPoints) {
        Point lowestX = Stream.of(importantPoints).min(Comparator.comparingInt(Point::getX)).get();
        Point highestX = Stream.of(importantPoints).max(Comparator.comparingInt(Point::getX)).get();
        Point lowestY = Stream.of(importantPoints).min(Comparator.comparingInt(Point::getY)).get();
        Point highestY = Stream.of(importantPoints).max(Comparator.comparingInt(Point::getY)).get();

        return IntStreamEx.range(lowestX.getX(), highestX.getX() + 1).boxed()
                .cross(IntStreamEx.range(lowestY.getY(), highestY.getY() + 1).boxed().toList())
                .mapKeyValue(Point::new);
    }

    private static List<Point> getNearestPoints(Point[] importantPoints, Point gridPoint) {
        return StreamEx.of(importantPoints)
                .mapToEntry(point -> point.distance(gridPoint))
                .invert()
                .sortedBy(Map.Entry::getKey)
                .collapseKeys()
                .values()
                .findFirst()
                .get();
    }

    private static int sumOfAllDistances(Point coordinate, Point[] importantPoints) {
        return StreamEx.of(importantPoints)
                .mapToInt(point -> point.distance(coordinate))
                .sum();
    }

}

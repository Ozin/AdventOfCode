package AOC2018.day10;

import AOC2018.day08.Day08;
import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Stream;

public class Day10 {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day08.class.getResourceAsStream("/2018/input10")))) {
            Point[] points = br.lines().map(Point::new).toArray(Point[]::new);

            System.out.println("Result of 10 A:");
            Point[] stars = a(points);
            print(stars);

            System.out.printf("Result of 10 B: %s%n", b(points));
        }
    }

    private static Point[] a(Point[] points) {
        //Point[] movedPoints = Stream.of(points).map(Point.atSecond(10036)).toArray(Point[]::new);
        //print(movedPoints);

        long previousArea = new Area(points).calculate();
        Point[] previousPoints = null;
        for(int i = 0; ; i++) {
            Point[] newPoints = Stream.of(points).map(Point.atSecond(i)).toArray(Point[]::new);
            long newArea = new Area(newPoints).calculate();
            if(newArea > previousArea) {
                return previousPoints;
            }

            previousArea = newArea;
            previousPoints = newPoints;
        }
    }

    private static long b(Point[] points) {
        long previousArea = new Area(points).calculate();
        for(int i = 0; ; i++) {
            Point[] newPoints = Stream.of(points).map(Point.atSecond(i)).toArray(Point[]::new);
            long newArea = new Area(newPoints).calculate();
            if(newArea > previousArea) {
                return i - 1;
            }

            previousArea = newArea;
        }
    }

    private static void print(Point[] points) {
        Area area = new Area(points);

        Set<Point> pointSet = StreamEx.of(points)
                .map(p -> new Point(p.getX(), p.getY()))
                .toSet();

        StringBuilder sb = new StringBuilder();
        for (long y = area.getMinY(); y <= area.getMaxY(); y++) {
            for (long x = area.getMinX(); x <= area.getMaxX(); x++) {
                sb.append(pointSet.contains(new Point(x, y)) ? "#" : " ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

}

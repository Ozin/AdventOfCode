package AOC2019;

import one.util.streamex.StreamEx;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.util.Comparator.comparingInt;

public class Day03 extends AbstractDay<String[][]> {
    public static void main(final String[] args) {
        new Day03().run();
    }

    @Override
    protected String[][] parseInput(final String[] rawInput) {
        return Arrays.stream(rawInput).map(s -> s.split(",")).toArray(String[][]::new);
    }

    protected Object a(final String[][] input) {
        final Set<LinkedList<Point>> wires = new HashSet<>();
        final Set<Point> intersections = new HashSet<>();

        for (final String[] wire : input) {
            final LinkedList<Point> newWire = getSingleWire(wire);

            intersections.addAll(findIntersections(newWire, wires));

            wires.add(newWire);
        }

        return StreamEx.of(intersections).min(comparingInt(Point::manhattenDistanceFromSource)).orElseThrow().manhattenDistanceFromSource();
    }

    protected Object b(final String[][] input) {
        final Set<LinkedList<Point>> wires = new HashSet<>();
        final Set<Point> intersections = new HashSet<>();

        for (final String[] wire : input) {
            final LinkedList<Point> newWire = getSingleWire(wire);

            intersections.addAll(findIntersections(newWire, wires));

            wires.add(newWire);
        }

        int minDist = Integer.MAX_VALUE;
        for (final Point intersection : intersections) {
            final int currentDist = wires.stream()
                    .mapToInt(wire -> wire.indexOf(intersection))
                    .sum();

            minDist = Math.min(minDist, currentDist);
        }

        return minDist + 2;
    }

    private Set<Point> findIntersections(final Collection<Point> newWire, final Set<? extends List<Point>> wires) {
        final Set<Point> intersections = new HashSet<>();

        for (final List<Point> oldWire : wires) {
            final Set<Point> intersection = new HashSet<>(oldWire);
            intersection.retainAll(new HashSet<>(newWire));

            intersections.addAll(intersection);
        }

        return intersections;
    }

    private LinkedList<Point> getSingleWire(final String[] wire) {
        final LinkedList<Point> wirePoints = new LinkedList<>();
        Point joinPoint = new Point(0, 0);
        for (final String wirePart : wire) {
            joinPoint = addWirePart(joinPoint, wirePart, wirePoints);
        }

        return wirePoints;
    }

    private Point addWirePart(final Point source, final String wirePart, final List<Point> wirePoints) {
        final String direction = wirePart.substring(0, 1);
        final int amount = Integer.parseInt(wirePart.substring(1));

        int dX = 0;
        int dY = 0;
        switch (direction) {
            case "R":
                dX = 1;
                break;
            case "L":
                dX = -1;
                break;
            case "U":
                dY = 1;
                break;
            case "D":
                dY = -1;
                break;
            default:
                throw new IllegalStateException("Unknown direction");
        }

        for (int i = 1; i <= amount; i++) {
            final Point newPoint = source.addY(dY * i).addX(dX * i);
            wirePoints.add(newPoint);
        }

        return source.addY(dY * amount).addX(dX * amount);
    }

}

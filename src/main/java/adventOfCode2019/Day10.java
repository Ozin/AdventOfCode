package adventOfCode2019;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;
import static java.util.function.Predicate.not;

public class Day10 extends AbstractDay<Set<Point>> {

    public static void main(final String[] args) {
        new Day10().run();
    }

    int MAX_X;
    int MAX_Y;

    @Override
    protected Set<Point> parseInput(final String[] rawInput) throws Exception {
        final Set<Point> set = new HashSet<>();
        for (int y = 0; y < rawInput.length; y++) {
            for (int x = 0; x < rawInput[y].length(); x++) {
                if (rawInput[y].charAt(x) == '.') continue;

                MAX_X = Math.max(MAX_X, x);
                MAX_Y = Math.max(MAX_Y, y);
                set.add(new Point(x, y));
            }
        }
        return set;
    }

    @Override
    protected Map.Entry<Point, Integer> a(final Set<Point> asteroids) throws Exception {
        final Map<Point, List<Point>> pointsWithDirectSights = StreamEx.of(asteroids)
                .cross(asteroids)
                .filterKeyValue((a, b) -> !a.equals(b))
                .filterKeyValue((a, b) -> !haveDirectSight(a, b, asteroids))
                .grouping();

//        for (int y = 0; y <= MAX_Y; y++) {
//            for (int x = 0; x <= MAX_X; x++) {
//                final Point current = new Point(x, y);
//                if (asteroids.contains(current))
//                    System.out.print(Integer.toString(pointsWithDirectSights.get(current).size(), 36));
//                else
//                    System.out.print(".");
//            }
//            System.out.println();
//        }

        return EntryStream.of(pointsWithDirectSights)
                .mapValues(List::size)
                .maxByInt(Map.Entry::getValue)
                .orElseThrow();
    }

    @Override
    protected Object b(final Set<Point> asteroids) throws Exception {
        final Point laser = a(asteroids).getKey();

        final Map<Vector, List<Vector>> sameDirectionMap = StreamEx.of(asteroids)
                .filter(not(laser::equals))
                .map(asteroid -> new Vector(laser, asteroid))
                .groupingBy(Vector::reduce);

        final List<LinkedList<Vector>> sameDirectionsAndSorted = EntryStream.of(sameDirectionMap)
                .sortedByDouble(entry -> getAngleVector(entry.getKey()))
                .mapValues(this::toLinkedList)
                .values()
                .collect(Collectors.toList());

        final List<Vector> vectors = new ArrayList<>(asteroids.size());
        final Vector laserVector = new Vector(laser);

        while (!sameDirectionsAndSorted.isEmpty()) {
            for (final LinkedList<Vector> sameDirections : sameDirectionsAndSorted) {
                vectors.add(sameDirections.removeFirst());
            }

            sameDirectionsAndSorted.removeIf(List::isEmpty);
        }


        return new Vector(laser).add(vectors.get(199)).toPoint();
    }

    private LinkedList<Vector> toLinkedList(final List<Vector> vectors) {
        final var result = new LinkedList<>(vectors);
        result.sort(comparingDouble(Vector::length));
        return result;
    }

    private boolean haveDirectSight(final Point a, final Point b, final Set<Point> asteroids) {
        final Set<Point> lineOfSight = getLineOfSight(a, b);
        return lineOfSight.stream()
                .filter(not(a::equals))
                .filter(not(b::equals))
                .anyMatch(asteroids::contains);
    }

    private Set<Point> getLineOfSight(final Point a, final Point b) {
        final Vector source = new Vector(a);
        final Vector target = new Vector(b);
        final Vector ab = new Vector(a, b).reduce();

        return StreamEx.iterate(source, prev -> prev.add(ab))
                .takeWhileInclusive(newPoint -> !target.equals(newPoint))
                .map(Point::new)
                // .filter(not(a::equals))
                // .filter(not(b::equals))
                .toSet();
    }

    private double getAngleVector(final Vector vector) {
        return (vector.angle() + (5D / 2D) * Math.PI) % (2 * Math.PI);
    }
}

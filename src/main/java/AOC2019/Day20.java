package AOC2019;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import utils.Abstract2DPuzzle;
import utils.Point;

public class Day20 extends Abstract2DPuzzle {

    @Override
    protected Object b(final Map<Point, Character> input) throws Exception {
        return null;
    }

    @Override
    protected Object a(final Map<Point, Character> input) throws Exception {
        final Map<String, List<Point>> labels = findLabels(input);

        final Point start = labels.get("A").get(0);
        final Point end = labels.get("Z").get(0);

        final Map<Point, Point> portals = EntryStream.of(labels)
            .filterValues(l -> l.size() > 1)
            .values()
            .flatMapToEntry(p -> Map.of(p.get(0), p.get(1), p.get(1), p.get(0)))
            .toMap();

        final Set<Point> paths = filterPaths(input)
            .append(start)
            .append(end)
            .toSet();

        final List<Point> shortestPath = new PathfinderWithPortals(paths, portals).findShortestPath(start, end).orElseThrow();

        shortestPath.forEach(p -> input.put(p, '+'));
        printGame(input);
        return shortestPath.size() - 1;
    }

    private Map<String, List<Point>> findLabels(final Map<Point, Character> input) {

        final BiFunction<Point, Direction, Map.Entry<Set<Character>, Point>> getLabelPointlistEntry = (p, d) -> {
            final Point p1 = d.continueStraight(p);
            final Point p2 = d.continueStraight(p1);

            final Character v1 = input.get(p1);
            final Character v2 = input.get(p2);

            final Set<Character> key = new HashSet<>();
            key.add(v1);
            key.add(v2);

            return Map.entry(key, p);
        };

        return StreamEx.of(filterPaths(input))
            .mapToEntry(path -> path.getNeighbours().filterValues(neighbourPoint -> Character.isUpperCase(input.get(neighbourPoint))).keys().findAny())
            .filterValues(Optional::isPresent)
            .mapValues(Optional::get)
            .mapKeyValue(getLabelPointlistEntry)
            .mapToEntry(Map.Entry::getKey, Map.Entry::getValue)
            .sortedBy(e -> e.getKey().toString())
            .collapseKeys()
            .mapKeys(s -> StreamEx.of(s).joining())
            .toMap();
    }

    private StreamEx<Point> filterPaths(final Map<Point, Character> input) {
        return EntryStream.of(input).filterValues(c -> c.equals('.')).keys();
    }

    private class PathfinderWithPortals extends Pathfinder {
        private final Map<Point, Point> portals;

        public PathfinderWithPortals(final Set<Point> points, final Map<Point, Point> portals) {
            super(points);
            this.portals = portals;
        }

        @Override
        protected StreamEx<Point> getNeighbours(final Point current) {
            final StreamEx<Point> normalNeighbours = super.getNeighbours(current);
//            final Set<Point> portalNeighbours = current.getNeighbours().values()
//                .filter(portals::containsKey)
//                .map(portals::get)
//                .toSet();

            if (portals.containsKey(current)) {
                return normalNeighbours.append(portals.get(current));
            } else {
                return normalNeighbours;
            }
        }
    }

    public void printGame(final Map<Point, Character> gameState) {
        final int maxX = gameState.keySet().stream().mapToInt(Point::getX).max().orElseThrow();
        final int maxY = gameState.keySet().stream().mapToInt(Point::getY).max().orElseThrow();
        final int minX = gameState.keySet().stream().mapToInt(Point::getX).min().orElseThrow();
        final int minY = gameState.keySet().stream().mapToInt(Point::getY).min().orElseThrow();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                final char value = gameState.getOrDefault(new Point(x, y), '#');
                System.out.print(value == '.' ? ' ' : value);
            }
            System.out.println();
        }
    }
}

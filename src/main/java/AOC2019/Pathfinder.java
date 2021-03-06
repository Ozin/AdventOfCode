package AOC2019;

import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import utils.Point;

import java.util.*;

import static java.util.function.Predicate.not;

@RequiredArgsConstructor
public class Pathfinder {
    private final Set<Point> points;

    public Optional<List<Point>> findShortestPath(final Point a, final Point b) {
        return Optional.ofNullable(new AStar(b, a).shortestPath());
    }

    private class AStar {
        private final Map<Point, Integer> gCost = new HashMap<>();
        private final Map<Point, Point> parent = new HashMap<>();
        private final Set<Point> open = new HashSet<>();
        private final Set<Point> closed = new HashSet<>();

        private final Point a;
        private final Point b;

        public AStar(final Point a, final Point b) {
            this.a = a;
            this.b = b;
        }

        public int hCost(final Point x) {
            return x.manhattenDistance(b);
        }

        public int fCost(final Point x) {
            if (!gCost.containsKey(x)) {
                return Integer.MAX_VALUE;
            } else {
                return gCost.get(x) + hCost(x);
            }
        }

        public List<Point> shortestPath() {
            this.open.add(a);
            this.gCost.put(a, 0);

            while (!open.isEmpty()) {
                final Point current = StreamEx.of(open).minByInt(this::fCost).get();
                open.remove(current);
                closed.add(current);

                if (current.equals(b)) {
                    return traceBack(b, parent);
                }

                final Set<Point> openNeighbours = getNeighbours(current)
                    .filter(not(closed::contains))
                    .toSet();

                for (final Point neighbour : openNeighbours) {
                    final Integer currentNeighbourCosts = gCost.getOrDefault(neighbour, Integer.MAX_VALUE);
                    final int newNeighbourCost = gCost.get(current) + 1;
                    if (currentNeighbourCosts > newNeighbourCost || !open.contains(neighbour)) {
                        gCost.put(neighbour, newNeighbourCost);
                        parent.put(neighbour, current);
                        open.add(neighbour);
                    }
                }
            }

            return null;
        }

        private List<Point> traceBack(final Point b, final Map<Point, Point> parent) {
            return StreamEx.iterate(b, parent::get).takeWhile(Objects::nonNull).toList();
        }
    }

    protected StreamEx<Point> getNeighbours(final Point current) {
        return current.getNeighbours().values()
            .filter(Pathfinder.this.points::contains);
    }
}

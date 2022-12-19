package AOC2022;

import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.Objects;

public class Dijkstra {
    static <T> Try<List<T>> dijkstra(final T start, final T end, final Map<T, List<T>> graph) {
        final var distance = new java.util.HashMap<T, Integer>();
        final var previous = new java.util.HashMap<T, T>();

        distance.put(start, 0);
        var q = graph.keySet().toList();

        while (q.nonEmpty()) {
            final var t = q.minBy(e -> distance.getOrDefault(e, Integer.MAX_VALUE)).get();

            if (t.equals(end)) break;

            q = q.remove(t);

            for (final T neighbor : graph.get(t).toStream().flatMap(l -> l)) {
                if (q.contains(neighbor)) {
                    updateDistance(t, neighbor, distance, previous);
                }
            }
        }

        if (!previous.containsKey(end))
            return Try.failure(new IllegalArgumentException("Can't find path between %s and %s".formatted(
                    start,
                    end
            )));

        return Try.success(Stream.iterate(end, previous::get).takeWhile(Objects::nonNull).toList().reverse());
    }

    private static <T> void updateDistance(final T current, final T neighbor, final java.util.HashMap<T, Integer> distance, final java.util.HashMap<T, T> previous) {
        final int alternative = getAlternative(current, distance);
        if (alternative < distance.getOrDefault(neighbor, Integer.MAX_VALUE)) {
            distance.put(neighbor, alternative);
            previous.put(neighbor, current);
        }
    }

    private static <T> int getAlternative(final T current, final java.util.HashMap<T, Integer> distance) {
        try {
            return Math.addExact(distance.getOrDefault(current, Integer.MAX_VALUE), 1);
        } catch (final ArithmeticException e) {
            return Integer.MAX_VALUE;
        }
    }
}

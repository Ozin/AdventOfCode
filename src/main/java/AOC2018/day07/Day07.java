package AOC2018.day07;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class Day07 {
    // Step X must be finished before step C can begin.

    public static void main(final String[] args) throws IOException {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(Day07.class.getResourceAsStream("/2018/input07")))) {
            final Map<String, List<String>> dependencies = StreamEx.of(br.lines())
                .mapToEntry(Day07::toKey, Day07::toValue)
                .sortedBy(Map.Entry::getKey)
                .collapseKeys()
                .toMap();

            final var graph = NodeFactory.getAllNodes();

            NodeFactory.build(dependencies);
            a(graph);

            b(graph);
        }
    }

    private static void b(final Collection<Node> graph) {
        final TreeSet<Node> possibilities = getStartingNodes(graph);
        final var finished = new ArrayList<Node>();
        final Worker[] workers = new Worker[]{
            new Worker(possibilities, finished, graph),
            new Worker(possibilities, finished, graph),
            new Worker(possibilities, finished, graph),
            new Worker(possibilities, finished, graph),
            new Worker(possibilities, finished, graph)
        };

        for (int i = 0; ; i++) {
            System.out.printf("%5d", i);
            for (final Worker worker : workers) {
                worker.doWork();
            }

            for (final Worker worker : workers) {
                if (worker.isAvailable()) {
                    worker.doWork();
                }

                System.out.printf("%5s", worker.getCurrentJob() == null ? "." : worker.getCurrentJob());
            }

            System.out.printf("    %s%n", finished);

            if (Stream.of(workers).allMatch(Worker::isFinished)) {
                System.out.printf("Result of 07 B: %s%n", i);
                return;
            }
        }
    }

    private static void a(final Collection<Node> graph) {
        final var possibilities = getStartingNodes(graph);

        final var steps = new ArrayList<>();

        while (!possibilities.isEmpty()) {
            final var firstNextPossibility = possibilities.pollFirst();
            steps.add(firstNextPossibility);

            graph.stream()
                .filter(not(steps::contains))
                .filter(n -> steps.containsAll(n.getPreconditions()))
                .forEach(possibilities::add);
        }

        final String stepsString = steps.stream().map(Object::toString).collect(Collectors.joining(""));
        System.out.printf("Result of 07 A: %s%n", stepsString);
    }

    private static TreeSet<Node> getStartingNodes(final Collection<Node> graph) {
        final var startingNodes = new TreeSet<Node>();

        graph.stream()
            .filter(n -> n.getPreconditions().isEmpty())
            .forEach(startingNodes::add);

        return startingNodes;
    }

    private static String toValue(final String s) {
        return s.substring(36, 37);
    }

    private static String toKey(final String s) {
        return s.substring(5, 6);
    }
}


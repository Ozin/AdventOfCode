package AOC2018.day07;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class Day07 {
    // Step X must be finished before step C can begin.

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day07.class.getResourceAsStream("/2018/input07")))) {
            Map<String, List<String>> dependencies = StreamEx.of(br.lines())
                    .mapToEntry(Day07::toKey, Day07::toValue)
                    .sortedBy(Map.Entry::getKey)
                    .collapseKeys()
                    .toMap();

            var graph = NodeFactory.getAllNodes();

            NodeFactory.build(dependencies);
            a(graph);

            b(graph);
        }
    }

    private static void b(Collection<Node> graph) {
        TreeSet<Node> possibilities = getStartingNodes(graph);
        var finished = new ArrayList<Node>();
        Worker[] workers = new Worker[]{
                new Worker(possibilities, finished, graph),
                new Worker(possibilities, finished, graph),
                new Worker(possibilities, finished, graph),
                new Worker(possibilities, finished, graph),
                new Worker(possibilities, finished, graph)
        };

        for (int i = 0; ; i++) {
            System.out.printf("%5d", i);
            for (Worker worker : workers) {
                worker.doWork();
            }

            for (Worker worker : workers) {
                if(worker.isAvailable()) {
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

    private static void a(Collection<Node> graph) {
        var possibilities = getStartingNodes(graph);

        var steps = new ArrayList<>();

        while (!possibilities.isEmpty()) {
            var firstNextPossibility = possibilities.pollFirst();
            steps.add(firstNextPossibility);

            graph.stream()
                    .filter(not(steps::contains))
                    .filter(n -> steps.containsAll(n.getPreconditions()))
                    .forEach(possibilities::add);
        }

        String stepsString = steps.stream().map(Object::toString).collect(Collectors.joining(""));
        System.out.printf("Result of 07 A: %s%n", stepsString);
    }

    private static TreeSet<Node> getStartingNodes(Collection<Node> graph) {
        var startingNodes = new TreeSet<Node>();

        graph.stream()
                .filter(n -> n.getPreconditions().isEmpty())
                .forEach(startingNodes::add);

        return startingNodes;
    }

    private static String toValue(String s) {
        return s.substring(36, 37);
    }

    private static String toKey(String s) {
        return s.substring(5, 6);
    }
}


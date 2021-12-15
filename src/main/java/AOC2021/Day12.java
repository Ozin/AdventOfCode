package AOC2021;


import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import one.util.streamex.StreamEx;

import static java.util.function.Predicate.not;

public class Day12 {

    protected String a(final String[] input) throws Exception {
        var nodes = getNodes(input);
        Option<Set<List<String>>> paths = findPaths(nodes, "start", HashSet.of("start"));
        return "" + paths.getOrElse(HashSet.empty()).size();
    }

    protected String b(final String[] input) throws Exception {
        var nodes = getNodes(input);
        Option<Set<List<String>>> paths = findPathsSingleCaveTwice(nodes, "start", List.of());
        return "" + paths.getOrElse(HashSet.empty()).size();
    }

    private Option<Set<List<String>>> findPaths(final Map<String, Set<String>> nodes, String currentNode, Set<String> pastNodes) {
        if (currentNode.equals("end"))
            return Option.some(HashSet.of(List.of("end")));

        if (currentNode.matches("^[a-z]+$"))
            pastNodes = pastNodes.add(currentNode);


        var nextNodes = nodes.get(currentNode)
                .getOrElse(HashSet.empty())
                .filter(not(pastNodes::contains));

        if (nextNodes.isEmpty()) {
            return Option.none();
        }

        final Set<String> finalPastNodes = pastNodes;
        var pathsFromHere = nextNodes
                .map(nextNode -> findPaths(nodes, nextNode, finalPastNodes));

        if (pathsFromHere.forAll(Option::isEmpty))
            return Option.none();

        return Option.some(
                pathsFromHere
                        .filter(not(Option::isEmpty))
                        .flatMap(Option::get)
                        .map(path -> path.prepend(currentNode))
        );
    }

    private Option<Set<List<String>>> findPathsSingleCaveTwice(final Map<String, Set<String>> nodes, final String currentNode, List<String> pastNodes) {
        if (currentNode.equals("end"))
            return Option.some(HashSet.of(List.of("end")));

        if (currentNode.matches("^[a-z]+$"))
            pastNodes = pastNodes.append(currentNode);


        final List<String> finalPastNodes = pastNodes;
        var nextNodes = nodes.get(currentNode)
                .getOrElse(HashSet.empty())
                .filter(possibleCave ->
                        !"start".equals(possibleCave)
                        && (
                                !hasTwoSmallCaves(finalPastNodes, possibleCave)
                                || !finalPastNodes.contains(possibleCave)
                        )
                );

        if (nextNodes.isEmpty()) {
            return Option.none();
        }

        var pathsFromHere = nextNodes
                .map(nextNode -> findPathsSingleCaveTwice(nodes, nextNode, finalPastNodes));

        if (pathsFromHere.forAll(Option::isEmpty))
            return Option.none();

        return Option.some(
                pathsFromHere
                        .filter(not(Option::isEmpty))
                        .flatMap(Option::get)
                        .map(path -> path.prepend(currentNode))
        );
    }

    private boolean hasTwoSmallCaves(List<String> finalPastNodes, String possibleCave) {
        final Long maxCaveVisits = StreamEx.of(finalPastNodes.toJavaStream())
                .sorted()
                .runLengths()
                .values()
                .maxByLong(l -> l).orElse(0L);
        return maxCaveVisits > 1;
    }

    private Map<String, Set<String>> getNodes(String[] input) {
        java.util.Map<String, String> map = new java.util.HashMap<>(input.length * 2);
        return StreamEx.of(input)
                .map(s -> s.split("-"))
                .flatMapToEntry(s -> java.util.Map.of(
                        s[0], s[1],
                        s[1], s[0]
                ))
                .sorted(java.util.Map.Entry.comparingByKey())
                .collapseKeys(HashSet.collector())
                .collect(HashMap.collector(java.util.Map.Entry::getKey, java.util.Map.Entry::getValue));
    }
}

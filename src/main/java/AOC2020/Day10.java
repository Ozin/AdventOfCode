package AOC2020;

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 extends AbstractDay<int[]> {
    public static void main(final String[] args) {
        new Day10().run();
    }

    @Override
    protected int[] parseInput(final String[] rawInput) throws Exception {
        return StreamEx.of(rawInput).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    protected Object a(final int[] input) throws Exception {
        final int[] extendedInput = extendInput(input);


        final Map<Integer, Long> entries = IntStreamEx.range(extendedInput.length - 1)
            .map(i -> extendedInput[i + 1] - extendedInput[i])
            .sorted()
            .boxed()
            .runLengths()
            .toMap();
        return entries.get(1L) * entries.get(3L);
    }

    @Override
    protected Object b(final int[] input) throws Exception {
        final int[] extendedInput = extendInput(input);

        final Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < extendedInput.length; i++) {
            for (int j = i + 1; j < i + 4 && j < extendedInput.length; j++) {
                if (extendedInput[j] - extendedInput[i] <= 3) {
                    graph.merge(extendedInput[i], List.of(extendedInput[j]), this::mergeList);
                }
            }
        }

        //return countPaths(new HashMap<>(), graph, 0, 6);
        return countPaths(new HashMap<>(), graph, extendedInput[0], extendedInput[extendedInput.length - 1]);
    }

    private int[] extendInput(final int[] input) {
        Arrays.sort(input);
        final int[] extendedInput = new int[input.length + 2];
        System.arraycopy(input, 0, extendedInput, 1, input.length);
        extendedInput[0] = 0;
        extendedInput[extendedInput.length - 1] = input[input.length - 1] + 3;
        return extendedInput;
    }

    private long countPaths(final Map<Integer, Long> cache, final Map<Integer, List<Integer>> graph, final int start, final int end) {
        final Long cached = cache.get(start);
        if (cached != null) {
            return cached;
        }

        if (start == end) {
            return 1;
        }

        final long sum = graph.get(start)
            .stream()
            .mapToLong(newStart -> countPaths(cache, graph, newStart, end))
            .sum();

        cache.put(start, sum);

        return sum;
    }

    private <T> List<T> mergeList(final List<T> a, final List<T> b) {
        return Stream.of(a, b).flatMap(List::stream).collect(Collectors.toList());
    }

}

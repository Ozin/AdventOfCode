package AOC2020;

import static java.util.function.Predicate.not;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import one.util.streamex.EntryStream;
import one.util.streamex.LongStreamEx;
import one.util.streamex.StreamEx;

public class Day13 extends AbstractDay<Entry<Integer, String[]>> {
    public static void main(final String[] args) {
        new Day13().run();
    }

    @Override
    protected Entry<Integer, String[]> parseInput(final String[] rawInput) throws Exception {
        return Map.entry(Integer.parseInt(rawInput[0]), rawInput[1].split(","));
    }

    @Override
    protected Object a(final Entry<Integer, String[]> input) throws Exception {

        final int chosenBus = StreamEx.of(input.getValue())
            .filter(not("x"::equals))
            .mapToInt(Integer::parseInt)
            .sortedByInt(bus -> (input.getKey() / bus + 1) * bus - input.getKey())
            .findFirst()
            .orElseThrow();

        return (chosenBus * (input.getKey() / chosenBus + 1) - input.getKey()) * chosenBus;
    }

    @Override
    protected Object b(final Entry<Integer, String[]> input) throws Exception {
        final List<Entry<Integer, Integer>> busIds = new ArrayList<>();
        int count = 0;
        for (final String busInfo : input.getValue()) {
            if (!"x".equals(busInfo)) {
                busIds.add(Map.entry(count, Integer.parseInt(busInfo)));
            }

            count++;
        }

        long time = busIds.get(0).getValue();
        long increment = busIds.get(0).getValue();
        for (int i = 1; i < busIds.size(); i++) {
            final Entry<Integer, Integer> indexed = busIds.get(i);
            while ((time + indexed.getKey()) % indexed.getValue() != 0) {
                time += increment;
            }
            increment *= indexed.getValue();
        }
        return time;
    }
}

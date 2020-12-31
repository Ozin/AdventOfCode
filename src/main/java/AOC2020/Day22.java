package AOC2020;

import io.vavr.collection.List;
import one.util.streamex.StreamEx;
import utils.AbstractDay;
import utils.Indexed;
import utils.Tuple2;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day22 extends AbstractDay<Tuple2<LinkedList<Integer>, LinkedList<Integer>>> {
    public static void main(final String[] args) {
        new Day22().run();
    }

    @Override
    protected Tuple2<LinkedList<Integer>, LinkedList<Integer>> parseInput(final String[] rawInput) throws Exception {
        final String[] split = String.join("\n", rawInput).split(Pattern.quote("\n\n"));
        final LinkedList<Integer> a = StreamEx.of(split[0].split("\n")).skip(1).map(Integer::parseInt).collect(Collectors.toCollection(LinkedList::new));
        final LinkedList<Integer> b = StreamEx.of(split[1].split("\n")).skip(1).map(Integer::parseInt).collect(Collectors.toCollection(LinkedList::new));
        return new Tuple2<>(a, b);
    }

    @Override
    protected Object a(final Tuple2<LinkedList<Integer>, LinkedList<Integer>> input) throws Exception {
        final LinkedList<Integer> a = input.getA();
        final LinkedList<Integer> b = input.getB();
        while (!a.isEmpty() && !b.isEmpty()) {
            final int aNext = a.removeFirst();
            final int bNext = b.removeFirst();

            if (aNext > bNext) {
                a.addLast(aNext);
                a.addLast(bNext);
            } else {
                b.addLast(bNext);
                b.addLast(aNext);
            }
        }

        return StreamEx.of(a, b).flatMap(Collection::stream)
            .map(Indexed.map())
            .mapToEntry(Indexed::getIndex, Indexed::getValue)
            .mapKeys(k -> (a.size() + b.size()) - k)
            .mapToInt(e -> e.getKey() * e.getValue())
            .sum();
    }

    @Override
    protected Object b(final Tuple2<LinkedList<Integer>, LinkedList<Integer>> input) throws Exception {
        return null;
    }

    private Object recursiveGame(final List<Integer> a, final List<Integer> b, final Set<Tuple2<LinkedList<Integer>, LinkedList<Integer>>> previousGames) {
        return null;
    }
}

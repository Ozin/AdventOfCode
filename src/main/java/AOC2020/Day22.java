package AOC2020;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import utils.AbstractDay;

import java.util.regex.Pattern;

public class Day22 extends AbstractDay<Tuple2<List<Integer>, List<Integer>>> {
    public static void main(final String[] args) {
        new Day22().run();
    }

    @Override
    protected Tuple2<List<Integer>, List<Integer>> parseInput(final String[] rawInput) throws Exception {
        final String[] split = String.join("\n", rawInput).split(Pattern.quote("\n\n"));
        final List<Integer> a = Stream.of(split[0].split("\n")).tail().map(Integer::parseInt).toList();
        final List<Integer> b = Stream.of(split[1].split("\n")).tail().map(Integer::parseInt).toList();
        return new Tuple2<>(a, b);
    }

    @Override
    protected Object a(final Tuple2<List<Integer>, List<Integer>> input) throws Exception {
        List<Integer> a = input._1;
        List<Integer> b = input._2;
        while (!a.isEmpty() && !b.isEmpty()) {
            final int aNext = a.head();
            final int bNext = b.head();

            a = a.tail();
            b = b.tail();

            if (aNext > bNext) {
                a = a.append(aNext).append(bNext);
            } else {
                b = b.append(bNext).append(aNext);
            }
        }

        return Stream.concat(a, b)
            .zipWithIndex()
            .toMap(Tuple2::_1, Tuple2::_2)
            .mapValues(k -> (input._1.size() + input._2.size()) - k)
            .iterator((key, value) -> key * value)
            .sum();
    }

    @Override
    protected Object b(final Tuple2<List<Integer>, List<Integer>> input) throws Exception {
        return null;
    }

    private Object recursiveGame(final List<Integer> a, final List<Integer> b, final Set<Tuple2<List<Integer>, List<Integer>>> previousGames) {
        return null;
    }
}

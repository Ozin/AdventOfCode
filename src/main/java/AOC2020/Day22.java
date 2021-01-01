package AOC2020;

import io.vavr.Tuple2;
import io.vavr.collection.HashSet;
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

        return getScore(a, b);
    }

    private Number getScore(final List<Integer> a, final List<Integer> b) {
        return Stream.concat(a, b)
            .zipWithIndex()
            .toMap(Tuple2::_1, Tuple2::_2)
            .mapValues(k -> (a.size() + b.size()) - k)
            .iterator((key, value) -> key * value)
            .sum();
    }

    @Override
    protected Object b(final Tuple2<List<Integer>, List<Integer>> input) throws Exception {
        final Tuple2<List<Integer>, List<Integer>> gameResult = recursiveGame(input);
        return getScore(gameResult._1, gameResult._2);
    }

    private Tuple2<List<Integer>, List<Integer>> recursiveGame(final Tuple2<List<Integer>, List<Integer>> input) {
        Tuple2<List<Integer>, List<Integer>> currentRound = input;
        Set<Tuple2<List<Integer>, List<Integer>>> playedRounds = HashSet.empty();

        while (!currentRound._1.isEmpty() && !currentRound._2.isEmpty()) {
            final Tuple2<List<Integer>, List<Integer>> next = recursiveRound(currentRound, playedRounds);
            playedRounds = playedRounds.add(currentRound);
            currentRound = next;
        }

        return currentRound;
    }

    private Tuple2<List<Integer>, List<Integer>> recursiveRound(final Tuple2<List<Integer>, List<Integer>> round, final Set<Tuple2<List<Integer>, List<Integer>>> previousGames) {
        final List<Integer> a = round._1;
        final List<Integer> b = round._2;

        if (previousGames.contains(round)) {
            return new Tuple2<>(
                a.tail().append(a.head()).append(b.head()),
                b.tail()
            );
        }

        final int aNext = a.head();
        final int bNext = b.head();

        final List<Integer> aTail = a.tail();
        final List<Integer> bTail = b.tail();

        if (aTail.size() >= aNext && bTail.size() >= bNext) {
            return evaluateRecursiveRound(
                aNext, aTail,
                bNext, bTail
            );
        } else {
            if (aNext > bNext) {
                return new Tuple2<>(
                    a.tail().append(a.head()).append(b.head()),
                    b.tail()
                );
            } else {
                return new Tuple2<>(
                    a.tail(),
                    b.tail().append(b.head()).append(a.head())
                );
            }
        }
    }

    private Tuple2<List<Integer>, List<Integer>> evaluateRecursiveRound(final int aHead, final List<Integer> aTail, final int bHead, final List<Integer> bTail) {
        final Tuple2<List<Integer>, List<Integer>> recursiveGame = recursiveGame(
            new Tuple2<>(
                aTail.take(aHead),
                bTail.take(bHead)
            )
        );

        if (recursiveGame._2.isEmpty()) {
            return new Tuple2<>(
                aTail.append(aHead).append(bHead),
                bTail
            );
        } else {
            return new Tuple2<>(
                aTail,
                bTail.append(bHead).append(aHead)
            );
        }
    }
}

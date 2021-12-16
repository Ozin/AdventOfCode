package AOC2021;


import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;

public class Day14 {

    protected String a(final String[] input) throws Exception {
        Stream<String> polymer = Stream.of(input[0].split(""));
        Map<List<String>, List<String>> map = Stream.of(input)
                .drop(2)
                .map(s -> s.split(" -> "))
                .toMap(s -> List.of(s[0].split("")), s -> List.of(s[1], s[0].split("")[1]));

        for (int i = 0; i < 10; i++) {
            polymer = polymer.sliding(2)
                    .flatMap(s -> map.getOrElse(s.toList(), s.toList()))
                    .toStream()
                    .prepend(polymer.head());
        }

        Seq<Integer> counts = polymer
                .groupBy(p -> p)
                .values()
                .map(Stream::length);
        int min = counts.min().get();
        int max = counts.max().get();
        return "" + (max - min);
    }

    protected String b(final String[] input) throws Exception {
        Map<String, Long> pairCount = HashMap.empty();
        // NNCB => NN -> 1, NC -> 1, CB -> 1
        for (int i = 0; i < input[0].length() - 1; i++) {
            pairCount = pairCount.put(input[0].substring(i, i + 2), 1L, Long::sum);
        }

        Map<String, List<String>> iterationMap = Stream.of(input)
                .drop(2)
                .map(s -> s.split(" -> "))
                .toMap(s -> s[0], s -> {
                    final String[] t = s[0].split("");
                    return List.of(t[0] + s[1], s[1] + t[1]);
                });

        for (int i = 0; i < 40; i++) {
            pairCount = pairCount
                    .flatMap(oldPair -> iterationMap.get(oldPair._1).get().map(newPair -> Tuple.of(newPair, oldPair._2)))
                    .groupBy(Tuple2::_1)
                    .mapValues(newPairs -> newPairs.map(newPair -> newPair._2).reduce(Long::sum));
        }

        Seq<Long> counts = pairCount
                .mapKeys(pair -> Array.of(pair.split("")))
                .toStream()
                .flatMap(pair -> pair._1.map(singlePolymer -> Tuple.of(singlePolymer, pair._2)))
                .groupBy(Tuple2::_1)
                .mapValues(vs -> vs.map(Tuple2::_2))
                .mapValues(vs -> vs.reduce(Long::sum))
                .values()
                .map(v -> (v + 1) / 2);

        long min = counts.min().get();
        long max = counts.max().get();

        return "" + (max - min);
    }
}

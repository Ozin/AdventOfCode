package AOC2021;


import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import one.util.streamex.EntryStream;
import utils.LinkedList;
import utils.Rational;

import java.util.HashMap;


public class Day14 {

    protected String a(final String[] input) throws Exception {
        Stream<String> polymer = Stream.of(input[0].split(""));
        Map<List<String>, List<String>> map = Stream.of(input)
                .drop(2)
                .map(s -> s.split(" -> "))
                .toMap(s -> List.of(s[0].split("")), s -> List.of(s[1], s[0].split("")[1]));

        int prev = 0;
        for (int i = 0; i < 10; i++) {
            polymer = polymer.sliding(2)
                    .flatMap(s -> map.getOrElse(s.toList(), s.toList()))
                    .toStream()
                    .prepend(polymer.head());

            HashMap<String, Integer> counts = new HashMap<>();
            polymer.forEach(p -> counts.merge(p, 1, Integer::sum));
            int min = EntryStream.of(counts).values().minByInt(j -> j).get();
            int max = EntryStream.of(counts).values().maxByInt(j -> j).get();

            System.out.printf("%2d; %10d; %10d; %s%n",i, max-min, max-min-prev, ((double) max-min) / (max-min-prev));
            prev = max-min;
        }

        HashMap<String, Integer> counts = new HashMap<>();
        polymer.forEach(p -> counts.merge(p, 1, Integer::sum));
        int min = EntryStream.of(counts).values().minByInt(i -> i).get();
        int max = EntryStream.of(counts).values().maxByInt(i -> i).get();
        return "" + (max - min);
    }

    protected String b(final String[] input) throws Exception {
        final LinkedList<String> first = new LinkedList<>(input[0].substring(0, 1));

        LinkedList<String> current = first;
        for (int i = 1; i < input[0].length(); i++) {
            current = current.add(input[0].substring(i, i + 1));
        }

        Map<String, String> map = Stream.of(input)
                .drop(2)
                .map(s -> s.split(" -> "))
                .toMap(s -> s[0], s -> s[1]);

        int prev = 0;
        for (int i = 0; i < 40; i++) {
            iterate(first, map);

            HashMap<String, Integer> counts = new HashMap<>();
            first.forEach(p -> counts.merge(p, 1, Integer::sum));
            int min = EntryStream.of(counts).values().minByInt(j -> j).get();
            int max = EntryStream.of(counts).values().maxByInt(j -> j).get();

            System.out.printf("%2d; %10d; %10d; %50s; %s%n",i, max-min, max-min-prev, new Rational(max-min, max-min-prev).reduce(), ((double) max - min) / (max - min - prev));
            prev = max-min;
        }

        HashMap<String, Integer> counts = new HashMap<>();
        first.forEach(p -> counts.merge(p, 1, Integer::sum));
        int min = EntryStream.of(counts).values().minByInt(i -> i).get();
        int max = EntryStream.of(counts).values().maxByInt(i -> i).get();

        return "" + (max - min);
    }

    private void iterate(LinkedList<String> first, Map<String, String> map) {
        var current = first;
        Set<String> usedPattern = HashSet.empty();
        while (current.getNext() != null) {
            String currentPair = current.getValue() + current.getNext().getValue();
            Option<String> insert = map.get(currentPair);
            if(insert.isDefined()) {
                current = current.add(insert.get());
            } else {
                System.out.println(currentPair  + "not defined");
            }

            current = current.getNext();
            usedPattern = usedPattern.add(currentPair);
        }
        System.out.println("unused patterns: " + usedPattern.mkString(","));
    }
}

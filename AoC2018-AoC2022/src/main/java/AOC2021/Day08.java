package AOC2021;

import io.vavr.collection.HashSet;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Day08 {

    protected String a(final String[] input) throws Exception {
        return "" + StreamEx.of(input)
                .map(s -> s.substring(61))
                .flatArray(s -> s.split(" "))
                .mapToInt(String::length)
                .filter(List.of(2, 4, 3, 7)::contains)
                .count();
    }

    protected String b(final String[] input) throws Exception {
        return "" + StreamEx.of(input)
                .map(s -> s.split(Pattern.quote(" | ")))
                .mapToEntry(s -> s[0], s -> s[1])
                .mapKeys(this::findTranslationMap)
                .mapKeyValue(this::translate)
                .mapToInt(i -> i)
                .sum();
    }

    private int translate(Map<Set<Segment>, Integer> translationMap, String s) {
        return Integer.parseInt(
                StreamEx.of(s.split(" "))
                        .map(Segment::parse)
                        .map(translationMap::get)
                        .joining("")
        );
    }

    private Map<Set<Segment>, Integer> findTranslationMap(String inputs) {
        final Map<Integer, ArrayList<Set<Segment>>> clustered = StreamEx.of(inputs.split(" "))
                .map(Segment::parse)
                .mapToEntry(Set::size)
                .invert()
                .sortedByInt(Map.Entry::getKey)
                .collapseKeys()
                .mapValues(ArrayList::new)
                .toMap();

        Map<Integer, Set<Segment>> mappings = new HashMap<>();
        mappings.put(1, clustered.get(2).get(0));
        mappings.put(7, clustered.get(3).get(0));
        mappings.put(4, clustered.get(4).get(0));
        mappings.put(8, clustered.get(7).get(0));

        mappings.put(3,
                StreamEx.of(clustered.get(5))
                        .filter(s -> s.containsAll(mappings.get(1)))
                        .findFirst().get()
        );
        clustered.get(5).remove(mappings.get(3));

        mappings.put(9,
                StreamEx.of(clustered.get(6))
                        .filter(s -> s.containsAll(mappings.get(3)))
                        .findFirst().get()
        );
        clustered.get(6).remove(mappings.get(9));

        mappings.put(5,
                StreamEx.of(clustered.get(5))
                        .filter(s -> s.containsAll(HashSet.ofAll(mappings.get(4)).removeAll(mappings.get(1)).toJavaSet()))
                        .findFirst().get()
        );
        clustered.get(5).remove(mappings.get(5));

        mappings.put(6,
                StreamEx.of(clustered.get(6))
                        .filter(s -> s.containsAll(mappings.get(5)))
                        .findFirst().get()
        );
        clustered.get(6).remove(mappings.get(6));

        mappings.put(0, clustered.get(6).get(0));
        mappings.put(2, clustered.get(5).get(0));

        return EntryStream.of(mappings)
                .invert()
                .toMap();
    }

    enum Segment {
        A, B, C, D, E, F, G;

        public static Map<Set<Segment>, Integer> segmentToInt = Map.of(
                Set.of(C, F), 1,
                Set.of(A, C, F), 7,
                Set.of(B, C, D, F), 4,
                Set.of(A, C, D, E, G), 2,
                Set.of(A, C, D, F, G), 3,
                Set.of(A, B, D, F, G), 5,
                Set.of(A, B, C, E, F, G), 0,
                Set.of(A, B, D, E, F, G), 6,
                Set.of(A, B, C, D, F, G), 9,
                Set.of(A, B, C, D, E, F, G), 8
        );
        public static Map<Integer, Set<Segment>> intToSegment = Map.of(
                1, Set.of(C, F),
                7, Set.of(A, C, F),
                4, Set.of(B, C, D, F),
                2, Set.of(A, C, D, E, G),
                3, Set.of(A, C, D, F, G),
                5, Set.of(A, B, D, F, G),
                0, Set.of(A, B, C, E, F, G),
                6, Set.of(A, B, D, E, F, G),
                9, Set.of(A, B, C, D, F, G),
                8, Set.of(A, B, C, D, E, F, G)
        );

        public static int decode(String segments) {
            return Integer.parseInt(
                    StreamEx.of(segments.split(" "))
                            .map(Segment::parse)
                            .map(segmentToInt::get)
                            .joining("")
            );

        }

        public static Set<Segment> parse(String s) {
            return StreamEx.of(s.toUpperCase().split(""))
                    .map(Segment::valueOf)
                    .toSet();
        }
    }
}

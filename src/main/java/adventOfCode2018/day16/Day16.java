package adventOfCode2018.day16;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class Day16 {

    public static void main(final String[] args) throws Exception {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(Day16.class.getResourceAsStream("/2018/input16")))) {
            final String[] raw = br.lines().collect(Collectors.joining("\n")).split("\\n\\n\\n\\n");
            final int[][] inputB = Arrays.stream(raw[1].split("\\n")).map(Record::toIntArray).toArray(int[][]::new);
            final Record[] inputA = Arrays.stream(raw[0].split("\\n\\n")).map(Record::new).toArray(Record[]::new);
            System.out.println("Result of 16 A: " + a(inputA));
            System.out.println("Result of 16 B: " + b(inputA, inputB));


        }

//        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day08.class.getResourceAsStream("/input16b")))) {
//            Point[] points = br.lines().map(Point::new).toArray(Point[]::new);
//
//            System.out.println("Result of 16 B:", b(points));
//        }

    }

    private static Object a(final Record[] records) {
        return StreamEx.of(records).cross(OpsCode.values())
                .filterKeyValue(Day16::fits)
                .collapseKeys()
                .filterValues(list -> list.size() >= 3)
                .count();
    }

    private static Object b(final Record[] inputA, final int[][] inputB) {
        final var yetToMap = new HashSet<>(Set.of(inputA));
        final var opsCodeMap = new HashMap<Integer, OpsCode>();
        while (!yetToMap.isEmpty()) {
            final var mappedCodes = StreamEx.of(yetToMap)
                    // find for each record the possible opsCodes (without the found ones)
                    .mapToEntry(r -> findOpsCodes(r, opsCodeMap.values()))
                    // check if a record has only one possibility
                    .filterValues(l -> l.size() == 1)
                    .mapValues(l -> l.get(0))
                    .mapKeys(r -> r.getInstruction()[0])
                    .distinctValues()
                    .toMap();

            opsCodeMap.putAll(mappedCodes);
            yetToMap.removeIf(r -> mappedCodes.keySet().contains(r.getInstruction()[0]));

//            var mappedCodes2 = StreamEx.of(yetToMap)
//                    // map all records to their opsCodes (0 - 15)
//                    .mapToEntry(r -> r.getInstruction()[0], Function.identity())
//                    .sortedByInt(Map.Entry::getKey)
//                    .collapseKeys()
//                    // check if there is a list of records where only one opsCode fits on all values
//                    .mapValues(list -> findOpsCodes(list, opsCodeMap.values()))
//                    .filterValues(list -> list.size() == 1)
//                    .mapValues(list -> list.get(0))
//                    .toMap();
//
//            opsCodeMap.putAll(mappedCodes2);
//            yetToMap.removeIf(r -> mappedCodes2.keySet().contains(r.getInstruction()[0]));
        }

        int[] register = new int[]{0, 0, 0, 0};
        for (int[] instruction : inputB) {
            register = opsCodeMap.get(instruction[0]).execute(register, instruction);
        }

        return register[0];
    }

    private static List<OpsCode> findOpsCodes(final Record record, final Collection<OpsCode> alreadyMappedCodes) {
        return StreamEx.of(OpsCode.values()).filter(not(alreadyMappedCodes::contains)).filter(opsCode -> fits(record, opsCode)).collect(Collectors.toList());
    }

    private static List<OpsCode> findOpsCodes(final List<Record> records, final Collection<OpsCode> alreadyMappedCodes) {
        return StreamEx.of(OpsCode.values()).filter(not(alreadyMappedCodes::contains)).filter(fitsAll(records)).collect(Collectors.toList());
    }

    private static Predicate<? super OpsCode> fitsAll(final List<Record> records) {
        return opsCode -> records.stream().allMatch(record -> fits(record, opsCode));
    }

    public static boolean fits(final Record record, final OpsCode opsCode) {
        return Arrays.equals(opsCode.execute(record.getBefore(), record.getInstruction()), record.getAfter());
    }
}

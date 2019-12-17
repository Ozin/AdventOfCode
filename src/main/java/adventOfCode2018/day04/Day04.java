package adventOfCode2018.day04;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class Day04 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day04.class.getResourceAsStream("/2018/input04")))) {
            var events = br.lines().map(Event::new).sorted().toArray(Event[]::new);
            var shifts = Shift.extractShifts(events);

            a(shifts);
            b(shifts);
        }
    }

    private static void b(Map<Integer, List<LocalTime>> shifts) {
        Map.Entry<Integer, Integer> guardMinute = EntryStream.of(shifts)
                .filterValues(not(List::isEmpty))
                .mapValues(Day04::mostFrequentMinute)
                .reverseSorted(Comparator.comparingLong(e -> e.getValue().getValue()))
                .mapValues(Map.Entry::getKey)
                .mapValues(LocalTime::getMinute)
                .findFirst()
                .get();

        System.out.printf("Result of 04 B: %d*%d=%d%n", guardMinute.getKey(), guardMinute.getValue(), guardMinute.getKey() * guardMinute.getValue());

    }

    private static Map.Entry<LocalTime, Long> mostFrequentMinute(List<LocalTime> localTimes) {
        return StreamEx.of(localTimes)
                .sorted()
                .runLengths()
                .reverseSorted(Comparator.comparingLong(Map.Entry::getValue))
                .findFirst()
                .orElse(null);
    }

    private static void a(Map<Integer, List<LocalTime>> shifts) {
        Integer mostSleepiestGuard = EntryStream.of(shifts)
                .mapValues(StreamEx::of)
                .collapseKeys(StreamEx::append)
                .flatMapValues(Function.identity())
                .collapseKeys(Collectors.counting())
                .reverseSorted(Comparator.comparingLong(Map.Entry::getValue))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);

        Integer mostFrequentMinute = StreamEx.of(shifts.get(mostSleepiestGuard))
                .sorted()
                .runLengths()
                .reverseSorted(Comparator.comparingLong(Map.Entry::getValue))
                .keys()
                .findFirst()
                .map(LocalTime::getMinute)
                .orElse(null);


        System.out.printf("Result of 04 A: %d*%d=%d%n", mostSleepiestGuard, mostFrequentMinute, mostSleepiestGuard * mostFrequentMinute);
    }

}

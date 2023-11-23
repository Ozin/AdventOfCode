package AOC2021;

import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import utils.Indexed;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

public class Day03 {

    protected String a(final String[] input) throws Exception {
        int[] counts = getCounts(Set.of(input));

        char[] gamma = IntStreamEx.of(counts)
                .map(i -> i > input.length / 2 ? 1 : 0)
                .map(i -> i + 48)
                .toCharArray();

        final int gammaRate = Integer.parseInt(new String(gamma), 2);
        final int mask = (1 << counts.length) - 1;
        final int epsilonRate = gammaRate ^ mask;

        return "" + (gammaRate * epsilonRate);
    }

    private int[] getCounts(Iterable<String> input) {
        Map<Integer, Integer> counts = new HashMap<>(20);
        for (String binNumberString : input) {
            binNumberString.chars()
                    .map(i -> i - 48)
                    .boxed()
                    .map(Indexed.map())
                    .forEach(binNumber -> counts.merge(binNumber.getIndex(), binNumber.getValue(), Integer::sum));
        }

        return EntryStream.of(counts)
                .sorted(Map.Entry.comparingByKey())
                .values()
                .mapToInt(i -> i)
                .toArray();
    }

    protected String b(final String[] input) throws Exception {
        HashSet<String> possibleOxygenRatings = new HashSet<>(Set.of(input));
        HashSet<String> possibleCo2Ratings = new HashSet<>(Set.of(input));
        for (int i = 0; i < input[0].length(); i++) {
            if (possibleOxygenRatings.size() > 1)
                filterList(possibleOxygenRatings, i, (charAtI, majority) -> charAtI != majority);
            if (possibleCo2Ratings.size() > 1)
                filterList(possibleCo2Ratings, i, (charAtI, majority) -> charAtI == majority);
        }
        final int oxygenRating = Integer.parseInt(possibleOxygenRatings.stream().findFirst().get(), 2);
        final int co2Rating = Integer.parseInt(possibleCo2Ratings.stream().findFirst().get(), 2);
        return "" + (oxygenRating * co2Rating);
    }

    private void filterList(HashSet<String> diagnosticReport, int i, BiPredicate<Character, Character> removeIf) {
        final int threshold = diagnosticReport.size() / 2;
        final int[] counts = getCounts(diagnosticReport);
        final char majority = counts[i] *2== diagnosticReport.size() ? '1' : counts[i] > threshold ? '1' : '0';

        diagnosticReport.removeIf(binary -> removeIf.test(binary.charAt(i), majority));
    }
}

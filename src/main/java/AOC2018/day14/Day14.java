package AOC2018.day14;

import org.magicwerk.brownies.collections.primitive.IntObjBigList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {

    public static void main(final String[] args) throws Exception {
        final String input = "170641";
        System.out.printf("Result of 12 A: %s%n", a(input));
        System.out.printf("Result of 12 B: %s%n", b(input));
    }

    private static int b(final String target) {
        final List<Integer> scores = new IntObjBigList();
        scores.add(3);
        scores.add(7);

        int indexA = 0;
        int indexB = 1;

        for (int i = 0; ; i++) {
            final int scoreA = scores.get(indexA);
            final int scoreB = scores.get(indexB);

            Stream.of(Integer.toString(scoreA + scoreB).split(""))
                .map(Integer::parseInt)
                .forEach(scores::add);

            indexA = (indexA + scoreA + 1) % scores.size();
            indexB = (indexB + scoreB + 1) % scores.size();

            // if (i % 10000000 == 0) {
            if (scores.size() > 20316375) {
                final String scoresString = scores.stream().map(integer -> Integer.toString(integer)).collect(Collectors.joining(""));

                final int result = scoresString.indexOf(target);
                if (result != -1) {
                    return result;
                }
            }
        }
    }

    private static String a(final String inputString) {
        final List<Integer> scores = new IntObjBigList();
        scores.add(3);
        scores.add(7);

        int indexA = 0;
        int indexB = 1;

        final int input = Integer.parseInt(inputString);

        for (int i = 0; i < input + 10; i++) {
            final int scoreA = scores.get(indexA);
            final int scoreB = scores.get(indexB);

            Stream.of(Integer.toString(scoreA + scoreB).split(""))
                .map(Integer::parseInt)
                .forEach(scores::add);

            indexA = (indexA + scoreA + 1) % scores.size();
            indexB = (indexB + scoreB + 1) % scores.size();

            //System.out.printf("%2d %2d %s%n", indexA, indexB, scores);
        }


        return scores.subList(input, input + 10).stream().map(i -> Integer.toString(i)).collect(Collectors.joining(""));
    }
}

package AOC2018.day12;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day12.class.getResourceAsStream("/2018/input12")))) {
            List<String> input = br.lines().collect(Collectors.toList());
            // String pots = "                                                                                                    " + input.get(0).substring(15).replace(".", " ") + "                                                                                                    ";

            Map<String, String> rules = StreamEx.of(input.stream())
                    .skip(2)
                    .mapToEntry(s -> s.substring(0, 5), s -> s.substring(9))
                    .toMap();

            String pots = input.get(0).substring(15);
            System.out.printf("Result of 12 A: %s%n", a(pots, rules));
            System.out.printf("Result of 12 B: %s%n", b(pots, rules));
        }
    }

    private static int a(String pots, Map<String, String> rules) {
        int offset = 0;

        System.out.printf(" 0: %s%n", pots);
        for (int i = 0; i < 20; i++) {
            offset -= 2;
            final String paddedPots = "...." + pots + "....";

            pots = IntStream.range(0, pots.length() + 4)
                    .mapToObj(j -> paddedPots.substring(j, j + 5))
                    .map(pattern -> rules.getOrDefault(pattern, "."))
                    .collect(Collectors.joining());

            System.out.printf("% 2d: %s%n", i + 1, pots);

        }

        final String finalPots = pots;
        final int finalOffset = offset;

        return IntStream.range(0, pots.length())
                .filter(i -> finalPots.charAt(i) == '#')
                .map(i -> i + finalOffset)
                .sum();
    }

    private static int b(String pots, Map<String, String> rules) {
        int offset = 0;

        System.out.printf(" 0: %s%n", pots);
        for (int iteration = 0; iteration < 505L; iteration++) {
            offset -= 2;
            final String paddedPots = "...." + pots + "....";

            pots = IntStream.range(0, pots.length() + 4)
                    .mapToObj(j -> paddedPots.substring(j, j + 5))
                    .map(pattern -> rules.getOrDefault(pattern, "."))
                    .collect(Collectors.joining());

            //System.out.printf("% 2d: %s%n", iteration + 1, pots);


            final String finalPots = pots;
            final int finalOffset = offset;

            System.out.println(IntStream.range(0, pots.length())
                    .filter(j -> finalPots.charAt(j) == '#')
                    .map(j -> j + finalOffset)
                    .sum());
        }

        final String finalPots = pots;
        final int finalOffset = offset;

        return IntStream.range(0, pots.length())
                .filter(i -> finalPots.charAt(i) == '#')
                .map(i -> i + finalOffset)
                .sum();
    }

}

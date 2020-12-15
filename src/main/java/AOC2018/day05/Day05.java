package AOC2018.day05;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Stream;

public class Day05 {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day05.class.getResourceAsStream("/2018/input05")))) {
            var polymers = br.lines().findFirst().get();

            a(polymers);
            b(polymers);
        }
    }

    private static void b(String polymers) {
        int shortestLength = StreamEx.split(ALPHABET, "")
                .mapToEntry(c -> withoutChar(c, polymers))
                .peekKeyValue((character, integer) -> System.out.printf("Length for '%s' was %d%n", character, integer))
                .sortedBy(Map.Entry::getValue)
                .values()
                .findFirst()
                .get();

        System.out.printf("Result of 05 B: %d%n", shortestLength);
    }

    private static Integer withoutChar(String c, String polymers) {
        String smallAlphabet = ALPHABET.replace(c, "");
        String smallPolymers = polymers.replace(c, "").replace(c.toUpperCase(), "");

        return reducePolymers(smallPolymers, smallAlphabet).length();
    }

    private static void a(String polymers) {
        polymers = reducePolymers(polymers, ALPHABET);

        System.out.printf("Result of 05 A: %s%n", polymers.length());
    }

    private static String reducePolymers(String polymers, String alphabet) {
        String regex = StreamEx.split(alphabet, "")
                .flatMap(c -> Stream.of(c + c.toUpperCase(), c.toUpperCase() + c))
                .joining("|");

        while (true) {
            String next = polymers.replaceAll(regex, "");

            if (next.length() == polymers.length()) {
                break;
            }

            polymers = next;
        }

        return polymers;
    }
}

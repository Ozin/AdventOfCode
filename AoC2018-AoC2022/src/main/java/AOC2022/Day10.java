package AOC2022;


import io.vavr.collection.Stream;

import java.util.ArrayList;

public class Day10 {
    public static void main(final String[] args) {
        System.out.printf("Solution for the first riddle: %s%n", one(input()));
        System.out.printf("Solution for the second riddle: %s%n", two(input()));
    }

    private static Object one(final String[] input) {
        final ArrayList<Integer> results = computeCycles(input);

        return Stream.iterate(20, i -> i + 40)
                     .takeWhile(i -> i < results.size())
                     .map(i -> i * results.get(i))
                     .sum();
    }

    private static ArrayList<Integer> computeCycles(final String[] input) {
        int register = 1;
        final var results = new ArrayList<Integer>();
        results.add(register);

        for (final String command : input) {
            if ("noop".equals(command)) {
                results.add(register);
            } else if (command.startsWith("addx ")) {
                results.add(register);
                results.add(register);
                register += Integer.parseInt(command.substring(5));
            }
        }
        return results;
    }

    private static Object two(final String[] input) {
        final var cycles = computeCycles(input);

        final var sb = new StringBuilder("\n");
        for (int i = 1; i < cycles.size(); i++) {
            final int currentRegister = cycles.get(i);
            final String pixel = currentRegister <= i % 40 && i % 40 <= currentRegister + 2 ? "#" : ".";

            sb.append(pixel);
            if (i % 40 == 0) sb.append("\n");

        }

        return sb.toString();
    }

    private static String[] testInput() {
        return """
                addx 15
                addx -11
                addx 6
                addx -3
                addx 5
                addx -1
                addx -8
                addx 13
                addx 4
                noop
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx -35
                addx 1
                addx 24
                addx -19
                addx 1
                addx 16
                addx -11
                noop
                noop
                addx 21
                addx -15
                noop
                noop
                addx -3
                addx 9
                addx 1
                addx -3
                addx 8
                addx 1
                addx 5
                noop
                noop
                noop
                noop
                noop
                addx -36
                noop
                addx 1
                addx 7
                noop
                noop
                noop
                addx 2
                addx 6
                noop
                noop
                noop
                noop
                noop
                addx 1
                noop
                noop
                addx 7
                addx 1
                noop
                addx -13
                addx 13
                addx 7
                noop
                addx 1
                addx -33
                noop
                noop
                noop
                addx 2
                noop
                noop
                noop
                addx 8
                noop
                addx -1
                addx 2
                addx 1
                noop
                addx 17
                addx -9
                addx 1
                addx 1
                addx -3
                addx 11
                noop
                noop
                addx 1
                noop
                addx 1
                noop
                noop
                addx -13
                addx -19
                addx 1
                addx 3
                addx 26
                addx -30
                addx 12
                addx -1
                addx 3
                addx 1
                noop
                noop
                noop
                addx -9
                addx 18
                addx 1
                addx 2
                noop
                noop
                addx 9
                noop
                noop
                noop
                addx -1
                addx 2
                addx -37
                addx 1
                addx 3
                noop
                addx 15
                addx -21
                addx 22
                addx -6
                addx 1
                noop
                addx 2
                addx 1
                noop
                addx -10
                noop
                noop
                addx 20
                addx 1
                addx 2
                addx 2
                addx -6
                addx -11
                noop
                noop
                noop
                """.stripIndent().split("\n");
    }

    private static String[] input() {
        return """
                noop
                noop
                noop
                noop
                addx 5
                addx 5
                noop
                addx 3
                noop
                addx 2
                addx 1
                noop
                noop
                noop
                addx 4
                addx -4
                addx 7
                addx 7
                noop
                addx -2
                addx 5
                addx -23
                addx 26
                addx -38
                noop
                noop
                noop
                addx 3
                addx 2
                addx 5
                addx 2
                addx 9
                addx -8
                addx 2
                addx 16
                addx -9
                addx 3
                addx -2
                addx 2
                noop
                addx 7
                addx -2
                addx 5
                addx 2
                addx 3
                noop
                addx -40
                addx 5
                noop
                addx 2
                addx -6
                addx 11
                addx -1
                addx 3
                addx 3
                noop
                noop
                noop
                addx 5
                addx -2
                noop
                addx 7
                addx 8
                addx -2
                addx -3
                addx 5
                addx 2
                addx -10
                addx -26
                addx 1
                noop
                addx 8
                addx -5
                addx 4
                addx 3
                addx -3
                addx 4
                addx 2
                addx -9
                addx 16
                addx 2
                noop
                addx 3
                addx 3
                addx 2
                addx -2
                addx 5
                addx 2
                addx 2
                noop
                addx -38
                addx 34
                addx -28
                addx -2
                addx 5
                addx 2
                addx 3
                addx -2
                addx 2
                addx 7
                noop
                noop
                addx -4
                addx 5
                addx 2
                addx 15
                addx -8
                addx 3
                noop
                addx 2
                addx -8
                addx 9
                addx -38
                addx 26
                noop
                addx -18
                noop
                noop
                addx 4
                addx 4
                addx -3
                addx 2
                addx 20
                addx -12
                noop
                noop
                noop
                addx 4
                addx 1
                noop
                addx 5
                noop
                noop
                addx 5
                noop
                noop
                noop
                noop
                noop
                noop
                noop
                """.stripIndent().split("\n");
    }
}

package AOC2022;


import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Stream;

import java.util.regex.Pattern;

public class Day05 {
    public static void main(final String[] args) {
        System.out.printf("Solution for the first riddle: %s%n", one());
        System.out.printf("Solution for the second riddle: %s%n", two());
    }


    private static Object one() {
        final Tuple2<List<Stack>, Stream<Move>> riddle = parseRiddle();

        return riddle._2
                       .flatMap(m -> Stream.fill(m.amount, new Move(m.from, m.to, 1)))
                       .foldLeft(riddle._1, Crane::moveCrate)
                       .map(Stack::head)
                       .map(Crate::id)
                       .mkString("");
    }

    private static Object two() {
        final Tuple2<List<Stack>, Stream<Move>> riddle = parseRiddle();

        return riddle._2
                       .foldLeft(riddle._1, Crane::moveCrate)
                       .map(Stack::head)
                       .map(Crate::id)
                       .mkString("");
    }

    private static Tuple2<List<Stack>, Stream<Move>> parseRiddle() {
        final String[] input = input().split("\n\n");
        return new Tuple2<>(Stack.of(input[0]), Stream.of(input[1].split("\n")).map(Move::of));
    }

    record Crane() {
        public static List<Stack> moveCrate(final List<Stack> stacks, final Move move) {
            final List<Crate> crates = stacks.get(move.from).crates.take(move.amount);
            return stacks.update(move.to, stack -> stack.pushAll(crates.reverse()))
                         .update(move.from, s -> s.drop(move.amount));
        }
    }

    record Move(int from, int to, int amount) {
        static final Pattern p = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

        // move 1 from 2 to 1
        public static Move of(final String line) {
            final var matcher = p.matcher(line);
            if (!matcher.find()) throw new IllegalArgumentException("Pattern does not fit: " + line);

            return new Move(
                    Integer.parseInt(matcher.group(2)) - 1,
                    Integer.parseInt(matcher.group(3)) - 1,
                    Integer.parseInt(matcher.group(1))
            );
        }
    }

    record Stack(List<Crate> crates) {

        public static List<Stack> of(final String s) {
            final String[] lines = s.split("\n");
            final int amountOfStacks = List.of(lines).last().split("\\d+").length;
            final Stack[] stacks = new Stack[amountOfStacks];
            for (int lineIndex = 0; lineIndex < lines.length - 1; lineIndex++) {
                final String line = lines[lineIndex];
                for (int stack = 0; stack < amountOfStacks; stack++) {
                    if (stacks[stack] == null) stacks[stack] = new Stack(List.empty());
                    // 1 + stack*4
                    final int crateIndex = 1 + stack * 4;
                    if (crateIndex >= line.length()) continue;

                    final String crateId = line.substring(crateIndex, crateIndex + 1);
                    if (crateId.isBlank()) continue;

                    stacks[stack] = new Stack(stacks[stack].crates.append(new Crate(crateId)));
                }
            }
            return List.of(stacks);
        }

        public Stack pushAll(final List<Crate> crates) {
            return new Stack(this.crates.pushAll(crates));
        }

        public Stack drop(final int amount) {
            return new Stack(crates.drop(amount));
        }

        public Crate head() {
            return crates.head();
        }
    }

    record Crate(String id) {
    }


    private static String testInput() {
        return """
                    [D]   \s
                [N] [C]   \s
                [Z] [M] [P]
                 1   2   3 \s
                                
                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
                """.stripIndent();
    }

    private static String input() {
        return """
                [Q]         [N]             [N]   \s
                [H]     [B] [D]             [S] [M]
                [C]     [Q] [J]         [V] [Q] [D]
                [T]     [S] [Z] [F]     [J] [J] [W]
                [N] [G] [T] [S] [V]     [B] [C] [C]
                [S] [B] [R] [W] [D] [J] [Q] [R] [Q]
                [V] [D] [W] [G] [P] [W] [N] [T] [S]
                [B] [W] [F] [L] [M] [F] [L] [G] [J]
                 1   2   3   4   5   6   7   8   9\s
                                          
                move 3 from 6 to 2
                move 2 from 8 to 7
                move 3 from 3 to 8
                move 2 from 5 to 3
                move 5 from 9 to 7
                move 5 from 3 to 5
                move 1 from 4 to 2
                move 3 from 2 to 1
                move 2 from 9 to 6
                move 4 from 1 to 4
                move 6 from 5 to 8
                move 1 from 6 to 3
                move 8 from 8 to 9
                move 5 from 9 to 2
                move 1 from 3 to 4
                move 11 from 7 to 2
                move 1 from 4 to 1
                move 1 from 5 to 9
                move 1 from 3 to 9
                move 1 from 9 to 5
                move 21 from 2 to 6
                move 2 from 8 to 4
                move 5 from 8 to 6
                move 4 from 9 to 7
                move 2 from 5 to 6
                move 5 from 4 to 2
                move 4 from 7 to 2
                move 20 from 6 to 9
                move 7 from 2 to 7
                move 1 from 2 to 6
                move 7 from 9 to 6
                move 3 from 7 to 9
                move 7 from 1 to 9
                move 3 from 7 to 4
                move 1 from 2 to 5
                move 1 from 5 to 2
                move 1 from 1 to 9
                move 23 from 9 to 1
                move 1 from 2 to 4
                move 1 from 9 to 6
                move 1 from 1 to 5
                move 20 from 1 to 7
                move 1 from 5 to 9
                move 12 from 4 to 2
                move 2 from 1 to 3
                move 1 from 3 to 5
                move 4 from 2 to 9
                move 2 from 6 to 4
                move 9 from 7 to 4
                move 11 from 6 to 7
                move 7 from 2 to 8
                move 1 from 5 to 7
                move 2 from 9 to 7
                move 1 from 6 to 4
                move 6 from 8 to 2
                move 3 from 7 to 1
                move 6 from 2 to 4
                move 1 from 3 to 2
                move 7 from 4 to 3
                move 1 from 8 to 5
                move 3 from 6 to 7
                move 1 from 2 to 9
                move 1 from 6 to 7
                move 4 from 4 to 1
                move 1 from 3 to 1
                move 22 from 7 to 6
                move 3 from 7 to 6
                move 4 from 7 to 6
                move 5 from 4 to 5
                move 26 from 6 to 2
                move 8 from 1 to 9
                move 2 from 6 to 5
                move 9 from 9 to 5
                move 2 from 9 to 4
                move 1 from 5 to 3
                move 1 from 9 to 5
                move 1 from 5 to 6
                move 1 from 4 to 3
                move 3 from 5 to 8
                move 1 from 6 to 2
                move 1 from 6 to 1
                move 1 from 1 to 8
                move 4 from 5 to 2
                move 7 from 2 to 4
                move 8 from 5 to 3
                move 1 from 5 to 7
                move 12 from 2 to 8
                move 6 from 3 to 8
                move 1 from 7 to 6
                move 10 from 3 to 4
                move 11 from 8 to 7
                move 6 from 8 to 3
                move 11 from 7 to 4
                move 1 from 6 to 3
                move 6 from 3 to 1
                move 6 from 1 to 5
                move 15 from 4 to 7
                move 1 from 3 to 5
                move 7 from 2 to 3
                move 5 from 5 to 9
                move 2 from 3 to 8
                move 1 from 9 to 4
                move 1 from 9 to 7
                move 1 from 4 to 5
                move 5 from 7 to 8
                move 13 from 4 to 1
                move 8 from 8 to 2
                move 2 from 2 to 7
                move 7 from 7 to 4
                move 1 from 5 to 1
                move 1 from 5 to 9
                move 3 from 8 to 9
                move 7 from 9 to 8
                move 1 from 5 to 2
                move 6 from 8 to 2
                move 6 from 7 to 6
                move 2 from 2 to 7
                move 2 from 8 to 3
                move 3 from 4 to 5
                move 1 from 7 to 1
                move 3 from 3 to 5
                move 4 from 4 to 6
                move 3 from 6 to 3
                move 11 from 2 to 9
                move 5 from 3 to 4
                move 1 from 1 to 4
                move 7 from 9 to 4
                move 1 from 6 to 4
                move 5 from 5 to 4
                move 9 from 1 to 3
                move 4 from 6 to 3
                move 2 from 1 to 7
                move 3 from 9 to 8
                move 1 from 9 to 5
                move 5 from 3 to 4
                move 3 from 4 to 6
                move 3 from 7 to 5
                move 4 from 2 to 4
                move 10 from 3 to 1
                move 2 from 8 to 9
                move 1 from 8 to 4
                move 2 from 2 to 9
                move 5 from 5 to 8
                move 2 from 6 to 3
                move 4 from 9 to 4
                move 2 from 3 to 7
                move 2 from 6 to 3
                move 1 from 6 to 1
                move 1 from 8 to 5
                move 1 from 5 to 8
                move 1 from 4 to 8
                move 17 from 4 to 2
                move 11 from 4 to 2
                move 1 from 8 to 6
                move 28 from 2 to 3
                move 10 from 3 to 1
                move 3 from 8 to 1
                move 1 from 7 to 8
                move 1 from 7 to 1
                move 1 from 6 to 5
                move 10 from 1 to 5
                move 20 from 3 to 5
                move 3 from 1 to 6
                move 3 from 8 to 1
                move 18 from 5 to 1
                move 4 from 4 to 6
                move 4 from 5 to 1
                move 1 from 6 to 8
                move 7 from 5 to 8
                move 2 from 5 to 3
                move 34 from 1 to 8
                move 4 from 1 to 7
                move 36 from 8 to 6
                move 6 from 8 to 4
                move 3 from 6 to 4
                move 1 from 1 to 2
                move 1 from 3 to 2
                move 1 from 3 to 5
                move 1 from 1 to 8
                move 1 from 7 to 2
                move 3 from 2 to 8
                move 3 from 8 to 1
                move 2 from 7 to 5
                move 5 from 6 to 4
                move 31 from 6 to 4
                move 1 from 7 to 3
                move 13 from 4 to 7
                move 2 from 5 to 9
                move 1 from 1 to 9
                move 1 from 3 to 1
                move 11 from 4 to 9
                move 12 from 4 to 3
                move 4 from 9 to 1
                move 1 from 9 to 8
                move 1 from 5 to 9
                move 3 from 6 to 5
                move 3 from 5 to 1
                move 11 from 7 to 8
                move 6 from 4 to 8
                move 3 from 3 to 8
                move 5 from 1 to 6
                move 1 from 7 to 3
                move 5 from 8 to 3
                move 2 from 4 to 7
                move 8 from 8 to 4
                move 5 from 8 to 2
                move 2 from 2 to 1
                move 7 from 9 to 2
                move 5 from 6 to 7
                move 6 from 2 to 4
                move 3 from 9 to 1
                move 3 from 1 to 4
                move 2 from 2 to 1
                move 5 from 1 to 2
                move 6 from 2 to 9
                move 4 from 7 to 6
                move 2 from 9 to 6
                move 1 from 2 to 5
                move 1 from 6 to 5
                move 5 from 3 to 1
                move 1 from 5 to 3
                move 2 from 6 to 1
                move 1 from 9 to 7
                move 3 from 7 to 3
                move 4 from 8 to 4
                move 1 from 5 to 6
                move 9 from 1 to 4
                move 4 from 6 to 8
                move 2 from 7 to 4
                move 2 from 1 to 9
                move 10 from 3 to 1
                move 7 from 1 to 3
                move 1 from 1 to 2
                move 1 from 2 to 4
                move 2 from 3 to 8
                move 6 from 8 to 9
                move 2 from 1 to 2
                move 30 from 4 to 3
                move 29 from 3 to 7
                move 2 from 2 to 4
                move 7 from 9 to 5
                move 6 from 4 to 8
                move 5 from 8 to 9
                move 5 from 5 to 7
                move 1 from 5 to 4
                move 17 from 7 to 9
                move 6 from 3 to 9
                move 4 from 3 to 7
                move 1 from 8 to 6
                move 17 from 9 to 8
                move 8 from 9 to 3
                move 1 from 5 to 6
                move 9 from 8 to 7
                move 3 from 9 to 5
                move 1 from 4 to 5
                move 2 from 6 to 1
                move 3 from 3 to 8
                move 2 from 3 to 5
                move 1 from 3 to 8
                move 10 from 8 to 4
                move 2 from 1 to 9
                move 1 from 8 to 1
                move 1 from 1 to 5
                move 1 from 8 to 6
                move 4 from 4 to 5
                move 1 from 3 to 9
                move 3 from 9 to 6
                move 1 from 9 to 8
                move 2 from 9 to 1
                move 2 from 1 to 7
                move 1 from 9 to 1
                move 3 from 4 to 6
                move 2 from 4 to 9
                move 1 from 1 to 8
                move 2 from 8 to 1
                move 5 from 6 to 2
                move 2 from 1 to 4
                move 2 from 9 to 1
                move 2 from 6 to 3
                move 2 from 3 to 1
                move 2 from 4 to 7
                move 4 from 1 to 5
                move 15 from 5 to 4
                move 4 from 2 to 5
                move 7 from 4 to 2
                move 4 from 4 to 5
                move 1 from 3 to 9
                move 3 from 5 to 2
                move 9 from 2 to 1
                move 3 from 5 to 4
                move 1 from 5 to 3
                move 1 from 9 to 7
                move 1 from 5 to 8
                move 4 from 1 to 6
                move 1 from 3 to 2
                move 2 from 1 to 2
                move 3 from 2 to 8
                move 14 from 7 to 2
                move 2 from 6 to 4
                move 19 from 7 to 8
                move 1 from 7 to 1
                move 23 from 8 to 2
                move 33 from 2 to 1
                move 1 from 7 to 1
                move 7 from 4 to 3
                move 1 from 6 to 2
                move 15 from 1 to 7
                move 6 from 2 to 8
                move 1 from 8 to 2
                move 1 from 2 to 8
                move 2 from 3 to 8
                move 3 from 8 to 5
                move 1 from 6 to 1
                move 2 from 4 to 7
                move 1 from 5 to 9
                move 3 from 8 to 3
                move 1 from 2 to 6
                move 18 from 1 to 4
                move 1 from 6 to 3
                move 2 from 5 to 1
                move 2 from 8 to 2
                move 5 from 1 to 9
                move 15 from 4 to 9
                move 5 from 9 to 5
                move 1 from 1 to 5
                move 1 from 1 to 3
                move 1 from 1 to 2
                move 3 from 2 to 8
                move 9 from 9 to 8
                move 11 from 8 to 4
                move 1 from 8 to 3
                move 4 from 7 to 8
                move 3 from 3 to 1
                move 3 from 3 to 7
                move 3 from 5 to 8
                move 3 from 5 to 3
                move 5 from 9 to 7
                move 9 from 4 to 3
                move 1 from 8 to 9
                move 9 from 3 to 7
                move 2 from 3 to 2
                move 1 from 4 to 1
                move 1 from 8 to 6
                move 10 from 7 to 1
                move 2 from 2 to 6
                move 2 from 6 to 8
                move 2 from 9 to 4
                move 14 from 1 to 9
                move 3 from 4 to 7
                move 1 from 6 to 3
                move 2 from 8 to 4
                move 8 from 7 to 5
                move 6 from 7 to 5
                move 12 from 9 to 3
                move 3 from 9 to 8
                move 8 from 8 to 2
                move 7 from 2 to 1
                move 1 from 7 to 2
                move 6 from 7 to 2
                move 7 from 3 to 6
                move 1 from 6 to 3
                move 7 from 2 to 1
                move 5 from 4 to 8
                move 2 from 7 to 9
                move 1 from 2 to 7
                move 4 from 6 to 1
                move 2 from 8 to 1
                move 1 from 7 to 6
                move 2 from 6 to 1
                move 3 from 3 to 7
                move 1 from 4 to 6
                move 7 from 3 to 8
                move 6 from 8 to 1
                move 1 from 9 to 7
                move 22 from 1 to 9
                move 2 from 7 to 2
                move 3 from 3 to 2
                move 5 from 1 to 3
                move 2 from 2 to 7
                move 2 from 6 to 9
                move 3 from 9 to 4
                move 2 from 4 to 5
                move 1 from 4 to 7
                move 1 from 1 to 9
                move 13 from 9 to 7
                move 3 from 9 to 5
                move 14 from 5 to 3
                move 5 from 9 to 5
                move 2 from 9 to 7
                move 9 from 5 to 3
                move 15 from 3 to 2
                move 12 from 7 to 3
                move 3 from 2 to 7
                move 8 from 7 to 5
                move 4 from 8 to 9
                move 1 from 9 to 6
                move 1 from 7 to 5
                move 14 from 2 to 7
                move 2 from 9 to 4
                move 1 from 6 to 5
                move 18 from 3 to 2
                move 5 from 3 to 9
                move 2 from 3 to 6
                move 2 from 4 to 8
                move 15 from 7 to 6
                move 1 from 9 to 1
                move 2 from 8 to 3
                move 1 from 7 to 9
                move 6 from 9 to 6
                move 2 from 3 to 7
                move 3 from 5 to 8
                move 8 from 5 to 3
                move 2 from 7 to 9
                move 22 from 6 to 9
                move 12 from 2 to 3
                move 1 from 1 to 9
                move 1 from 2 to 6
                move 1 from 6 to 5
                move 6 from 2 to 6
                move 7 from 6 to 3
                move 20 from 9 to 4
                move 5 from 9 to 3
                move 7 from 3 to 5
                move 14 from 4 to 6
                move 2 from 4 to 1
                move 2 from 8 to 3
                move 2 from 1 to 5
                move 9 from 6 to 1
                move 20 from 3 to 4
                move 5 from 6 to 8
                move 1 from 5 to 9
                move 1 from 9 to 6
                move 9 from 5 to 7
                move 1 from 6 to 5
                move 2 from 3 to 4
                move 4 from 8 to 2
                move 2 from 8 to 4
                move 3 from 3 to 7
                move 5 from 1 to 7
                move 4 from 2 to 7
                move 1 from 1 to 3
                move 3 from 3 to 6
                move 4 from 7 to 3
                move 1 from 1 to 4
                move 3 from 3 to 5
                move 1 from 1 to 7
                move 28 from 4 to 3
                move 20 from 3 to 5
                move 16 from 5 to 6
                move 3 from 3 to 2
                move 2 from 3 to 6
                move 6 from 7 to 5
                move 1 from 3 to 6
                move 1 from 2 to 1
                move 10 from 6 to 8
                move 2 from 1 to 5
                move 1 from 4 to 8
                move 1 from 6 to 9
                move 2 from 2 to 5
                move 10 from 7 to 4
                move 2 from 3 to 4
                move 1 from 3 to 8
                move 1 from 9 to 4
                move 6 from 4 to 1
                move 10 from 8 to 6
                move 1 from 1 to 4
                move 8 from 4 to 9
                move 3 from 1 to 5
                move 14 from 5 to 8
                move 2 from 7 to 5
                move 3 from 9 to 7
                move 5 from 9 to 5
                move 2 from 7 to 3
                move 16 from 6 to 9
                move 3 from 6 to 3
                move 1 from 1 to 5
                move 1 from 1 to 4
                move 1 from 7 to 3
                move 2 from 6 to 1
                move 2 from 5 to 7
                move 2 from 7 to 1
                move 3 from 3 to 8
                move 12 from 5 to 4
                move 1 from 5 to 8
                move 1 from 1 to 4
                move 9 from 4 to 1
                move 11 from 1 to 7
                move 10 from 7 to 4
                move 3 from 3 to 7
                move 1 from 1 to 7
                move 5 from 4 to 5
                move 8 from 4 to 1
                move 1 from 4 to 1
                move 5 from 5 to 4
                move 2 from 7 to 5
                move 2 from 7 to 3
                move 9 from 1 to 7
                move 16 from 8 to 5
                move 3 from 8 to 7
                move 6 from 4 to 3
                move 17 from 5 to 1
                move 14 from 1 to 2
                move 7 from 2 to 4
                move 5 from 2 to 6
                """.stripIndent();
    }
}

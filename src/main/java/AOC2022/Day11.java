package AOC2022;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {
    public static void main(final String[] args) {
        System.out.printf("Solution for the first riddle: %s%n", one(input()));
        System.out.printf("Solution for the second riddle: %s%n", two(testInput()));
    }

    private static Object one(final String[] input) {
        final List<Monkey> monkeys = Stream.of(input).map(Monkey::new).toList();
        final List<Integer> carried = new ArrayList<>();

        for (int round = 0; round < 20; round++) {
            for (final Monkey monkey : monkeys) {
                for (final int item : monkey.items) {
                    final int worryLevel = monkey.operation.apply(item) / 3;
                    final boolean test = monkey.test.test(worryLevel);
                    if (test) monkeys.get(monkey.whenTrue).items.add(worryLevel);
                    else monkeys.get(monkey.whenFalse).items.add(worryLevel);
                }
                monkey.carried += monkey.items.size();
                monkey.items.clear();
            }
        }

        return monkeys.stream()
                      .map(Monkey::getCarried)
                      .sorted(Comparator.reverseOrder())
                      .limit(2)
                      .reduce((a, b) -> a * b)
                      .get();
    }

    private static Object two(final String[] input) {
        return null;
    }

    static class Monkey {
        private final List<Integer> items;
        private final Function<Integer, Integer> operation;
        private final Predicate<Integer> test;
        private final int whenTrue;
        private final int whenFalse;
        public int carried = 0;

        public Monkey(final List<Integer> items, final Function<Integer, Integer> operation, final Predicate<Integer> test, final int whenTrue, final int whenFalse) {
            this.items = items;
            this.operation = operation;
            this.test = test;
            this.whenTrue = whenTrue;
            this.whenFalse = whenFalse;
        }

        public Monkey(final String input) {
            final var monkeyLines = input.split("\n");
            this.items = Arrays.stream(monkeyLines[1].substring(18).split(", "))
                               .map(Integer::parseInt)
                               .collect(Collectors.toCollection(ArrayList::new));
            this.operation = getOperation(monkeyLines[2]);
            this.test = i -> i % Integer.parseInt(monkeyLines[3].substring(21)) == 0;
            this.whenTrue = Integer.parseInt(monkeyLines[4].substring("    If true: throw to monkey ".length()));
            this.whenFalse = Integer.parseInt(monkeyLines[5].substring("    If false: throw to monkey ".length()));
        }

        private static Function<Integer, Integer> getOperation(final String monkeyLine) {
            final Function<Integer, Integer> operand = i -> monkeyLine.substring(25)
                                                                      .equals("old") ? i : Integer.parseInt(monkeyLine.substring(
                    25));
            return (monkeyLine.charAt(23) == '*') ? i -> i * operand.apply(i) : i -> i + operand.apply(i);
        }

        public int getCarried() {
            return carried;
        }
    }

    private static String[] testInput() {
        return """
                Monkey 0:
                  Starting items: 79, 98
                  Operation: new = old * 19
                  Test: divisible by 23
                    If true: throw to monkey 2
                    If false: throw to monkey 3
                                
                Monkey 1:
                  Starting items: 54, 65, 75, 74
                  Operation: new = old + 6
                  Test: divisible by 19
                    If true: throw to monkey 2
                    If false: throw to monkey 0
                                
                Monkey 2:
                  Starting items: 79, 60, 97
                  Operation: new = old * old
                  Test: divisible by 13
                    If true: throw to monkey 1
                    If false: throw to monkey 3
                                
                Monkey 3:
                  Starting items: 74
                  Operation: new = old + 3
                  Test: divisible by 17
                    If true: throw to monkey 0
                    If false: throw to monkey 1
                """.stripIndent().split("\n\n");
    }

    private static String[] input() {
        return """
                Monkey 0:
                  Starting items: 71, 56, 50, 73
                  Operation: new = old * 11
                  Test: divisible by 13
                    If true: throw to monkey 1
                    If false: throw to monkey 7
                                
                Monkey 1:
                  Starting items: 70, 89, 82
                  Operation: new = old + 1
                  Test: divisible by 7
                    If true: throw to monkey 3
                    If false: throw to monkey 6
                                
                Monkey 2:
                  Starting items: 52, 95
                  Operation: new = old * old
                  Test: divisible by 3
                    If true: throw to monkey 5
                    If false: throw to monkey 4
                                
                Monkey 3:
                  Starting items: 94, 64, 69, 87, 70
                  Operation: new = old + 2
                  Test: divisible by 19
                    If true: throw to monkey 2
                    If false: throw to monkey 6
                                
                Monkey 4:
                  Starting items: 98, 72, 98, 53, 97, 51
                  Operation: new = old + 6
                  Test: divisible by 5
                    If true: throw to monkey 0
                    If false: throw to monkey 5
                                
                Monkey 5:
                  Starting items: 79
                  Operation: new = old + 7
                  Test: divisible by 2
                    If true: throw to monkey 7
                    If false: throw to monkey 0
                                
                Monkey 6:
                  Starting items: 77, 55, 63, 93, 66, 90, 88, 71
                  Operation: new = old * 7
                  Test: divisible by 11
                    If true: throw to monkey 2
                    If false: throw to monkey 4
                                
                Monkey 7:
                  Starting items: 54, 97, 87, 70, 59, 82, 59
                  Operation: new = old + 8
                  Test: divisible by 17
                    If true: throw to monkey 1
                    If false: throw to monkey 3
                """.stripIndent().split("\n\n");
    }
}

package de.ozyn.aoc2023;

import de.ozyn.aoc2023.util.LineDay;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day01 implements LineDay {
    public static void main(String[] args) throws Exception {
        Day01 day = new Day01();
        System.out.printf("Solution of part 01: %s%n", day.a(day.getInput()));
        System.out.printf("Solution of part 02: %s%n", day.b(day.getInput()));
    }

    @Override
    public Object a(Stream<String> input) {
        return input.map(s -> s.chars()
                               .filter(Character::isDigit)
                               .mapToObj(Character::toString)
                               .collect(Collectors.joining()))
                    .map(s -> "%s%s".formatted(s.charAt(0), s.charAt(s.length() - 1)))
                    .mapToInt(Integer::parseInt)
                    .sum();
    }

    @Override
    public Object b(Stream<String> input) {
        Map.of(
                "one", 1,
                "two", 2,
                "three", 3
        )
        return null;
    }
}

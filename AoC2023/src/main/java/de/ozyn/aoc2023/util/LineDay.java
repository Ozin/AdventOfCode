package de.ozyn.aoc2023.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface LineDay extends AbstractDay<Stream<String>> {
    @Override
    default Stream<String> parseInput(Stream<String> input) {
        return input.toList().stream();
    }
}

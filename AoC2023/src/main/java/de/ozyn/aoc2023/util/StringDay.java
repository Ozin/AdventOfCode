package de.ozyn.aoc2023.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface StringDay extends AbstractDay<String> {
    @Override
    default String parseInput(Stream<String> input) {
        return input.collect(Collectors.joining("\n"));
    }
}

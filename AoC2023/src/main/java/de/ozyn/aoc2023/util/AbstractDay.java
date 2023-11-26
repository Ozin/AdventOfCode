package de.ozyn.aoc2023.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public interface AbstractDay<T> {
    Object a(T input) throws Exception;

    Object b(T input) throws Exception;

    default T getInput() throws IOException {
        final String lowerClassName = this.getClass().getSimpleName().toLowerCase();
        var is = getClass().getResourceAsStream("/%s.txt".formatted(lowerClassName));

        try (var br = new BufferedReader(new InputStreamReader(is))) {
            return parseInput(br.lines());
        }
    }

    T parseInput(Stream<String> input);
}


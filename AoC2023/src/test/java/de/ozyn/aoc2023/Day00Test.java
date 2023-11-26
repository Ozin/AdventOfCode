package de.ozyn.aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day00Test {
    Day00 day = new Day00();

    @Test
    void a01() {
        var expected = "";
        var input = """
                """.stripIndent().lines();

        var result = day.a(day.parseInput(input));

        assertEquals(expected, result);
    }

    @Test
    void b01() {
        var expected = 0;
        var input = """
                """.stripIndent().lines();

        var result = day.b(day.parseInput(input));
    }
}
package AOC2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day20Test implements AocTest  {
    Day20 day = new Day20();
    @Test
    void test1() {
        // GIVEN
        String expected = """
                .......#.
                .#..#.#..
                #.#...###
                #...##.#.
                #.....#.#
                .#.#####.
                ..#.#####
                ...##.##.
                ....###..
                """.stripIndent();

        String rawInput = """
                ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
                                
                #..#.
                #....
                ##..#
                ..#..
                ..###""".stripIndent();

        // WHEN
        Day20.Input input = Day20.Input.parse(rawInput.split("\n"));
        Day20.Image output = day.enhanceInputImage(input.inputImage(), input.imageEnhancementAlgorithm(), '.');
        output = day.enhanceInputImage(output, input.imageEnhancementAlgorithm(), '.');

        // THEN
        assertEquals(expected, output.printImage());
    }
}
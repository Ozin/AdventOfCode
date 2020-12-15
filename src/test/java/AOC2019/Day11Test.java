package AOC2019;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day11Test {
    Day11 day = new Day11();

    @Test
    public void a() throws Exception {
        assertEquals(2539, day.getA());
    }

    @Test
    public void b() throws Exception {
        final String result =
                "...........................................\n" +
                ".####.#....####.###..#..#...##.###...##....\n" +
                "....#.#....#....#..#.#.#.....#.#..#.#..#...\n" +
                "...#..#....###..###..##......#.#..#.#..#...\n" +
                "..#...#....#....#..#.#.#.....#.###..####...\n" +
                ".#....#....#....#..#.#.#..#..#.#.#..#..#.>.\n" +
                ".####.####.####.###..#..#..##..#..#.#..#...\n" +
                "...........................................\n";
        // ZLEBKJRA
        assertEquals(result, day.getB());
    }
}

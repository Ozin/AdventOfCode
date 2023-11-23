package AOC2020;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day20Test {
    Day20 day = new Day20();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/Day20-01.txt");

        final Object result = day.getA();

        assertEquals(20899048083289L, result);
    }

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/Day20-01.txt");

        final Object result = day.getB();

        assertEquals(273, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(15003787688423L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(341L, result);
    }

    @Test
    void flipY_test() {
        // GIVEN
        List<String> given = List.of(
                "Tile 0:",
                "..#.###...",
                "##.##....#",
                "..#.###..#",
                "###.#..###",
                ".######.##",
                "#.#.#.#...",
                "#.###.###.",
                "#.###.##..",
                ".######...",
                ".##...####"
        );

        Day20.Tile t = new Day20.Tile(given, new HashSet<>());

        // WHEN
        t.flipY();

        // THEN
        assertArrayEquals(new Boolean[][]{
                new Boolean[]{false, true, true, false, false, false, true, true, true, true},
                new Boolean[]{false, true, true, true, true, true, true, false, false, false},
                new Boolean[]{true, false, true, true, true, false, true, true, false, false},
                new Boolean[]{true, false, true, true, true, false, true, true, true, false},
                new Boolean[]{true, false, true, false, true, false, true, false, false, false},
                new Boolean[]{false, true, true, true, true, true, true, false, true, true},
                new Boolean[]{true, true, true, false, true, false, false, true, true, true},
                new Boolean[]{false, false, true, false, true, true, true, false, false, true},
                new Boolean[]{true, true, false, true, true, false, false, false, false, true},
                new Boolean[]{false, false, true, false, true, true, true, false, false, false}
        }, t.bits);
    }

    @Test
    void flipX_test() {
        // GIVEN
        List<String> given = List.of(
                "Tile 0:",
                "..#.###...",
                "##.##....#",
                "..#.###..#",
                "###.#..###",
                ".######.##",
                "#.#.#.#...",
                "#.###.###.",
                "#.###.##..",
                ".######...",
                ".##...####"
        );

        Day20.Tile t = new Day20.Tile(given, new HashSet<>());

        // WHEN
        t.flipX();

        // THEN
        assertArrayEquals(new Boolean[][]{
                new Boolean[]{false, false, false, true, true, true, false, true, false, false},
                new Boolean[]{true, false, false, false, false, true, true, false, true, true},
                new Boolean[]{true, false, false, true, true, true, false, true, false, false},
                new Boolean[]{true, true, true, false, false, true, false, true, true, true},
                new Boolean[]{true, true, false, true, true, true, true, true, true, false},
                new Boolean[]{false, false, false, true, false, true, false, true, false, true},
                new Boolean[]{false, true, true, true, false, true, true, true, false, true},
                new Boolean[]{false, false, true, true, false, true, true, true, false, true},
                new Boolean[]{false, false, false, true, true, true, true, true, true, false},
                new Boolean[]{true, true, true, true, false, false, false, true, true, false}
        }, t.bits);
    }
}
package AOC2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class Day12Test {
    Day12 day = new Day12();

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/day12-01.txt");

        final Object result = day.getB();

        assertEquals(286, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(51249, result);
    }
}

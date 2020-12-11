package AOC2020;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day11Test {
    Day11 day = new Day11();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/day11-01.txt");

        final Object result = day.getA();

        assertEquals(37L, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(2316L, result);
    }

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/day11-01.txt");

        final Object result = day.getB();

        assertEquals(26L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(2128L, result);
    }
}

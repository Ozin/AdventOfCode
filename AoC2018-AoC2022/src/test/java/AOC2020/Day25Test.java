package AOC2020;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day25Test {
    Day25 day = new Day25();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/day25-01.txt");

        final Object result = day.getA();

        assertEquals(14897079L, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(297257L, result);
    }

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/day25-01.txt");

        final Object result = day.getB();

        assertEquals(null, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(null, result);
    }
}

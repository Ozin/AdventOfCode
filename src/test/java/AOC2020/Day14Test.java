package AOC2020;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day14Test {
    Day14 day = new Day14();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/day14-01.txt");

        final Object result = day.getA();

        assertEquals(165L, result);
    }

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/day14-02.txt");

        final Object result = day.getB();

        assertEquals(208L, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(2346881602152L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(3885232834169L, result);
    }
}

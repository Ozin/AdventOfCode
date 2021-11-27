package AOC2020;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day24Test {
    Day24 day = new Day24();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/day24-01.txt");

        final Object result = day.getA();

        assertEquals(10, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(346, result);
    }

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/day24-01.txt");

        final Object result = day.getB();

        assertEquals(2208, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(3802, result);
    }
}

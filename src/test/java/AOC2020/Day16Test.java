package AOC2020;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day16Test {
    final Day16 day = new Day16();

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/day16-01.txt");

        final Object result = day.getB();

        assertEquals(26026, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(26026, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(1305243193339L, result);
    }
}

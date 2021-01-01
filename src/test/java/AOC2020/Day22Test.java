package AOC2020;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day22Test {
    Day22 day = new Day22();

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/day22-01.txt");

        assertEquals(291L, day.getB());
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(32448L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(32949L, result);
    }
}
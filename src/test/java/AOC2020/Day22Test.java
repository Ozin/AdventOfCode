package AOC2020;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day22Test {
    Day22 day = new Day22();

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(32448, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(null, result);
    }
}
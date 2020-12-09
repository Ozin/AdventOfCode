package AOC2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class Day09Test {
    Day09 day = new Day09();

    @Test
    public void a1() {
        final long[] input = new long[] {35, 20, 15, 25, 47, 40, 62, 55, 65, 95, 102, 117, 150, 182, 127, 219, 299, 277, 309, 576};

        final long result = day.findFirstWeakNumber(input, 5);

        assertEquals(127L, result);
    }

    @Test
    public void b1() {
        final long[] input = new long[] {35, 20, 15, 25, 47, 40, 62, 55, 65, 95, 102, 117, 150, 182, 127, 219, 299, 277, 309, 576};

        final long result = day.findWeakness(input, 5);

        assertEquals(62L, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(70639851L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(8249240L, result);
    }
}

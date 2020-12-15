package AOC2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class Day10Test {
    Day10 day = new Day10();

    @Test
    public void a1() throws Exception {
        final int[] input = new int[] {16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4};

        final Object result = day.a(input);

        assertEquals(35L, result);
    }

    @Test
    public void a2() throws Exception {
        final int[] input = new int[] {28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3};

        final Object result = day.a(input);

        assertEquals(220L, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(2380L, result);
    }

    @Test
    public void b1() throws Exception {
        final int[] input = new int[] {16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4};

        final Object result = day.b(input);

        assertEquals(8L, result);
    }

    @Test
    public void b2() throws Exception {
        final int[] input = new int[] {28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3};

        final Object result = day.b(input);

        assertEquals(19208L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(48358655787008L, result);
    }
}

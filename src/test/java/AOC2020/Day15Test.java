package AOC2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class Day15Test {
    Day15 day = new Day15();

    @Test
    public void a1() throws Exception {
        final int[] input = new int[] {0, 3, 6};

        final Object result = day.a(input);

        assertEquals(436, result);
    }

    @Test
    public void a2() throws Exception {
        final int[] input = new int[] {1, 3, 2};

        final Object result = day.a(input);

        assertEquals(1, result);
    }

    @Test
    public void a3() throws Exception {
        final int[] input = new int[] {2, 1, 3};

        final Object result = day.a(input);

        assertEquals(10, result);
    }

    @Test
    public void a4() throws Exception {
        final int[] input = new int[] {1, 2, 3};

        final Object result = day.a(input);

        assertEquals(27, result);
    }

    @Test
    public void a5() throws Exception {
        final int[] input = new int[] {2, 3, 1};

        final Object result = day.a(input);

        assertEquals(78, result);
    }

    @Test
    public void a6() throws Exception {
        final int[] input = new int[] {3, 2, 1};

        final Object result = day.a(input);

        assertEquals(438, result);
    }

    @Test
    public void a7() throws Exception {
        final int[] input = new int[] {3, 1, 2};

        final Object result = day.a(input);

        assertEquals(1836, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(866, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(1437692, result);
    }
}

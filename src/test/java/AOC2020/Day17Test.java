package AOC2020;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day17Test {
    final Day17 day = new Day17();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/day17-01.txt");

        final Object result = day.getA();

        assertEquals(112, result);
    }

    @Test
    public void a2() throws Exception {
        day.setInputFilePath("/2020/day17-02.txt");

        final Object result = day.getA();

        assertEquals(0, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(388, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(2280, result);
    }
}

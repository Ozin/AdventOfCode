package AOC2020;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day19Test {
    Day19 day = new Day19();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/day19-01.txt");

        final Object result = day.getA();

        assertEquals(2L, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(230L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(169899524778212L, result);
    }
}
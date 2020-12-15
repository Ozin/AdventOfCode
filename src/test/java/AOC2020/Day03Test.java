package AOC2020;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day03Test {
    Day03 day03 = new Day03();

    @Test
    public void a1() throws Exception {
        day03.setInputFilePath("/2020/day03-01.txt");

        assertEquals(7, day03.getA());
    }

    @Test
    public void b1() throws Exception {
        day03.setInputFilePath("/2020/day03-01.txt");

        assertEquals(336L, day03.getB());
    }
}

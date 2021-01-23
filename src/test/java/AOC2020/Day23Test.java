package AOC2020;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day23Test {
    Day23 day = new Day23();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/day23-01.txt");

        assertEquals("67384529", day.getA());
    }

    @Test
    public void aFinal() throws Exception {
        assertEquals("69425837", day.getA());
    }

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/day23-01.txt");

        assertEquals(149245887792L, day.getB());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(218882971435L, day.getB());
    }
}
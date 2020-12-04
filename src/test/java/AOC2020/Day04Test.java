package AOC2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class Day04Test {
    Day04 day04 = new Day04();

    @Test
    public void a1() throws Exception {
        day04.setInputFilePath("/2020/day04-01.txt");

        assertEquals(10, day04.getA());
    }

    @Test
    public void b1() throws Exception {
        day04.setInputFilePath("/2020/day04-02.txt");

        assertEquals(4L, day04.getB());
    }
}

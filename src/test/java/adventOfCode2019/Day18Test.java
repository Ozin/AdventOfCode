package adventOfCode2019;

import static org.junit.Assert.*;


import org.junit.Test;

public class Day18Test {
    Day18 day18 = new Day18();

    @Test
    public void a1() throws Exception {
        day18.setInputFilePath("/2019/day18-01.txt");

        assertEquals(8, day18.getA());
    }

    @Test
    public void a2() throws Exception {
        day18.setInputFilePath("/2019/day18-02.txt");

        assertEquals(86, day18.getA());
    }

    @Test
    public void a3() throws Exception {
        day18.setInputFilePath("/2019/day18-03.txt");

        assertEquals(132, day18.getA());
    }
}
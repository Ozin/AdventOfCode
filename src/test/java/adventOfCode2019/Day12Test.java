package adventOfCode2019;

import org.junit.Test;

import static org.junit.Assert.*;

public class Day12Test {
    Day12 day12 = new Day12();

    @Test
    public void a1() throws Exception {
        day12.setInputFilePath("/2019/day12-01.txt");

        assertEquals(183, day12.getA());
    }

    @Test
    public void a2() throws Exception {
        day12.setInputFilePath("/2019/day12-02.txt");

        assertEquals(14645, day12.getA());
    }

    @Test
    public void aFinal() throws Exception {
        assertEquals(5517, day12.getA());
    }

    @Test
    public void b1() throws Exception {
        day12.setInputFilePath("/2019/day12-01.txt");

        assertEquals(2772L, day12.getB());
    }

    @Test
    public void b2() throws Exception {
        day12.setInputFilePath("/2019/day12-02.txt");

        assertEquals(4686774924L * 2, day12.getB());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(303070460651184L, day12.getB());
    }
}
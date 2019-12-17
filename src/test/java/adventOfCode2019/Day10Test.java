package adventOfCode2019;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Day10Test {
    Day10 day = new Day10();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2019/day10-01.txt");

        assertEquals(Map.entry(new Point(3, 4), 8), day.getA());
    }

    @Test
    public void a2() throws Exception {
        day.setInputFilePath("/2019/day10-02.txt");

        assertEquals(Map.entry(new Point(5, 8), 33), day.getA());
    }

    @Test
    public void a3() throws Exception {
        day.setInputFilePath("/2019/day10-03.txt");

        assertEquals(Map.entry(new Point(1, 2), 35), day.getA());
    }

    @Test
    public void a4() throws Exception {
        day.setInputFilePath("/2019/day10-04.txt");

        assertEquals(Map.entry(new Point(6, 3), 41), day.getA());
    }

    @Test
    public void a5() throws Exception {
        day.setInputFilePath("/2019/day10-05.txt");

        assertEquals(Map.entry(new Point(11, 13), 210), day.getA());
    }

    @Test
    public void aFinal() throws Exception {
        assertEquals(Map.entry(new Point(22, 25), 286), day.getA());
    }

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2019/day10-05.txt");

        assertEquals(new Point(8,2), day.getB());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(new Point(5,4), day.getB());
    }
}
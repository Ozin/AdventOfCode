package adventOfCode2019;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day14Test {
    Day14 day14 = new Day14();

    @Test
    public void a1() throws Exception {
        day14.setInputFilePath("/2019/day14-01.txt");

        assertEquals(31, day14.getA());
    }

    @Test
    public void a2() throws Exception {
        day14.setInputFilePath("/2019/day14-02.txt");

        assertEquals(165, day14.getA());
    }

    @Test
    public void a3() throws Exception {
        day14.setInputFilePath("/2019/day14-03.txt");

        assertEquals(13312, day14.getA());
    }

    @Test
    public void a4() throws Exception {
        day14.setInputFilePath("/2019/day14-04.txt");

        assertEquals(180697, day14.getA());
    }

    @Test
    public void a5() throws Exception {
        day14.setInputFilePath("/2019/day14-05.txt");

        assertEquals(2210736, day14.getA());
    }

    @Test
    public void aFinal() throws Exception {
        assertEquals(362713, day14.getA());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(14956L, day14.getB());
    }
}
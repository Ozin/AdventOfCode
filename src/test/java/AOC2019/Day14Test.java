package AOC2019;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day14Test {
    Day14 day14 = new Day14();

    @Test
    public void a1() throws Exception {
        day14.setInputFilePath("/2019/day14-01.txt");

        assertEquals(31L, day14.getA());
    }

    @Test
    public void a2() throws Exception {
        day14.setInputFilePath("/2019/day14-02.txt");

        assertEquals(165L, day14.getA());
    }

    @Test
    public void a3() throws Exception {
        day14.setInputFilePath("/2019/day14-03.txt");

        assertEquals(13312L, day14.getA());
    }

    @Test
    public void a4() throws Exception {
        day14.setInputFilePath("/2019/day14-04.txt");

        assertEquals(180697L, day14.getA());
    }

    @Test
    public void a5() throws Exception {
        day14.setInputFilePath("/2019/day14-05.txt");

        assertEquals(2210736L, day14.getA());
    }

    @Test
    public void aFinal() throws Exception {
        assertEquals(362713L, day14.getA());
    }

    // The 13312 ORE-per-FUEL example could produce 82892753 FUEL.
    @Test
    public void b1() throws Exception {
        day14.setInputFilePath("/2019/day14-03.txt");

        assertEquals(82892753L, day14.getB());
    }

    // The 180697 ORE-per-FUEL example could produce 5586022 FUEL.
    @Test
    public void b2() throws Exception {
        day14.setInputFilePath("/2019/day14-04.txt");

        assertEquals(5586022L, day14.getB());
    }

    // The 2210736 ORE-per-FUEL example could produce 460664 FUEL.
    @Test
    public void b3() throws Exception {
        day14.setInputFilePath("/2019/day14-05.txt");

        assertEquals(460664L, day14.getB());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(3281820L, day14.getB());
    }
}

package AOC2019;

import static org.junit.Assert.*;


import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

        assertEquals(136, day18.getA());
    }

    @Test
    public void a4() throws Exception {
        day18.setInputFilePath("/2019/day18-04.txt");

        assertEquals(132, day18.getA());
    }

    @Test
    public void a5() throws Exception {
        day18.setInputFilePath("/2019/day18-05.txt");

        assertEquals(81, day18.getA());
    }

    @Test
    @Ignore
    public void aFinal() throws Exception {
        assertEquals(5288, day18.getA());
    }

    @Test
    public void b6() throws Exception {
        day18.setInputFilePath("/2019/day18-06.txt");

        assertEquals(8, day18.getB());
    }

    @Test
    public void b7() throws Exception {
        day18.setInputFilePath("/2019/day18-07.txt");

        assertEquals(24, day18.getB());
    }
    @Test
    public void b8() throws Exception {
        day18.setInputFilePath("/2019/day18-08.txt");

        assertEquals(32, day18.getB());
    }
    @Test
    public void b9() throws Exception {
        day18.setInputFilePath("/2019/day18-09.txt");

        assertEquals(72, day18.getB());
    }

    @Test
    @Ignore
    public void bFinal() throws Exception {
        assertEquals(2082, day18.getB());
    }
}

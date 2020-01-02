package adventOfCode2019;

import static org.junit.Assert.assertEquals;


import org.junit.Ignore;
import org.junit.Test;

public class Day16Test {
    Day16 day16 = new Day16();

    @Test
    public void aFinal() throws Exception {
        assertEquals("49254779", day16.getA());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals("55078585", day16.getB());
    }

    @Test
    public void a1() throws Exception {
        day16.setInputFilePath("/2019/day16-01.txt");

        assertEquals("23845678", day16.getA());
    }

    @Test
    public void a2() throws Exception {
        day16.setInputFilePath("/2019/day16-02.txt");

        assertEquals("24176176", day16.getA());
    }

    @Test
    public void a3() throws Exception {
        day16.setInputFilePath("/2019/day16-03.txt");

        assertEquals("73745418", day16.getA());
    }

    @Test
    public void a4() throws Exception {
        day16.setInputFilePath("/2019/day16-04.txt");

        assertEquals("52432133", day16.getA());
    }

    @Test
    public void b1() throws Exception {
        day16.setInputFilePath("/2019/day16-05.txt");

        assertEquals("84462026", day16.getB());
    }

    @Test
    public void b2() throws Exception {
        day16.setInputFilePath("/2019/day16-06.txt");

        assertEquals("78725270", day16.getB());
    }

    @Test
    public void b3() throws Exception {
        day16.setInputFilePath("/2019/day16-07.txt");

        assertEquals("53553731", day16.getB());
    }
}
package adventOfCode2019;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day04Test {
    Day04 day = new Day04();

    @Test
    public void a() throws Exception {
        assertEquals(1079L, day.getA());
    }

    @Test
    public void b() throws Exception {
        assertEquals(699L, day.getB());
    }
}
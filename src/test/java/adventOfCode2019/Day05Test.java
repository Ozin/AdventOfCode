package adventOfCode2019;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day05Test {
    Day05 day = new Day05();

    @Test
    public void a() throws Exception {
        assertEquals(10987514L, day.getA());
    }

    @Test
    public void b() throws Exception {
        assertEquals(14195011L, day.getB());
    }
}
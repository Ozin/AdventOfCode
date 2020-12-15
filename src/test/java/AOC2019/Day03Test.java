package AOC2019;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day03Test {
    Day03 day = new Day03();

    @Test
    public void a() throws Exception {
        assertEquals(489, day.getA());
    }

    @Test
    public void b() throws Exception {
        assertEquals(93654, day.getB());
    }
}

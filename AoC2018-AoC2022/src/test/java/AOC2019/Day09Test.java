package AOC2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test {
    Day09 day = new Day09();

    @Test
    public void a() throws Exception {
        assertEquals(3497884671L, day.getA());
    }

    @Test
    public void b() throws Exception {
        assertEquals(46470L, day.getB());
    }
}

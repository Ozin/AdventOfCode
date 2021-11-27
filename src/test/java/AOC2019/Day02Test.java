package AOC2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test {
    Day02 day = new Day02();

    @Test
    public void a() throws Exception {
        assertEquals(4570637L, day.getA());
    }

    @Test
    public void b() throws Exception {
        assertEquals(5485, day.getB());
    }
}

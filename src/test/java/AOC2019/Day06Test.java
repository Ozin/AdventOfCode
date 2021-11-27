package AOC2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test {
    Day06 day = new Day06();

    @Test
    public void a() throws Exception {
        assertEquals(261306, day.getA());
    }

    @Test
    public void b() throws Exception {
        assertEquals(382, day.getB());
    }
}

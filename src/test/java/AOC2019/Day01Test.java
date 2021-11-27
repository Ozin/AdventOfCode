package AOC2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test {
    Day01 day = new Day01();

    @Test
    public void a() throws Exception {
        assertEquals(3371958, day.getA());
    }

    @Test
    public void b() throws Exception {
        assertEquals(5055050, day.getB());
    }
}

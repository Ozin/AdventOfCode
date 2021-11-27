package AOC2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07Test {
    Day07 day = new Day07();

    @Test
    public void a() throws Exception {
        assertEquals(567045L, day.getA());
    }

    @Test
    public void b() throws Exception {
        assertEquals(39016654L, day.getB());
    }
}

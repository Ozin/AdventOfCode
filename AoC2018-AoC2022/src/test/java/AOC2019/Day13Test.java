package AOC2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day13Test {
    Day13 day13 = new Day13();

    @Test
    public void aFinal() throws Exception {
        assertEquals(298L, day13.getA());
    }
    @Test
    public void bFinal() throws Exception {
        assertEquals(13956L, day13.getB());
    }
}

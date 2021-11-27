package AOC2019;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class Day15Test {
    Day15 day15 = new Day15();

    @Test
    public void aFinal() throws Exception {
        assertEquals(298L, day15.getA());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(13956L, day15.getB());
    }
}

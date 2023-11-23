package AOC2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day17Test {
    Day17 day17 = new Day17();

    @Test
    public void aFinal() throws Exception {
        assertEquals(13580, day17.getA());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(1063081, day17.getB());
    }
}

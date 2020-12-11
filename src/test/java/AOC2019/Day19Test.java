package AOC2019;

import static org.junit.Assert.*;


import org.junit.Test;

public class Day19Test {
    Day19 day19 = new Day19();

    @Test
    public void aFinal() throws Exception {
        assertEquals(199L, day19.getA());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(10180726, day19.getB());
    }

}

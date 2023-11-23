package AOC2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day20Test {
    Day20 day20 = new Day20();

    @Test
    public void a1() throws Exception {
        day20.setInputFilePath("/2019/day20-01.txt");

        assertEquals(23, day20.getA());
    }

    @Test
    public void a2() throws Exception {
        day20.setInputFilePath("/2019/day20-02.txt");

        assertEquals(58, day20.getA());
    }

    @Test
    public void aFinal() throws Exception {
        assertEquals(544, day20.getA());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(10180726, day20.getB());
    }
}

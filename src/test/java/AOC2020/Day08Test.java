package AOC2020;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Test {

    Day08 day08 = new Day08();

    @Test
    public void a1() throws Exception {
        day08.setInputFilePath("/2020/day08-01.txt");

        assertEquals(new Day08.State(1, 5, false), day08.getA());
    }

    @Test
    public void b1() throws Exception {
        day08.setInputFilePath("/2020/day08-01.txt");

        assertEquals(new Day08.State(9, 8, true), day08.getB());
    }

    @Test
    public void aFinal() throws Exception {
        assertEquals(new Day08.State(458, 1939, false), day08.getA());
    }

    @Test
    public void bFinal() throws Exception {
        assertEquals(new Day08.State(636, 2212, true), day08.getB());
    }
}

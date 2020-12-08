package AOC2020;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.junit.Test;

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
}

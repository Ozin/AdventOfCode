package AOC2020;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import adventOfCode2019.Day20;
import org.junit.Test;

public class Day02Test {
    Day02 day02 = new Day02();

    @Test
    public void b1() throws Exception {
        day02.setInputFilePath("/2020/day02-01.txt");

        assertEquals(1L, day02.getB());
    }
}

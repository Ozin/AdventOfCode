package adventOfCode2019;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class IntcodeComputerTest {
    @Test
    public void day09_1() {
        final long[] input = {109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99};
        final long[] output = new IntcodeComputer(input).finishProgram();

        assertArrayEquals(input, output);
    }

    @Test
    public void day09_2() {
        final long[] input = {1102, 34915192, 34915192, 7, 4, 7, 99, 0};
        final long[] output = new IntcodeComputer(input).finishProgram();

        assertEquals(1, output.length);
        assertEquals(16, Long.toString(output[0]).length());
    }

    @Test
    public void day09_3() {
        final long[] input = {104, 1125899906842624L, 99};
        final long[] output = new IntcodeComputer(input).finishProgram();

        assertEquals(1, output.length);
        assertEquals(1125899906842624L, output[0]);
    }

    @Test
    public void day05_1() {
        final long[] input = {1002, 4, 3, 4, 33};

        final IntcodeComputer intcodeComputer = new IntcodeComputer(input);
        final long[] output = intcodeComputer.finishProgram();

        assertEquals(0, 0);
        assertArrayEquals(new long[]{1002, 4, 3, 4, 99}, intcodeComputer.getState());

    }
}
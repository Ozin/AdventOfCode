package AOC2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test implements AocTest {
    @Test
    void testBinaryCalc() {
        // GIVEN
        int number = 0b10110;
        int mask = 0b11111;

        // WHEN
        int invert = number ^ mask;

        // THEN
        assertEquals(0b1001, invert);
    }
}

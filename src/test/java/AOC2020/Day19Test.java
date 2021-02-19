package AOC2020;

import lombok.val;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Day19Test {
    Day19 day = new Day19();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/day19-01.txt");

        final Object result = day.getA();

        assertEquals(2L, result);
    }

    @Test
    public void a2() throws Exception {
        day.setInputFilePath("/2020/day19-02.txt");

        final Object result = day.getA();

        assertEquals(3L, result);
    }

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/day19-02.txt");

        final Object result = day.getB();

        assertEquals(12L, result);
    }

    @Test
    public void b2() throws Exception {
        day.setInputFilePath("/2020/day19-03.txt");

        final Object result = day.getB();

        assertEquals(1L, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(230L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(341L, result);
    }

    @Test
    public void and1() {
        val input = Map.of(
                0, "1 2",
                1, "\"a\"",
                2, "\"b\" | 0"
        );

        Map<Integer, Day19.Rule> rules = Day19.Rule.of(input);
        boolean result = rules.get(0).matches("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab", rules);

        assertTrue(result);
    }
}
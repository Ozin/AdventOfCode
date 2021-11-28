package AOC2020;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day20Test {
    Day20 day = new Day20();

    @Test
    public void a1() throws Exception {
        day.setInputFilePath("/2020/Day20-01.txt");

        final Object result = day.getA();

        assertEquals(20899048083289L, result);
    }

    @Test
    public void b1() throws Exception {
        day.setInputFilePath("/2020/Day20-01.txt");

        final Object result = day.getB();

        assertEquals(273, result);
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(15003787688423L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(341L, result);
    }
}
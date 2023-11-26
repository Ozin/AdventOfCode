package AOC2020;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day21Test {
    Day21 day = new Day21();

    @Test
    public void a1() throws Exception {
        var input = day.parseInput("""
                mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                trh fvjkl sbzzf mxmxvkd (contains dairy)
                sqjhc fvjkl (contains soy)
                sqjhc mxmxvkd sbzzf (contains fish)""".stripIndent().split("\n"));

        final Object result = day.a(input);

        assertEquals(5, result);
    }

    @Test
    public void b1() throws Exception {
        var input = day.parseInput("""
                mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                trh fvjkl sbzzf mxmxvkd (contains dairy)
                sqjhc fvjkl (contains soy)
                sqjhc mxmxvkd sbzzf (contains fish)""".stripIndent().split("\n"));

        final Object result = day.b(input);

        assertEquals("mxmxvkd,sqjhc,fvjkl", result);
    }
}
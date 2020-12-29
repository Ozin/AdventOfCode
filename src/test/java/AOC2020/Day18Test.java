package AOC2020;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day18Test {
    Day18 day = new Day18();

    @Test
    public void ast1() {
        final Day18.Ast ast = Day18.Ast.parse("1 + 2 * 3 + 4 * 5 + 6");

        assertEquals(71, ast.evaluate());
    }

    @Test
    public void ast2() {
        final Day18.Ast ast = Day18.Ast.parse("1 + (2 * 3) + (4 * (5 + 6))");

        assertEquals(51, ast.evaluate());
    }

    @Test
    public void ast3() {
        final Day18.Ast ast = Day18.Ast.parse("2 * 3 + (4 * 5)");

        assertEquals(26, ast.evaluate());
    }

    @Test
    public void ast4() {
        final Day18.Ast ast = Day18.Ast.parse("5 + (8 * 3 + 9 + 3 * 4 * 3)");

        assertEquals(437, ast.evaluate());
    }

    @Test
    public void ast5() {
        final Day18.Ast ast = Day18.Ast.parse("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))");

        assertEquals(12240, ast.evaluate());
    }

    @Test
    public void ast6() {
        final Day18.Ast ast = Day18.Ast.parse("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2");

        assertEquals(13632, ast.evaluate());
    }

    @Test
    public void ast7() {
        final Day18.Ast ast = Day18.Ast.parseWithPrecedence("1 + (2 * 3) + (4 * (5 + 6))");
        assertEquals(51, ast.evaluate());
    }

    @Test
    public void ast8() {
        final Day18.Ast ast = Day18.Ast.parseWithPrecedence("2 * 3 + (4 * 5)");
        assertEquals(46, ast.evaluate());
    }

    @Test
    public void ast9() {
        final Day18.Ast ast = Day18.Ast.parseWithPrecedence("5 + (8 * 3 + 9 + 3 * 4 * 3)");
        assertEquals(1445, ast.evaluate());
    }

    @Test
    public void ast10() {
        final Day18.Ast ast = Day18.Ast.parseWithPrecedence("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))");
        assertEquals(669060, ast.evaluate());
    }

    @Test
    public void ast11() {
        final Day18.Ast ast = Day18.Ast.parseWithPrecedence("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2");
        assertEquals(23340, ast.evaluate());
    }

    @Test
    public void aFinal() throws Exception {
        final Object result = day.getA();

        assertEquals(21022630974613L, result);
    }

    @Test
    public void bFinal() throws Exception {
        final Object result = day.getB();

        assertEquals(169899524778212L, result);
    }
}
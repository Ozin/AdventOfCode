package AOC2021;

import AOC2021.Day18Alternative.SnailfishNumber;
import AOC2021.Day18Alternative.SnailfishNumberNode;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18AlternativeTest implements AocTest {

    @Test
    void snailfishNumber_reduce_01() {
        final SnailfishNumber result = SnailfishNumber.parse("[[[[[9,8],1],2],3],4]").reduce();

        assertEquals("[[[[0,9],2],3],4]", result.toString());
    }

    @Test
    void snailfishNumber_reduce_02() {
        final SnailfishNumber result = SnailfishNumber.parse("[7,[6,[5,[4,[3,2]]]]]").reduce();

        assertEquals("[7,[6,[5,[7,0]]]]", result.toString());
    }

    @Test
    void snailfishNumber_reduce_03() {
        final SnailfishNumber result = SnailfishNumber.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]").reduce();

        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", result.toString());
    }

    @Test
    void snailfishNumber_reduce_04() {
        final SnailfishNumber result = SnailfishNumber.parse("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]").reduce();

        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", result.toString());
    }

    @Test
    void snailfishNumber_reduce_05() {
        final SnailfishNumber result = SnailfishNumber.parse("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]").reduce();

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", result.toString());
    }

    @Test
    void snailfishNumber_reduce_06() {
        final SnailfishNumber result = SnailfishNumber.parse("[[[[0,7],4],[15,[0,13]]],[1,1]]").reduce();

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", result.toString());
    }

    @Test
    void snailfishNumber_add_01() {

        final SnailfishNumber a = SnailfishNumber.parse("[[[[4,3],4],4],[7,[[8,4],9]]]");
        final SnailfishNumber b = SnailfishNumber.parse("[1,1]");
        final SnailfishNumberNode result = a.add(b);

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", result.toString());
    }

    @Test
    void snailfishNumber_add_02() {
        final SnailfishNumber result = Stream.of(
                "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                "[7,[5,[[3,8],[1,4]]]]",
                "[[2,[2,2]],[8,[8,1]]]",
                "[2,9]",
                "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                "[[[5,[7,4]],7],1]",
                "[[[[4,2],2],6],[8,7]]"
        )
                .map(SnailfishNumber::parse)
                .reduce(SnailfishNumber::add)
                .get();

        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", result.toString());
    }

    @Test
    void snailfishNumber_magnitude_01() {
        final int result = SnailfishNumber.parse("[[1,2],[[3,4],5]]").magnitude();

        assertEquals(143, result);
    }

    @Test
    void snailfishNumber_magnitude_02() {
        final int result = SnailfishNumber.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").magnitude();

        assertEquals(1384, result);
    }

    @Test
    void snailfishNumber_magnitude_03() {
        final int result = SnailfishNumber.parse("[[[[1,1],[2,2]],[3,3]],[4,4]]").magnitude();

        assertEquals(445, result);
    }

    @Test
    void snailfishNumber_magnitude_04() {
        final int result = SnailfishNumber.parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").magnitude();

        assertEquals(3488, result);
    }

    @Test
    void snailfishNumber_magnitude_05() {
        final int result = SnailfishNumber.parse("[[[[5,0],[7,4]],[5,5]],[6,6]]").magnitude();

        assertEquals(1137, result);
    }
}
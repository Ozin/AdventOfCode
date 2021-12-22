package AOC2021;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test implements AocTest {
    @Test
    void snailfishNumber_reduce_01() {
        final Day18 day = new Day18();

        final String result = day.reduce("[[[[[9,8],1],2],3],4]");

        assertEquals("[[[[0,9],2],3],4]", result);
    }

    @Test
    void snailfishNumber_reduce_02() {
        final Day18 day = new Day18();

        final String result = day.reduce("[7,[6,[5,[4,[3,2]]]]]");

        assertEquals("[7,[6,[5,[7,0]]]]", result);
    }

    @Test
    void snailfishNumber_reduce_03() {
        final Day18 day = new Day18();

        final String result = day.reduce("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");

        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", result);
    }

    @Test
    void snailfishNumber_reduce_04() {
        final Day18 day = new Day18();

        final String result = day.reduce("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");

        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", result);
    }

    @Test
    void snailfishNumber_reduce_05() {
        final Day18 day = new Day18();

        final String result = day.reduce("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", result);
    }

    @Test
    void snailfishNumber_reduce_06() {
        final Day18 day = new Day18();

        final String result = day.reduce("[[[[0,7],4],[15,[0,13]]],[1,1]]");

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", result);
    }

    @Test
    void snailfishNumber_add_01() {
        final Day18 day = new Day18();

        final String result = day.add("[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]");

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", result);
    }

    @Test
    void snailfishNumber_add_02() {
        final Day18 day = new Day18();

        final String result = Stream.of(
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
                .reduce(day::add)
                .get();

        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", result);
    }

    @Test
    void snailfishNumber_magnitude_01() {
        final Day18 day = new Day18();

        final int result = day.magnitude("[[1,2],[[3,4],5]]");
        assertEquals(143, result);
    }

    @Test
    void snailfishNumber_magnitude_02() {
        final Day18 day = new Day18();

        final int result = day.magnitude("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");

        assertEquals(1384, result);
    }

    @Test
    void snailfishNumber_magnitude_03() {
        final Day18 day = new Day18();

        final int result = day.magnitude("[[[[1,1],[2,2]],[3,3]],[4,4]]");

        assertEquals(445, result);
    }

    @Test
    void snailfishNumber_magnitude_04() {
        final Day18 day = new Day18();

        final int result = day.magnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");

        assertEquals(3488, result);
    }

    @Test
    void snailfishNumber_magnitude_05() {
        final Day18 day = new Day18();

        final int result = day.magnitude("[[[[5,0],[7,4]],[5,5]],[6,6]]");

        assertEquals(1137, result);
    }
}

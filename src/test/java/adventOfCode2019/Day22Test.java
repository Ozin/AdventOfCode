package adventOfCode2019;

import static adventOfCode2019.Day22.ShuffleMethod.CUT;
import static adventOfCode2019.Day22.ShuffleMethod.INCREMENT;
import static adventOfCode2019.Day22.ShuffleMethod.NEW_STACK;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.junit.Ignore;
import org.junit.Test;

public class Day22Test {

    private int[] ints10 = IntStream.range(0, 10).toArray();
    private final Day22 day22 = new Day22();

    @Test
    @Ignore
    public void bFinal() throws Exception {
        assertEquals(5540, day22.getB());
    }

    @Test
    public void aFinal() throws Exception {
        assertEquals(5540, day22.getA());
    }

    @Test
    public void a1() {
        final Function<int[], int[]> reduced = composeShuffles(
            Map.entry(INCREMENT, 7),
            Map.entry(NEW_STACK, 0),
            Map.entry(NEW_STACK, 0)
        );

        assertArrayEquals(new int[] {0, 3, 6, 9, 2, 5, 8, 1, 4, 7}, reduced.apply(ints10));
    }

    @Test
    public void a2() {
        final Function<int[], int[]> reduced = composeShuffles(
            Map.entry(CUT, 6),
            Map.entry(INCREMENT, 7),
            Map.entry(NEW_STACK, 0)
        );

        assertArrayEquals(new int[] {3, 0, 7, 4, 1, 8, 5, 2, 9, 6}, reduced.apply(ints10));
    }

    @Test
    public void a3() {
        final Function<int[], int[]> reduced = composeShuffles(
            Map.entry(INCREMENT, 7),
            Map.entry(INCREMENT, 9),
            Map.entry(CUT, -2)
        );

        assertArrayEquals(new int[] {6, 3, 0, 7, 4, 1, 8, 5, 2, 9}, reduced.apply(ints10));
    }

    @Test
    public void a4() {
        final Function<int[], int[]> reduced = composeShuffles(
            Map.entry(NEW_STACK, 0),
            Map.entry(CUT, -2),
            Map.entry(INCREMENT, 7),
            Map.entry(CUT, 8),
            Map.entry(CUT, -4),
            Map.entry(INCREMENT, 7),
            Map.entry(CUT, 3),
            Map.entry(INCREMENT, 9),
            Map.entry(INCREMENT, 3),
            Map.entry(CUT, -1)
        );

        assertArrayEquals(new int[] {9, 2, 5, 8, 1, 4, 7, 0, 3, 6}, reduced.apply(ints10));
    }

    private Function<int[], int[]> composeShuffles(final Map.Entry<Day22.ShuffleMethod, Integer>... list) {
        final StreamEx<Function<int[], int[]>> methods = EntryStream.of(List.of(list).iterator())
            .mapKeyValue((method, amount) -> (cards -> method.shuffle(cards, amount)));

        return methods.reduce(Function::andThen).orElseThrow();
    }

    @Test
    public void shuffle1() {
        final int[] shuffle = NEW_STACK.shuffle(ints10, 0);

        assertArrayEquals(new int[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, shuffle);
    }

    @Test
    public void shuffle2() {
        final int[] shuffle = CUT.shuffle(ints10, 3);

        assertArrayEquals(new int[] {3, 4, 5, 6, 7, 8, 9, 0, 1, 2}, shuffle);
    }

    @Test
    public void shuffle3() {
        final int[] shuffle = CUT.shuffle(ints10, -4);

        assertArrayEquals(new int[] {6, 7, 8, 9, 0, 1, 2, 3, 4, 5}, shuffle);
    }

    @Test
    public void shuffle4() {
        assertArrayEquals(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, INCREMENT.shuffle(ints10, 1));
        assertArrayEquals(new int[] {0, 7, 4, 1, 8, 5, 2, 9, 6, 3}, INCREMENT.shuffle(ints10, 3));
        assertArrayEquals(new int[] {0, 3, 6, 9, 2, 5, 8, 1, 4, 7}, INCREMENT.shuffle(ints10, 7));
        assertArrayEquals(new int[] {0, 9, 8, 7, 6, 5, 4, 3, 2, 1}, INCREMENT.shuffle(ints10, 9));
    }

    @Test
    public void shuffle5() {
        final int[] shuffle = INCREMENT.shuffle(ints10, 7);

        assertArrayEquals(new int[] {0, 3, 6, 9, 2, 5, 8, 1, 4, 7}, shuffle);
    }
}
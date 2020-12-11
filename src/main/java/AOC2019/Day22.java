package AOC2019;

import static AOC2019.Day22.ShuffleMethod.CUT;
import static AOC2019.Day22.ShuffleMethod.INCREMENT;
import static AOC2019.Day22.ShuffleMethod.NEW_STACK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

public class Day22 extends AbstractDay<List<Map.Entry<Day22.ShuffleMethod, Integer>>> {
    @Override
    protected List<Map.Entry<ShuffleMethod, Integer>> parseInput(final String[] rawInput) throws Exception {
        final ArrayList<Map.Entry<ShuffleMethod, Integer>> list = new ArrayList<Map.Entry<ShuffleMethod, Integer>>(rawInput.length);
        for (final String s : rawInput) {
            if (s.startsWith("deal with increment ")) {
                list.add(Map.entry(INCREMENT, Integer.parseInt(s.substring(20))));
            } else if (s.equals("deal into new stack")) {
                list.add(Map.entry(NEW_STACK, 0));
            } else {
                list.add(Map.entry(CUT, Integer.parseInt(s.substring(4))));
            }
        }
        return list;
    }

    @Override
    protected Object a(final List<Map.Entry<ShuffleMethod, Integer>> input) throws Exception {
        Function<int[], int[]> function = composeShuffles(input.toArray(Map.Entry[]::new));
        int[] stack = function.apply(IntStream.range(0, 10007).toArray());
        return indexOf(2019, stack);
    }

    @Override
    protected Object b(final List<Map.Entry<ShuffleMethod, Integer>> input) throws Exception {
//        Function<long[], long[]> function = composeShuffles(input.toArray(Map.Entry[]::new));
//        long[] stack = function.apply(LongStream.range(0, 119315717514047L).toArray());
//        return indexOf(2019, stack);
        return null;
    }

    public enum ShuffleMethod {
        NEW_STACK,
        CUT,
        INCREMENT;

        public int[] newStack(final int[] cards) {
            final int length = cards.length;
            final int[] result = new int[length];
            for (int i = 0; i < length; i++) {
                result[i] = cards[length - i - 1];
            }
            return result;
        }

        public int[] cut(final int[] cards, int amount) {
            final int length = cards.length;
            final int[] result = new int[length];
            for (int i = 0; i < length; i++) {
                result[i] = cards[(i + length + amount) % length];
            }
            return result;
        }

        public int[] increment(final int[] cards, int amount) {
            final int length = cards.length;
            final int[] result = new int[length];
            for (int i = 0; i < length; i++) {
                result[(i * amount) % length] = cards[i];
            }
            return result;
        }


        public int[] shuffle(final int[] cards, final int amount) {
            switch (this) {
                case NEW_STACK:
                    return newStack(cards);
                case CUT:
                    return cut(cards, amount);
                case INCREMENT:
                    return increment(cards, amount);
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private Function<int[], int[]> composeShuffles(final Map.Entry<Day22.ShuffleMethod, Integer>... list) {
        final StreamEx<Function<int[], int[]>> methods = EntryStream.of(List.of(list).iterator())
            .mapKeyValue((method, amount) -> (cards -> method.shuffle(cards, amount)));

        return methods.reduce(Function::andThen).orElseThrow();
    }

    public static int indexOf(int needle, int[] haystack) {
        for (int i = 0; i < haystack.length; i++) {
            if (haystack[i] == needle) {
                return i;
            }
        }

        return -1;
    }
}

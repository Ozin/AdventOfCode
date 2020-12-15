package AOC2020;

import static java.util.function.Predicate.not;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Value;
import one.util.streamex.LongStreamEx;
import one.util.streamex.StreamEx;
import utils.Indexed;

public class Day15 extends AbstractDay<int[]> {
    public static void main(final String[] args) {
        new Day15().run();
    }

    @Override
    protected int[] parseInput(final String[] rawInput) throws Exception {
        return StreamEx.of(rawInput[0].split(",")).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    protected Object a(final int[] input) throws Exception {
        final int target = 2020;
        return playGame(input, target);
    }

    @Override
    protected Object b(final int[] input) throws Exception {
        final int target = 30000000;
        return playGame(input, target);
    }

    private Object playGame(final int[] input, final int target) {
        final Map<Integer, Integer> memory = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            memory.put(input[i], i + 1);
        }

        int nextNumber = input[input.length - 1];
        for (int i = input.length; i < target; i++) {
            final Integer previous = memory.get(nextNumber);
            memory.put(nextNumber, i);
            if (previous == null) {
                nextNumber = 0;
            } else {
                nextNumber = i - previous;
            }
        }

        return nextNumber;
    }
}

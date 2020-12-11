package AOC2019;

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Day04 extends AbstractDay<int[]> {

    public static final Predicate<String> TWO_ADJACENT_DIGITS = Pattern.compile("^.*(00|11|22|33|44|55|66|77|88|99).*$").asPredicate();
    public static final Predicate<String> THREE_ADJACENT_DIGITS = Pattern.compile("^.*(000|111|222|333|444|555|666|777|888|999).*$").asPredicate();
    public static final Predicate<String> MONOTONE_INCREASING_DIGITS = Pattern.compile("^0*1*2*3*4*5*6*7*8*9*$").asPredicate();

    public static void main(final String[] args) {
        new Day04().run();
    }

    @Override
    protected int[] parseInput(final String[] rawInput) throws Exception {
        return Arrays.stream(rawInput[0].split("-")).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    protected Object a(final int[] input) throws Exception {
        return IntStreamEx.rangeClosed(input[0], input[1])
                .mapToObj(Integer::toString)
                .filter(TWO_ADJACENT_DIGITS)
                .filter(MONOTONE_INCREASING_DIGITS)
                .count();
    }

    @Override
    protected Object b(final int[] input) throws Exception {
        return IntStreamEx.rangeClosed(input[0], input[1])
                .mapToObj(Integer::toString)
                .filter(TWO_ADJACENT_DIGITS)
                .filter(MONOTONE_INCREASING_DIGITS)
                .filter(this::hasExactlyTwoTimesTheSameDigit)
                .count();
    }

    private boolean hasExactlyTwoTimesTheSameDigit(final String number) {
        return StreamEx.of(number.split(""))
                .runLengths()
                .anyMatch((key, value) -> value == 2);
    }
}

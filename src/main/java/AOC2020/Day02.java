package AOC2020;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lombok.Value;
import one.util.streamex.StreamEx;

public class Day02 extends AbstractDay<Day02.Password[]> {
    public static void main(final String[] args) {
        new Day02().run();
    }

    //5-10 v: vvbvsvtmtvvvvv
    Pattern linePattern = Pattern.compile("^(\\d+)-(\\d+) (.): (.+)$");

    @Override
    protected Password[] parseInput(final String[] rawInput) throws Exception {
        return Stream.of(rawInput)
            .map(linePattern::matcher)
            .filter(Matcher::find)
            .map(m -> new Password(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), m.group(3), m.group(4)))
            .toArray(Password[]::new);
    }

    @Override
    protected Object a(final Password[] input) throws Exception {
        final Predicate<? super Password> isValid = p -> {
            final long amount = StreamEx.of(p.getPassword().split(""))
                .sorted()
                .runLengths()
                .filterKeys(p.getValChar()::equals)
                .values()
                .findFirst()
                .orElse(0L);

            return amount >= p.getLower_border() && amount <= p.getUpper_border();
        };

        return Stream.of(input).filter(isValid).count();
    }

    @Override
    protected Object b(final Password[] input) throws Exception {
        final Predicate<? super Password> isValid = p -> {
            final String[] chars = p.getPassword().split("");

            return chars[p.getLower_border() - 1].equals(p.getValChar())
                != chars[p.getUpper_border() - 1].equals(p.getValChar());
        };

        return Stream.of(input).filter(isValid).count();
    }

    @Value
    static class Password {
        int lower_border, upper_border;
        String valChar, password;
    }
}

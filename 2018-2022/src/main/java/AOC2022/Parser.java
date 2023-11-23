package AOC2022;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Parser<T> {
    private final Pattern pattern;
    private final Function<String, T> converter;

    public Parser(final Pattern pattern, final Function<String, T> converter) {
        this.pattern = pattern;
        this.converter = converter;
    }

    public List<T> parse(final String string) {
        final Matcher m = pattern.matcher(string);
        if (!m.find()) throw new IllegalArgumentException("Pattern does not fit string: " + string);

        return IntStream.range(1, m.groupCount() + 1)
                        .mapToObj(m::group)
                        .map(converter)
                        .toList();
    }
}

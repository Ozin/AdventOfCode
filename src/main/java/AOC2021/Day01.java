package AOC2021;

import io.vavr.collection.Stream;
import io.vavr.collection.Traversable;

public class Day01 {

    protected String a(final String input) throws Exception {
        return "" + Stream.of(input.split("\n"))
                .map(Integer::parseInt)
                .sliding(2)
                .map(window -> window.toJavaArray(Integer[]::new))
                .map(window -> window[1] - window[0])
                .count(i -> i > 0);
    }

    protected String b(final String input) throws Exception {
        return "" + Stream.of(input.split("\n"))
                .map(Integer::parseInt)
                .sliding(3)
                .map(Traversable::sum)
                .map(Number::intValue)
                .sliding(2)
                .map(window -> window.toJavaArray(Integer[]::new))
                .map(window -> window[1] - window[0])
                .count(i -> i > 0);
    }
}

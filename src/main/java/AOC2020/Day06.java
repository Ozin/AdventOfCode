package AOC2020;

import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Day06 extends AbstractDay<String> {
    public static void main(final String[] args) {
        new Day06().run();
    }

    @Override
    protected String parseInput(final String[] rawInput) throws Exception {
        return String.join("\n", rawInput);
    }

    @Override
    protected Object a(final String input) throws Exception {
        return input
            .replaceAll("\n([^\n])", "$1")
            .lines()
            .map(s -> s.split(""))
            .map(List::of)
            .map(Set::copyOf)
            .mapToInt(Set::size)
            .sum();
    }

    @Override
    protected Object b(final String input) throws Exception {
        final String[] groups = input.split(Pattern.quote("\n\n"));

        return StreamEx.of(groups)
            .map(s -> s.split("\n"))
            .mapToInt(this::getEveryoneTrue)
            .sum();
    }

    private int getEveryoneTrue(final String[] group) {
        return StreamEx.of(group)
            .map(s -> s.split(""))
            .map(List::of)
            .map(Set::copyOf)
            .reduce(this::intersect)
            .orElseThrow()
            .size();
    }

    private <T> Set<T> intersect(final Set<T> a, final Set<T> b) {
        final Set<T> intersection = new HashSet<T>(a); // use the copy constructor
        intersection.retainAll(b);

        return Set.copyOf(intersection);
    }
}

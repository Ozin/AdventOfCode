package AOC2020;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.Value;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

public class Day16 extends AbstractDay<Day16.Notes> {
    public static void main(final String[] args) {
        new Day16().run();
    }

    @Override
    protected Notes parseInput(final String[] rawInput) throws Exception {
        final String[] mainParts = String.join("\n", rawInput).split(Pattern.quote("\n\n"));

        final Property[] properties = StreamEx.of(mainParts[0].split(Pattern.quote("\n")))
            .map(Property::new)
            .toArray(Property[]::new);
        final int[] ownTicket = StreamEx.of(mainParts[1].substring(13).split(","))
            .mapToInt(Integer::parseInt)
            .toArray();
        final List<int[]> otherTickets = StreamEx.of(mainParts[2].split(Pattern.quote("\n")))
            .skip(1)
            .map(s -> StreamEx.of(s.split(","))
                .mapToInt(Integer::parseInt)
                .toArray()
            )
            .collect(Collectors.toList());

        return new Notes(properties, ownTicket, otherTickets);
    }

    @Override
    protected Object a(final Notes input) throws Exception {
        return input.otherTickets.stream()
            .flatMapToInt(IntStream::of)
            .filter(i -> StreamEx.of(input.properties).noneMatch(p -> p.isValidValue(i)))
            .sum();
    }

    @Override
    protected Object b(final Notes input) throws Exception {
        final Property[] properties = input.getProperties();
        final List<int[]> validTickets = getValidTickets(input);

        for (final Property property : properties) {
            final int[] possibleColumns = IntStreamEx.range(properties.length)
                .filter(columnIndex -> property.isValidColumn(validTickets, columnIndex))
                .toArray();

            continue;
        }

        return null;
    }

    private boolean columnIsValid(final List<int[]> validTickets, final Property property, final int columnIndex) {
        return StreamEx.of(validTickets)
            .allMatch(ticket -> property.isValidValue(ticket[columnIndex]));
    }

    private List<int[]> getValidTickets(final Notes input) {
        return Stream.concat(
            Stream.of(input.ownTicket),
            input.otherTickets.stream().filter(ticket -> isValidTicket(ticket, input.getProperties()))
        ).collect(Collectors.toList());
    }

    public boolean isValidTicket(final int[] fields, final Property[] properties) {
        return StreamEx.of(properties)
            .allMatch(property -> IntStream.of(fields).anyMatch(property::isValidValue));
    }

    @Value
    public static class Notes {
        Property[] properties;
        int[] ownTicket;
        List<int[]> otherTickets;
    }

    @Value
    public static class Property {
        String name;
        Range[] ranges;

        public Property(final String property) {
            final String[] first = property.split(": ");
            this.name = first[0];
            this.ranges = StreamEx.of(first[1].split(" or ")).map(Range::new).toArray(Range[]::new);
        }

        public boolean isValidValue(final int value) {
            final boolean isValid = StreamEx.of(ranges).anyMatch(range -> range.inBetween(value));
            return isValid;
        }

        public boolean isValidColumn(final List<int[]> tickets, final int column) {
            final List<Boolean> collect = tickets.stream()
                .map(ticket -> isValidValue(ticket[column])).collect(Collectors.toList());
            return collect.stream().allMatch(b -> b);
        }
    }

    @Value
    private static class Range {
        int lower;
        int upper;

        public Range(final String range) {
            this(range.split("-"));
        }

        public Range(final String[] strings) {
            this.lower = Integer.parseInt(strings[0]);
            this.upper = Integer.parseInt(strings[1]);
        }

        public boolean inBetween(final int value) {
            return lower <= value && value <= upper;
        }
    }
}

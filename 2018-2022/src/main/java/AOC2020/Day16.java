package AOC2020;

import lombok.Value;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.AbstractDay;
import utils.Indexed;
import utils.Range;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

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
        var fieldColumns = StreamEx.of(getFieldColumns(validTickets))
            .map(Indexed.map())
            .cross(properties)
            .invert()
            .filterKeyValue((prop, column) -> columnIsValid(column.getValue(), prop))
            .grouping();

        final Map<Property, Integer> propertyColumnIndexes = new HashMap<>();

        while (!fieldColumns.isEmpty()) {
            final Map.Entry<Property, List<Indexed<int[]>>> oneColumn = EntryStream.of(fieldColumns)
                .findFirst(e -> e.getValue().size() == 1).orElseThrow();

            final int specifiedIndex = oneColumn.getValue().get(0).getIndex();
            propertyColumnIndexes.put(oneColumn.getKey(), specifiedIndex);

            fieldColumns = EntryStream.of(fieldColumns)
                .filterKeys(not(oneColumn.getKey()::equals))
                .mapValues(list -> list.stream()
                    .filter(indexed -> indexed.getIndex() != specifiedIndex)
                    .collect(Collectors.toList()))
                .toMap();
        }

        return EntryStream.of(propertyColumnIndexes)
            .filterKeys(p -> p.getName().startsWith("departure "))
            .values()
            .mapToLong(index -> input.ownTicket[index])
            .reduce((a, b) -> a * b)
            .orElseThrow();
    }

    private int[][] getFieldColumns(final List<int[]> validTickets) {
        final int[][] columns = new int[validTickets.get(0).length][validTickets.size()];
        for (int ticketIndex = 0; ticketIndex < validTickets.size(); ticketIndex++) {
            final int[] currentTicket = validTickets.get(ticketIndex);
            for (int fieldIndex = 0; fieldIndex < currentTicket.length; fieldIndex++) {
                columns[fieldIndex][ticketIndex] = currentTicket[fieldIndex];
            }
        }

        return columns;
    }

    private boolean columnIsValid(final int[] column, final Property property) {
        return IntStreamEx.of(column)
            .allMatch(property::isValidValue);
    }

    private List<int[]> getValidTickets(final Notes input) {
        return Stream.concat(
            Stream.of(input.ownTicket),
            input.otherTickets.stream().filter(ticket -> isValidTicket(ticket, input.getProperties()))
        ).collect(Collectors.toList());
    }

    public boolean isValidTicket(final int[] fields, final Property[] properties) {
        for (final int field : fields) {
            if (!isValidField(properties, field)) {
                return false;
            }
        }

        return true;
    }

    int wrongCount = 0;

    private boolean isValidField(final Property[] properties, final int field) {
        for (final Property property : properties) {
            if (property.isValidValue(field)) {
                return true;
            }
        }

        wrongCount += field;

        return false;
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
            this.ranges = StreamEx.of(first[1].split(" or ")).map(this::rangeOf).toArray(Range[]::new);
        }

        private Range rangeOf(final String range) {
            final String[] split = range.split("-");
            return new Range(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }

        public boolean isValidValue(final int value) {
            return StreamEx.of(ranges).anyMatch(range -> range.inBetween(value));
        }
    }

}

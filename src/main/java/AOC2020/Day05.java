package AOC2020;

import static java.util.function.Predicate.not;

import java.util.Set;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

public class Day05 extends AbstractDay<Day05.Seat[]> {
    public static void main(final String[] args) {
        new Day05().run();
    }

    @Override
    protected Seat[] parseInput(final String[] rawInput) throws Exception {
        return Stream.of(rawInput)
            .map(Seat::new)
            .toArray(Seat[]::new);
    }

    @Override
    protected Object a(final Seat[] input) throws Exception {
        return Stream.of(input)
            .mapToInt(Seat::getId)
            .max()
            .orElseThrow();
    }

    @Override
    protected Object b(final Seat[] input) throws Exception {
        final Set<Seat> seats = Set.of(input);

        return IntStreamEx.range(Seat.MIN.getId(), Seat.MAX.getId() + 1)
            .filter(i -> seats.contains(new Seat(i + 1)))
            .skip(1)
            .mapToObj(Seat::new)
            .filter(not(seats::contains))
            .findFirst()
            .orElseThrow()
            .getId();
    }

    @Value
    public static class Seat {
        public static final Seat MIN = new Seat("FFFFFFFLLL"), MAX = new Seat("BBBBBBBRRR");
        int row, column, id;

        public Seat(final int row, final int column) {
            this.row = row;
            this.column = column;
            this.id = row * 8 + column;
        }

        public Seat(final String s) {
            this(
                Integer.parseInt(
                    s.substring(0, 7)
                        .replaceAll("F", "0")
                        .replaceAll("B", "1"),
                    2),
                Integer.parseInt(
                    s.substring(7)
                        .replace("R", "1")
                        .replace("L", "0"),
                    2)
            );
        }

        public Seat(final int id) {
            this(id / 8, id % 8);
        }
    }
}

package AOC2020;

import lombok.EqualsAndHashCode;
import utils.AbstractDay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day23 extends AbstractDay<Day23.Circle> {
    public static void main(final String[] args) {
        new Day23().run();
    }

    @Override
    protected Circle parseInput(final String[] rawInput) throws Exception {
        final Circle circle = new Circle();
        Stream.of(rawInput[0].split("")).map(Integer::parseInt).forEach(circle::add);
        circle.nextValue();
        return circle;
    }

    @Override
    protected Object a(final Circle circle) throws Exception {
        for (int i = 0; i < 100; i++) {
            playRound(circle);
        }

        return circle.printFromValue(1).substring(1);
    }

    @Override
    protected Object b(final Circle circle) throws Exception {
        circle.setCurrent(7);
        IntStream.range(10, 1000001).forEach(circle::add);
        circle.nextValue();

        for (int i = 0; i < 10000000; i++) {
            playRound(circle);
        }

        circle.setCurrent(1);
        final long a = circle.nextValue();
        final long b = circle.nextValue();

        return a * b;
    }

    private void playRound(final Circle circle) {
        final List<Integer> removed = List.of(
            circle.removeNext(),
            circle.removeNext(),
            circle.removeNext()
        );

        final int destination = findDestinationCup(circle);

        final int oldCurrent = circle.currentValue();

        circle.setCurrent(destination);
        removed.forEach(circle::add);
        circle.setCurrent(oldCurrent);
        circle.nextValue();
    }

    private int findDestinationCup(final Circle circle) {
        return Stream.concat(
            IntStream.iterate(circle.currentValue() - 1, i -> i > 0, i -> i - 1).boxed(),
            IntStream.iterate(1000000, i -> i >= 0, i -> i - 1).boxed()
        )
            .filter(circle::contains)
            .findFirst()
            .get();
    }

    public static class Circle {
        private final Map<Integer, Cup> map = new HashMap<>(1000000);
        private Cup currentCup;

        public Circle() {
        }

        public void add(final int value) {
            if (map.containsKey(value)) {
                throw new IllegalArgumentException("value " + value + " is already part of the circle");
            }

            final Cup cup = new Cup(value);
            map.put(value, cup);

            if (currentCup == null) {
                cup.next = cup;

                currentCup = cup;
            } else {
                cup.next = currentCup.next;
                currentCup.next = cup;
            }

            setCurrent(value);
        }

        public int removeNext() {
            final Cup next = currentCup.next;
            currentCup.next = currentCup.next.next;
            map.remove(next.value);

            return next.value;
        }

        public int currentValue() {
            return currentCup.value;
        }

        public int nextValue() {
            currentCup = currentCup.next;
            return currentValue();
        }

        public void setCurrent(final int value) {
            final Cup searchedCup = map.get(value);

            if (searchedCup == null) {
                throw new IllegalArgumentException("Cup " + value + " does not exist");
            }

            this.currentCup = searchedCup;
        }

        public boolean contains(final int value) {
            return map.containsKey(value);
        }

        public String printFromValue(final int value) {
            final StringBuilder stringBuilder = new StringBuilder();
            final Cup start = map.get(value);

            if (start == null) {
                throw new IllegalArgumentException("value does not exist in circle");
            }

            Cup current = start.next;

            stringBuilder.append(start.value);
            while (start != current) {
                stringBuilder.append(current.value);
                current = current.next;
            }

            return stringBuilder.toString();
        }

    }

    @EqualsAndHashCode(exclude = {"next"})
    public static class Cup {
        private final int value;

        private Cup next;

        public Cup(final int value) {
            this.value = value;
        }
    }

}

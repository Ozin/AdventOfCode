package AOC2021;

import one.util.streamex.StreamEx;
import utils.Point;
import utils.Vector;

import java.util.concurrent.atomic.AtomicInteger;

public class Day02 {

    protected String a(final String input) throws Exception {
        Point[] start = new Point[]{new Point(0, 0)};

        StreamEx.of(input)
                .flatMap(String::lines)
                .map(instruction -> instruction.split(" "))
                .mapToEntry(instruction -> instruction[0], instruction -> instruction[1])
                .mapValues(Integer::parseInt)
                .forKeyValue((direction, amount) -> {
                    switch (direction) {
                        case "forward":
                            start[0] = start[0].addX(amount);
                            break;
                        case "up":
                            start[0] = start[0].addY(-amount);
                            break;
                        case "down":
                            start[0] = start[0].addY(amount);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected direction: " + direction);
                    }
                });

        return "" + (start[0].getX() * start[0].getY());
    }

    protected String b(final String input) throws Exception {
        Point[] position = new Point[]{new Point(0, 0)};
        AtomicInteger aim = new AtomicInteger(0);

        StreamEx.of(input)
                .flatMap(String::lines)
                .map(instruction -> instruction.split(" "))
                .mapToEntry(instruction -> instruction[0], instruction -> instruction[1])
                .mapValues(Integer::parseInt)
                .forKeyValue((direction, amount) -> {
                    switch (direction) {
                        case "forward":
                            position[0] = position[0].addX(amount).addY(aim.get() * amount);
                            break;
                        case "up":
                            aim.addAndGet(-amount);
                            break;
                        case "down":
                            aim.addAndGet(amount);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected direction: " + direction);
                    }
                });

        return "" + (position[0].getX() * position[0].getY());
    }
}

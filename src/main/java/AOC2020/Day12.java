package AOC2020;

import AOC2019.Direction;
import utils.AbstractDay;
import utils.Point;
import utils.Vector;

import java.util.stream.Stream;

public class Day12 extends AbstractDay<String[][]> {
    public static void main(final String[] args) {
        new Day12().run();
    }

    @Override
    protected String[][] parseInput(final String[] rawInput) throws Exception {
        return Stream.of(rawInput)
            .map(s -> new String[]{s.substring(0, 1), s.substring(1)})
            .toArray(String[][]::new);
    }

    @Override
    protected Object a(final String[][] input) throws Exception {
        Point curPos = new Point(0, 0);
        Direction curDirection = Direction.RIGHT;

        for (final String[] instruction : input) {
            final int amount = Integer.parseInt(instruction[1]);
            switch (instruction[0]) {
                case "N":
                    curPos = Direction.UP.continueStraight(curPos, amount);
                    break;
                case "S":
                    curPos = Direction.DOWN.continueStraight(curPos, amount);
                    break;
                case "W":
                    curPos = Direction.LEFT.continueStraight(curPos, amount);
                    break;
                case "E":
                    curPos = Direction.RIGHT.continueStraight(curPos, amount);
                    break;
                case "L":
                    curDirection = turn(curDirection, false, amount);
                    break;
                case "F":
                    curPos = curDirection.continueStraight(curPos, amount);
                    break;
                case "R":
                    curDirection = turn(curDirection, true, amount);
                    break;
            }
        }

        return curPos.manhattenDistance(new Point(0, 0));
    }

    @Override
    protected Object b(final String[][] input) throws Exception {
        Vector wayVector = new Vector(10, 1);
        Vector shipPos = new Vector(0, 0);

        for (final String[] instruction : input) {
            final int amount = Integer.parseInt(instruction[1]);
            switch (instruction[0]) {
                case "N":
                    wayVector = wayVector.add(new Vector(0, amount));
                    break;
                case "S":
                    wayVector = wayVector.add(new Vector(0, -amount));
                    break;
                case "W":
                    wayVector = wayVector.add(new Vector(-amount, 0));
                    break;
                case "E":
                    wayVector = wayVector.add(new Vector(amount, 0));
                    break;
                case "L":
                    wayVector = wayVector.turn(false, amount / 90);
                    break;
                case "F":
                    shipPos = shipPos.add(wayVector.scalar(amount));
                    break;
                case "R":
                    wayVector = wayVector.turn(true, amount / 90);
                    break;
            }
        }

        return shipPos.toPoint().manhattenDistance(new Point(0, 0));
    }

    private Direction turn(final Direction direction, final boolean clockwise, final int amount) {
        if (amount == 0) {
            return direction;
        }
        return turn(clockwise ? direction.turnRight() : direction.turnLeft(), clockwise, amount - 90);
    }
}

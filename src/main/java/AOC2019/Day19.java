package AOC2019;

import java.util.Map;
import one.util.streamex.IntStreamEx;
import utils.Point;

public class Day19 extends AbstractIntcodePuzzle {
    @Override
    protected Object a(final long[] input) throws Exception {
        final Map<Point, Boolean> map = IntStreamEx.range(50).boxed()
            .cross(IntStreamEx.range(50).boxed().toList())
            .mapKeyValue(Point::new)
            .mapToEntry(this::isInBeam)
            .toMap();

        printGame(map);

        return map.values().stream().filter(Boolean::booleanValue).count();
    }

    @Override
    protected Object b(final long[] input) throws Exception {
        final Point foundPoint = searchUpperLeftCorner();

        final Map<Point, Boolean> map = IntStreamEx.range(foundPoint.getX() - 5, foundPoint.getX() + 110).boxed()
            .cross(IntStreamEx.range(foundPoint.getY() - 5, foundPoint.getY() + 110).boxed().toList())
            .mapKeyValue(Point::new)
            .filter(p -> p.getX() < foundPoint.getX() || p.getX() > foundPoint.addX(100).getX() || p.getY() < foundPoint.getY() || p.getY() > foundPoint.addY(100).getY())
            .mapToEntry(this::isInBeam)
            .toMap();


        printGame(map);
        return foundPoint.getX() * 10000 + foundPoint.getY();
    }

    private Point searchUpperLeftCorner() {
        int curX = 0;
        int curY = 10;

        while (true) {
            final Point bottemLeftCorner = new Point(curX, curY);
            final Point upperLeftCorner = bottemLeftCorner.addY(-99);
            final Point upperRightCorner = upperLeftCorner.addX(99);

            if (!isInBeam(bottemLeftCorner)) {
                curX++;
                continue;
            }

            if (!isInBeam(upperLeftCorner) || !isInBeam(upperRightCorner)) {
                curY++;
                continue;
            }

            return upperLeftCorner;
        }
    }

    private boolean isInBeam(final Point point) {
        final IntcodeComputer intcodeComputer = new IntcodeComputer(getProgram());
        intcodeComputer.addInput(point.getX());
        intcodeComputer.addInput(point.getY());

        final int output = Math.toIntExact(intcodeComputer.nextOutput());
        switch (output) {
            case 1:
                return true;
            case 0:
                return false;
            default:
                throw new IllegalStateException();
        }
    }

    public void printGame(final Map<Point, Boolean> gameState) {
        final int maxX = gameState.keySet().stream().mapToInt(Point::getX).max().orElseThrow();
        final int maxY = gameState.keySet().stream().mapToInt(Point::getY).max().orElseThrow();
        final int minX = gameState.keySet().stream().mapToInt(Point::getX).min().orElseThrow();
        final int minY = gameState.keySet().stream().mapToInt(Point::getY).min().orElseThrow();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                final Boolean value = gameState.get(new Point(x, y));
                final char string = value == null ? ' ' : value ? '#' : '.';
                System.out.print(string);
            }
            System.out.println();
        }
    }
}

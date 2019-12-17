package adventOfCode2019;

import java.util.HashMap;
import java.util.Map;

import static adventOfCode2019.Direction.UP;

public class PaintingRobot {
    private Direction curDirection;
    private final IntcodeComputer intcodeComputer;
    private final Map<Point, Color> tiles;
    private Point curTile;

    public PaintingRobot(final long[] brain) {
        curDirection = UP;
        intcodeComputer = new IntcodeComputer(brain);
        tiles = new HashMap<>();
        curTile = new Point(0, 0);
    }

    public Map<Point, Color> getTiles() {
        return tiles;
    }

    public void autonomicDrive(final Color startingColor) {
        while (!intcodeComputer.isDone()) {
            final Color currentColor = tiles.getOrDefault(curTile, startingColor);
            drive(currentColor);
        }
    }

    public void drive(final Color input) {
        int output = (int) intcodeComputer.nextOutput(input.ordinal());
        if(intcodeComputer.isDone()) return;

        final Color targetColor = Color.values()[output];
        paint(targetColor);

        final boolean turnLeft = intcodeComputer.nextOutput() == 0;
        if(intcodeComputer.isDone()) return;

        move(turnLeft);

        //System.out.println(toString());
    }

    private void paint(final Color targetColor) {
        tiles.put(curTile, targetColor);
    }

    private void move(final boolean turnLeft) {
        curDirection = turnLeft ? curDirection.turnLeft() : curDirection.turnRight();

        int dX = 0, dY = 0;
        switch (curDirection) {
            case UP:
                dY = -1;
                break;
            case DOWN:
                dY = 1;
                break;
            case LEFT:
                dX = -1;
                break;
            case RIGHT:
                dX = 1;
                break;
        }

        curTile = curTile.addX(dX).addY(dY);
    }

    @Override
    public String toString() {
        final int minX = tiles.keySet().stream().mapToInt(Point::getX).min().orElseThrow();
        final int maxX = tiles.keySet().stream().mapToInt(Point::getX).max().orElseThrow() + 1;
        final int minY = tiles.keySet().stream().mapToInt(Point::getY).min().orElseThrow() - 1;
        final int maxY = tiles.keySet().stream().mapToInt(Point::getY).max().orElseThrow() + 2;

        final StringBuilder sb = new StringBuilder();
        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                final Point key = new Point(x, y);
                if (key.equals(curTile)) {
                    sb.append(curDirection.getGridString());
                } else {
                    sb.append(tiles.getOrDefault(key, Color.BLACK));
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public enum Color {
        BLACK("."),
        WHITE("#");

        private final String representation;

        Color(final String representation) {
            this.representation = representation;
        }

        @Override
        public String toString() {
            return representation;
        }
    }
}

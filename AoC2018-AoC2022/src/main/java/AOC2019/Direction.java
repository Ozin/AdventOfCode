package AOC2019;

import one.util.streamex.StreamEx;
import utils.Point;

public enum Direction {
    UP("^"),
    RIGHT(">"),
    DOWN("v"),
    LEFT("<");

    public static final Direction[] VALUES = values();
    private final String gridString;

    Direction(final String gridString) {
        this.gridString = gridString;
    }

    public Direction turnRight() {
        return VALUES[(ordinal() + 1) % VALUES.length];
    }

    public Direction turnLeft() {
        return VALUES[(ordinal() - 1 + VALUES.length) % VALUES.length];
    }

    public String getGridString() {
        return gridString;
    }

    public static Direction getByString(final String representation) {
        return StreamEx.of(VALUES)
            .findFirst(v -> v.getGridString().equals(representation))
            .orElseThrow();
    }

    public Point continueStraight(final Point p) {
        return continueStraight(p, 1);
    }

    public Point continueStraight(final Point p, final int amount) {
        switch (this) {
            case UP:
                return p.addY(-amount);
            case RIGHT:
                return p.addX(amount);
            case DOWN:
                return p.addY(amount);
            case LEFT:
                return p.addX(-amount);
            default:
                throw new UnsupportedOperationException("Direction " + this + " is not supported");
        }
    }
}

package AOC2019;

import one.util.streamex.StreamEx;

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

    public Point continueStraight(Point p) {
        switch (this) {
            case UP:
                return p.addY(-1);
            case RIGHT:
                return p.addX(1);
            case DOWN:
                return p.addY(1);
            case LEFT:
                return p.addX(-1);
            default:
                throw new UnsupportedOperationException("Direction " + this + " is not supported");
        }
    }
}

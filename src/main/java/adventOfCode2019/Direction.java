package adventOfCode2019;

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
}

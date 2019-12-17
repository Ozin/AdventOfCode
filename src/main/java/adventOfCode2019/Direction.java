package adventOfCode2019;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public static final Direction[] VALUES = values();

    public Direction turnRight() {
        return VALUES[ordinal() + 1 % VALUES.length];
    }

    public Direction turnLeft() {
        return VALUES[ordinal() - 1 % VALUES.length];
    }
}

package de.ozyn.aoc2023.util;

public record Point2D(int x, int y) {
    public int manhatten(Point2D other) {
        return Math.abs(x - other.x) + Math.abs(y + other.y);
    }
}

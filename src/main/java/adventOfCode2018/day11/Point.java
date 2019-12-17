package adventOfCode2018.day11;

import lombok.Value;

@Value
public class Point {
    int x, y;

    public Point addX(int dx) {
        return new Point(x + dx, y);
    }

    public Point addY(int dy) {
        return new Point(x, y + dy);
    }
}

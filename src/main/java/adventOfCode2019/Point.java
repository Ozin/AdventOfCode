package adventOfCode2019;

import lombok.NonNull;
import lombok.Value;
import lombok.With;
import one.util.streamex.EntryStream;

@Value
public class Point {
    public static final Point CENTER = new Point(0, 0);

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Point(final Vector v) {
        this(v.getDx(), v.getDy());
    }

    @With
    int x;

    @With
    int y;

    public Point addX(final int dX) {
        return withX(x + dX);
    }

    public Point addY(final int dY) {
        return withY(y + dY);
    }

    public int manhattenDistance(@NonNull final Point b) {
        return Math.abs(x - b.x) + Math.abs(y - b.y);
    }

    public int manhattenDistanceFromSource() {
        return manhattenDistance(CENTER);
    }

    public EntryStream<Direction, Point> getNeighbours() {
        return EntryStream.of(
            Direction.LEFT, addX(-1),
            Direction.RIGHT, addX(1),
            Direction.UP, addY(-1),
            Direction.DOWN, addY(1)
        );
    }
}

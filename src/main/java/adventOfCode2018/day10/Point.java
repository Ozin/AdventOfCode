package adventOfCode2018.day10;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.function.Function;

@Value
@AllArgsConstructor
public class Point {
    long x, y, vx, vy;

    public Point(long x, long y) {
        this(x, y, 0, 0);
    }

    public Point(String input) {
        // position=<-19942, -39989> velocity=< 2,  4>
        this.x = Integer.parseInt(input.substring(10, 16).trim());
        this.y = Integer.parseInt(input.substring(18, 24).trim());
        this.vx = Integer.parseInt(input.substring(36, 38).trim());
        this.vy = Integer.parseInt(input.substring(40, 42).trim());
    }

    public static Function<Point, Point> atSecond(long seconds) {
        return point -> new Point(point.x + point.vx * seconds, point.y + point.vy * seconds, point.vx, point.vy);
    }
}

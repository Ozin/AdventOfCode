package utils;

import lombok.Value;

import java.util.stream.Stream;

@Value
public class Line {
    Point start;
    Point end;

    public Stream<Point> interpolateLineInclusive() {
        Vector slope = getSlope();
        return Stream.concat(
                Stream.iterate(start, p -> !p.equals(end), p -> p.add(slope.toPoint())),
                Stream.of(end)
        );
    }

    public Vector getSlope() {
        Rational rational = new Rational(end.getX() - start.getX(), end.getY() - start.getY()).reduce();
        return new Vector(rational.getDividend(), rational.getDivisor());
    }
}

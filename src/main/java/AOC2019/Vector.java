package AOC2019;

import lombok.Value;

@Value
public class Vector {
    final int dx;
    final int dy;

    public Vector(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Vector(Point p) {
        this(p.getX(), p.getY());
    }

    public Vector(Point a, Point b) {
        this(b.getX() - a.getX(), b.getY() - a.getY());
    }

    public Vector reduce() {
        final int gcd = gcd();
        return new Vector(dx / gcd, dy / gcd);
    }

    public Vector scalar(final int factor) {
        return new Vector(dx * factor, dy * factor);
    }

    public Vector add(final Vector other) {
        return new Vector(this.dx + other.dx, this.dy + other.dy);
    }

    public Vector subtract(final Vector other) {
        return this.add(other.reverse());
    }

    public Vector reverse() {
        return new Vector(-dx, -dy);
    }

    public double angle() {
        int factor = 1;
        if (dy < 0) {
            factor = -1;
        }
        return factor * (Math.acos(dx / length()));
    }

    public double length() {
        return Math.sqrt(dx * dx + dy * dy);
    }

    private int gcd() {
        int a = Math.abs(dx);
        int b = Math.abs(dy);

        while (b != 0) {
            final int t = b;
            b = a % b;
            a = t;
        }

        return a;
    }

    public Point toPoint() {
        return new Point(dx,dy);
    }
}

package AOC2018.day06;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Point implements Comparable<Point> {
    int x, y;//, id;

    //static AtomicInteger counter = new AtomicInteger(0);

    public Point(String input) {
        this(input.split(", "));
    }

    public Point(String[] inputs) {
        this(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));//, counter.getAndIncrement());
    }

    public int distance(Point other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    public int compareTo(Point o) {
        int dx = this.x - o.x;
        return dx == 0 ? this.y - o.y : dx;
    }
}

package AOC2022;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Point(int x, int y) {
    public Point(final String s) {
        this(s.split(","));
    }

    public Point(final String[] split) {
        this(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public static Collector<Point, PointSummary, PointSummary> pointSummary() {
        return new PointSummary();
    }

    public Stream<Point> interpolate(final Point other) {
        return Stream.iterate(this, point -> !other.equals(point), point -> {
            return new Point(
                    point.x == other.x ? point.x : point.x + (point.x - other.x < 0 ? 1 : -1),
                    point.y == other.y ? point.y : point.y + (point.y - other.y < 0 ? 1 : -1)
            );
        });
    }

    public int manhattenDistance(final Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    public Point down() {
        return new Point(x, y + 1);
    }

    public Point left() {
        return new Point(x - 1, y);
    }

    public Point right() {
        return new Point(x + 1, y);
    }

    public Point up() {
        return new Point(x, y - 1);
    }

    public Stream<Point> streamGrid(final Point other) {
        final Function<Integer, Stream<Point>> runY = currentX -> IntStream.range(
                Math.min(y, other.y),
                Math.max(y, other.y)
        ).mapToObj(y -> new Point(currentX, y));

        return IntStream.range(Math.min(x, other.x), Math.max(x, other.x)).boxed().flatMap(runY);
    }

    static class PointSummary implements Collector<Point, PointSummary, PointSummary> {
        private int minX = Integer.MAX_VALUE;
        private int maxX = Integer.MIN_VALUE;
        private int minY = Integer.MAX_VALUE;
        private int maxY = Integer.MIN_VALUE;

        private PointSummary() {
        }

        public int minX() {
            return minX;
        }

        public int maxX() {
            return maxX;
        }

        public int minY() {
            return minY;
        }

        public int maxY() {
            return maxY;
        }

        @Override
        public Supplier<PointSummary> supplier() {
            return PointSummary::new;
        }

        @Override
        public BiConsumer<PointSummary, Point> accumulator() {
            return (sum, p) -> {
                sum.minX = Math.min(p.x, sum.minX);
                sum.maxX = Math.max(p.x, sum.maxX);
                sum.minY = Math.min(p.y, sum.minY);
                sum.maxY = Math.max(p.y, sum.maxY);
            };
        }

        @Override
        public BinaryOperator<PointSummary> combiner() {
            return (sum1, sum2) -> {
                sum1.minX = Math.min(sum2.minX, sum1.minX);
                sum1.maxX = Math.max(sum2.maxX, sum1.maxX);
                sum1.minY = Math.min(sum2.minY, sum1.minY);
                sum1.maxY = Math.max(sum2.maxY, sum1.maxY);
                return sum1;
            };
        }

        @Override
        public Function<PointSummary, PointSummary> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.IDENTITY_FINISH);
        }

        public Stream<Point> streamGrid() {
            return new Point(minX, minY).streamGrid(new Point(maxX, maxY));
        }

        @Override
        public String toString() {
            return "PointSummary{" +
                   "minX=" + minX +
                   ", maxX=" + maxX +
                   ", minY=" + minY +
                   ", maxY=" + maxY +
                   '}';
        }
    }
}

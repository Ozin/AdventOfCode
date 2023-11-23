package AOC2022;


import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.vavr.Predicates.not;

public class Day15 {

    // public static final int SCAN_Y_LEVEL = 10;
    public static final int SCAN_Y_LEVEL = 2000000;

    public static final int POSSIBLE_BEACON_POSITION = 20;
    //public static final int POSSIBLE_BEACON_POSITION = 4000000;

    public static void main(final String[] args) {
        System.out.printf("Solution for the first riddle: %s%n", one(input()));
        System.out.printf("Solution for the second riddle: %s%n", two(testInput()));
    }

    private static Object one(final List<SensorBeacon> input) {
        final var maxLeftCoordinate = input.stream().map(SensorBeacon::mostLeft).mapToInt(Point::x).min().getAsInt();
        final var maxRightCoordinate = input.stream().map(SensorBeacon::mostRight).mapToInt(Point::x).max().getAsInt();

        final Function<SensorBeacon, Predicate<Point>> mapper = sb -> {
            final Predicate<Point> a = not(sb.beacon()::equals);
            final Predicate<Point> b = sb::withinDistance;
            return a.and(b);
        };

        final Predicate<Point> isCovered = input.stream().map(mapper).reduce(Predicate::or).get();

        return new Point(maxLeftCoordinate, SCAN_Y_LEVEL).interpolate(new Point(maxRightCoordinate, SCAN_Y_LEVEL))
                                                         .filter(isCovered)
                                                         .count();
    }


    private static Object two(final List<SensorBeacon> input) {
        final Function<SensorBeacon, Predicate<Point>> mapper = sb -> not(sb::withinDistance);

        final Predicate<Point> isNotCovered = input.stream().map(mapper).reduce(Predicate::and).get();

        final Point.PointSummary pointSummary = input.stream().map(SensorBeacon::sensor).collect(Point.pointSummary());
        System.out.println(pointSummary);

        final Point distressSignal = input.stream()
                                          .flatMap(SensorBeacon::streamPerimeter)
                                          .distinct()
                                          .peek(System.out::println)
                                          .parallel()
                                          .filter(isNotCovered)
                                          .findAny()
                                          .get();
        return distressSignal.x() * 4000000 + distressSignal.y();
    }

    private static List<SensorBeacon> input() {
        return Arrays.stream("""
                Sensor at x=3291456, y=3143280: closest beacon is at x=3008934, y=2768339
                Sensor at x=3807352, y=3409566: closest beacon is at x=3730410, y=3774311
                Sensor at x=1953670, y=1674873: closest beacon is at x=2528182, y=2000000
                Sensor at x=2820269, y=2810878: closest beacon is at x=2796608, y=2942369
                Sensor at x=3773264, y=3992829: closest beacon is at x=3730410, y=3774311
                Sensor at x=2913793, y=2629579: closest beacon is at x=3008934, y=2768339
                Sensor at x=1224826, y=2484735: closest beacon is at x=2528182, y=2000000
                Sensor at x=1866102, y=3047750: closest beacon is at x=1809319, y=3712572
                Sensor at x=3123635, y=118421: closest beacon is at x=1453587, y=-207584
                Sensor at x=2530789, y=2254773: closest beacon is at x=2528182, y=2000000
                Sensor at x=230755, y=3415342: closest beacon is at x=1809319, y=3712572
                Sensor at x=846048, y=51145: closest beacon is at x=1453587, y=-207584
                Sensor at x=3505756, y=3999126: closest beacon is at x=3730410, y=3774311
                Sensor at x=2506301, y=3745758: closest beacon is at x=1809319, y=3712572
                Sensor at x=1389843, y=957209: closest beacon is at x=1453587, y=-207584
                Sensor at x=3226352, y=3670258: closest beacon is at x=3730410, y=3774311
                Sensor at x=3902053, y=3680654: closest beacon is at x=3730410, y=3774311
                Sensor at x=2573020, y=3217129: closest beacon is at x=2796608, y=2942369
                Sensor at x=3976945, y=3871511: closest beacon is at x=3730410, y=3774311
                Sensor at x=107050, y=209321: closest beacon is at x=1453587, y=-207584
                Sensor at x=3931251, y=1787536: closest beacon is at x=2528182, y=2000000
                Sensor at x=1637093, y=3976664: closest beacon is at x=1809319, y=3712572
                Sensor at x=2881987, y=1923522: closest beacon is at x=2528182, y=2000000
                Sensor at x=3059723, y=2540501: closest beacon is at x=3008934, y=2768339
                """.stripIndent().split("\n")).map(SensorBeacon::new).toList();
    }

    private static List<SensorBeacon> testInput() {
        return Arrays.stream("""
                Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                Sensor at x=9, y=16: closest beacon is at x=10, y=16
                Sensor at x=13, y=2: closest beacon is at x=15, y=3
                Sensor at x=12, y=14: closest beacon is at x=10, y=16
                Sensor at x=10, y=20: closest beacon is at x=10, y=16
                Sensor at x=14, y=17: closest beacon is at x=10, y=16
                Sensor at x=8, y=7: closest beacon is at x=2, y=10
                Sensor at x=2, y=0: closest beacon is at x=2, y=10
                Sensor at x=0, y=11: closest beacon is at x=2, y=10
                Sensor at x=20, y=14: closest beacon is at x=25, y=17
                Sensor at x=17, y=20: closest beacon is at x=21, y=22
                Sensor at x=16, y=7: closest beacon is at x=15, y=3
                Sensor at x=14, y=3: closest beacon is at x=15, y=3
                Sensor at x=20, y=1: closest beacon is at x=15, y=3
                """.stripIndent().split("\n")).map(SensorBeacon::new).toList();
    }

    record SensorBeacon(Point sensor, Point beacon) {
        // Sensor at x=20, y=1: closest beacon is at x=15, y=3
        static final Pattern pattern = Pattern.compile(
                "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
        private static final Parser<Integer> parser = new Parser<>(pattern, Integer::parseInt);

        public SensorBeacon(final String line) {
            this(parser.parse(line));
        }

        public SensorBeacon(final List<Integer> parse) {
            this(new Point(parse.get(0), parse.get(1)), new Point(parse.get(2), parse.get(3)));
        }

        public boolean withinDistance(final Point p) {
            final int maxDistance = sensor.manhattenDistance(beacon);

            return sensor.manhattenDistance(p) <= maxDistance;
        }

        public Point mostRight() {
            final int manhattenDistance = sensor.manhattenDistance(beacon);
            return new Point(sensor.x() + manhattenDistance, sensor.y());
        }

        public Point mostLeft() {
            final int manhattenDistance = sensor.manhattenDistance(beacon);
            return new Point(sensor.x() - manhattenDistance, sensor.y());
        }

        public Stream<Point> streamPerimeter() {
            final int manhattenDistance = sensor.manhattenDistance(beacon) + 2;
            return IntStream.range(0, manhattenDistance).boxed().flatMap(currentY -> Stream.of(
                    new Point(sensor.x() - (manhattenDistance - currentY), sensor.y() - currentY),
                    new Point(sensor.x() - (manhattenDistance - currentY), sensor.y() + currentY),
                    new Point(sensor.x() + (manhattenDistance - currentY), sensor.y() - currentY),
                    new Point(sensor.x() + (manhattenDistance - currentY), sensor.y() + currentY)
            )).distinct();
        }
    }
}

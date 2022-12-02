package AOC2021;


import io.vavr.collection.List;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;

public class Day19 {

    public static final Pattern SCANNER_PATTERN = Pattern.compile("--- scanner (\\d+) ---");

    protected String a(final String[] input) throws Exception {
        final List<Scanner> scanners = getScanners(input);
        return "" + null;
    }

    public List<Scanner> getScanners(final String[] input) {
        List<Scanner> scanners = List.empty();
        List<Beacon> currentBeacons = List.empty();
        for (final String s : input) {
            if (s.isBlank()) continue;

            final Matcher matcher = SCANNER_PATTERN.matcher(s);
            if (matcher.find()) {
                if (!currentBeacons.isEmpty()) {
                    final List<NeighborAwareBeacon> neighborAwareBeacons = getNeighborAwareBeacons(currentBeacons);
                    final Scanner scanner = new Scanner(neighborAwareBeacons);
                    scanners = scanners.append(scanner);
                }
                currentBeacons = List.empty();
                continue;
            }

            currentBeacons = currentBeacons.append(new Beacon(s));
        }
        return scanners;
    }

    private static List<NeighborAwareBeacon> getNeighborAwareBeacons(final List<Beacon> currentBeacons) {
        return currentBeacons.map(b -> new NeighborAwareBeacon(b, currentBeacons));
    }

    protected String b(final String[] input) throws Exception {
        return "" + null;
    }

    record Scanner(List<NeighborAwareBeacon> beacons) {
        public int overlaps(final Scanner other) {
            //return this.beacons.map(neighborAwareBeacon -> other.beacons.map(neighborAwareBeacon::overlap).max()).max();
            return 0;
        }
    }

    record NeighborAwareBeacon(List<Beacon> neighbors) {
        NeighborAwareBeacon(final Beacon self, final List<Beacon> neighbors) {
            this(neighbors.map(self::relative).filter(not(new Beacon(0, 0, 0)::equals)));
        }

        public List<NeighborAwareBeacon> rotations() {
            return List.transpose(this.neighbors.map(Beacon::rotations)).map(NeighborAwareBeacon::new);
        }

        public int overlap(final NeighborAwareBeacon other) {
            return this.rotations()
                       .map(NeighborAwareBeacon::neighbors)
                       .map(rotation -> other.neighbors.count(rotation::contains))
                       .max()
                       .get();
        }
    }

    record Beacon(int x, int y, int z) implements Comparable<Beacon> {

        public static final Comparator<Beacon> BEACON_COMPARATOR = Comparator.comparingInt(Beacon::x)
                                                                             .thenComparingInt(Beacon::y)
                                                                             .thenComparingInt(Beacon::z);

        public Beacon(final String s) {
            this(Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray());
        }

        public Beacon(final int[] ints) {
            this(ints[0], ints[1], ints[2]);
        }

        public Beacon relative(final Beacon other) {
            return new Beacon(x - other.x, y - other.y, z - other.z);
        }

        public boolean relativeEqual(final Beacon other) {
            return rotations().contains(other);
        }

        public Set<Beacon> rotations() {
            //noinspection SuspiciousNameCombination
            return Set.of(
                    new Beacon(x, y, z),
                    new Beacon(x, -y, -z),
                    new Beacon(x, z, -y),
                    new Beacon(x, -z, y),
                    new Beacon(y, x, -z),
                    new Beacon(y, -x, z),
                    new Beacon(y, z, x),
                    new Beacon(y, -z, -x),
                    new Beacon(z, y, -x),
                    new Beacon(z, -y, x),
                    new Beacon(z, x, y),
                    new Beacon(z, -x, -y),
                    new Beacon(-x, y, -z),
                    new Beacon(-x, -y, z),
                    new Beacon(-x, z, y),
                    new Beacon(-x, -z, -y),
                    new Beacon(-y, x, z),
                    new Beacon(-y, -x, -z),
                    new Beacon(-y, z, -x),
                    new Beacon(-y, -z, x),
                    new Beacon(-z, x, -y),
                    new Beacon(-z, -x, y),
                    new Beacon(-z, y, x),
                    new Beacon(-z, -y, -x)
            );
        }

        private Beacon rotateZ() {
            //noinspection SuspiciousNameCombination
            return new Beacon(y, -x, z);
        }

        private Beacon rotateY() {
            return new Beacon(z, y, -x);
        }

        private Beacon rotateX() {
            return new Beacon(x, z, -y);
        }

        @Override
        public int compareTo(final Beacon o) {
            return BEACON_COMPARATOR.compare(this, o);
        }
    }
}

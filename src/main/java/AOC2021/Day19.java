package AOC2021;


import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;

import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    final Scanner scanner = new Scanner(null);//currentBeacons);
                    scanners = scanners.append(scanner);
                }
                currentBeacons = List.empty();
                continue;
            }

            currentBeacons = currentBeacons.append(new Beacon(s));
        }
        return scanners;
    }

    protected String b(final String[] input) throws Exception {
        return "" + null;
    }

    record Scanner(Set<NeighborAwareBeacon> beacons) {
        public int overlaps(final Scanner other) {
            //return this.beacons.map(neighborAwareBeacon -> other.beacons.map(neighborAwareBeacon::overlap).max()).max();
            return 0;
        }
    }

    record NeighborAwareBeacon(Beacon beacon, Set<Relation> neighbors) {
        public static Set<NeighborAwareBeacon> fromScanner(final Set<Beacon> beacons) {
            return beacons.map(singleBeacon -> fromScanner(singleBeacon, beacons));
        }

        private static NeighborAwareBeacon fromScanner(final Beacon singleBeacon, final Set<Beacon> beacons) {
            final Set<Relation> relations = beacons.map(otherBeacon -> new Relation(singleBeacon, otherBeacon));
            return new NeighborAwareBeacon(singleBeacon, relations);
        }
    }

    record Relation(int x, int y, int z) implements Comparable<Relation> {

        public static final Comparator<Relation> RELATION_COMPARATOR = Comparator.comparingInt(Relation::x)
                                                                                 .thenComparingInt(Relation::y)
                                                                                 .thenComparingInt(Relation::z);

        public Relation(final Beacon from, final Beacon to) {
            this(from.x - to.x, from.y - to.y, from.z - to.z);
        }

        public boolean relativeEqual(final Relation other) {
            return rotations().contains(other);
        }

        public Set<Relation> rotations() {
            //noinspection SuspiciousNameCombination
            return HashSet.of(
                    new Relation(x, y, z),
                    new Relation(x, -y, -z),
                    new Relation(x, z, -y),
                    new Relation(x, -z, y),
                    new Relation(y, x, -z),
                    new Relation(y, -x, z),
                    new Relation(y, z, x),
                    new Relation(y, -z, -x),
                    new Relation(z, y, -x),
                    new Relation(z, -y, x),
                    new Relation(z, x, y),
                    new Relation(z, -x, -y),
                    new Relation(-x, y, -z),
                    new Relation(-x, -y, z),
                    new Relation(-x, z, y),
                    new Relation(-x, -z, -y),
                    new Relation(-y, x, z),
                    new Relation(-y, -x, -z),
                    new Relation(-y, z, -x),
                    new Relation(-y, -z, x),
                    new Relation(-z, x, -y),
                    new Relation(-z, -x, y),
                    new Relation(-z, y, x),
                    new Relation(-z, -y, -x)
            );
        }

        public Relation normalize() {
            return rotations().toSortedSet().head();
        }

        private Relation rotateZ() {
            //noinspection SuspiciousNameCombination
            return new Relation(y, -x, z);
        }

        private Relation rotateY() {
            return new Relation(z, y, -x);
        }

        private Relation rotateX() {
            return new Relation(x, z, -y);
        }

        @Override
        public int compareTo(final Relation o) {
            return RELATION_COMPARATOR.compare(this, o);
        }
    }

    record Beacon(int x, int y, int z) {

        public Beacon(final String s) {
            this(Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray());
        }

        public Beacon(final int[] ints) {
            this(ints[0], ints[1], ints[2]);
        }

        public Beacon relative(final Beacon other) {
            return new Beacon(x - other.x, y - other.y, z - other.z);
        }

    }
}

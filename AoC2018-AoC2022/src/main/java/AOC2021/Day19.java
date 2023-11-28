package AOC2021;


import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;

import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {

    public static final Pattern SCANNER_PATTERN = Pattern.compile("--- scanner (\\d+) ---");

    protected String a(final String[] input) throws Exception {
        // GIVEN
        final List<Day19.Scanner> scanners = getScanners(input);

        // WHEN
        var result = Scanner.combine(scanners).beacons.size();
        return "" + result;
    }

    protected String b(final String[] input) throws Exception {
        // GIVEN
        final List<Day19.Scanner> scanners = getScanners(input);

        // WHEN
        var result = Scanner.combine(scanners);

        final Integer max = scanners.map(result::searchOverlap)
                                    .map(o -> o.getOrElseThrow(IllegalArgumentException::new))
                                    .map(AlignedScanner::position)
                                    .crossProduct()
                                    .map(Function2.of(Beacon::manhatten).tupled())
                                    .max().get();

        return "" + max;
    }

    public List<Scanner> getScanners(final String[] input) {
        List<Scanner> scanners = List.empty();
        HashSet<Beacon> currentBeacons = HashSet.empty();
        for (final String s : input) {
            if (s.isBlank()) continue;

            final Matcher matcher = SCANNER_PATTERN.matcher(s);
            if (matcher.find()) {
                if (!currentBeacons.isEmpty()) {
                    final Scanner scanner = new Scanner(currentBeacons);
                    scanners = scanners.append(scanner);
                }
                currentBeacons = HashSet.empty();
                continue;
            }

            currentBeacons = currentBeacons.add(new Beacon(s));
        }
        scanners = scanners.append(new Scanner(currentBeacons));
        return scanners;
    }

    public record Scanner(Set<Beacon> beacons) {

        public static Scanner combine(List<Scanner> scanners) {
            Scanner sum = scanners.head();
            scanners = scanners.tail();

            while (!scanners.isEmpty()) {
                for (Scanner s : scanners) {
                    var newSum = sum.add(s);
                    if (!newSum.isEmpty()) {
                        sum = newSum.get();
                        scanners = scanners.remove(s);
                    }
                }
            }

            return sum;
        }

        private Option<Scanner> add(Scanner other) {
            return searchOverlap(other).map(a -> a.owned.union(this.beacons)).map(Scanner::new);
        }

        public Option<AlignedScanner> searchOverlap(Scanner other) {
            List<Scanner> rotations = other.rotations();
            for (Scanner otherScanner : rotations) {
                if (this.equals(otherScanner))
                    return Option.some(new AlignedScanner(new Beacon(0, 0, 0), this.beacons, HashSet.empty()));
                final var distances = getDistances(otherScanner);
                final var mostCommonDistance = distances.filterValues(t -> t.size() > 11).maxBy(t -> t._2.size());

                if (!mostCommonDistance.isEmpty())
                    return mostCommonDistance.map(t -> mapToAlignedScanner(t._1, t._2, otherScanner));
            }

            return Option.none();
        }

        private AlignedScanner mapToAlignedScanner(Beacon shiftVector, Set<Tuple2<Beacon, Beacon>> sameBeacons, Scanner rotatedScanner) {
            final Set<Beacon> sharedBeacons = sameBeacons.map(Tuple2::_1);
            final Set<Beacon> owned = rotatedScanner.beacons().map(shiftVector::shift).removeAll(sharedBeacons);
            // System.out.printf(
            //         "shiftVector: %s, sharedBeacons: %s, owned: %s%n",
            //         shiftVector,
            //         sharedBeacons.size(),
            //         owned.size()
            // );
            return new AlignedScanner(shiftVector, sharedBeacons, owned);
        }

        private Map<Beacon, Set<Tuple2<Beacon, Beacon>>> getDistances(Scanner otherScanner) {
            return this.beacons()
                       .toList()
                       .crossProduct(otherScanner.beacons())
                       .groupBy(Function2.of(Beacon::difference).tupled())
                       .mapValues(HashSet::ofAll);
        }

        public List<Scanner> rotations() {
            var rotatedBeacons = this.beacons.map(Beacon::rotations);
            var numRotations = rotatedBeacons.head().size();

            return List.range(0, numRotations).map(i -> rotatedBeacons.map(l -> l.get(i))).map(Scanner::new);
        }


        public Scanner combineWith(Scanner other) {
            var aligned = searchOverlap(other).map(AlignedScanner::owned).getOrElse(HashSet.empty());
            return new Scanner(beacons.union(aligned));
        }
    }

    public record AlignedScanner(Beacon position, Set<Beacon> overlapped, Set<Beacon> owned) {
    }

    public record Beacon(int x, int y, int z) implements Comparable<Beacon> {

        public Beacon(final String s) {
            this(Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray());
        }

        public Beacon(final int[] ints) {
            this(ints[0], ints[1], ints[2]);
        }

        public Beacon difference(final Beacon other) {
            return new Beacon(x - other.x, y - other.y, z - other.z);
        }

        public int manhatten(final Beacon other) {
            return Math.abs(x() - other.x()) + Math.abs(y() - other.y()) + Math.abs(z() - other.z());
        }

        public List<Beacon> rotations() {
            //noinspection SuspiciousNameCombination
            return List.of(
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

        public boolean rotationalEqual(Beacon other) {
            return rotations().exists(other::equals);
        }

        @Override
        public String toString() {
            return "%d,%d,%d".formatted(x, y, z);
        }

        public Beacon shift(Beacon other) {
            return new Beacon(x + other.x, y + other.y, z + other.z);
        }

        @Override
        public int compareTo(Beacon o) {
            return Comparator.comparingInt(Beacon::x)
                             .thenComparingInt(Beacon::y)
                             .thenComparingInt(Beacon::z)
                             .compare(this, o);
        }

        public Beacon negate() {
            return new Beacon(-x, -y, -z);
        }
    }
}

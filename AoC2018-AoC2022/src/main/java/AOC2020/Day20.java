package AOC2020;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import one.util.streamex.StreamEx;
import utils.AbstractDay;
import utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class Day20 extends AbstractDay<List<Day20.Tile>> {
    public static void main(final String[] args) {
        new Day20().run();
    }

    @Override
    protected List<Tile> parseInput(String[] rawInput) throws Exception {
        List<Tile> tiles = new ArrayList<>();
        List<String> block = new ArrayList<>();
        Set<Tile> allTiles = new HashSet<>();

        for (String line : rawInput) {
            if (line.isBlank()) {
                tiles.add(new Tile(block, allTiles));
                block = new ArrayList<>();
                continue;
            }

            block.add(line);
        }

        if (!block.isEmpty()) {
            tiles.add(new Tile(block, allTiles));
        }

        return tiles;
    }

    @Override
    protected Object a(List<Tile> input) throws Exception {
        return StreamEx.of(input)
                       .mapToEntry(Tile::getNeighbors)
                       .mapValues(Set::stream)
                       .mapValues(s -> s.filter(Objects::nonNull).count())
                       .filterValues(i -> i == 2)
                       .keys()
                       .mapToLong(Tile::getId)
                       .reduce((a, b) -> a * b)
                       .getAsLong();
    }

    @Override
    protected Object b(List<Tile> input) throws Exception {
        final int dim = Math.toIntExact(Math.round(Math.sqrt(input.size())));

        Tile corner = StreamEx.of(input)
                              .mapToEntry(Tile::getNeighbors)
                              .filterValues(neighbors -> neighbors.size() == 2)
                              .keys()
                              .findFirst()
                              .get();

        corner.flipX();
        while (corner.north != null || corner.west != null) {
            corner.rotate();
        }

        Map<Point, Tile> tiles = layoutTiles(dim, corner);

        final Set<Point> image = getImage(tiles, dim);
        //print(null, image);

        final Set<Point> seaMonsters = searchMonsters(image);

        //print(seaMonsters, image);

        return image.stream()
                      .filter(not(seaMonsters::contains))
                      .count();
    }

    private static void print(Set<Point> seaMonsters, Set<Point> flipped) {
        final IntSummaryStatistics yStat = flipped.stream().mapToInt(Point::getY).summaryStatistics();
        final IntSummaryStatistics xStat = flipped.stream().mapToInt(Point::getX).summaryStatistics();

        for (int y = yStat.getMin(); y < yStat.getMax(); y++) {
            for (int x = xStat.getMin(); x < xStat.getMax(); x++) {
                if (seaMonsters != null && seaMonsters.contains(new Point(x, y))) {
                    System.out.print("O");
                    continue;
                }
                if (flipped.contains(new Point(x, y))) {
                    System.out.print("#");
                    continue;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private Set<Point> flip(Set<Point> image, Function<Point, Integer> alterX, Function<Point, Integer> alterY) {
        return image.stream()
                    .map(p -> p.withX(alterX.apply(p)).withY(alterY.apply(p)))
                    .collect(Collectors.toSet());
    }

    private Set<Point> searchMonsters(Set<Point> image) {
        Set<Point> monsters = new HashSet<>(image.size());

        Set<Point> monster = Set.of(
                new Point(0, 0),
                new Point(5, 0),
                new Point(6, 0),
                new Point(11, 0),
                new Point(12, 0),
                new Point(17, 0),
                new Point(18, 0),
                new Point(19, 0),
                new Point(1, 1),
                new Point(4, 1),
                new Point(7, 1),
                new Point(10, 1),
                new Point(13, 1),
                new Point(16, 1),
                new Point(18, -1)
        );

        IntUnaryOperator invert = i -> -i;

        final List<Set<Point>> allRotations = List.of(
                monster,
                flip(monster, Point::getX, point -> -point.getY()),
                flip(monster, point -> -point.getX(), Point::getY),
                flip(monster, Point::getY, Point::getX),
                flip(monster, point -> -point.getY(), Point::getX),
                flip(monster, Point::getY, point -> -point.getX())
        );

        //print(null, flip(monster, i->i, i->i+1));

        for (Set<Point> rotatedMonster : allRotations) {
            for (Point p : image) {
                final Set<Point> possibleMonster = rotatedMonster.stream()
                                                                 .map(p::add)
                                                                 .collect(Collectors.toSet());

                if (image.containsAll(possibleMonster)) {
                    monsters.addAll(possibleMonster);
                }
            }
            if (!monsters.isEmpty()) break;
        }

        return monsters;
    }

    private Set<Point> getImage(Map<Point, Tile> tiles, int dim) {
        Set<Point> image = new HashSet<>(tiles.size());
        for (int y = 0; y < dim; y++) {
            for (int lineY = 1; lineY < 9; lineY++) {
                for (int x = 0; x < dim; x++) {
                    Tile t = tiles.get(new Point(x, y));

                    for (int lineX = 1; lineX < 9; lineX++) {
                        if (t.bits[lineY][lineX])
                            image.add(new Point(lineX - 1 + x * 8, lineY - 1 + y * 8));
                    }
                }
            }
        }

        // for (int y = 0; y < 24; y++) {
        //     for (int x = 0; x < 24; x++) {
        //         if (image.contains(new Point(x, y))) System.out.print("#");
        //         else System.out.print(".");
        //
        //         if (x % 8 == 7) System.out.print(" ");
        //     }
        //     if (y % 8 == 7) System.out.println();
        //     System.out.println();
        // }

        return image;
    }

    private Map<Point, Tile> layoutTiles(int dim, Tile corner) {
        Map<Point, Tile> tiles = new HashMap<>();
        tiles.put(new Point(0, 0), corner);
        for (int y = 0; y < dim; y++) {
            for (int x = 0; x < dim; x++) {
                if (x == 0 && y == 0) continue;

                if (y == 0) {
                    final Tile last = tiles.get(new Point(x - 1, y));
                    if (last == null) continue;

                    final Tile next = last.east;
                    while (next.north != null) next.rotate();
                    if (next.west == null || last != next.west) next.flipX();

                    tiles.put(new Point(x, y), next);
                } else if (x == 0) {
                    final Tile last = tiles.get(new Point(x, y - 1));
                    if (last == null) continue;

                    final Tile next = last.south;
                    while (next.west != null) next.rotate();
                    if (next.north == null || last != next.north) next.flipY();

                    tiles.put(new Point(x, y), next);
                } else {
                    final Tile west = tiles.get(new Point(x - 1, y));
                    final Tile north = tiles.get(new Point(x, y - 1));
                    final Tile next = west.east;

                    while (next.west != west) next.rotate();
                    if (next.north != north) {
                        next.flipY();
                    }

                    tiles.put(new Point(x, y), next);
                }
            }
        }

        return tiles;
    }

    @ToString(exclude = {"north", "south", "west", "east", "bits"})
    @EqualsAndHashCode(exclude = {"north", "south", "west", "east"})
    public static class Tile {
        static final HashMap<Integer, Integer> reverseCache = new HashMap<>();

        final int id;
        private int n, s, w, e;
        Tile north, south, west, east;
        Boolean[][] bits;

        public Tile(List<String> input, Set<Tile> otherTiles) {
            id = Integer.parseInt(input.get(0).substring(5, input.get(0).length() - 1));
            bits = input.stream().skip(1)
                        .map(s ->
                                     Stream.of(s.split(""))
                                           .map(c -> c.equals("#"))
                                           .toArray(Boolean[]::new)
                        )
                        .toArray(Boolean[][]::new);

            StringBuilder eString = new StringBuilder();
            StringBuilder wString = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                wString.append(input.get(i + 1).charAt(0) == '#' ? 1 : 0);
                eString.append(input.get(i + 1).charAt(9) == '#' ? 1 : 0);
            }

            n = Integer.parseInt(input.get(1).replace('#', '1').replace('.', '0'), 2);
            s = Integer.parseInt(input.get(10).replace('#', '1').replace('.', '0'), 2);
            w = Integer.parseInt(wString.toString(), 2);
            e = Integer.parseInt(eString.toString(), 2);

            searchCommonEdge(otherTiles);
            otherTiles.add(this);
        }

        private void searchCommonEdge(Set<Tile> otherTiles) {
            int[] thisEdges = new int[]{n, s, w, e};
            List<Consumer<Tile>> thisSetters = List.of(
                    t -> north = t,
                    t -> south = t,
                    t -> west = t,
                    t -> east = t
            );
            tilesIterator:
            for (Tile other : otherTiles) {
                int[] otherEdges = new int[]{other.n, other.s, other.w, other.e};
                List<Consumer<Tile>> otherSetters = List.of(
                        t -> other.north = t,
                        t -> other.south = t,
                        t -> other.west = t,
                        t -> other.east = t
                );
                for (int thisIndex = 0; thisIndex < 4; thisIndex++) {
                    for (int otherIndex = 0; otherIndex < 4; otherIndex++) {
                        if (thisEdges[thisIndex] == otherEdges[otherIndex] || reverseEdge(thisEdges[thisIndex]) == otherEdges[otherIndex]) {
                            thisSetters.get(thisIndex).accept(other);
                            otherSetters.get(otherIndex).accept(this);
                            continue tilesIterator;
                        }
                    }
                }
            }
        }

        private int reverseEdge(int edge) {
            return reverseCache.computeIfAbsent(edge, i -> {
                final String bin = Integer.toString(i, 2);
                return Integer.parseInt(new StringBuilder("0".repeat(10 - bin.length()) + bin).reverse().toString(), 2);
            });
        }

        public int getId() {
            return id;
        }

        public Set<Tile> getNeighbors() {
            return Stream.of(
                                 Optional.ofNullable(north),
                                 Optional.ofNullable(east),
                                 Optional.ofNullable(south),
                                 Optional.ofNullable(west)
                         )
                         .filter(Optional::isPresent)
                         .map(Optional::get)
                         .collect(Collectors.toSet());
        }

        public void rotate() {
            int tmpInt = n;
            n = e;
            e = s;
            s = w;
            w = tmpInt;

            //Tile tmpTile = north;
            //north = east;
            //east = south;
            //south = west;
            //west = tmpTile;

            Tile tmpTile = north;
            north = west;
            west = south;
            south = east;
            east = tmpTile;

            Boolean[][] newBits = new Boolean[10][10];
            for (int i = 0; i < 10; ++i) {
                for (int j = 0; j < 10; ++j) {
                    newBits[i][j] = bits[10 - j - 1][i];
                }
            }
            bits = newBits;
        }

        public void flipX() {
            int tmpInt = w;
            w = e;
            e = tmpInt;

            Tile tmpTile = west;
            west = east;
            east = tmpTile;

            Boolean[][] newBits = new Boolean[10][10];
            for (int i = 0; i < 10; ++i) {
                for (int j = 0; j < 10; ++j) {
                    newBits[i][j] = bits[i][10 - j - 1];
                }
            }
            bits = newBits;
        }

        public void flipY() {
            int tmpInt = n;
            n = s;
            s = tmpInt;

            Tile tmpTile = north;
            north = south;
            south = tmpTile;

            Boolean[][] newBits = new Boolean[10][10];
            for (int i = 0; i < 10; ++i) {
                for (int j = 0; j < 10; ++j) {
                    newBits[i][j] = bits[10 - i - 1][j];
                }
            }
            bits = newBits;
        }
    }
}

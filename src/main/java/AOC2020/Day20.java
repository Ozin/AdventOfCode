package AOC2020;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Tile corner = StreamEx.of(input)
                .mapToEntry(Tile::getNeighbors)
                .filterValues(neighbors -> neighbors.size() == 2)
                .keys()
                .findFirst()
                .get();

        return null;
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

            Tile tmpTile = north;
            north = east;
            east = south;
            south = west;
            west = tmpTile;

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

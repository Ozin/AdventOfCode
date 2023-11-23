package AOC2020;

import lombok.Value;
import lombok.With;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24 extends AbstractDay<Day24.Direction[][]> {
    public static void main(final String[] args) {
        new Day24().run();
    }

    @Override
    protected Direction[][] parseInput(final String[] rawInput) throws Exception {
        final Pattern directionPattern = Pattern.compile("(e|ne|se|w|nw|sw)");
        return StreamEx.of(rawInput)
            .map(directionPattern::matcher)
            .map(matcher ->
                matcher.results()
                    .map(MatchResult::group)
                    .map(String::toUpperCase)
                    .map(Direction::valueOf)
                    .toArray(Direction[]::new))
            .toArray(Direction[][]::new);
    }

    @Override
    protected Object a(final Direction[][] input) throws Exception {
        return runSimpleDay(input).size();
    }

    private Set<Tile> runSimpleDay(final Direction[][] input) {
        final Set<Tile> blackTiles = new HashSet<>();

        for (final Direction[] directions : input) {
            final Tile nextTile = new Tile(0, 0, 0).move(directions);

            if (blackTiles.contains(nextTile)) {
                blackTiles.remove(nextTile);
            } else {
                blackTiles.add(nextTile);
            }
        }

        return blackTiles;
    }

    @Override
    protected Object b(final Direction[][] input) throws Exception {
        Set<Tile> tiles = new HashSet<>(runSimpleDay(input));

        for (int i = 0; i < 100; i++) {
            tiles = runDay(input, tiles);
            System.out.printf("%3d %d%n", i, tiles.size());
        }

        return tiles.size();
    }

    private Set<Tile> runDay(final Direction[][] input, final Set<Tile> blackTiles) {
        return StreamEx.of(blackTiles)
            .map(Tile::neighbours)
            .flatMap(Collection::stream)
            .append(blackTiles)
            .distinct()
            .mapToEntry(Function.identity(), Tile::neighbours)
            .mapValues(neighbours -> neighbours.stream().filter(blackTiles::contains).count())
            .mapValues(Math::toIntExact)
            .filterKeyValue((tile, neighbourCount) -> neighbourCount == 2 || (blackTiles.contains(tile) && neighbourCount == 1))
            .keys()
            .toSet();
    }

    protected enum Direction {
        E(1, 1, 0),
        NE(0, 1, 1),
        SE(1, 0, -1),
        W(-1, -1, 0),
        NW(-1, 0, 1),
        SW(0, -1, -1);

        private final int x;
        private final int y;
        private final int z;

        Direction(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    @Value
    @With
    private static class Tile {
        int x, y, z;

        public Tile move(final Direction direction) {
            return this.withX(x + direction.x)
                .withY(y + direction.y)
                .withZ(z + direction.z);
        }

        public Tile move(final Direction[] directions) {
            Tile tile = this;
            for (final Direction direction : directions) {
                tile = tile.move(direction);
            }
            return tile;
        }

        public List<Tile> neighbours() {
            return Stream.of(Direction.values())
                .map(this::move)
                .collect(Collectors.toList());
        }
    }
}

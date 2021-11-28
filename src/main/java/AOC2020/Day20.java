package AOC2020;

import lombok.ToString;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Day20 extends AbstractDay<List<Day20.Tile>> {
    public static void main(final String[] args) {
        new Day20().run();
    }

    @Override
    protected List<Tile> parseInput(String[] rawInput) throws Exception {
        List<Tile> tiles = new ArrayList<>();
        List<String> block = new ArrayList<>();

        for (String line : rawInput) {
            if (line.isBlank()) {
                tiles.add(new Tile(block));
                block = new ArrayList<>();
                continue;
            }

            block.add(line);
        }

        if (!block.isEmpty()) {
            tiles.add(new Tile(block));
        }

        return tiles;
    }

    @Override
    protected Object a(List<Tile> input) throws Exception {
        return StreamEx.of(input)
                .cross(input)
                .filterKeyValue((k, v) -> k != v)
                .filterKeyValue(Tile::hasCommonEdge)
                .mapKeys(Tile::getId)
                .keys()
                .sorted()
                .runLengths()
                .filterValues(i -> i == 2)
                .keys()
                .map(Long::valueOf)
                .reduce((a, b) -> a * b)
                .get();
    }

    @Override
    protected Object b(List<Tile> input) throws Exception {
        return null;
    }

    @ToString
    public static class Tile {
        static final HashMap<Integer, Integer> reverseCache = new HashMap<>();

        final int id, north, south, west, east;
        final Boolean[][] bits;
        boolean xFlipped = false;
        boolean yFlipped = false;

        public Tile(List<String> input) {
            id = Integer.parseInt(input.get(0).substring(5, input.get(0).length() - 1));
            bits = input.stream().skip(1)
                    .map(s ->
                            Stream.of(s.split(""))
                                    .map(c -> c.equals("#"))
                                    .toArray(Boolean[]::new)
                    )
                    .toArray(Boolean[][]::new);

            StringBuilder e = new StringBuilder();
            StringBuilder w = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                w.append(input.get(i + 1).charAt(0) == '#' ? 1 : 0);
                e.append(input.get(i + 1).charAt(9) == '#' ? 1 : 0);
            }

            north = Integer.parseInt(input.get(1).replace('#', '1').replace('.', '0'), 2);
            south = Integer.parseInt(input.get(10).replace('#', '1').replace('.', '0'), 2);
            west = Integer.parseInt(w.toString(), 2);
            east = Integer.parseInt(e.toString(), 2);
        }

        private int reverseEdge(int edge) {
            return reverseCache.computeIfAbsent(edge, i -> {
                final String bin = Integer.toString(i, 2);
                return Integer.parseInt(new StringBuilder("0".repeat(10 - bin.length()) + bin).reverse().toString(), 2);
            });
        }

        public List<Integer> possibleEdges() {
            return List.of(
                    north,
                    east,
                    south,
                    west,
                    reverseEdge(north),
                    reverseEdge(east),
                    reverseEdge(south),
                    reverseEdge(west)
            );
        }

        public boolean hasCommonEdge(Tile other) {
            List<Integer> tmp = new ArrayList<>(possibleEdges());
            tmp.retainAll(other.possibleEdges());
            return tmp.size() > 0;
        }

        public int getId() {
            return id;
        }
/*
        public void flipX() {
            xFlipped = !xFlipped;
        }

        public void flipY() {
            yFlipped = !yFlipped;
        }

        public int getNorth() {
            return xFlipped ? reverseEdge(north) : north;
        }

        public int getSouth() {
            return xFlipped ? reverseEdge(south) : south;
        }

        public int getWest() {
            return xFlipped ? east : west;
        }

        public int getEast() {
            return east;
        }*/
    }
}

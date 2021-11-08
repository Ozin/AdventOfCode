package AOC2020;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        return null;
    }

    @Override
    protected Object b(List<Tile> input) throws Exception {
        return null;
    }

    @Getter
    public static class Tile {
        final int id;
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
        }

        public void flipX() {
            xFlipped = !xFlipped;
        }

        public void flipY() {
            yFlipped = !yFlipped;
        }

        public List<Boolean> getWestEdge() {
            return IntStream.range(0, 10)
                    .mapToObj(i -> yFlipped ? bits[9 - i][0] : bits[i][0])
                    .collect(Collectors.toList());
        }
    }
}

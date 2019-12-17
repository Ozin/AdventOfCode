package adventOfCode2019;

import lombok.Value;

public class IntcodeGame {

    @Value
    public static class Tile {
        int x, y;
        TileType type;
    }

    public enum TileType {
        EMPTY(" "),
        WALL("#"),
        BLOCK("="),
        HORIZONTAL("_"),
        BALL("o");

        private final String representation;

        TileType(final String representation) {
            this.representation = representation;
        }

        @Override
        public String toString() {
            return representation;
        }
    }
}

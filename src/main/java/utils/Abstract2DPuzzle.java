package utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Abstract2DPuzzle extends AbstractDay<Map<utils.Point, Character>> {
    private int maxX = 0;
    private int maxY = 0;

    @Override
    protected Map<Point, Character> parseInput(final String[] rawInput) throws Exception {
        final Map<Point, Character> map = new HashMap<>();
        for (int y = 0; y < rawInput.length; y++) {
            maxY = Math.max(rawInput.length, maxY);
            maxX = Math.max(rawInput[y].length(), maxX);
            for (int x = 0; x < rawInput[y].length(); x++) {
                map.put(new Point(x, y), rawInput[y].charAt(x));
            }
        }
        return map;
    }

    public void printMap(final Map<Point, Character> map) {
        final int maxX = map.keySet().stream().mapToInt(Point::getX).max().orElseThrow();
        final int maxY = map.keySet().stream().mapToInt(Point::getY).max().orElseThrow();
        final int minX = map.keySet().stream().mapToInt(Point::getX).min().orElseThrow();
        final int minY = map.keySet().stream().mapToInt(Point::getY).min().orElseThrow();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                final char value = map.getOrDefault(new Point(x, y), ' ');
                System.out.print(value);
            }
            System.out.println();
        }
    }
}

package AOC2020;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import utils.Point;

@Getter
public abstract class Abstract2DPuzzle extends AbstractDay<Map<Point, Character>> {
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
}

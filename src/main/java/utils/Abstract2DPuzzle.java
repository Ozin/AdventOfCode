package utils;

import java.util.HashMap;
import java.util.Map;

public abstract class Abstract2DPuzzle extends AbstractDay<Map<utils.Point, Character>> {
    @Override
    protected Map<utils.Point, Character> parseInput(final String[] rawInput) throws Exception {
        final Map<utils.Point, Character> map = new HashMap<>();
        for (int y = 0; y < rawInput.length; y++) {
            for (int x = 0; x < rawInput[y].length(); x++) {
                map.put(new Point(x, y), rawInput[y].charAt(x));
            }
        }
        return map;
    }
}

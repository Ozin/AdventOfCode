package AOC2021;


import lombok.Value;
import one.util.streamex.IntStreamEx;
import utils.Point;
import utils.Range;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day17 {

    protected String a(final String[] input) throws Exception {
        final MatchResult results = Pattern.compile("target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)")
                .matcher(input[0])
                .results()
                .findAny()
                .get();
        Range rangeX = new Range(Integer.parseInt(results.group(1)), Integer.parseInt(results.group(2)));
        Range rangeY = new Range(Integer.parseInt(results.group(3)), Integer.parseInt(results.group(4)));

        int yVelocity = -rangeY.getLower() - 1;

        int y = 0;
        while (yVelocity > 0) {
            y += yVelocity;
            yVelocity--;
        }


        return "" + y;
    }

    protected String b(final String[] input) throws Exception {
        final MatchResult results = Pattern.compile("target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)")
                .matcher(input[0])
                .results()
                .findAny()
                .get();
        Range rangeX = new Range(Integer.parseInt(results.group(1)), Integer.parseInt(results.group(2)));
        Range rangeY = new Range(Integer.parseInt(results.group(3)), Integer.parseInt(results.group(4)));

        return "" + IntStreamEx.range(rangeX.getUpper() + 1).boxed()
                .cross(IntStreamEx.range(rangeY.getLower(), -rangeY.getLower() + 1).boxed().toSet())
                .mapKeyValue((x, y) -> new Probe(new Point(0, 0), new Point(x, y)))
                .filter(p -> p.hits(rangeX, rangeY))
                .count();
    }

    @Value
    static class Probe {
        Point position;
        Point velocity;

        public Probe fly() {
            return new Probe(position.add(velocity), velocity.withByX(x -> Math.max(0, x - 1)).addY(-1));
        }

        public boolean hits(Range rangeX, Range rangeY) {
            if (position.getX() > rangeX.getUpper() || position.getY() < rangeY.getLower()) return false;

            if (rangeX.inBetween(position.getX()) && rangeY.inBetween(position.getY())) return true;

            return fly().hits(rangeX, rangeY);
        }

    }

}

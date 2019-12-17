package adventOfCode2018.day03;

import lombok.Value;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day03 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day03.class.getResourceAsStream("/2018/input03a")))) {
            Claim[] claims = br.lines().map(Claim::new).toArray(Claim[]::new);

            a(claims);
            b(claims);
        }
    }

    private static void b(Claim[] claims) {
        Map<Point, Long> usedPoints = StreamEx.of(claims)
                .flatMap(Claim::createClaimedInches)
                .sorted()
                .runLengths()
                .toImmutableMap();

        Claim unoverlappingClaim = StreamEx.of(claims)
                .cross(Claim::createClaimedInches)
                .mapValues(usedPoints::get)
                .collapseKeys(Math::max)
                .filterValues(v -> v <= 1)
                .keys()
                .findFirst()
                .orElse(null);

        System.out.printf("Result of 03 B: %s%n", unoverlappingClaim);
    }

    private static void a(Claim[] claims) {
        long amountDoubleClaimedInches = StreamEx.of(claims)
                .flatMap(Claim::createClaimedInches)
                .sorted()
                .runLengths()
                .filterValues(occurences -> occurences > 1)
                .count();

        System.out.printf("Result of 03 A: %d%n", amountDoubleClaimedInches);
    }

    @Value
    public static class Claim {
        int id, xOffset, yOffset, width, height;

        static Pattern inputPattern = Pattern.compile("^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$");

        public Claim(String input) {
            // #1 @ 287,428: 27x20
            MatchResult matchResult = getMatchResult(input);
            id = Integer.parseInt(matchResult.group(1));
            xOffset = Integer.parseInt(matchResult.group(2));
            yOffset = Integer.parseInt(matchResult.group(3));
            width = Integer.parseInt(matchResult.group(4));
            height = Integer.parseInt(matchResult.group(5));

        }

        private MatchResult getMatchResult(String input) {
            Matcher matcher = inputPattern.matcher(input);
            matcher.find();
            return matcher.toMatchResult();
        }

        Stream<Point> createClaimedInches() {
            List<Integer> widthCoordinates = IntStreamEx.range(width).boxed().toList();
            List<Integer> heightCoordinates = IntStreamEx.range(height).boxed().toList();
            return StreamEx.of(widthCoordinates)
                    .cross(heightCoordinates)
                    .mapKeyValue(Point.create(xOffset, yOffset));
        }
    }

    @Value
    public static class Point implements Comparable<Point> {
        int x, y;

        static BiFunction<Integer, Integer, Point> create(int xOffset, int yOffset) {
            return (x, y) -> new Point(x + xOffset, y + yOffset);
        }

        @Override
        public int compareTo(Point other) {
            int dx = this.x - other.x;

            return dx != 0 ? dx : this.y - other.y;
        }
    }
}

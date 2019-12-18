package adventOfCode2019;

import one.util.streamex.IntStreamEx;

import java.util.HashMap;
import java.util.Map;

import static java.util.function.Predicate.not;

public class Day13 extends AbstractIntcodePuzzle {

    public static void main(final String[] args) {
        new Day13().run();
    }

    @Override
    protected Object a(final long[] program) throws Exception {
        IntcodeComputer intcodeComputer = new IntcodeComputer(program, new long[0]);

        long[] longs = intcodeComputer.finishProgram();
        Map<Point, Long> gameState = mapOutput(longs);
        printGame(gameState);
        System.out.println("output has a length of " + longs.length);

        return IntStreamEx.range(0, longs.length / 3)
                .map(i -> Math.max(0, i * 3 - 1))
                .mapToLong(i -> longs[i])
                .filter(fileType -> fileType == 2)
                .count();
    }

    @Override
    protected Object b(final long[] program) throws Exception {
        program[0] = 2;
        IntcodeComputer intcodeComputer = new IntcodeComputer(program, new long[]{0});

        Map<Point, Long> gameState = mapOutput(initScreen(intcodeComputer));
        while (!intcodeComputer.isDone()) {
            long[] longs = nextScreen(intcodeComputer);

            gameState.putAll(mapOutput(longs));

            if (isBallMovement(longs)) {
                intcodeComputer.addInput(adjustPaddle(gameState));
            }
            //printGame(gameState);
        }

        return gameState.get(new Point(-1, 0));
    }

    private long[] initScreen(final IntcodeComputer intcodeComputer) {
        var res = new long[3120 - 9];
        for (int i = 0; i < res.length; i += 3) {
            long[] tmp = nextScreen(intcodeComputer);
            res[i] = tmp[0];
            res[i + 1] = tmp[1];
            res[i + 2] = tmp[2];
        }
        return res;
    }

    private int adjustPaddle(final Map<Point, Long> gameState) {
        int paddle = -1;
        int ball = -1;

        for (Map.Entry<Point, Long> tile : gameState.entrySet()) {
            if (tile.getValue() == IntcodeGame.TileType.BALL.ordinal()) {
                ball = tile.getKey().getX();
            }
            if (tile.getValue() == IntcodeGame.TileType.HORIZONTAL.ordinal()) {
                paddle = tile.getKey().getX();
            }

            if (paddle != -1 && ball != -1) break;
        }

        return Integer.signum(ball - paddle);
    }

    private boolean isBallMovement(final long[] longs) {
        return longs[2] == IntcodeGame.TileType.BALL.ordinal();
    }

    private long[] nextScreen(final IntcodeComputer intcodeComputer) {
        long[] screen = new long[3];
        for (int i = 0; i < screen.length; i++) {
            long output = intcodeComputer.nextOutput();
            if (!intcodeComputer.isDone())
                screen[i] = output;
            else break;
        }
        return screen;
    }

    public void printGame(final Map<Point, Long> gameState) {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        final Point scorePosition = new Point(-1, 0);
        maxX = gameState.keySet().stream().filter(not(scorePosition::equals)).mapToInt(Point::getX).max().orElseThrow();
        maxY = gameState.keySet().stream().filter(not(scorePosition::equals)).mapToInt(Point::getY).max().orElseThrow();
        minX = gameState.keySet().stream().filter(not(scorePosition::equals)).mapToInt(Point::getX).min().orElseThrow();
        minY = gameState.keySet().stream().filter(not(scorePosition::equals)).mapToInt(Point::getY).min().orElseThrow();

        System.out.printf("Current score: %d%n", gameState.getOrDefault(scorePosition, -1L));
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                int value = Math.toIntExact(gameState.getOrDefault(new Point(x, y), (long) IntcodeGame.TileType.EMPTY.ordinal()));
                String stringValue = IntcodeGame.TileType.values()[value].toString();
                System.out.print(stringValue);
            }
            System.out.println();
        }

    }

    private Map<Point, Long> mapOutput(final long[] output) {
        final Map<Point, Long> gameState = new HashMap<>();
        for (int i = 0; i < output.length; i += 3) {
            int y = (int) output[i + 1];
            int x = (int) output[i];

            Point key = new Point(x, y);
            long value = output[i + 2];
            gameState.put(key, value);
        }
        return gameState;
    }
}

package adventOfCode2019;

import static java.util.function.Predicate.not;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import one.util.streamex.EntryStream;

public class Day18 extends Abstract2DPuzzle {

    public static final Character AT_SIGN = '@';
    public static final Character HASH = '#';

    @Override
    protected Map<Point, Character> parseInput(final String[] rawInput) throws Exception {
        return EntryStream.of(super.parseInput(rawInput)).filterValues(not(HASH::equals)).toMap();
    }

    @Override
    protected Object b(final Map<Point, Character> input) throws Exception {
        return null;
    }

    @Override
    protected Object a(final Map<Point, Character> input) throws Exception {

        final LinkedList<Map.Entry<Map<Point, Character>, Integer>> openGames = new LinkedList<>();
        openGames.add(Map.entry(input, 0));
        int minimalSteps = Integer.MAX_VALUE;

        while (!openGames.isEmpty()) {
            // if(openGames.size() > 10) break;

            System.out.println("Number of games: " + openGames.size());
            final Map.Entry<Map<Point, Character>, Integer> currentGame = openGames.removeFirst();

            //System.out.println("old");
            //printGame(currentGame.getKey());

            if (gameIsDone(currentGame)) {
                minimalSteps = Math.min(minimalSteps, currentGame.getValue());
                final int m = minimalSteps;
                openGames.removeIf(game -> game.getValue() >= m);
                continue;
            }

            final Pathfinder pathfinder = new Pathfinder(currentGame.getKey().keySet());
            final Point currentPosition = getCurrentPosition(currentGame.getKey());

            final Map<Point, Character> availableKeys = getAvailableKeys(currentGame.getKey(), currentPosition);
            for (final Map.Entry<Point, Character> availableKey : availableKeys.entrySet()) {
                final int nextSegment = pathfinder.findShortestPath(currentPosition, availableKey.getKey())
                    .map(List::size)
                    .orElseThrow() - 1;

                final Map<Point, Character> newMap = new HashMap<>(currentGame.getKey());
                EntryStream.of(newMap)
                    .findFirst(tile -> tile.getValue().equals(Character.toUpperCase(availableKey.getValue())))
                    .map(Map.Entry::getKey)
                    .ifPresent(openedDoor -> newMap.put(openedDoor, '.'));

                newMap.put(availableKey.getKey(), AT_SIGN);
                newMap.put(currentPosition, '.');

                openGames.add(Map.entry(newMap, nextSegment + currentGame.getValue()));

                //System.out.println();
                //System.out.println("new");
                //printGame(newMap);
            }
        }

        return minimalSteps;
    }

    private boolean gameIsDone(final Map.Entry<Map<Point, Character>, Integer> currentGame) {
        return currentGame.getKey().values().stream().noneMatch(Character::isAlphabetic);
    }

    private Map<Point, Character> getAvailableKeys(final Map<Point, Character> input, final Point currentPosition) {
        final Set<Point> visitedPoints = new HashSet<>();
        final LinkedList<Point> possibleNextPositions = new LinkedList<>();

        final Map<Point, Character> keys = new HashMap<>();

        possibleNextPositions.add(currentPosition);

        while (!possibleNextPositions.isEmpty()) {
            final Point next = possibleNextPositions.removeFirst();
            final Character nextValue = input.get(next);

            visitedPoints.add(next);

            if (Character.isLowerCase(nextValue)) {
                keys.put(next, nextValue);
                continue;
            }

            next
                .getNeighbours()
                .values()
                .filter(not(visitedPoints::contains))
                .filter(input.keySet()::contains)
                .filter(point -> !Character.isUpperCase(input.get(point)))
                .forEach(possibleNextPositions::add);
        }

        return keys;
    }

    private Point getCurrentPosition(final Map<Point, Character> input) {
        return EntryStream.of(input)
            .filterValues(AT_SIGN::equals)
            .keys()
            .findFirst()
            .orElseThrow();
    }

    public void printGame(final Map<Point, Character> gameState) {
        final int maxX = gameState.keySet().stream().mapToInt(Point::getX).max().orElseThrow();
        final int maxY = gameState.keySet().stream().mapToInt(Point::getY).max().orElseThrow();
        final int minX = gameState.keySet().stream().mapToInt(Point::getX).min().orElseThrow();
        final int minY = gameState.keySet().stream().mapToInt(Point::getY).min().orElseThrow();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                final char value = gameState.getOrDefault(new Point(x, y), '#');
                System.out.print(value);
            }
            System.out.println();
        }

    }
}

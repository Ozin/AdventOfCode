package adventOfCode2019;

import static java.util.function.Predicate.not;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import one.util.streamex.EntryStream;

public class Day18 extends Abstract2DPuzzle {

    public static final Character AT_SIGN = '@';
    public static final Character HASH = '#';

    int depth = 0;
    Map<Map<Point, Character>, Integer> mapCache;
    Map<Map.Entry<Point, Point>, Integer> segmentCache;

    private void reset() {
        depth = 0;
        mapCache = new HashMap<>();
        segmentCache = new HashMap<>();
    }

    @Override
    protected Map<Point, Character> parseInput(final String[] rawInput) throws Exception {
        return EntryStream.of(super.parseInput(rawInput)).filterValues(not(HASH::equals)).toMap();
    }

    @Override
    protected Object b(final Map<Point, Character> input) throws Exception {
        reset();
        Map<Point, Character> alteredMap = new HashMap<>(input);

        Point center = getCurrentPositions(alteredMap).iterator().next();
        alteredMap.put(center.addY(-1).addX(-1), '@');
        alteredMap.put(center.addY(1).addX(-1), '@');
        alteredMap.put(center.addY(-1).addX(1), '@');
        alteredMap.put(center.addY(1).addX(1), '@');

        // instead of replacing with hashes, remove invalid positions
        // alteredMap.put(center, '#');
        // alteredMap.put(center.addX(1), '#');
        // alteredMap.put(center.addX(-1), '#');
        // alteredMap.put(center.addY(1), '#');
        // alteredMap.put(center.addY(-1), '#');
        alteredMap.remove(center);
        alteredMap.remove(center.addX(1));
        alteredMap.remove(center.addX(-1));
        alteredMap.remove(center.addY(1));
        alteredMap.remove(center.addY(-1));

        return findShortestSolution(alteredMap);
    }

    @Override
    protected Object a(final Map<Point, Character> input) throws Exception {
        reset();
        return findShortestSolution(Map.copyOf(input));
    }

    private int findShortestSolution(final Map<Point, Character> currentGame) {
        if (mapCache.containsKey(currentGame)) {
            return mapCache.get(currentGame);
        }

        if (mapCache.size() % 100 == 0) {
            System.out.printf("Current depth: %s; Closed solutions: %s%n", depth, mapCache.size());
        }
        depth++;
        // if(openGames.size() > 10) break;

//            System.out.println("old");
//            printGame(currentGame);

        if (gameIsDone(currentGame)) {
            depth--;
            return 0;
        }

        final Pathfinder pathfinder = new Pathfinder(currentGame.keySet());
        final Set<Point> currentPositions = getCurrentPositions(currentGame);

        int minimalSteps = Integer.MAX_VALUE;

        for (final Point currentPosition : currentPositions) {
            final Map<Point, Character> availableKeys = getAvailableKeys(currentGame, currentPosition);
//            System.out.println();
//            System.out.println("availableKeys: " + availableKeys.values());
//            printGame(currentGame);
            for (final Map.Entry<Point, Character> availableKey : availableKeys.entrySet()) {
                final int nextSegment = findSegment(pathfinder, currentPosition, availableKey.getKey());

                final Map<Point, Character> nextMap = nextMap(currentGame, currentPosition, availableKey);

                minimalSteps = Math.min(minimalSteps, findShortestSolution(nextMap) + nextSegment);

//                System.out.println();
//                System.out.println("old");
//                printGame(currentGame);
//                System.out.println();
//                System.out.println("new");
//                printGame(nextMap);
            }
        }

        depth--;
        final int finalMinimalSteps = minimalSteps;
        mapCache.compute(currentGame, (key, oldValue) -> Math.min(oldValue == null ? Integer.MAX_VALUE : oldValue, finalMinimalSteps));
        return minimalSteps;
    }

    private int findSegment(final Pathfinder pathfinder, final Point currentPosition, final Point availableKey) {
        Integer segment = segmentCache.get(Map.entry(currentPosition, availableKey));

        if (segment != null) {
            return segment;
        }

        segment = segmentCache.get(Map.entry(availableKey, currentPosition));

        if (segment != null) {
            return segment;
        }

        segment = pathfinder.findShortestPath(currentPosition, availableKey)
            .map(List::size)
            .orElseThrow() - 1;

        segmentCache.put(Map.entry(currentPosition, availableKey), segment);

        System.out.println("Segments: " + segmentCache.size());

        return segment;
    }

    private Map<Point, Character> nextMap(final Map<Point, Character> currentGame, final Point currentPosition, final Map.Entry<Point, Character> availableKey) {
        final Map<Point, Character> newMap = new HashMap<>(currentGame);
        EntryStream.of(newMap)
            .findFirst(tile -> tile.getValue().equals(Character.toUpperCase(availableKey.getValue())))
            .map(Map.Entry::getKey)
            .ifPresent(openedDoor -> newMap.put(openedDoor, '.'));

        newMap.put(availableKey.getKey(), AT_SIGN);
        newMap.put(currentPosition, '.');

        return Map.copyOf(newMap);
    }

    private boolean gameIsDone(final Map<Point, Character> currentGame) {
        return currentGame.values().stream().noneMatch(Character::isAlphabetic);
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

    private Set<Point> getCurrentPositions(final Map<Point, Character> input) {
        return EntryStream.of(input)
            .filterValues(AT_SIGN::equals)
            .keys()
            .toSet();
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

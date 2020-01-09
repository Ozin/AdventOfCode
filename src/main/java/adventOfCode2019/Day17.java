package adventOfCode2019;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

public class Day17 extends AbstractIntcodePuzzle {
    @Override
    protected Object a(final long[] initialState) throws Exception {
        final Map<Point, String> map = getMap(initialState);
        final Map<Point, String> mapWithIntersections = EntryStream.of(map)
            .filterValues("#"::equals)
            .filterKeys(this.surroundedByScaffolding(map))
            .mapValues(value -> "O")
            .toMap();

//        Map<Point, String> copy = new HashMap<>(map);
//        copy.putAll(mapWithIntersections);
//
        printMap(map);
//        printMap(copy);

        return StreamEx.of(mapWithIntersections.keySet())
            .mapToInt(p -> p.getX() * p.getY())
            .sum();
    }

    @Override
    protected Object b(final long[] state) throws Exception {
        // final Map<Point, String> map = getMap(state);
        // final String path = findPath(map);
        // System.out.println(String.join("", path));


        final long[] awakeRobotState = state;
        awakeRobotState[0] = 2;
        final IntcodeComputer intcodeComputer = new IntcodeComputer(awakeRobotState);
        final String[] inputs = new String[] {
            //////////////////////
            "A,A,B,C,B,C,B,C,B,A",
            "R,6,L,12,R,6",
            "L,12,R,6,L,8,L,12",
            "R,12,L,10,L,10",
            "n"
        };
        for (final String input : inputs) {
            input.chars().forEach(intcodeComputer::addInput);
            intcodeComputer.addInput('\n');
        }

        Optional<String> line = Optional.empty();
        while (!intcodeComputer.isDone()) {
            line = Optional.of(getLine(intcodeComputer));
            line.ifPresent(System.out::println);
        }

        return line.map(Integer::parseInt).orElseThrow();
    }

    private String getLine(final IntcodeComputer intcodeComputer) {
        ArrayList<Long> returnValues = new ArrayList<>();
        while (true) {
            long nextOutput = intcodeComputer.nextOutput();

            if (nextOutput == 10 || intcodeComputer.isDone()) {
                break;
            }

            returnValues.add(nextOutput);
        }

        if (returnValues.size() == 1) {
            return returnValues.get(0) + "";
        }

        return StreamEx.of(returnValues)
            .mapToInt(Long::intValue)
            .mapToObj(i -> Character.valueOf((char) i).toString())
            .joining();
    }

    private String findPath(final Map<Point, String> map) {
        final Map.Entry<Point, String> currentTile = EntryStream.of(map)
            .filterValues("^v><"::contains)
            .findFirst()
            .orElseThrow();

        final Set<Point> scaffoldings = EntryStream.of(map)
            .filterValues("#"::equals)
            .keys()
            .toSet();

        Point currentPoint = currentTile.getKey();
        Direction currentDirection = Direction.getByString(currentTile.getValue());
        final List<String> path = new ArrayList<>();

        while (true) {
            final Point straight = currentDirection.continueStraight(currentPoint);
            final Point left = currentDirection.turnLeft().continueStraight(currentPoint);
            final Point right = currentDirection.turnRight().continueStraight(currentPoint);
            if (scaffoldings.contains(straight)) {
                path.add("1");
                currentPoint = straight;
            } else if (scaffoldings.contains(left)) {
                path.add("L");
                path.add("1");

                currentDirection = currentDirection.turnLeft();
                currentPoint = left;
            } else if (scaffoldings.contains(right)) {
                path.add("R");
                path.add("1");

                currentDirection = currentDirection.turnRight();
                currentPoint = right;
            } else {
                return String.join("", path);
            }
        }
    }

    private Predicate<? super Point> surroundedByScaffolding(final Map<Point, String> map) {
        return point -> EntryStream.of(point.getNeighbours())
            .values()
            .map(map::get)
            .allMatch("#"::equals);
    }

    private void printMap(final Map<Point, String> map) {
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 64; x++) {
                System.out.print(map.getOrDefault(new Point(x, y), ""));
            }
            System.out.println();
        }
    }

    private Map<Point, String> getMap(final long[] initialState) {
        final IntcodeComputer intcodeComputer = new IntcodeComputer(initialState);
        final Map<Point, String> map = new HashMap<>();

        int column = 0;
        int row = 0;
        while (!intcodeComputer.isDone()) {
            final String currentObject = Character.valueOf((char) intcodeComputer.nextOutput()).toString();
            if ("\n".equals(currentObject)) {
                row++;
                column = 0;
            } else {
                map.put(new Point(column, row), currentObject);
                column++;
            }
        }

        return map;
    }
}

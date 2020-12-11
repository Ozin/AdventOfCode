package AOC2019;

public class Day15 extends AbstractIntcodePuzzle {

    @Override
    protected Object a(final long[] program) throws Exception {
        return null;

//        IntcodeComputer intcodeComputer = new IntcodeComputer(program);
//        Map<Point, String> visited = new HashMap<>();
//        Point currentPosition = new Point(0, 0);
//        Direction currentDirection = Direction.UP;
//        while (true) {
//
//            intcodeComputer.addInput(currentDirection.ordinal() + 1);
//            long output = intcodeComputer.nextOutput();
//            if (output == 1) {
//                currentPosition = updatePosition(currentPosition, currentDirection);
//                if (visited.containsKey(currentPosition)) {
//                    break;
//                }
//
//                visited.put(currentPosition, ".");
//            } else if (output == 0) {
//                final Point wall = updatePosition(currentPosition, currentDirection);
//                visited.put(wall, "#");
//            } else if (output == 2) {
//                final Point oxygen = updatePosition(currentPosition, currentDirection);
//                visited.put(oxygen, "O");
//            }
//
//            currentDirection = currentDirection.turnRight();
//        }
//
//        throw new IllegalStateException("Could not finish map");
    }

    private Point updatePosition(final Point currentPosition, final Direction currentDirection) {
        int dX = 0, dY = 0;
        switch (currentDirection) {
            case UP:
                dY = -1;
                break;
            case DOWN:
                dY = 1;
                break;
            case LEFT:
                dX = -1;
                break;
            case RIGHT:
                dX = 1;
                break;
        }

        return currentPosition.addX(dX).addY(dY);
    }

    @Override
    protected Object b(final long[] program) throws Exception {
        return null;
    }
}

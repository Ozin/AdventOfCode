package AOC2019;

public class Day11 extends AbstractIntcodePuzzle {

    public static void main(final String[] args) {
        new Day11().run();
    }

    @Override
    protected Object a(final long[] input) throws Exception {
        final PaintingRobot paintingRobot = new PaintingRobot(input);
        paintingRobot.autonomicDrive(PaintingRobot.Color.BLACK);
        paintingRobot.getTiles().keySet().size();
        return paintingRobot.getTiles().keySet().size();
    }

    @Override
    protected Object b(final long[] input) throws Exception {
        final PaintingRobot paintingRobot = new PaintingRobot(input);
        paintingRobot.autonomicDrive(PaintingRobot.Color.WHITE);
        paintingRobot.getTiles().keySet().size();
        return paintingRobot.toString();
    }
}

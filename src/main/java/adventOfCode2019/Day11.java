package adventOfCode2019;

public class Day11 extends AbstractIntcodePuzzle {

    public static void main(final String[] args) {
        new Day11().run();
    }

    @Override
    protected Object a(final long[] input) throws Exception {
        PaintingRobot paintingRobot = new PaintingRobot(input);
        paintingRobot.autonomicDrive();
        paintingRobot.getTiles().keySet().size();
        return paintingRobot.getTiles().keySet().size();
    }

    @Override
    protected Object b(final long[] input) throws Exception {
        return null;
    }
}

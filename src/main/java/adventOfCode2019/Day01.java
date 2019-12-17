package adventOfCode2019;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Day01 extends AbstractDay<int[]> {
    public static void main(final String[] args) throws IOException {
        new Day01().run();
    }

    @Override
    protected int[] parseInput(final String[] rawInput) throws Exception {
        return Arrays.stream(rawInput).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    protected Object a(final int[] input) {
        return Arrays.stream(input).map(Day01::getFuel).sum();
    }

    @Override
    protected Object b(final int[] input) {
        return Arrays.stream(input).map(Day01::getFuelOfFuels).sum();
    }

    private static int getFuelOfFuels(int mass) {
        int total = 0;

        while (getFuel(mass) > 0) {
            final int newMass = getFuel(mass);
            total += newMass;

            mass = newMass;
        }

        return total;
    }

    public static int getFuel(final int mass) {
        return mass / 3 - 2;
    }
}

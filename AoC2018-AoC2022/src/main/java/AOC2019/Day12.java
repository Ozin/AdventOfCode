package AOC2019;

import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.math3.util.ArithmeticUtils.lcm;

public class Day12 extends AbstractDay<List<Moon>> {

    public static void main(final String[] args) {
        new Day12().run();
    }

    @Override
    protected List<Moon> parseInput(final String[] rawInput) throws Exception {
        return Arrays.stream(rawInput).map(Moon::new).collect(Collectors.toList());
    }

    @Override
    protected Object a(List<Moon> input) throws Exception {
        for (int i = 0; i < 1000; i++) {
            final List<Moon> oldInput = input;
            input = StreamEx.of(oldInput)
                .map(moon -> moon.applyGravity(oldInput))
                .map(Moon::applyVelocity)
                .toList();

        }
        return StreamEx.of(input).mapToInt(Moon::totalEnergy).sum();
    }

    @Override
    protected Object b(List<Moon> input) throws Exception {
        final int[] originalXs = getDimension(input, 0);
        final int[] originalYs = getDimension(input, 1);
        final int[] originalZs = getDimension(input, 2);

        int cycleX = 0, cycleY = 0, cycleZ = 0;

        for (int i = 1; ; i++) {
            final List<Moon> oldInput = input;
            input = StreamEx.of(oldInput)
                .map(moon -> moon.applyGravity(oldInput))
                .map(Moon::applyVelocity)
                .toList();

            if (Arrays.equals(originalXs, getDimension(input, 0))) {
                cycleX = i;
            }
            if (Arrays.equals(originalYs, getDimension(input, 1))) {
                cycleY = i;
            }
            if (Arrays.equals(originalZs, getDimension(input, 2))) {
                cycleZ = i;
            }

            if (cycleX != 0 && cycleY != 0 && cycleZ != 0) {
                return leastCommonMultiple(cycleX, cycleY, cycleZ);
            }
        }
    }

    private long leastCommonMultiple(final long cycleX, final long cycleY, final long cycleZ) {
        return lcm(lcm(cycleX, cycleY), cycleZ);
    }

    public int[] getDimension(final List<Moon> moons, final int dimension) {
        final IntStream positions = moons.stream().mapToInt(moon -> moon.getPosition().get(dimension));
        final IntStream velocities = moons.stream().mapToInt(moon -> moon.getVelocity().get(dimension));
        return IntStream.concat(positions, velocities).toArray();
    }
}

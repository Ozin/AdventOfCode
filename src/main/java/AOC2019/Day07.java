package AOC2019;

import one.util.streamex.StreamEx;

import java.util.Arrays;
import java.util.Map;
import utils.Utils;

public class Day07 extends AbstractIntcodePuzzle {

    public static void main(final String[] args) {
        new Day07().run();
    }

    @Override
    protected Object a(final long[] program) throws Exception {
        return StreamEx.of(Utils.permutation("01234"))
            .map(this::getPhaseSettings)
            .mapToEntry(phaseSettings -> runAmplifiers(program, phaseSettings))
            .mapKeys(Arrays::toString)
            // .peek(System.out::println)
            .maxByLong(Map.Entry::getValue)
            .orElseThrow()
            .getValue();
    }

    @Override
    protected Object b(final long[] program) throws Exception {
        return StreamEx.of(Utils.permutation("56789"))
            .map(this::getPhaseSettings)
            .mapToEntry(phaseSettings -> runAmplifiersWithFeedbackLoop(program, phaseSettings))
            .mapKeys(Arrays::toString)
            // .peek(System.out::println)
            .maxByLong(Map.Entry::getValue)
            .orElseThrow()
            .getValue();
    }

    private int[] getPhaseSettings(final String phaseString) {
//        final int rad5 = Integer.parseInt(Integer.toString(i, 5));
//        final String phaseString = String.format("%05d", rad5);
        return Arrays.stream(phaseString.split("")).mapToInt(Integer::parseInt).toArray();
    }

    private long runAmplifiers(final long[] program, final int... phaseSettings) {
        long acc = 0;
        for (final int phaseSetting : phaseSettings) {
            acc = new IntcodeComputer(program, new long[] {phaseSetting, acc}).nextOutput();
        }
        return acc;
    }

    private long runAmplifiersWithFeedbackLoop(final long[] program, final int[] phaseSettings) {

        final IntcodeComputer[] amplifiers = new IntcodeComputer[5];

        long output = 0;
        for (int i = 0; i < amplifiers.length; i++) {
            amplifiers[i] = new IntcodeComputer(program, new long[] {phaseSettings[i], output});
            output = amplifiers[i].nextOutput();
        }

        long lastOutput;
        while (true) {
            for (int i = 0; i < amplifiers.length; i++) {
                lastOutput = output;
                amplifiers[i].addInput(lastOutput);
                output = amplifiers[i].nextOutput();
                if (amplifiers[i].isDone()) {
                    return lastOutput;
                }
            }
        }
    }
}

package adventOfCode2019;

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Day08 extends AbstractDay<int[][]> {
    //public final int WIDTH = 2;//25;
    //public final int HEIGHT = 2;//6;
    public final int WIDTH = 25;
    public final int HEIGHT = 6;

    public final int LAYER_SIZE = WIDTH * HEIGHT;

    public static void main(final String[] args) {
        new Day08().run();
    }

    @Override
    protected int[][] parseInput(final String[] rawInput) throws Exception {
        final int[] ints = Arrays.stream(rawInput[0].split("")).mapToInt(Integer::parseInt).toArray();
        //int[] ints = Arrays.stream("0222112222120000".split("")).mapToInt(Integer::parseInt).toArray();

        final int layerAmount = ints.length / LAYER_SIZE;
        final int[][] layers = new int[layerAmount][];

        for (int i = 0; i < layerAmount; i++) {
            layers[i] = new int[LAYER_SIZE];
            System.arraycopy(ints, i * LAYER_SIZE, layers[i], 0, LAYER_SIZE);
        }

        return layers;
    }

    @Override
    protected Object a(final int[][] layers) throws Exception {

        final Map<Integer, Long> layerWithFewest0 = StreamEx.of(layers)
                .map(this::getIntCount)
                .minByLong(map -> map.getOrDefault(0, Long.MIN_VALUE))
                .orElseThrow();

        return layerWithFewest0.get(1) * layerWithFewest0.get(2);
    }

    public Map<Integer, Long> getIntCount(final int[] arr) {
        return IntStreamEx.of(arr).sorted().boxed().runLengths().toMap();
    }

    @Override
    protected Object b(final int[][] layers) throws Exception {
        final int[] result = new int[LAYER_SIZE];
        for (int layerIndex = layers.length - 1; layerIndex >= 0; layerIndex--) {
            final int[] currentLayer = layers[layerIndex];

            for (int i = 0; i < currentLayer.length; i++) {
                if (currentLayer[i] == 2) continue;

                result[i] = currentLayer[i];
            }
        }

        return "\n" + Arrays.stream(result)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(""))
                .replaceAll(String.format("(\\d{%d})", WIDTH), "$1\n")
                .replace("1", "#")
                .replace("0", " ");
    }
}

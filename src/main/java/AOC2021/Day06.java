package AOC2021;

import java.util.Arrays;
import java.util.stream.LongStream;

public class Day06 {

    protected String a(final String[] input) throws Exception {
        long[] fishes = new long[9];

        for (String i : input[0].split(",")) fishes[Integer.parseInt(i)]++;
        System.out.println("Initial state: " + Arrays.toString(fishes));

        for (int i = 0; i < 80; i++) {
            fishes = newFishGeneration(fishes);
            System.out.printf("After %2d day: %20s, Amount: %s%n", i + 1, Arrays.toString(fishes), LongStream.of(fishes).sum());
        }

        return "" + LongStream.of(fishes).sum();
    }

    private long[] newFishGeneration(long[] fishes) {
        long[] newFishes = new long[9];
        System.arraycopy(fishes, 1, newFishes, 0, 8);

        long givingBirth = fishes[0];
        newFishes[6] += givingBirth;
        newFishes[8] += givingBirth;

        fishes = newFishes;
        return fishes;
    }

    protected String b(final String[] input) throws Exception {
        long[] fishes = new long[9];

        for (String i : input[0].split(",")) fishes[Integer.parseInt(i)]++;

        for (int i = 0; i < 256; i++) {
            fishes = newFishGeneration(fishes);
        }

        return "" + LongStream.of(fishes).sum();
    }
}

package AOC2018.day12;

import java.util.Map;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class PotsRow {
    Map<Integer, Boolean> pots;
    private int lastPot = Integer.MIN_VALUE;
    private int firstPot = Integer.MAX_VALUE;

    public PotsRow(String input) {
        TreeSet<Pot> pots = new TreeSet<>();
        IntStream.range(0, input.length())
                .mapToObj(i -> new Pot(i, input.charAt(i) == '#'))
                .forEach(pots::add);
    }

    public int getLastPot() {
        return lastPot;
    }

    public int getFirstPot() {
        return firstPot;
    }

    public boolean hasPlant(int index) {
        Boolean hasPlant = pots.get(index);
        return hasPlant == null ? false : hasPlant;
    }

    public void setPlant(int index, boolean hasPlant) {
        pots.put(index, hasPlant);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = firstPot; i < lastPot; i++) {
            sb.append(pots.getOrDefault(i, false) ? "#" : ".");
        }
        return sb.toString();
    }
}

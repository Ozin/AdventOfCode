package AOC2019;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Day14 extends AbstractDay<Map<String, Reaction>> {

    public static final long TRILLION = 1000000000000L;
    private List<String> orderOfBreakdown;

    public static void main(final String[] args) {
        new Day14().run();
    }

    @Override
    protected Map<String, Reaction> parseInput(final String[] rawInput) throws Exception {
        final Map<String, Reaction> reactions = StreamEx.of(rawInput)
                .map(Reaction::new)
                .mapToEntry(Reaction::getOutput, Function.identity())
                .mapKeys(Map.Entry::getKey)
                .toMap();

        orderOfBreakdown = EntryStream.of(reactions)
                .sortedByInt(entry -> -calculateReactionLevel(entry.getValue(), reactions))
                .keys()
                .toList();

        return reactions;
    }

    @Override
    protected Object a(final Map<String, Reaction> reactions) throws Exception {
        return oreNeedForFuelAmount(1, reactions);
    }

//    @Override
//    protected Object b(final Map<String, Reaction> reactions) throws Exception {
//        long baseline = 1;
//        while (oreNeedForFuelAmount(baseline, reactions) < TRILLION) {
//            baseline *= 2;
//        }
//
//        for (long i = baseline / 2; i < baseline; i++) {
//            if (oreNeedForFuelAmount(i, reactions) > TRILLION) {
//                return i - 1;
//            }
//        }
//
//        throw new IllegalStateException("Could not find result");
//    }

    @Override
    protected Object b(final Map<String, Reaction> reactions) throws Exception {
        long baseline = 1;
        while (oreNeedForFuelAmount(baseline, reactions) < TRILLION) {
            baseline *= 2;
        }

        long lower = 0;
        long higher = baseline;
        while(true) {
            final long average = (lower + higher) / 2;
            final long averageNeed = oreNeedForFuelAmount(average, reactions);

            if(averageNeed < TRILLION) {
                lower = average;
            } else {
                higher = average;
            }

            if(Math.abs(lower - higher) <= 1) {
                return lower;
            }
        }
    }

    private long oreNeedForFuelAmount(final long fuelNeed, final Map<String, Reaction> reactions) {
        final Map<String, Long> summedBreakdown = new HashMap<>();
        summedBreakdown.put("FUEL", fuelNeed);
        for (final String element : orderOfBreakdown) {
            breakDown(element, summedBreakdown, reactions);
        }

        return summedBreakdown.get("ORE");
    }

    private void breakDown(final String currentElement, final Map<String, Long> summedBreakdown, final Map<String, Reaction> reactions) {
        final Reaction currentReaction = reactions.get(currentElement);

        final long neededAmount = summedBreakdown.get(currentElement);
        final int reactionAmount = currentReaction.getOutput().getValue();
        final long reactionFactor = (reactionAmount + neededAmount - 1) / reactionAmount;
        EntryStream.of(currentReaction.getInput())
                .mapValues(theoreticAmount -> reactionFactor * theoreticAmount)
                .forKeyValue((element, amount) -> summedBreakdown.merge(element, amount, Utils::add));
    }

    public int calculateReactionLevel(final Reaction reaction, final Map<String, Reaction> allReactions) {
        final Set<String> sourceElements = reaction.getInput().keySet();
        if (sourceElements.stream().allMatch("ORE"::equals))
            return 0;

        return StreamEx.of(sourceElements)
                .mapToInt(element -> 1 + calculateReactionLevel(allReactions.get(element), allReactions))
                .max()
                .orElseThrow();
    }
}

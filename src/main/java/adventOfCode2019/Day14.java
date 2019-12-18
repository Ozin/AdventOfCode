package adventOfCode2019;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static adventOfCode2019.Utils.multiply;

public class Day14 extends AbstractDay<Map<String, Reaction>> {

    public static void main(final String[] args) {
        new Day14().run();
    }

    @Override
    protected Map<String, Reaction> parseInput(final String[] rawInput) throws Exception {
        return StreamEx.of(rawInput)
                .map(Reaction::new)
                .mapToEntry(Reaction::getOutput, Function.identity())
                .mapKeys(Map.Entry::getKey)
                .toMap();
    }

    @Override
    protected Object a(final Map<String, Reaction> reactions) throws Exception {
        final List<String> orderOfBreakdown = EntryStream.of(reactions)
                .sortedByInt(entry -> -calculateReactionLevel(entry.getValue(), reactions))
                .keys()
                .toList();

        final Map<String, Integer> summedBreakdown = new HashMap<>();
        summedBreakdown.put("FUEL", 1);
        for (final String element : orderOfBreakdown) {
            breakDown(element, summedBreakdown, reactions);
        }

        return summedBreakdown.get("ORE");
    }

    private void breakDown(final String currentElement, final Map<String, Integer> summedBreakdown, final Map<String, Reaction> reactions) {
        final Reaction currentReaction = reactions.get(currentElement);

        final int currentAmount = summedBreakdown.get(currentElement);
        final int reactionAmount = currentReaction.getOutput().getValue();
        final int actualAmount = (reactionAmount + currentAmount - 1) / reactionAmount;
        EntryStream.of(currentReaction.getInput())
                .mapValues(multiply(actualAmount))
                .forKeyValue((element, amount) -> summedBreakdown.merge(element, amount, Utils::add));
    }

    @Override
    protected Object b(final Map<String, Reaction> input) throws Exception {
        return null;
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

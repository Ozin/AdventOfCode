package AOC2020;

import lombok.Value;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Day19 extends AbstractDay<Day19.Input> {
    public static void main(final String[] args) {
        new Day19().run();
    }

    @Override
    protected Input parseInput(final String[] rawInput) throws Exception {
        final String[] blocks = String.join("\n", rawInput).split(Pattern.quote("\n\n"));
        final Map<Integer, String> rawRuleMap = StreamEx.of(blocks[0].split("\n"))
            .map(s -> s.split(": "))
            .toMap(s -> Integer.parseInt(s[0]), s -> s[1]);


        return new Input(rawRuleMap, Arrays.asList(blocks[1].split("\n")));
    }

    @Override
    protected Object a(final Input input) throws Exception {
        final Map<Integer, String> rules = input.getRules();
        final String rule = getRule(0, rules, new HashMap<Integer, String>());
        final Predicate<String> pattern = Pattern.compile("^" + rule + "$").asPredicate();

        return StreamEx.of(input.getMessages()).filter(pattern).count();
    }

    private String getRule(final int i, final Map<Integer, String> rawRules, final HashMap<Integer, String> cache) {
        String rule = cache.get(i);
        if (rule != null) {
            return rule;
        }

        final String rawRule = rawRules.get(i);

        if (rawRule.matches("\".\"")) {
            final String ab = Character.toString(rawRule.charAt(1));
            cache.put(i, ab);
            return ab;
        }

        final String[] ors = rawRule.split(" ?\\| ?");

        rule = StreamEx.of(ors)
            .map(subRuleGroup -> StreamEx.of(subRuleGroup.split(" ")).mapToInt(Integer::parseInt).toArray())
            .map(subRules -> assembleRule(rawRules, cache, subRules))
            .joining("|");

        cache.put(i, rule);
        return rule;
    }

    private String assembleRule(final Map<Integer, String> rawRules, final HashMap<Integer, String> cache, final int[] subRules) {
        return IntStreamEx.of(subRules)
            .mapToObj(next -> getRule(next, rawRules, cache))
            .map(next -> "(" + next + ")")
            .joining();
    }

    @Override
    protected Object b(final Input input) throws Exception {
        // aa | aa+
        // aacc || aaaacccc
        input.getRules().put(8, "42 | 42 8");
        input.getRules().put(11, "42 31 | 42 11 31");
        return null;
    }

    @Value
    public static class Input {
        Map<Integer, String> rules;
        List<String> messages;
    }
}

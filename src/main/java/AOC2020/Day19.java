package AOC2020;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        return new Input(Rule.of(rawRuleMap), Arrays.asList(blocks[1].split("\n")));
    }

    @Override
    protected Object a(final Input input) throws Exception {
        final Map<Integer, Rule> rules = input.getRules();

        return StreamEx.of(input.getMessages())
            .filter(message -> input.getRules().get(0).matches(message, input.getRules()))
            .count();
    }

    @Override
    protected Object b(final Input input) throws Exception {

        // 8, "42 | 42 8"
        input.getRules().put(8, new OrRule(new AndRule(42), new AndRule(42, 8)));
        // 11, "42 31 | 42 11 31"
        input.getRules().put(11, new OrRule(new AndRule(42, 31), new AndRule(42, 11, 31)));

        return StreamEx.of(input.getMessages())
            .filter(message -> input.getRules().get(0).matches(message, input.getRules()))
            .count();
    }

    @Value
    public static class Input {
        Map<Integer, Rule> rules;
        List<String> messages;
    }

    interface Rule {
        static Map<Integer, Rule> of(final Map<Integer, String> rawRuleMap) {
            return EntryStream.of(rawRuleMap)
                .mapValues(Rule::getStringOrRuleFunction)
                .toMap();
        }

        private static Rule getStringOrRuleFunction(final String rawRule) {
            if (rawRule.matches("\".\"")) {
                return new ConstRule(rawRule.substring(1, 2));
            }

            final String[] orRules = rawRule.split(" \\| ");

            if (orRules.length > 1) {
                return new OrRule(StreamEx.of(orRules).map(Rule::getStringOrRuleFunction).toSet());
            } else {
                return new AndRule(StreamEx.of(orRules[0].split(" ")).map(Integer::parseInt).toList());
            }
        }

        boolean matches(String test, Map<Integer, Rule> rules);
    }

    @Value
    @RequiredArgsConstructor
    public static class OrRule implements Rule {
        Set<Rule> subRules;
        Map<String, Boolean> cache = new HashMap<>();

        public OrRule(final Rule... subRules) {
            this.subRules = StreamEx.of(subRules).toSet();
        }

        @Override
        public boolean matches(final String test, final Map<Integer, Rule> rules) {
            Boolean cached = cache.get(test);
            if (cached == null) {
                cached = evaluateMatch(test, rules);
                cache.put(test, cached);
            }

            return cached;
        }

        private boolean evaluateMatch(final String s, final Map<Integer, Rule> rules) {
            return StreamEx.of(this.subRules)
                .anyMatch(rule -> rule.matches(s, rules));
        }
    }

    @Value
    @RequiredArgsConstructor
    public static class AndRule implements Rule {
        List<Integer> ruleIds;
        Map<String, Boolean> cache = new HashMap<>();

        public AndRule(final int... ruleIds) {
            this.ruleIds = IntStreamEx.of(ruleIds).boxed().toList();
        }

        @Override
        public boolean matches(final String test, final Map<Integer, Rule> rules) {
            Boolean cached = cache.get(test);
            if (cached == null) {
                cached = evaluateMatch(test, rules);
                cache.put(test, cached);
            }

            return cached;
        }

        private boolean evaluateMatch(final String s, final Map<Integer, Rule> rules) {
            int startIndex = 0;
            final List<Rule> subRules = ruleIds.stream()
                .map(rules::get)
                .collect(Collectors.toList());

            for (final Rule rule : subRules) {
                boolean ruleMatched = false;
                for (int i = startIndex + 1; i < s.length() + 1; i++) {
                    if (rule.matches(s.substring(startIndex, i), rules)) {
                        startIndex = i;
                        ruleMatched = true;
                        break;
                    }
                }

                if (!ruleMatched) {
                    return false;
                }
            }

            return startIndex == s.length();
        }

    }

    @Value
    public static class ConstRule implements Rule {
        String value;

        @Override
        public boolean matches(final String s, final Map<Integer, Rule> rules) {
            return value.equals(s);
        }

    }
}

package AOC2020;

import lombok.Value;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07 extends AbstractDay<Map<String, Day07.Bag>> {
    public static void main(final String[] args) {
        new Day07().run();
    }

    @Override
    protected Map<String, Bag> parseInput(final String[] rawInput) throws Exception {
        final Map<String, Map<String, Integer>> colors = new HashMap<>();

        final Pattern secondaryPattern = Pattern.compile("((\\d) ([a-z ]+) bags?[,.])+");

        for (final String input : rawInput) {
            final String[] split = input.split(Pattern.quote(" bags contain "));

            if (input.endsWith("bags contain no other bags.")) {
                colors.put(split[0], Map.of());
                continue;
            }

            final Matcher matcher = secondaryPattern.matcher(split[1]);
            while (matcher.find()) {
                final int amount = Integer.parseInt(matcher.group(2));
                final String color = matcher.group(3);

                colors.merge(split[0], Map.of(color, amount), (a, b) -> {
                    final var m = new HashMap<>(a);
                    m.putAll(b);
                    return m;
                });
            }
        }

        return of(colors);
    }

    @Override
    protected Object a(final Map<String, Bag> input) throws Exception {
        final Bag shiny_gold = input.get("shiny gold");
        return input.values().stream().filter(b -> b.contains(shiny_gold)).count();
    }

    @Override
    protected Object b(final Map<String, Bag> input) throws Exception {
        return input.get("shiny gold").numberOfContainedBags();
    }

    public Map<String, Bag> of(final Map<String, Map<String, Integer>> colors) {
        final Map<String, Bag> cache = new HashMap<>();


        return EntryStream.of(colors)
            .keys()
            .mapToEntry(color -> fromMap(color, colors, cache))
            .toMap();
    }

    private Bag fromMap(final String color, final Map<String, Map<String, Integer>> colors, final Map<String, Bag> cache) {
        Bag bag = cache.get(color);
        if (bag != null) {
            return bag;
        }

        final Map<Bag, Integer> innerBags = new HashMap<>();
        for (final var innerColors : colors.get(color).entrySet()) {
            final Bag innerBag = fromMap(innerColors.getKey(), colors, cache);
            final Integer amount = innerColors.getValue();
            innerBags.put(innerBag, amount);
        }

        bag = new Bag(color, Map.copyOf(innerBags));
        cache.put(color, bag);
        return bag;
    }

    @Value
    public static class Bag {
        String color;
        Map<Bag, Integer> containments;

        public boolean contains(final Bag bag) {
            if (this.containments.containsKey(bag)) {
                return true;
            }

            return StreamEx.of(containments.keySet())
                .anyMatch(b -> b.contains(bag));
        }

        public int numberOfContainedBags() {
            return EntryStream.of(containments)
                .mapKeyValue((bag, amount) -> amount + amount * bag.numberOfContainedBags())
                .mapToInt(value -> value)
                .sum();
        }
    }
}

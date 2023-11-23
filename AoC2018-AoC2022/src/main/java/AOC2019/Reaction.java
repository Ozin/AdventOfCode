package AOC2019;

import lombok.Value;
import one.util.streamex.StreamEx;

import java.util.Map;

@Value
public class Reaction {
    private final Map<String, Integer> input;
    private final Map.Entry<String, Integer> output;

    public Reaction(final String rawInput) {
        final String[] reaction = rawInput.split(" => ");
        input = StreamEx.of(reaction[0].split(","))
            .map(String::trim)
            .map(s -> s.split(" "))
            .mapToEntry(s -> s[1], s -> Integer.parseInt(s[0]))
            .toMap();
        final String[] rawOutput = reaction[1].split(" ");
        output = Map.entry(rawOutput[1], Integer.parseInt(rawOutput[0]));
    }
}

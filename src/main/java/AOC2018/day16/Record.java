package AOC2018.day16;

import lombok.Value;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Record {
    int[] before;
    int[] instruction;
    int[] after;

    static Pattern inputPattern = Pattern.compile("^.*(\\d+).*(\\d+).*(\\d+).*(\\d+).*$");

    public Record(final String raw) {
        String[] lines = raw.split("\\n");

        if(!lines[0].startsWith("Before") || !lines[2].startsWith("After") || (lines[1].length() != 7 && lines[1].length() != 8)) {
            throw new IllegalArgumentException("wrong lines: " + raw);
        }

        before = toIntArray(lines[0]);
        instruction = toIntArray(lines[1]);
        after = toIntArray(lines[2]);
    }

    public static int[] toIntArray(final String line) {
        String[] split = line.replaceAll("[^\\d ]", "").trim().split("[^\\d]+");
        return new int[] {
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2]),
                Integer.parseInt(split[3])
        };
    }

    private MatchResult getMatchResult(String input) {
        Matcher matcher = inputPattern.matcher(input);
        matcher.find();
        return matcher.toMatchResult();
    }
}

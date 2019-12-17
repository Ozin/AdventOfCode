package adventOfCode2018.day04;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Event implements Comparable<Event> {
    LocalDateTime dateTime;
    EventType eventType;
    Optional<Integer> id;

    static Pattern inputPattern = Pattern.compile("^\\[(.+)] (.+)$");

    public Event(String input) {
        MatchResult matchResult = getMatchResult(input);
        dateTime = LocalDateTime.parse(matchResult.group(1).replace(" ", "T"));
        eventType = parseEventType(matchResult.group(2));
        id = parseId(matchResult.group(2));
    }

    private Optional<Integer> parseId(String input) {
        try {
            String substring = input.substring(7, input.indexOf(" ", 7));
            return Optional.of(Integer.parseInt(substring));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    private EventType parseEventType(String input) {
        switch (input) {
            case "falls asleep":
                return EventType.ASLEEP;
            case "wakes up":
                return EventType.AWAKE;
            default:
                return EventType.START;
        }
    }

    @Override
    public int compareTo(Event o) {
        return this.dateTime.compareTo(o.dateTime);
    }

    private MatchResult getMatchResult(String input) {
        Matcher matcher = inputPattern.matcher(input);
        matcher.find();
        return matcher.toMatchResult();
    }
}

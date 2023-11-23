package AOC2021;

import lombok.Value;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.Indexed;
import utils.Line;
import utils.Point;
import utils.Vector;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class Day05 {
    static Pattern coordsPattern = Pattern.compile("^\\s*(\\d+),(\\d+) -> (\\d+),(\\d+)\\s*$");

    protected String a(final String[] input) throws Exception {
        final List<Line> straightLines = Stream.of(input)
                .map(String::trim)
                .map(coordsPattern::matcher)
                .peek(Matcher::matches)
                .map(matcher -> new Line(new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))), new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))))
                .filter(l -> {
                    Vector slope = l.getSlope();
                    return Math.abs(slope.getDx()) + Math.abs(slope.getDy()) == 1;
                })
                .collect(Collectors.toList());

        return "" + StreamEx.of(straightLines)
                .flatMap(Line::interpolateLineInclusive)
                .sorted(Comparator.comparingInt(Point::getX).thenComparingInt(Point::getY))
                .runLengths()
                .filterValues(l -> l > 1)
                .count();
    }

    protected String b(final String[] input) throws Exception {
        final List<Line> straightLines = Stream.of(input)
                .map(String::trim)
                .map(coordsPattern::matcher)
                .peek(Matcher::matches)
                .map(matcher -> new Line(new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))), new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))))
                .collect(Collectors.toList());

        return "" + StreamEx.of(straightLines)
                .flatMap(Line::interpolateLineInclusive)
                .sorted(Comparator.comparingInt(Point::getX).thenComparingInt(Point::getY))
                .runLengths()
                .filterValues(l -> l > 1)
                .count();
    }

}

package AOC2021;


import one.util.streamex.IntStreamEx;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day18 {

    protected String a(final String[] input) throws Exception {
        return "" + magnitude(Stream.of(input).reduce(this::add).get());
    }

    protected String b(final String[] input) throws Exception {
        return "" + io.vavr.collection.Stream.of(input)
                .crossProduct(2)
                .map(s -> this.add(s.head(), s.tail().head()))
                .map(this::magnitude)
                .max().get();
    }

    public int magnitude(final String snailfishNumber) {
        return magnitudeWithParantheses(snailfishNumber.replaceAll(Pattern.quote("["), "(").replaceAll(Pattern.quote("]"), ")"));
    }

    private int magnitudeWithParantheses(final String snailfishNumber) {
        if (snailfishNumber.matches("^\\d+$")) return Integer.parseInt(snailfishNumber);

        final String[] subGroups = findLeftRight(snailfishNumber);

        return 3 * magnitudeWithParantheses(subGroups[0])
               + 2 * magnitudeWithParantheses(subGroups[1]);
    }

    private String[] findLeftRight(final String snailfishNumber) {
        final Matcher simple = Pattern.compile("^\\((\\d+),(\\d+)\\)$").matcher(snailfishNumber);
        if (simple.find()) return new String[]{simple.group(1), simple.group(2)};

        final Matcher simple2 = Pattern.compile("^\\((\\d+),(.*)\\)$").matcher(snailfishNumber);
        if (simple2.find()) return new String[]{simple2.group(1), simple2.group(2)};


        // Source: https://stackoverflow.com/a/47162099
        final Matcher matcher = Pattern.compile("(?=\\()(?=((?:(?=.*?\\((?!.*?\\2)(.*\\)(?!.*\\3).*))(?=.*?\\)(?!.*?\\3)(.*)).)+?.*?(?=\\2)[^(]*(?=\\3$)))").matcher(snailfishNumber);
        matcher.find();
        matcher.find();
        final String firstPart = matcher.group(1);

        return new String[]{
                firstPart,
                snailfishNumber.substring(matcher.end(1) + 1, snailfishNumber.length() - 1)
        };
    }

    public String add(final String left, final String right) {
        return reduce("[" + left + "," + right + "]");
    }

    public String reduce(String snailfishNumber) {
        while (true) {
            final Optional<int[]> explosion = findExplosion(snailfishNumber);
            if (explosion.isPresent()) {
                snailfishNumber = explode(snailfishNumber, explosion.get()[0], explosion.get()[1]);
                continue;
            }

            final Matcher split = Pattern.compile("(\\d{2,})").matcher(snailfishNumber);
            if (split.find()) {
                final int tooBigNumber = Integer.parseInt(split.group(0));
                snailfishNumber = snailfishNumber.substring(0, split.start())
                                  + String.format("[%d,%d]", tooBigNumber / 2, (tooBigNumber + 1) / 2)
                                  + snailfishNumber.substring(split.end());

                continue;
            }

            break;
        }

        return snailfishNumber;
    }

    private Optional<int[]> findExplosion(final String snailfishNumber) {
        int count = 0;
        for (int i = 0; i < snailfishNumber.length(); i++) {
            final char curChar = snailfishNumber.charAt(i);

            if (Character.isDigit(curChar)) continue;

            if (curChar == '[') count++;
            else if (curChar == ']') count--;

            if (count == 5) {
                return Optional.of(new int[]{
                        i + 1,
                        snailfishNumber.indexOf(']', i)
                });
            }
        }
        return Optional.empty();
    }

    private String explode(final String snailfishNumber, final int start, final int end) {
        final int[] numbers = Stream.of(snailfishNumber.substring(start, end).split(",")).mapToInt(Integer::parseInt).toArray();

        final String replacedWithZero = snailfishNumber.substring(0, start - 1)
                                        + 0
                                        + snailfishNumber.substring(end + 1);

        final Optional<Integer> nextLeftIndex = IntStreamEx.range(start - 1)
                .map(i -> start - 1 - i - 1)
                .mapToEntry(i -> i, replacedWithZero::charAt)
                .filterValues(Character::isDigit)
                .filterKeys(i -> !Character.isDigit(replacedWithZero.charAt(i - 1)))
                .keys()
                .findFirst();

        final Optional<Integer> nextRightIndex = IntStreamEx.range(start + 1, replacedWithZero.length())
                .mapToEntry(i -> i, replacedWithZero::charAt)
                .filterValues(Character::isDigit)
                .keys()
                .findFirst();

        final String explodedRight = nextRightIndex
                .map(i -> explode(numbers[1], replacedWithZero, i))
                .orElse(replacedWithZero);

        return nextLeftIndex
                .map(i -> explode(numbers[0], explodedRight, i))
                .orElse(explodedRight);
    }

    private String explode(final int number, final String snailfishNumber, final Integer nextLeftIndex) {
        final String prefix = snailfishNumber.substring(0, nextLeftIndex);
        final Matcher matcher = Pattern.compile("^(\\d+)(.*)$").matcher(snailfishNumber.substring(nextLeftIndex));
        matcher.find();
        return prefix
               + (Integer.parseInt(matcher.group(1)) + number)
               + matcher.group(2);
    }
}

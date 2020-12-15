package AOC2020;

import static java.util.function.Predicate.not;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Value;
import one.util.streamex.LongStreamEx;
import one.util.streamex.StreamEx;
import utils.Indexed;

public class Day14 extends AbstractDay<String[]> {
    public static void main(final String[] args) {
        new Day14().run();
    }

    @Override
    protected String[] parseInput(final String[] rawInput) throws Exception {
        return rawInput;
    }

    @Override
    protected Object a(final String[] input) throws Exception {
        Mask mask = null;
        final long[] memory = new long[1 << 16];
        final Pattern memPattern = Pattern.compile("mem\\[(\\d+)] = (\\d+)");
        for (final String s : input) {
            if (s.startsWith("mask = ")) {
                mask = new Mask(s.substring(7));
                continue;
            }

            final Matcher matcher = memPattern.matcher(s);
            matcher.find();
            memory[Integer.parseInt(matcher.group(1))] = mask.apply(Long.parseLong(matcher.group(2)));

        }
        return LongStreamEx.of(memory).sum();
    }

    @Override
    protected Object b(final String[] input) throws Exception {
        MaskV2 mask = null;
        final Map<Long, Integer> memory = new HashMap<>();
        final Pattern memPattern = Pattern.compile("mem\\[(\\d+)] = (\\d+)");
        for (final String s : input) {
            if (s.startsWith("mask = ")) {
                mask = new MaskV2(s.substring(7));
                continue;
            }

            final Matcher matcher = memPattern.matcher(s);
            matcher.find();
            final int value = Integer.parseInt(matcher.group(2));

            mask.apply(Integer.parseInt(matcher.group(1)))
                .forEach(address -> memory.put(address, value));

        }
        return memory.values().stream().mapToLong(Integer::intValue).sum();
    }

    @Value
    public static class Mask {
        Map<Integer, String> mask;

        public Mask(final String mask) {
            this.mask = StreamEx.of(mask.split(""))
                .map(Indexed.map())
                .mapToEntry(Indexed::getIndex, Indexed::getValue)
                .filterValues(not("X"::equals))
                .toMap();
        }

        public long apply(final long l) {
            final String bits = String.format("%36s", Long.toString(l, 2));
            final String s = StreamEx.of(bits.split(""))
                .map(v -> " ".equals(v) ? "0" : v)
                .map(Indexed.map())
                .map(v -> mask.getOrDefault(v.getIndex(), v.getValue()))
                .joining();
            return Long.parseLong(s, 2);
        }
    }

    @Value
    public static class MaskV2 {
        String mask;

        public List<Long> apply(final long orignalAddress) {
            final String orignalAddressBits = String.format("%" + mask.length() + "s", Long.toString(orignalAddress, 2))
                .replaceAll(" ", "0");
            return apply(0, mask.toCharArray(), orignalAddressBits).collect(Collectors.toList());
        }

        private Stream<Long> apply(final int i, final char[] maskChar, final String orignalAddress) {
            if (i >= mask.length()) {
                return Stream.of(orignalAddress).map(s -> Long.parseLong(s, 2));
            }

            switch (maskChar[i]) {
                case '0':
                    return apply(i + 1, maskChar, orignalAddress);
                case '1':
                    return apply(i + 1, maskChar, replace(i, orignalAddress, '1'));
                case 'X':
                    return Stream.concat(
                        apply(i + 1, maskChar, replace(i, orignalAddress, '1')),
                        apply(i + 1, maskChar, replace(i, orignalAddress, '0'))
                    );
                default:
                    throw new IllegalArgumentException("unknown mask char: " + mask + "; at " + i);
            }

        }

        public String replace(final int index, final String str, final char ch) {
            final char[] chars = str.toCharArray();
            chars[index] = ch;
            return String.valueOf(chars);
        }
    }
}

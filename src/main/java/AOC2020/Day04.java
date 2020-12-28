package AOC2020;

import lombok.Value;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 extends AbstractDay<List<Day04.Passport>> {
    public static void main(final String[] args) {
        new Day04().run();
    }

    @Override
    protected List<Passport> parseInput(final String[] rawInput) throws Exception {
        return Stream.of(String.join("\\n", rawInput).split(Pattern.quote("\\n\\n")))
            .map(Passport::new)
            .collect(Collectors.toList());
    }

    @Override
    protected Object a(final List<Passport> input) throws Exception {
        final Predicate<Passport> isValid = p -> {
            return p.getFeatures().size() == 8
                || p.getFeatures().size() == 7 && !p.getFeatures().containsKey(Feature.COUNTRY_ID);
        };

        return StreamEx.of(input)
            .filter(isValid)
            .count();
    }

    @Override
    protected Object b(final List<Passport> input) throws Exception {
        final Predicate<Passport> isValid = p -> {
            return (p.getFeatures().size() == 8
                || p.getFeatures().size() == 7 && !p.getFeatures().containsKey(Feature.COUNTRY_ID))
                && EntryStream.of(p.getFeatures()).mapKeyValue(Feature::isValidValue).allMatch(b -> b);
        };

        return StreamEx.of(input)
            .filter(isValid)
            .count();
    }

    @Value
    public static class Passport {
        Map<Feature, String> features;

        Passport(final String string) {
            features = StreamEx.split(string, "\\s|" + Pattern.quote("\\n"))
                .map(String::strip)
                .map(s -> s.split(":"))
                .mapToEntry(s -> Feature.of(s[0]), s -> s[1])
                .toMap();
        }

    }

    public enum Feature {
        BIRTH_YEAR("byr", s -> {
            int num = Integer.parseInt(s);
            return 1920 <= num && num <= 2002;
        }),
        ISSUE_YEAR("iyr", s -> {
            int num = Integer.parseInt(s);
            return 2010 <= num && num <= 2020;
        }),
        EXPIRATION_YEAR("eyr", s -> {
            int num = Integer.parseInt(s);
            return 2020 <= num && num <= 2030;
        }),
        HEIGHT("hgt", s -> {
            int num = Integer.parseInt(s.substring(0, s.length() - 2));
            if (s.endsWith("cm")) {
                return 150 <= num && num <= 193;
            } else {
                return 59 <= num && num <= 76;
            }
        }),
        HAIR_COLOR("hcl", s -> {
            return s.matches("^#[a-f0-9]{6}$");
        }),
        EYE_COLOR("ecl", s -> {
            return List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(s);
        }),
        PASSPORT_ID("pid", s -> {
            return s.matches("^\\d{9}$");
        }),
        COUNTRY_ID("cid", s -> {
            return true;
        });

        private static final Map<String, Feature> featureMap;

        private final String abbr;
        private final Predicate<String> isValid;

        static {
            featureMap = StreamEx.of(Feature.values())
                .mapToEntry(Feature::getAbbr)
                .invert()
                .toMap();
        }

        Feature(final String abbr, final Predicate<String> isValid) {
            this.abbr = abbr;
            this.isValid = isValid;
        }

        public static Feature of(final String s) {
            return featureMap.get(s);
        }

        public boolean isValidValue(final String value) {
            try {
                return isValid.test(value);
            } catch (final Exception e) {
                return false;
            }
        }

        public String getAbbr() {
            return abbr;
        }
    }
}

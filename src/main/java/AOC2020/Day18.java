package AOC2020;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Day18 extends AbstractDay<String[]> {
    public static void main(final String[] args) {
        new Day18().run();
    }

    @Override
    protected String[] parseInput(final String[] rawInput) throws Exception {
        return rawInput;
    }

    @Override
    protected Object a(final String[] input) throws Exception {
        return StreamEx.of(input)
            .map(Ast::parse)
            .mapToLong(Ast::evaluate)
            .sum();
    }

    @Override
    protected Object b(final String[] input) throws Exception {
        return StreamEx.of(input)
            .map(Ast::parseWithPrecedence)
            .mapToLong(Ast::evaluate)
            .sum();
    }

    interface Ast {
        long evaluate();

        static Ast parseWithPrecedence(final String expression) {
            final List<String> tokens = new ArrayList<>(Arrays.asList(expression
                .replaceAll("\\(", "( ")
                .replaceAll("\\)", " )")
                .split(" ")));

            for (int i = tokens.size() - 1; i >= 0; i--) {
                if ("+".equals(tokens.get(i))) {
                    tokens.add(expressionBorder(tokens, i + 1, tokens.size() - 1) + 1, ")");
                    tokens.add(expressionBorder(tokens, i - 1, 0), "(");
                }
            }

            return parse(tokens.toArray(String[]::new), 0, tokens.size() - 1);
        }

        static int expressionBorder(final List<String> tokens, final int from, final int to) {
            if (isNumber(tokens.get(from))) {
                return from;
            }

            final int increment = to - from >= 0 ? 1 : -1;
            int count = 0;
            for (int i = from; true; i += increment) {
                if (")".equals(tokens.get(i))) {
                    count += 1;
                } else if ("(".equals(tokens.get(i))) {
                    count -= 1;
                }

                if (count == 0) {
                    return i;
                }
            }
        }

        static Ast parse(final String expression) {
            final String[] tokens = expression
                .replaceAll("\\(", "( ")
                .replaceAll("\\)", " )")
                .split(" ");

            return parse(tokens, 0, tokens.length - 1);
        }

        private static Ast parse(final String[] tokens, final int start, final int end) {
            if (start == end) {
                return new Number(tokens[start]);
            }

            if (isNumber(tokens[end])) {
                return new Operation(
                    parse(tokens, start, end - 2),
                    new Number(tokens[end]),
                    TokenType.getType(tokens[end - 1])
                );
            }

            int count = 0;
            int index = 0;
            for (int i = end; i >= start; i--) {
                if (")".equals(tokens[i])) {
                    count += 1;
                } else if ("(".equals(tokens[i])) {
                    count -= 1;
                }

                if (count == 0) {
                    index = i;
                    break;
                }
            }

            if (index == start) {
                return parse(tokens, start + 1, end - 1);
            } else {
                return new Operation(
                    parse(tokens, start, index - 2),
                    parse(tokens, index + 1, end - 1),
                    TokenType.getType(tokens[index - 1])
                );
            }
        }

        static boolean isNumber(final String token) {
            return Pattern.compile("^\\d+$").asPredicate().test(token);
        }
    }

    @Value
    @RequiredArgsConstructor
    static class Number implements Ast {
        long value;

        public Number(final String token) {
            this(Integer.parseInt(token));
        }

        @Override
        public long evaluate() {
            return value;
        }

        public String toString() {
            return String.valueOf(value);
        }
    }

    @Value
    static class Operation implements Ast {
        Ast left, right;
        TokenType operation;

        @Override
        public long evaluate() {
            if (operation == TokenType.ADD) {
                return left.evaluate() + right.evaluate();
            } else {
                return left.evaluate() * right.evaluate();
            }
        }

        public String toString() {
            return String.format("(%s %s %s)", left, operation == TokenType.ADD ? "+" : "*", right);
        }
    }

    enum TokenType {
        ADD(Pattern.compile(Pattern.quote("+")).asPredicate()),
        MULT(Pattern.compile(Pattern.quote("*")).asPredicate()),
        NUMBER(Pattern.compile("\\d+").asPredicate()),
        OPEN(Pattern.compile(Pattern.quote("(")).asPredicate()),
        CLOSE(Pattern.compile(Pattern.quote(")")).asPredicate());

        private final Predicate<String> predicate;

        TokenType(final Predicate<String> predicate) {
            this.predicate = predicate;
        }

        static TokenType getType(final String s) {
            return StreamEx.of(TokenType.values())
                .findAny(type -> type.predicate.test(s))
                .orElseThrow(() -> new IllegalArgumentException("Unknown TokenType: " + s));
        }
    }
}

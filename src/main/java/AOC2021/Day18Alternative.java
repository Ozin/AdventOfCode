package AOC2021;


import java.util.Optional;

public class Day18Alternative {

    protected String a(final String[] input) throws Exception {
        return "" + SnailfishNumber.parse("[1,2]");
    }

    protected String b(final String[] input) throws Exception {
        return "" + null;
    }

    static abstract class SnailfishNumber {
        private SnailfishNumberNode parent;

        public SnailfishNumberNode add(final SnailfishNumber other) {
            return new SnailfishNumberNode(this, other);
        }

        public abstract SnailfishNumber reduce();

        public int getDepth() {
            if (parent == null) return 0;

            return 1 + parent.getDepth();
        }

        public abstract int magnitude();

        public static SnailfishNumber parse(final String number) {
            return parse(number, 0);
        }

        private static SnailfishNumber parse(final String number, final int start) {
            if (Character.isDigit(number.charAt(start))) {
                final int endOfDigits = findEndOfDigits(number, start);
                return new SnailfishNumberValue(Integer.parseInt(number.substring(start, endOfDigits)));
            }

            final int correspondingComma = findCorrespondingComma(number, start + 1);
            return new SnailfishNumberNode(
                    parse(number, start + 1),
                    parse(number, correspondingComma + 1));
        }

        private static int findCorrespondingComma(final String number, final int start) {
            if (number.charAt(start) != '[' && !Character.isDigit(number.charAt(start)))
                throw new IllegalArgumentException("must have opening bracket or number at " + start + " in " + number);

            int count = 0;
            for (int i = start; i < number.length(); i++) {
                final char currentChar = number.charAt(i);

                if (currentChar == '[') count++;
                if (currentChar == ']') count--;

                if (count == 0 && currentChar == ',') return i;
            }

            throw new IllegalArgumentException("couldn't find corresponding closing bracket at " + start + " in " + number);
        }

        private static int findEndOfDigits(final String number, final int start) {
            final int nextComma = number.indexOf(",", start);
            final int nextBracket = number.indexOf("]", start);
            if (nextComma == -1) return nextBracket;
            if (nextBracket == -1) return nextComma;
            return Math.min(nextComma, nextBracket);
        }
    }

    static class SnailfishNumberValue extends SnailfishNumber {
        int value;

        public SnailfishNumberValue(final int value) {
            this.value = value;
        }

        @Override
        public SnailfishNumber reduce() {
            if (value < 9) return new SnailfishNumberValue(value);

            return new SnailfishNumberNode(
                    new SnailfishNumberValue(value / 2),
                    new SnailfishNumberValue((value + 1) / 2)
            );
        }

        @Override
        public int magnitude() {
            return value;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    static class SnailfishNumberNode extends SnailfishNumber {
        SnailfishNumber left;
        SnailfishNumber right;

        public SnailfishNumberNode(final SnailfishNumber left, final SnailfishNumber right) {
            this.left = left;
            this.right = right;

            left.parent = this;
            right.parent = this;
        }

        @Override
        public SnailfishNumber reduce() {
            if (getDepth() < 4 || !(left instanceof SnailfishNumberValue leftV && right instanceof SnailfishNumberValue rightV))
                return new SnailfishNumberNode(left.reduce(), right.reduce());

            final SnailfishNumber current = this;
            findNextLeftValue(this).ifPresent(v -> v.value = leftV.value);
            findNextRightValue(this).ifPresent(v -> v.value = leftV.value);
            return new SnailfishNumberValue(0);

        }

        private Optional<SnailfishNumberValue> findNextRightValue(final SnailfishNumberNode snailfishNumberNode) {
            if (super.parent == null) {
                var current = this.right;
                while (current instanceof SnailfishNumberNode currentNode) {
                    current = currentNode.left;
                }
                if (current instanceof SnailfishNumberValue v) return Optional.of(v);

                return Optional.empty();
            }

            if (super.parent.left == this) {
                //findNextRightValue(super.parent.right);
            }

            return findNextRightValue(super.parent);
        }

        private Optional<SnailfishNumberValue> findNextLeftValue(final SnailfishNumberNode snailfishNumberNode) {
            if (right instanceof SnailfishNumberValue rightV) {
                return Optional.of(rightV);
            } else return null;


        }

        @Override
        public int magnitude() {
            return 3 * left.magnitude() + 2 * right.magnitude();
        }

        @Override
        public String toString() {
            return "[" + left + "," + right + "]";
        }
    }
}

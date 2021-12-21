package AOC2021;


public class Day18 {

    protected String a(final String[] input) throws Exception {
        return "" + SnailfishNumber.parse("[1,2]");
    }

    protected String b(final String[] input) throws Exception {
        return "" + null;
    }

    static abstract class SnailfishNumber {
        public SnailfishNumberNode add(SnailfishNumber other) {
            return new SnailfishNumberNode(this, other);
        }

        public SnailfishNumberNode reduce() {
            
        }

        public static SnailfishNumber parse(String number) {
            return parse(number, 0);
        }

        private static SnailfishNumber parse(String number, int start) {
            if (Character.isDigit(number.charAt(start))) {
                final int endOfDigits = findEndOfDigits(number, start);
                return new SnailfishNumberValue(Integer.parseInt(number.substring(start, endOfDigits)));
            }

            final int correspondingComma = findCorrespondingComma(number, start + 1);
            return new SnailfishNumberNode(
                    parse(number, start + 1),
                    parse(number, correspondingComma + 1));
        }

        private static int findCorrespondingComma(String number, int start) {
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

        private static int findEndOfDigits(String number, int start) {
            final int nextComma = number.indexOf(",", start);
            final int nextBracket = number.indexOf("]", start);
            if (nextComma == -1) return nextBracket;
            if (nextBracket == -1) return nextComma;
            return Math.min(nextComma, nextBracket);
        }
    }

    static class SnailfishNumberValue extends SnailfishNumber {
        int value;

        public SnailfishNumberValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    static class SnailfishNumberNode extends SnailfishNumber {
        SnailfishNumber left;
        SnailfishNumber right;

        public SnailfishNumberNode(SnailfishNumber left, SnailfishNumber right) {
            this.left = left;
            this.right = right;
        }
    }
}

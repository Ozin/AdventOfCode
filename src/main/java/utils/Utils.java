package utils;

import java.util.ArrayList;
import java.util.function.Function;

public class Utils {
    /**
     * List permutations of a string.
     *
     * @param s the input string
     * @return the list of permutations
     */
    public static ArrayList<String> permutation(final String s) {
        // The result
        ArrayList<String> res = new ArrayList<String>();
        // If input string's length is 1, return {s}
        if (s.length() == 1) {
            res.add(s);
        } else if (s.length() > 1) {
            final int lastIndex = s.length() - 1;
            // Find out the last character
            final String last = s.substring(lastIndex);
            // Rest of the string
            final String rest = s.substring(0, lastIndex);
            // Perform permutation on the rest string and
            // merge with the last character
            res = merge(permutation(rest), last);
        }
        return res;
    }

    /**
     * @param list a result of permutation, e.g. {"ab", "ba"}
     * @param c    the last character
     * @return a merged new list, e.g. {"cab", "acb" ... }
     */
    public static ArrayList<String> merge(final ArrayList<String> list, final String c) {
        final ArrayList<String> res = new ArrayList<>();
        // Loop through all the string in the list
        for (final String s : list) {
            // For each string, insert the last character to all possible positions
            // and add them to the new list
            for (int i = 0; i <= s.length(); ++i) {
                final String ps = new StringBuffer(s).insert(i, c).toString();
                res.add(ps);
            }
        }
        return res;
    }

    public static Function<Integer, Integer> multiply(final int factor) {
        return otherFactor -> factor * otherFactor;
    }

    public static Function<Long, Long> multiply(final long factor) {
        return otherFactor -> factor * otherFactor;
    }

    public static Integer add(final Integer addend1, final Integer addend2) {
        return addend1 + addend2;
    }

    public static Long add(final Long addend1, final Long addend2) {
        return addend1 + addend2;
    }
}

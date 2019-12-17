package adventOfCode2018.day02;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Day02 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day02.class.getResourceAsStream("/2018/input02a")))) {
            String[] ids = br.lines().toArray(String[]::new);

            a(ids);
            b(ids);
        }
    }

    private static void b(String[] ids) {
        for(int i = 0; i < ids.length; i++) {
            for(int j = i + 1; j < ids.length; j++) {
                if(distanceOfOne(ids[i], ids[j])) {
                    System.out.printf("Result of 02 B: %n    %s%n    %s%n", ids[i], ids[j]);
                }
            }
        }
        // StreamEx.of(ids)
        // .cross(ids)
        // .filterKeyValue(adventOfCode2018.day02.Day02::distanceOfOne)
        // .collect(Collectors.toList());
    }

    private static boolean distanceOfOne(String a, String b) {
        return distanceOfOne(a.toCharArray(), b.toCharArray());
    }

    private static boolean distanceOfOne(char[] a, char[] b) {
        boolean distanceIsOne = false;
        for(int i = 0; i < a.length; i++) {
            if(distanceIsOne && a[i] != b[i]) {
                return false;
            } else if (a[i] != b[i]) {
                distanceIsOne = true;
            }
        }

        return distanceIsOne;
    }

    private static void a(String[] ids) {
        int twos = 0;
        int threes = 0;

        for (String id : ids) {
            if (containsNTimesSameLetter(2, id)) {
                twos++;
            }

            if (containsNTimesSameLetter(3, id)) {
                threes++;
            }
        }

        System.out.printf("Result of 02 A: %d%n", twos * threes);
    }

    private static boolean containsNTimesSameLetter(int times, String id) {
        return StreamEx.split(id, "")
                .sorted()
                .runLengths()
                .mapToLong(Map.Entry::getValue)
                .anyMatch(i -> i == times);
    }


}

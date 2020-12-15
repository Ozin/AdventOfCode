package AOC2018.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Day01 {
    public static void main(String[] args) throws IOException {
        a();
        b();
    }

    private static void a() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day01.class.getResourceAsStream("/2018/input01a")))) {
            int sum = br.lines().mapToInt(Integer::parseInt).sum();
            System.out.printf("Result of 01.txt A: %d%n", sum);
        }
    }

    private static void b() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day01.class.getResourceAsStream("/2018/input01a")))) {
            Set<Integer> intermediates = new HashSet<>();
            var frequencies = br.lines().mapToInt(Integer::parseInt).toArray();
            var sum = 0;

            for(int i = 0; ; i++) {
                sum += frequencies[i % frequencies.length];
                if(intermediates.contains(sum)) {
                    System.out.printf("Result of 01.txt B. frequency: %d, iterations: %d%n", sum, i);
                    break;
                } else {
                    intermediates.add(sum);
                }
            }
        }
    }


}

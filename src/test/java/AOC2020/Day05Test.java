package AOC2020;

import static org.junit.Assert.assertArrayEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.junit.Test;

public class Day05Test {

    Day05 day05 = new Day05();

    @Test
    public void a1() throws Exception {
        final Day05.Seat[] seats = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/2020/day05-01.txt")))
            .lines()
            .map(Day05.Seat::new)
            .toArray(Day05.Seat[]::new);

        assertArrayEquals(new Day05.Seat[] {
            new Day05.Seat(70, 7),
            new Day05.Seat(14, 7),
            new Day05.Seat(102, 4),
            new Day05.Seat(44, 5)
        }, seats);
    }
}

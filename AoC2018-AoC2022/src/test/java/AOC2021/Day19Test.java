package AOC2021;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

public class Day19Test implements AocTest {
    Day19 day19 = new Day19();

    @Test
    public void testOrientation() {
        // GIVEN
        final List<Day19.Scanner> scanners = day19.getScanners("""
                --- scanner 0 ---
                -1,-1,1
                -2,-2,2
                -3,-3,3
                -2,-3,1
                5,6,-4
                8,0,7
                                
                --- scanner 0 ---
                1,-1,1
                2,-2,2
                3,-3,3
                2,-1,3
                -5,4,-6
                -8,-7,0
                                
                --- scanner 0 ---
                -1,-1,-1
                -2,-2,-2
                -3,-3,-3
                -1,-3,-2
                4,6,5
                -7,0,8
                                
                --- scanner 0 ---
                1,1,-1
                2,2,-2
                3,3,-3
                1,3,-2
                -4,-6,5
                7,0,8
                                
                --- scanner 0 ---
                1,1,1
                2,2,2
                3,3,3
                3,1,2
                -6,-4,-5
                0,7,-8""".stripIndent().split("\n"));

        // WHEN
        final Day19.Scanner firstScanner = scanners.head();
        // final List<Integer> integers = scanners.map(firstScanner::countOverlap).toList();
        // System.out.println(integers.mkString("\n"));


        // THEN
    }
}

package AOC2021;

import io.vavr.Function2;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day19Test implements AocTest {
    Day19 day19 = new Day19();

    @Test
    public void testOrientation() {
        // GIVEN
        final List<Day19.Scanner> scanners = day19.getScanners(smallExample);

        // WHEN
        final var firstScannerRotations = scanners.get(0).rotations();
        // System.out.println(integers.mkString("\n"));

        // THEN
        assertTrue(firstScannerRotations.containsAll(scanners));
    }

    @Test
    void test1() {
        // GIVEN
        final List<Day19.Scanner> scanners = day19.getScanners(bigExample);

        // WHEN
        final Day19.Scanner a = scanners.get(0);
        final Day19.Scanner b = scanners.get(1);

        // final Day19.Scanner scannerRelativeToZero = a.combineWith(b);

        final Day19.AlignedScanner alignedScanner = a.searchOverlap(b).getOrElseThrow(NullPointerException::new);
        var result = alignedScanner.overlapped().map(String::valueOf);

        // THEN
        assertEquals("68,-1246,-43", alignedScanner.position().toString());
        assertIterableEquals(HashSet.of("""
                -618,-824,-621
                -537,-823,-458
                -447,-329,318
                404,-588,-901
                544,-627,-890
                528,-643,409
                -661,-816,-575
                390,-675,-793
                423,-701,434
                -345,-311,381
                459,-707,401
                -485,-357,347""".stripIndent().split("\n")), result);
    }

    @Test
    void test2() {
        // GIVEN
        final List<Day19.Scanner> scanners = day19.getScanners(bigExample);

        // WHEN
        final Day19.Scanner a = scanners.get(0);
        final Day19.Scanner b = scanners.get(1);

        // final Day19.Scanner scannerRelativeToZero = a.combineWith(b);

        var result = b.searchOverlap(a)
                      .getOrElseThrow(NullPointerException::new)
                      .overlapped()
                      .map(Day19.Beacon::toString);

        // THEN
        assertEquals(HashSet.of("""
                686,422,578
                605,423,415
                515,917,-361
                -336,658,858
                -476,619,847
                -460,603,-452
                729,430,532
                -322,571,750
                -355,545,-477
                413,935,-424
                -391,539,-444
                553,889,-390""".stripIndent().split("\n")), result);
    }

    @Test
    @Disabled
    void test3() {
        // GIVEN
        final List<Day19.Scanner> scanners = day19.getScanners(bigExample);

        // WHEN
        final Day19.Scanner a = scanners.get(4);
        final Day19.Scanner b = scanners.get(1);

        // final Day19.Scanner scannerRelativeToZero = a.combineWith(b);

        final var superScanner = scanners.crossProduct()
                                                     .map(Function2.of(Day19.Scanner::combineWith).tupled())
                                                     .reduceLeft(Day19.Scanner::combineWith)
                                                     .searchOverlap(scanners.get(1))
                                                     .get()
                                                     .overlapped();
        var result = superScanner;

        // THEN

        assertEquals(List.of("""
                459,-707,401
                -739,-1745,668
                -485,-357,347
                432,-2009,850
                528,-643,409
                423,-701,434
                -345,-311,381
                408,-1815,803
                534,-1912,768
                -687,-1600,576
                -447,-329,318
                -635,-1737,486""".stripIndent().split("\n")).sorted(), result.toList().sorted());
    }

    @Test
    void test4() {
        // GIVEN
        final List<Day19.Scanner> scanners = day19.getScanners(bigExample);

        // WHEN
        var result = Day19.Scanner.combine(scanners);

        // THEN
        assertEquals(79, result.beacons().size());
        assertEquals(
                List.of(prooveTest).sorted().mkString("\n"),
                result.beacons().map(Day19.Beacon::toString).toList().sorted().mkString("\n")
        );
    }

    final static String[] prooveTest = """
            -892,524,684
            -876,649,763
            -838,591,734
            -789,900,-551
            -739,-1745,668
            -706,-3180,-659
            -697,-3072,-689
            -689,845,-530
            -687,-1600,576
            -661,-816,-575
            -654,-3158,-753
            -635,-1737,486
            -631,-672,1502
            -624,-1620,1868
            -620,-3212,371
            -618,-824,-621
            -612,-1695,1788
            -601,-1648,-643
            -584,868,-557
            -537,-823,-458
            -532,-1715,1894
            -518,-1681,-600
            -499,-1607,-770
            -485,-357,347
            -470,-3283,303
            -456,-621,1527
            -447,-329,318
            -430,-3130,366
            -413,-627,1469
            -345,-311,381
            -36,-1284,1171
            -27,-1108,-65
            7,-33,-71
            12,-2351,-103
            26,-1119,1091
            346,-2985,342
            366,-3059,397
            377,-2827,367
            390,-675,-793
            396,-1931,-563
            404,-588,-901
            408,-1815,803
            423,-701,434
            432,-2009,850
            443,580,662
            455,729,728
            456,-540,1869
            459,-707,401
            465,-695,1988
            474,580,667
            496,-1584,1900
            497,-1838,-617
            527,-524,1933
            528,-643,409
            534,-1912,768
            544,-627,-890
            553,345,-567
            564,392,-477
            568,-2007,-577
            605,-1665,1952
            612,-1593,1893
            630,319,-379
            686,-3108,-505
            776,-3184,-501
            846,-3110,-434
            1135,-1161,1235
            1243,-1093,1063
            1660,-552,429
            1693,-557,386
            1735,-437,1738
            1749,-1800,1813
            1772,-405,1572
            1776,-675,371
            1779,-442,1789
            1780,-1548,337
            1786,-1538,337
            1847,-1591,415
            1889,-1729,1762
            1994,-1805,1792""".stripIndent().trim().split("\n");

    final static String[] smallExample = """
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
            0,7,-8""".stripIndent().split("\n");

    final static String[] bigExample = """
            --- scanner 0 ---
            404,-588,-901
            528,-643,409
            -838,591,734
            390,-675,-793
            -537,-823,-458
            -485,-357,347
            -345,-311,381
            -661,-816,-575
            -876,649,763
            -618,-824,-621
            553,345,-567
            474,580,667
            -447,-329,318
            -584,868,-557
            544,-627,-890
            564,392,-477
            455,729,728
            -892,524,684
            -689,845,-530
            423,-701,434
            7,-33,-71
            630,319,-379
            443,580,662
            -789,900,-551
            459,-707,401
                        
            --- scanner 1 ---
            686,422,578
            605,423,415
            515,917,-361
            -336,658,858
            95,138,22
            -476,619,847
            -340,-569,-846
            567,-361,727
            -460,603,-452
            669,-402,600
            729,430,532
            -500,-761,534
            -322,571,750
            -466,-666,-811
            -429,-592,574
            -355,545,-477
            703,-491,-529
            -328,-685,520
            413,935,-424
            -391,539,-444
            586,-435,557
            -364,-763,-893
            807,-499,-711
            755,-354,-619
            553,889,-390
                        
            --- scanner 2 ---
            649,640,665
            682,-795,504
            -784,533,-524
            -644,584,-595
            -588,-843,648
            -30,6,44
            -674,560,763
            500,723,-460
            609,671,-379
            -555,-800,653
            -675,-892,-343
            697,-426,-610
            578,704,681
            493,664,-388
            -671,-858,530
            -667,343,800
            571,-461,-707
            -138,-166,112
            -889,563,-600
            646,-828,498
            640,759,510
            -630,509,768
            -681,-892,-333
            673,-379,-804
            -742,-814,-386
            577,-820,562
                        
            --- scanner 3 ---
            -589,542,597
            605,-692,669
            -500,565,-823
            -660,373,557
            -458,-679,-417
            -488,449,543
            -626,468,-788
            338,-750,-386
            528,-832,-391
            562,-778,733
            -938,-730,414
            543,643,-506
            -524,371,-870
            407,773,750
            -104,29,83
            378,-903,-323
            -778,-728,485
            426,699,580
            -438,-605,-362
            -469,-447,-387
            509,732,623
            647,635,-688
            -868,-804,481
            614,-800,639
            595,780,-596
                        
            --- scanner 4 ---
            727,592,562
            -293,-554,779
            441,611,-461
            -714,465,-776
            -743,427,-804
            -660,-479,-426
            832,-632,460
            927,-485,-438
            408,393,-506
            466,436,-512
            110,16,151
            -258,-428,682
            -393,719,612
            -211,-452,876
            808,-476,-593
            -575,615,604
            -485,667,467
            -680,325,-822
            -627,-443,-432
            872,-547,-609
            833,512,582
            807,604,487
            839,-516,451
            891,-625,532
            -652,-548,-490
            30,-46,-14
            """.stripIndent().split("\n");
}

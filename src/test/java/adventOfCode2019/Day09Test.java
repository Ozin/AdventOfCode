package adventOfCode2019;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day09Test {
    Day09 day = new Day09();

    @Test
    public void a() throws Exception {
        assertEquals(3497884671L, day.getA());
    }

    @Test
    public void b() throws Exception {
        String jafraString = "\n" +
                "  ##  ##  #### ###   ##  \n" +
                "   # #  # #    #  # #  # \n" +
                "   # #  # ###  #  # #  # \n" +
                "   # #### #    ###  #### \n" +
                "#  # #  # #    # #  #  # \n" +
                " ##  #  # #    #  # #  # \n";
        assertEquals(jafraString, day.getB());
    }
}
package AOC2019;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day08Test {
    Day08 day = new Day08();

    @Test
    public void a() throws Exception {
        assertEquals(1806L, day.getA());
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

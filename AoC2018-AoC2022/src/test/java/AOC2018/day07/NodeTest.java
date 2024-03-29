package AOC2018.day07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {
    @Test
    public void A() {
        // GIVEN
        Node a = new Node("A");

        // WHEN

        // THEN
        assertEquals(61, a.getTimeToFinish());
    }
    @Test
    public void Z() {
        // GIVEN
        Node a = new Node("Z");

        // WHEN

        // THEN
        assertEquals(60 + 26, a.getTimeToFinish());
    }
}

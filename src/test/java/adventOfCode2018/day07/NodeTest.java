package adventOfCode2018.day07;

import org.junit.Test;

import static org.junit.Assert.*;

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
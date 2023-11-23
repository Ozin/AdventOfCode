package AOC2022;

import AOC2022.Day13.Item;
import AOC2022.Day13.Item.IntegerItem;
import AOC2022.Day13.Item.ListItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day13Test {
    @Test
    void test01() {
        // GIVEN
        final String input = "[1,1,3,1,1]";
        final ListItem expected = new ListItem(
                new IntegerItem(1),
                new IntegerItem(1),
                new IntegerItem(3),
                new IntegerItem(1),
                new IntegerItem(1)
        );

        // WHEN
        final Item actual = Item.parse(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void test02() {
        // GIVEN
        final String input = "[[1],[2,3,4]]";
        final ListItem expected = new ListItem(
                new ListItem(new IntegerItem(1)),
                new ListItem(new IntegerItem(2), new IntegerItem(3), new IntegerItem(4))
        );

        // WHEN
        final Item actual = Item.parse(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void test03() {
        // GIVEN
        final String input = "[[8,7,6]]";
        final ListItem expected = new ListItem(new ListItem(
                new IntegerItem(8),
                new IntegerItem(7),
                new IntegerItem(6)
        ));

        // WHEN
        final Item actual = Item.parse(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void test04() {
        // GIVEN
        final String input = "[[4,4],4,4]";
        final ListItem expected = new ListItem(
                new ListItem(new IntegerItem(4), new IntegerItem(4)),
                new IntegerItem(4),
                new IntegerItem(4)
        );

        // WHEN
        final Item actual = Item.parse(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void test05_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[1,1,3,1,1]");
        final Item i2 = Item.parse("[1,1,5,1,1]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual < 0);
    }

    @Test
    void test06_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[[1],[2,3,4]]");
        final Item i2 = Item.parse("[[1],4]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual < 0);
    }

    @Test
    void test07_NOT_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[9]");
        final Item i2 = Item.parse("[[8,7,6]]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual > 0);
    }

    @Test
    void test08_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[[4,4],4,4]");
        final Item i2 = Item.parse("[[4,4],4,4,4]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual < 0);
    }

    @Test
    void test09_NOT_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[7,7,7,7]");
        final Item i2 = Item.parse("[7,7,7]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual > 0);
    }

    @Test
    void test10_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[]");
        final Item i2 = Item.parse("[3]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual < 0);
    }

    @Test
    void test11_NOT_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[[[]]]");
        final Item i2 = Item.parse("[[]]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual > 0);
    }

    @Test
    void test12_NOT_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[1,[2,[3,[4,[5,6,7]]]],8,9]");
        final Item i2 = Item.parse("[1,[2,[3,[4,[5,6,0]]]],8,9]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual > 0);
    }

    @Test
    void test13_NOT_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[[[8],2,0]]");
        final Item i2 = Item.parse("[[8,[],[8,[],[10,4,4,1]]],[[9,10]],[3,9,3,3]]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual > 0);
    }

    @Test
    void test14_NOT_right_order() {
        // GIVEN
        final Item i1 = Item.parse(
                "[[],[[[1,0,9],[1,4,1,0],[1,3],5,[9,10,5,1,0]],9,[[7,8,9],[6,5,0,8,6],10,[9,8,8],10],[8,4,[]],[]]]");
        final Item i2 = Item.parse("[[]]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual > 0);
    }

    @Test
    void test15_NOT_right_order() {
        // GIVEN
        final Item i1 = Item.parse("[[],[[6,[6,3,0,9,10],2,7,0]],[[[6,4,0,0,7],1,0],8]]");
        final Item i2 = Item.parse(
                "[[],[0,5,[5,9,10,[6,0,1,0,5]],[[6,5,6,5]],[[0,1]]],[[[],8,[]],[[2,2],2,[1,4,6,9],[],10],5,6,[[3,0],4,7,[3,6,9]]],[[9]]]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual > 0);
    }

    @Test
    void test16_should_be_equal() {
        // GIVEN
        final Item i1 = Item.parse("[]");
        final Item i2 = Item.parse("[]");


        // WHEN
        final int actual = i1.compareTo(i2);

        // THEN
        assertTrue(actual == 0);
    }

    @Test
    void test16_emptyList() {
        // GIVEN
        final var i1 = "[]";

        // WHEN
        final var actual = Item.parse(i1);

        // THEN
        assertEquals(new ListItem(), actual);
    }
}
package AOC2021;

import AOC2021.day16.packet.LiteralPacket;
import AOC2021.day16.packet.OperatorPacket;
import AOC2021.day16.packet.Packet;
import AOC2021.day16.packet.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PacketTest {
    @Test
    void test01() {
        // GIVEN
        String hex = "D2FE28";

        // WHEN
        Packet p = Packet.of(Packet.hexToBin(hex));

        // THEN
        assertInstanceOf(LiteralPacket.class, p);
        assertEquals("110100101111111000101000", p.getInput());
        assertEquals(0, p.getInitialIndex());
        assertEquals(2021, p.getValue());
        assertEquals(21, p.getNextIndex());
        assertEquals(Type.LITERAL, p.getType());
        assertEquals(6, p.getVersion());
        assertEquals(6, p.getVersion());
    }

    @Test
    void test02() {
        // GIVEN
        String hex = "38006F45291200";

        // WHEN
        OperatorPacket p = (OperatorPacket) Packet.of(Packet.hexToBin(hex));

        // THEN
        assertInstanceOf(OperatorPacket.class, p);
        assertEquals("00111000000000000110111101000101001010010001001000000000", p.getInput());
        assertEquals(0, p.getInitialIndex());
        assertEquals(1, p.getVersion());
        assertEquals(Type.OPERATOR, p.getType());
        assertEquals(0, p.getLengthTypeId());
        assertEquals(49, p.getNextIndex());
        assertEquals(27, p.getSubPacketsLength());
        assertEquals(2, p.getSubPackets().size());
        assertEquals(10, p.getSubPackets().get(0).getValue());
        assertEquals(20, p.getSubPackets().get(1).getValue());
    }

    @Test
    void test03() {
        // GIVEN
        String hex = "EE00D40C823060";

        // WHEN
        OperatorPacket p = (OperatorPacket) Packet.of(Packet.hexToBin(hex));

        // THEN
        assertInstanceOf(OperatorPacket.class, p);
        assertEquals("11101110000000001101010000001100100000100011000001100000", p.getInput());
        assertEquals(0, p.getInitialIndex());
        assertEquals(7, p.getVersion());
        assertEquals(Type.OPERATOR2, p.getType());
        assertEquals(1, p.getLengthTypeId());
        assertEquals(51, p.getNextIndex());
        assertEquals(3, p.getSubPacketsLength());
        assertEquals(3, p.getSubPackets().size());
        assertEquals(1, p.getSubPackets().get(0).getValue());
        assertEquals(2, p.getSubPackets().get(1).getValue());
        assertEquals(3, p.getSubPackets().get(2).getValue());
    }
}

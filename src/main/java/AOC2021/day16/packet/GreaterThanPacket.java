package AOC2021.day16.packet;

import java.util.List;

public class GreaterThanPacket extends OperatorPacket {

    public GreaterThanPacket(String input, Integer initialIndex) {
        super(input, initialIndex);
    }

    @Override
    public long getValue() {
        final List<Packet> subPackets = getSubPackets();
        return subPackets.get(0).getValue() > subPackets.get(1).getValue() ? 1L : 0L;
    }
}

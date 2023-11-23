package AOC2021.day16.packet;

public class MaximumPacket extends OperatorPacket {

    public MaximumPacket(String input, Integer initialIndex) {
        super(input, initialIndex);
    }

    @Override
    public long getValue() {
        return getSubPackets().stream().mapToLong(Packet::getValue).max().getAsLong();
    }
}

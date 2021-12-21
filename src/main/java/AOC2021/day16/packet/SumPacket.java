package AOC2021.day16.packet;

public class SumPacket extends OperatorPacket {

    public SumPacket(String input, Integer initialIndex) {
        super(input, initialIndex);
    }

    @Override
    public long getValue() {
        return getSubPackets().stream().mapToLong(Packet::getValue).sum();
    }
}

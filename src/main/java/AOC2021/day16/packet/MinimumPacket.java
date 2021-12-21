package AOC2021.day16.packet;

public class MinimumPacket extends OperatorPacket {

    public MinimumPacket(String input, Integer initialIndex) {
        super(input, initialIndex);
    }

    @Override
    public long getValue() {
        return getSubPackets().stream().mapToLong(Packet::getValue).min().getAsLong();
    }
}

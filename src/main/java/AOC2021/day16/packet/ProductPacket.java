package AOC2021.day16.packet;

public class ProductPacket extends OperatorPacket {

    public ProductPacket(String input, Integer initialIndex) {
        super(input, initialIndex);
    }

    @Override
    public long getValue() {
        return getSubPackets().stream().mapToLong(Packet::getValue).reduce((a, b) -> a * b).getAsLong();
    }
}

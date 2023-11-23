package AOC2021.day16.packet;

import java.util.List;

public class ConstantPacket extends OperatorPacket {
    public ConstantPacket(String input, Integer initialIndex) {
        super(input, initialIndex);
    }

    @Override
    public int getNextIndex() {
        return getInitialIndex() + 6 + (Long.toString(getValue(), 2).length() + 3) / 4 * 5;
    }

    @Override
    public long getValue() {
        StringBuilder sb = new StringBuilder();
        int index = getInitialIndex() + 6;
        do {
            sb.append(Packet.readString(getInput(), index + 1, 4));
            index += 5;
        } while (getInput().charAt(index - 5) == '1');
        return Long.parseLong(sb.toString(), 2);
    }

    @Override
    public List<Packet> getSubPackets() {
        return List.of();
    }
}

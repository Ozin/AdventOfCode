package AOC2021.day16.packet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OperatorPacket implements Packet {
    private String input;
    private Integer initialIndex;

    public OperatorPacket(String input, Integer initialIndex) {
        this.input = input;
        this.initialIndex = initialIndex;
    }

    @Override
    public int getInitialIndex() {
        return initialIndex;
    }

    @Override
    public String getInput() {
        return input;
    }

    @Override
    public int getNextIndex() {
        if (getLengthTypeId() == 0) return initialIndex + 7 + 15 + getSubPacketsLength();
        else {
            final List<Packet> subPackets = getSubPackets();
            return subPackets.get(subPackets.size() - 1).getNextIndex();
        }
    }

    @Override
    public long getValue() {
        return 0;
    }

    @Override
    public Stream<Packet> traverseTree() {
        return Stream.concat(
                Stream.of(this),
                getSubPackets().stream().flatMap(Packet::traverseTree)
        );
    }

    public int getSubPacketsLength() {
        if (getLengthTypeId() == 0) {
            return Packet.readInt(input, initialIndex + 7, 15);
        } else if (getLengthTypeId() == 1)
            return Packet.readInt(input, initialIndex + 7, 11);

        throw new IllegalArgumentException("unsupported length type: " + getLengthTypeId());
    }

    public List<Packet> getSubPackets() {
        List<Packet> subPackets = new ArrayList<>();

        if (getLengthTypeId() == 0) {
            for (int index = initialIndex + 7 + 15; index < initialIndex + 7 + 15 + getSubPacketsLength(); ) {
                final Packet newPacket = Packet.of(index, input);

                index = newPacket.getNextIndex();
                subPackets.add(newPacket);
            }
        } else {
            int index = initialIndex + 7 + 11;
            for (int i = 0; i < getSubPacketsLength(); i++) {
                final Packet newPacket = Packet.of(index, input);

                index = newPacket.getNextIndex();
                subPackets.add(newPacket);
            }
        }

        return subPackets;
    }

    public int getLengthTypeId() {
        return Packet.readInt(getInput(), getInitialIndex() + 6, 1);
    }
}

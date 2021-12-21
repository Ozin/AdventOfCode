package AOC2021.day16.packet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public interface Packet {
    static Packet of(String binaryInput) {
        return of(0, binaryInput);
    }

    static Packet of(int initialIndex, String binaryInput) {
        return Type.forId(readInt(binaryInput, initialIndex + 3, 3))
                .createPacket(binaryInput, initialIndex);
    }

    static int readInt(final String input, final int index, final int length) {
        return Integer.parseInt(readString(input, index, length), 2);
    }

    static String readString(String input, int index, int length) {
        return input.substring(index, index + length);
    }

    static String hexToBin(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 4) {
            final String hexSubstring = hex.substring(i, Math.min(hex.length(), i + 4));
            final String bin = Integer.toBinaryString(Integer.parseInt(hexSubstring, 16));
            sb.append("0".repeat(hexSubstring.length() * 4 - bin.length()));
            sb.append(bin);
        }
        return sb.toString();
    }

    int getInitialIndex();

    String getInput();

    default int getVersion() {
        return readInt(getInput(), getInitialIndex(), 3);
    }

    default Type getType() {
        return Type.forId(readInt(getInput(), getInitialIndex() + 3, 3));
    }

    default int getNextIndex() {
        if (getLengthTypeId() == 0) return getInitialIndex() + 7 + 15 + getSubPacketsLength();
        else {
            final List<Packet> subPackets = getSubPackets();
            return subPackets.get(subPackets.size() - 1).getNextIndex();
        }
    }

    default Stream<Packet> traverseTree() {
        return Stream.concat(
                Stream.of(this),
                getSubPackets().stream().flatMap(Packet::traverseTree)
        );
    }

    default int getSubPacketsLength() {
        if (getLengthTypeId() == 0) {
            return Packet.readInt(getInput(), getInitialIndex() + 7, 15);
        } else if (getLengthTypeId() == 1)
            return Packet.readInt(getInput(), getInitialIndex() + 7, 11);

        throw new IllegalArgumentException("unsupported length type: " + getLengthTypeId());
    }

    default List<Packet> getSubPackets() {
        List<Packet> subPackets = new ArrayList<>();

        if (getLengthTypeId() == 0) {
            for (int index = getInitialIndex() + 7 + 15; index < getInitialIndex() + 7 + 15 + getSubPacketsLength(); ) {
                final Packet newPacket = Packet.of(index, getInput());

                index = newPacket.getNextIndex();
                subPackets.add(newPacket);
            }
        } else {
            int index = getInitialIndex() + 7 + 11;
            for (int i = 0; i < getSubPacketsLength(); i++) {
                final Packet newPacket = Packet.of(index, getInput());

                index = newPacket.getNextIndex();
                subPackets.add(newPacket);
            }
        }

        return subPackets;
    }

    default int getLengthTypeId() {
        return Packet.readInt(getInput(), getInitialIndex() + 6, 1);
    }

    long getValue();
}

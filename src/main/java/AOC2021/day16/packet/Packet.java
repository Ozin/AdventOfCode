package AOC2021.day16.packet;

import java.util.stream.Stream;

public interface Packet {
    static Packet of(String binaryInput) {
        return of(0, binaryInput);
    }

    static Packet of(int initialIndex, String binaryInput) {
        return Type.forId(readInt(binaryInput, initialIndex + 3, 3))
                .createPacket(binaryInput, initialIndex);
    }

    int getInitialIndex();

    String getInput();

    int getNextIndex();

    default int getVersion() {
        return readInt(getInput(), getInitialIndex(), 3);
    }

    default Type getType() {
        return Type.forId(readInt(getInput(), getInitialIndex() + 3, 3));
    }

    long getValue();

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

    Stream<Packet> traverseTree();
}

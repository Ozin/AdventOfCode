package AOC2021.day16.packet;

import java.util.stream.Stream;

public class LiteralPacket implements Packet {
    private String input;
    private Integer initialIndex;

    public LiteralPacket(String input, Integer initialIndex) {
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
        return initialIndex + 6 + (Long.toString(getValue(), 2).length() + 3) / 4 * 5;
    }

    @Override
    public long getValue() {
        StringBuilder sb = new StringBuilder();
        int index = initialIndex + 6;
        do {
            sb.append(Packet.readString(input, index + 1, 4));
            index += 5;
        } while (input.charAt(index - 5) == '1');
        return Long.parseLong(sb.toString(), 2);
    }

    @Override
    public Stream<Packet> traverseTree() {
        return Stream.of(this);
    }
}

package AOC2021.day16.packet;

public abstract class OperatorPacket implements Packet {
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
}

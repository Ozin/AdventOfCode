package AOC2021;


import AOC2021.day16.packet.Packet;

public class Day16 {

    protected String a(final String[] input) throws Exception {
        final Packet root = Packet.of(Packet.hexToBin(input[0]));
        return "" + root.traverseTree().mapToInt(Packet::getVersion).sum();
    }

    protected String b(final String[] input) throws Exception {
        final Packet root = Packet.of(Packet.hexToBin(input[0]));
        return "" + root.getValue();
    }

}

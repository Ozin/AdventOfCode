package AOC2021.day16.packet;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum Type {
    LITERAL(4, LiteralPacket::new),
    OPERATOR(6, OperatorPacket::new),
    OPERATOR3(2, OperatorPacket::new),
    OPERATOR4(0, OperatorPacket::new),
    OPERATOR5(5, OperatorPacket::new),
    OPERATOR6(1, OperatorPacket::new),
    OPERATOR7(7, OperatorPacket::new),
    OPERATOR2(3, OperatorPacket::new);

    private final int id;
    private final static Map<Integer, Type> typesForId = new HashMap<>();
    private final BiFunction<String, Integer, Packet> constructor;

    Type(final int id, BiFunction<String, Integer, Packet> constructor) {
        this.id = id;
        this.constructor = constructor;
    }

    public static Type forId(final int id) {
        if (typesForId.isEmpty())
            Stream.of(values())
                    .forEach(t -> typesForId.put(t.id, t));

        final Type type = typesForId.get(id);

        if (type == null) throw new IllegalArgumentException("Type for id " + id + " does not exist");

        return type;
    }

    public Packet createPacket(String input, int initialIndex) {
        return constructor.apply(input, initialIndex);
    }
}

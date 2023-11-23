package AOC2021.day16.packet;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum Type {
    SUM(0, SumPacket::new),
    PRODUCT(1, ProductPacket::new),
    MINIMUM(2, MinimumPacket::new),
    MAXIMUM(3, MaximumPacket::new),
    CONSTANT(4, ConstantPacket::new),
    GREATER_THAN(5, GreaterThanPacket::new),
    LESS_THAN(6, LessThanPacket::new),
    EQUAL_TO(7, EqualToPacket::new),
    ;

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

package adventOfCode2018.day15;

import one.util.streamex.StreamEx;

import java.util.Map;
import java.util.function.Function;

public enum Type {
    WALL("#"),
    ELF("E"),
    GOBLIN("G"),
    PATH(".");

    String stringRepresentation;
    static Map<String, Type> representations = StreamEx.of(Type.values()).toMap(Type::toString, Function.identity());

    Type(final String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public static Type from(final String s) {
        Type type = representations.get(s);

        if (type == null) {
            throw new IllegalArgumentException(s + " is no valid cell type");
        }

        return type;
    }

    public boolean isCreature() {
        switch (this) {
            case ELF:
            case GOBLIN:
                return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return this.stringRepresentation;
    }
}

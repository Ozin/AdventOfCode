package utils;

import lombok.Value;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Value
public class Indexed<T> {
    int index;
    T value;

    public Map.Entry<Integer, T> toEntry() {
        return Map.entry(index, value);
    }

    public static <T> Function<T, Indexed<T>> map() {
        final AtomicInteger counter = new AtomicInteger();

        return value -> new Indexed<>(counter.getAndIncrement(), value);
    }
}

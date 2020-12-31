package utils;

import lombok.Value;

@Value
public class Tuple2<T, G> {
    T a;
    G b;
}

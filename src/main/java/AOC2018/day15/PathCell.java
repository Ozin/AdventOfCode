package AOC2018.day15;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;

@Getter
@Setter
@RequiredArgsConstructor
public class PathCell {
    public static final Comparator<? super PathCell> READING_ORDER = Comparator
            .comparingInt(PathCell::getY)
            .thenComparingInt(PathCell::getX);

    private final int x;
    private final int y;
    private final Type type;

    private PathCell parent;
    private int gCost = Integer.MAX_VALUE;

    public int getFCost(final PathCell target) {
        return getGCost() + getHCost(target);
    }

    public int getHCost(final PathCell target) {
        return Math.abs(target.x - this.x) + Math.abs(target.y - this.y);
    }

    public Stream<PathCell> stream() {
        return Stream.concat(
                Optional.ofNullable(parent).filter(p -> this != p).stream().flatMap(PathCell::stream),
                Stream.of(this)
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PathCell pathCell = (PathCell) o;
        return x == pathCell.x &&
                y == pathCell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PathCell.class.getSimpleName() + "[", "]")
                .add("x=" + x)
                .add("y=" + y)
                .add("type=" + type)
                .toString();
    }
}

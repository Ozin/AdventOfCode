package utils;

import lombok.Value;
import lombok.With;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@With
@Value
public class Point4D implements Comparable<Point4D> {
    int x, y, z, w;

    public Point4D addX(final int dx) {
        return withX(dx + x);
    }

    public Point4D addY(final int dy) {
        return withY(dy + y);
    }

    public Point4D addZ(final int dz) {
        return withZ(dz + z);
    }

    public Point4D addW(final int dz) {
        return withW(dz + w);
    }

    private final static Comparator<Point4D> COMPARATOR = Comparator.comparingInt(Point4D::getZ)
        .thenComparingInt(Point4D::getX)
        .thenComparingInt(Point4D::getY)
        .thenComparingInt(Point4D::getW);

    public Set<Point4D> getNeighbors() {
        return getNeighborsIncludingSelf()
            .stream()
            .filter(not(this::equals))
            .collect(Collectors.toSet());
    }

    public Set<Point4D> getNeighborsIncludingSelf() {
        final Set<Point4D> neighbors = new HashSet<>(81);
        for (int dx = -1; dx <= 1; dx++) {
            final Point4D newX = this.addX(dx);
            for (int dy = -1; dy <= 1; dy++) {
                final Point4D newY = newX.addY(dy);
                for (int dz = -1; dz <= 1; dz++) {
                    final Point4D newZ = newY.addZ(dz);
                    for (int dw = -1; dw <= 1; dw++) {
                        final Point4D next = newZ.addW(dw);
                        neighbors.add(next);
                    }
                }
            }
        }

//            neighbors.add(this.addX(1));
//            neighbors.add(this.addX(-1));
//            neighbors.add(this.addY(1));
//            neighbors.add(this.addY(-1));
//            neighbors.add(this.addZ(1));
//            neighbors.add(this.addZ(-1));
//            neighbors.add(this);

        return neighbors;
    }

    @Override
    public int compareTo(final Point4D o) {
        return COMPARATOR.compare(this, o);
    }

}

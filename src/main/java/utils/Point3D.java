package utils;

import lombok.Value;
import lombok.With;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Value
@With
public class Point3D implements Comparable<Point3D> {
    int x, y, z;

    public Point3D addX(final int dx) {
        return withX(dx + x);
    }

    public Point3D addY(final int dy) {
        return withY(dy + y);
    }

    public Point3D addZ(final int dz) {
        return withZ(dz + z);
    }

    private final static Comparator<Point3D> COMPARATOR = Comparator.comparingInt(Point3D::getZ)
        .thenComparingInt(Point3D::getX)
        .thenComparingInt(Point3D::getY);

    public Set<Point3D> getNeighbors() {
        return getNeighborsIncludingSelf()
            .stream()
            .filter(not(this::equals))
            .collect(Collectors.toSet());
    }

    public Set<Point3D> getNeighborsIncludingSelf() {
        final Set<Point3D> neighbors = new HashSet<>(27);
        for (int dx = -1; dx <= 1; dx++) {
            final Point3D newX = this.addX(dx);
            for (int dy = -1; dy <= 1; dy++) {
                final Point3D newY = newX.addY(dy);
                for (int dz = -1; dz <= 1; dz++) {
                    final Point3D next = newY.addZ(dz);
                    neighbors.add(next);
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
    public int compareTo(final Point3D o) {
        return COMPARATOR.compare(this, o);
    }

    public static void printCubes(final Collection<Point3D> point3DS) {
        final Range xs = point3DS.stream().map(Point3D::getX).reduce(Range.empty(), Range::extendWith, Range::union);
        final Range ys = point3DS.stream().map(Point3D::getY).reduce(Range.empty(), Range::extendWith, Range::union);
        final Range zs = point3DS.stream().map(Point3D::getZ).reduce(Range.empty(), Range::extendWith, Range::union);

        for (int z = zs.getLower(); z <= zs.getUpper(); z++) {
            System.out.println("z=" + z);
            for (int y = ys.getLower(); y <= ys.getUpper(); y++) {
                for (int x = xs.getLower(); x <= xs.getUpper(); x++) {
                    System.out.print(point3DS.contains(new Point3D(x, y, z)) ? "#" : ".");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}

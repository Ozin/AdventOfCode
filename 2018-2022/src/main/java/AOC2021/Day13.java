package AOC2021;


import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import lombok.Value;
import lombok.val;
import utils.Point;

import java.util.function.BiFunction;
import java.util.function.Function;


public class Day13 {

    protected String a(final String[] input) throws Exception {
        int splitIndex = Array.of(input).indexOf("");
        Set<Point> points = Stream.range(0, splitIndex)
                .map(i -> input[i])
                .map(s -> s.split(","))
                .map(s -> new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1])))
                .toSet();
        List<Fold> folds = Stream.range(splitIndex + 1, input.length)
                .map(i -> input[i])
                .map(Fold::new)
                .toList();

        return "" + folds.get(0).fold(points).size();
    }

    protected String b(final String[] input) throws Exception {
        int splitIndex = Array.of(input).indexOf("");
        Set<Point> points = Stream.range(0, splitIndex)
                .map(i -> input[i])
                .map(s -> s.split(","))
                .map(s -> new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1])))
                .toSet();
        List<Fold> folds = Stream.range(splitIndex + 1, input.length)
                .map(i -> input[i])
                .map(Fold::new)
                .toList();

        for (Fold f : folds) points = f.fold(points);

        return pointsToString(points).trim();
    }

    private String pointsToString(Set<Point> points) {
        int maxX = points.map(Point::getX).max().get();
        int maxY = points.map(Point::getY).max().get();
        int minX = points.map(Point::getX).min().get();
        int minY = points.map(Point::getY).min().get();

        StringBuilder sb = new StringBuilder();
        for (int y = minY; y < maxY + 1; y++) {
            for (int x = minX; x < maxX + 1; x++) {
                char c = points.contains(new Point(x, y)) ? '#' : ' ';
                sb.append(c);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Value
    static class Fold {
        enum FoldDirection {
            X(Point::getX, (p, i) -> p.withByX(x -> x * -1 + i * 2)),
            Y(Point::getY, (p, i) -> p.withByY(y -> y * -1 + i * 2));

            private Function<Point, Integer> partitionFunction;
            private BiFunction<Point, Integer, Point> folderFunction;

            FoldDirection(Function<Point, Integer> partitionFunction, BiFunction<Point, Integer, Point> folder) {
                this.partitionFunction = partitionFunction;
                this.folderFunction = folder;
            }

            public Function<Point, Point> applyFolderFunction(int i) {
                return p -> folderFunction.apply(p, i);
            }
        }

        int position;
        FoldDirection foldDirection;

        public Fold(String inputString) {
            foldDirection = FoldDirection.valueOf(inputString.substring(11, 12).toUpperCase());
            position = Integer.parseInt(inputString.substring(13));
        }

        public Set<Point> fold(Set<Point> points) {
            val partition = points.partition(p -> foldDirection.partitionFunction.apply(p) < position);

            Set<Point> foldedPoints = partition._2.map(foldDirection.applyFolderFunction(position));

            return partition._1.union(foldedPoints);
        }
    }
}

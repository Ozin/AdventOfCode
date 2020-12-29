package AOC2020;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import utils.Abstract2DPuzzle;
import utils.Point;
import utils.Point3D;
import utils.Point4D;

import java.util.Map;
import java.util.Set;

public class Day17 extends Abstract2DPuzzle {
    public static void main(final String[] args) {
        new Day17().run();
    }

    private Set<Point3D> parseInput3D(final Map<Point, Character> input) {
        return EntryStream.of(input)
            .filterValues(Character.valueOf('#')::equals)
            .keys()
            .map(p -> new Point3D(p.getX(), p.getY(), 0))
            .toSet();
    }

    private Set<Point4D> parseInput4D(final Map<Point, Character> input) {
        return EntryStream.of(input)
            .filterValues(Character.valueOf('#')::equals)
            .keys()
            .map(p -> new Point4D(p.getX(), p.getY(), 0, 0))
            .toSet();
    }

    @Override
    protected Object a(final Map<Point, Character> input) throws Exception {
        Set<Point3D> game = parseInput3D(input);

        for (int i = 0; i < 6; i++) {
            final Set<Point3D> oldGame = game;
            game = StreamEx.of(oldGame)
                .map(Point3D::getNeighborsIncludingSelf) // stream containing all interesting point3DS
                .flatMap(Set::stream)
                .distinct()
                .filter(point3D -> willLive(point3D, oldGame))
                .toSet();
        }

        return game.size();
    }

    @Override
    protected Object b(final Map<Point, Character> input) throws Exception {
        Set<Point4D> game = parseInput4D(input);

        for (int i = 0; i < 6; i++) {
            final Set<Point4D> oldGame = game;
            game = StreamEx.of(oldGame)
                .map(Point4D::getNeighborsIncludingSelf) // stream containing all interesting point3DS
                .flatMap(Set::stream)
                .distinct()
                .filter(point3D -> willLive(point3D, oldGame))
                .toSet();
        }

        return game.size();
    }

    private boolean willLive(final Point3D point3D, final Set<Point3D> game) {
        final Set<Point3D> aliveNeighbors = point3D.getNeighbors();
        aliveNeighbors.retainAll(game);

        switch (aliveNeighbors.size()) {
            case 3:
                return true;
            case 2:
                return game.contains(point3D);
            default:
                return false;
        }
    }

    private boolean willLive(final Point4D point4D, final Set<Point4D> game) {
        final Set<Point4D> aliveNeighbors = point4D.getNeighbors();
        aliveNeighbors.retainAll(game);

        switch (aliveNeighbors.size()) {
            case 3:
                return true;
            case 2:
                return game.contains(point4D);
            default:
                return false;
        }
    }

}

package AOC2018.day15;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
public class Pathfinding {
    private final Board board;

    public Optional<List<PathCell>> path(final Unit source, final Unit target) {
        final PathCell[][] pathCells = init();

        final PathCell sourceCell = getPathCell(pathCells, source);
        final PathCell targetCell = getPathCell(pathCells, target);

        sourceCell.setParent(sourceCell);

        final TreeSet<PathCell> openSet = new TreeSet<>(compareByFCost(targetCell));
        final TreeSet<PathCell> closedSet = new TreeSet<>(compareByFCost(targetCell));

        openSet.add(sourceCell);

        while (!openSet.isEmpty()) {
            final PathCell current = requireNonNull(openSet.pollFirst());

            closedSet.add(current);

            if (current == targetCell) {
                return Optional.of(backTrack(targetCell));
            }

            for (final PathCell neighbor : getNeighbors(current, pathCells)) {
                if (neighbor != targetCell && (neighbor.getType() != Type.PATH || closedSet.contains(neighbor))) {
                    continue;
                }

                if (current.getGCost() + 1 < neighbor.getGCost()) {
                    neighbor.setGCost(current.getGCost() + 1);
                    neighbor.setParent(current);
                    openSet.add(neighbor);
                }
            }
        }

        return Optional.empty();
    }

    private List<PathCell> backTrack(final PathCell targetCell) {
        final List<PathCell> cellList = targetCell.stream().collect(Collectors.toList());
        return cellList;
    }

    private Comparator<PathCell> compareByFCost(final PathCell target) {
        final ToIntFunction<PathCell> getFCost = cell -> cell.getFCost(target);
        return Comparator.comparingInt(getFCost)
            .thenComparing(PathCell.READING_ORDER);
    }

    private PathCell getPathCell(final PathCell[][] pathCells, final Unit unit) {
        final Cell cell = board.getUnitsCell(unit);
        return pathCells[cell.getY()][cell.getX()];
    }

    private PathCell[][] init() {
        final PathCell[][] pathCells = new PathCell[board.getUnits().length][];

        for (int y = 0; y < board.getUnits().length; y++) {
            pathCells[y] = new PathCell[board.getUnits()[y].length];
            for (int x = 0; x < board.getUnits()[y].length; x++) {
                pathCells[y][x] = new PathCell(x, y, board.getUnits()[y][x].getType());
            }
        }

        return pathCells;
    }

    private List<PathCell> getNeighbors(final PathCell a, final PathCell[][] pathCells) {
        final int x = a.getX();
        final int y = a.getY();

        return Stream.of(
            getSafe(x + 1, y, pathCells),
            getSafe(x - 1, y, pathCells),
            getSafe(x, y + 1, pathCells),
            getSafe(x, y - 1, pathCells)
        )
            .flatMap(Function.identity())
            .sorted(PathCell.READING_ORDER)
            .collect(Collectors.toList());
    }

    private Stream<PathCell> getSafe(final int x, final int y, final PathCell[][] pathCells) {
        if (y < pathCells.length) {
            if (x < pathCells[y].length) {
                return Stream.of(pathCells[y][x]);
            }
        }

        return Stream.empty();
    }
}

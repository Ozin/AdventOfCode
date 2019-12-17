package adventOfCode2018.day15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day15 {
    public static void main(final String[] args) throws Exception {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(Day15.class.getResourceAsStream("/2018/input15")))) {
            final List<String> input = br.lines().collect(Collectors.toList());

            final String[][] cells = new String[input.size()][];

            for (int y = 0; y < cells.length; y++) {
                cells[y] = input.get(y).split("");
            }

            final Board board = new Board(cells);

            System.out.printf("Result of 12 A: %s 27730%n", a(board));
            //System.out.printf("Result of 12 B: %s%n", b(pots, rules));
        }
    }

    private static Object a(final Board board) {
        System.out.println(board);

        int i = 0;
        while (!board.isFinished()) {
            board.streamUnits()
                    .forEach(unit -> doTurn(board, unit));

            System.out.printf("%s%n%s%n%n", ++i, board);
        }

        return board.stream().filter(Board.IS_LIVING_CREATURE).mapToInt(Unit::getHp).sum() * i;
    }

    private static void doTurn(final Board board, final Unit currentUnit) {
        final Optional<Map.Entry<Unit, List<PathCell>>> getPathToNextEnemyOpt = board.getPathToNextEnemy(currentUnit);

        // System.out.println("Path to next oponent: " + getPathToNextEnemyOpt);

        if (getPathToNextEnemyOpt.isEmpty()) {
            return;
        }

        final Unit nextOponent = getPathToNextEnemyOpt.get().getKey();
        final List<PathCell> getPathToNextEnemy = getPathToNextEnemyOpt.get().getValue();

        if (getPathToNextEnemy.size() > 3) {
            board.move(currentUnit, getPathToNextEnemy.get(getPathToNextEnemy.size() - 2));
        } else if (getPathToNextEnemy.size() == 3) {
            board.move(currentUnit, getPathToNextEnemy.get(getPathToNextEnemy.size() - 2));
            board.attack(currentUnit, nextOponent);
        } else {
            board.attack(currentUnit, nextOponent);
        }
    }
}

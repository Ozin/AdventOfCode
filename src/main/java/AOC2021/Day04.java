package AOC2021;

import lombok.Value;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.Indexed;
import utils.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class Day04 {

    protected String a(final String[] input) throws Exception {
        final BingoNumberFactory bingoNumberFactory = new BingoNumberFactory();
        final BingoGame bingoGame = BingoGame.of(input, bingoNumberFactory);
        return "" + bingoGame.getFirstWinnerScore();
    }

    protected String b(final String[] input) throws Exception {
        final BingoNumberFactory bingoNumberFactory = new BingoNumberFactory();
        final BingoGame bingoGame = BingoGame.of(input, bingoNumberFactory);
        return "" + bingoGame.getLastWinnerScore();
    }

    @Value
    static class BingoGame {
        List<Board> boards;
        List<Integer> chosenNumbers;
        BingoNumberFactory bingoNumberFactory;

        public static BingoGame of(String[] input, BingoNumberFactory bingoNumberFactory) {
            List<Integer> numbers = StreamEx.of(input[0].split(",")).map(Integer::parseInt).collect(Collectors.toList());

            for (int i = 2; i < input.length; i += 6) {
                final Map<Point, BingoNumber> bingoBoard = getBingoBoardFrom(input, i, bingoNumberFactory);
            }

            final List<Board> boards = IntStreamEx.range(2, input.length, 6)
                    .mapToObj(i -> getBingoBoardFrom(input, i, bingoNumberFactory))
                    .map(Board::new)
                    .toList();

            return new BingoGame(boards, numbers, bingoNumberFactory);
        }

        private static Map<Point, BingoNumber> getBingoBoardFrom(String[] input, int i, BingoNumberFactory bingoNumberFactory) {
            final Map<Point, BingoNumber> bingoBoard = IntStreamEx.range(5)
                    .mapToObj(j -> input[j + i])
                    .map(String::trim)
                    .map(Indexed.map())
                    .mapToEntry(Indexed::getIndex, Indexed::getValue)
                    .flatMapKeyValue((xValue, bingoLine) ->
                            StreamEx.of(bingoLine.split("\\s+"))
                                    .map(Integer::parseInt)
                                    .map(Indexed.map())
                                    .mapToEntry(Indexed::getIndex, Indexed::getValue)
                                    .mapKeys(yValue -> new Point(xValue, yValue))
                    )
                    .mapToEntry(Map.Entry::getKey, Map.Entry::getValue)
                    .mapValues(bingoNumberFactory::of)
                    .toMap();
            return bingoBoard;
        }

        public int getFirstWinnerScore() {
            for (int number : chosenNumbers) {
                bingoNumberFactory.of(number).picked = true;

                final Board winningBoard = boards.stream()
                        .filter(Board::hasBingo)
                        .findFirst()
                        .orElse(null);

                if (winningBoard == null) continue;

                return winningBoard.calculateScore() * number;
            }

            return -1;
        }

        public int getLastWinnerScore() {
            Set<Board> tmpBoards = new HashSet<>(boards);
            for (int number : chosenNumbers) {
                bingoNumberFactory.of(number).picked = true;

                final Optional<Board> eventuelLastBoad = tmpBoards.stream().findFirst();
                if (tmpBoards.size() == 1 && eventuelLastBoad.get().hasBingo()) {
                    return eventuelLastBoad.get().calculateScore() * number;
                }

                tmpBoards = tmpBoards.stream()
                        .filter(not(Board::hasBingo))
                        .collect(Collectors.toSet());
            }

            return -1;
        }
    }

    @Value
    static class Board {
        Map<Point, BingoNumber> numbers;

        public boolean hasBingo() {
            for (int x = 0; x < 5; x++) {
                int finalX = x;
                final boolean bingo = EntryStream.of(numbers)
                        .mapKeys(Point::getX)
                        .filterKeys(pointX -> pointX == finalX)
                        .values()
                        .allMatch(BingoNumber::isPicked);

                if (bingo) return true;
            }

            for (int y = 0; y < 5; y++) {
                int finalY = y;
                final boolean bingo = EntryStream.of(numbers)
                        .mapKeys(Point::getY)
                        .filterKeys(pointY -> pointY == finalY)
                        .values()
                        .allMatch(BingoNumber::isPicked);

                if (bingo) return true;
            }

            return false;
        }

        public int calculateScore() {
            return EntryStream.of(numbers)
                    .values()
                    .filter(not(BingoNumber::isPicked))
                    .mapToInt(BingoNumber::getValue)
                    .sum();
        }
    }

    static class BingoNumberFactory {
        private Map<Integer, BingoNumber> cache = new HashMap<>(100);

        public BingoNumber of(int value) {
            return cache.computeIfAbsent(value, i -> new BingoNumber(i, false));
        }
    }

    static class BingoNumber {
        final int value;
        boolean picked;

        private BingoNumber(int value, boolean picked) {
            this.value = value;
            this.picked = picked;
        }

        public boolean isPicked() {
            return picked;
        }

        public int getValue() {
            return value;
        }
    }
}

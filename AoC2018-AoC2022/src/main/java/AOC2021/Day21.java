package AOC2021;


import io.vavr.Function2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;

public class Day21 {

    public static final int WINNING_CONDITION = 21;
    public static Map<MultidimensionalGame, List<Long>> cachedResults = HashMap.empty();

    protected String a(final String[] rawInput) throws Exception {
        int[] positions = Stream.of(rawInput)
                                .map(s -> s.substring(28))
                                .toJavaStream()
                                .mapToInt(Integer::parseInt)
                                .toArray();
        int[] scores = new int[positions.length];

        final Iterator<Integer> iterator = deterministicDieStream().sliding(3, 3)
                                                                   .map(Stream::sum)
                                                                   .map(Number::intValue)
                                                                   .iterator();

        return playGame(scores, positions, iterator) + "";
    }

    private static int playGame(int[] scores, int[] positions, Iterator<Integer> iterator) {
        for (int i = 0; ; i++) {
            int currentPlayer = (i) % scores.length;
            positions[currentPlayer] = (positions[currentPlayer] + iterator.next()) % 10;
            scores[currentPlayer] += positions[currentPlayer] == 0 ? 10 : positions[currentPlayer];

            if (scores[currentPlayer] >= 1000) {
                return (i + 1) * 3 * scores[(i - 1) % scores.length];
            }
        }
    }

    protected String b(final String[] rawInput) throws Exception {
        final var diceResults = List.of(1, 2, 3)
                                    .crossProduct(3)
                                    .map(List::sum)
                                    .map(Number::intValue)
                                    .toList();

        List<PlayerState> players = Stream.of(rawInput)
                                          .map(s -> s.substring(28))
                                          .map(Integer::parseInt)
                                          .map(p -> new PlayerState(0, p))
                                          .toList();

        cachedResults = HashMap.empty();
        MultidimensionalGame g = new MultidimensionalGame(players, diceResults, 0);
        return g.play().max().get() + "";
    }

    public Stream<Integer> deterministicDieStream() {
        return Stream.iterate(1, i -> (i % 100) + 1);
    }

    public record MultidimensionalGame(List<PlayerState> players, List<Integer> diceResults,
                                       int currentPlayerIndex) {

        public List<Long> play() {
            if (cachedResults.containsKey(this)) return cachedResults.get(this).get();

            var wins = players.map(p -> 0L);
            for (var diceRoll : diceResults) {
                var currentPlayer = players.get(currentPlayerIndex).move(diceRoll);
                var updatedPlayers = this.players.update(currentPlayerIndex, currentPlayer);

                if (currentPlayer.score >= WINNING_CONDITION) {
                    wins = wins.update(currentPlayerIndex, l -> l + 1);
                } else {
                    var otherWins = new MultidimensionalGame(updatedPlayers, diceResults, nextPlayer()).play();
                    wins = wins.zip(otherWins).map(Function2.<Long, Long, Long>of(Math::addExact).tupled());
                }
            }

            if (cachedResults.containsKey(this)) throw new IllegalStateException("should not happen");
            cachedResults = cachedResults.put(this, wins);

            return wins;
        }

        private int nextPlayer() {
            return (currentPlayerIndex + 1) % players.size();
        }
    }

    record PlayerState(int score, int position) {
        PlayerState move(int diceRoll) {
            int newPosition = ((position + diceRoll) % 10);
            newPosition = newPosition == 0 ? 10 : newPosition;
            return new PlayerState(score + newPosition, newPosition);
        }
    }
}

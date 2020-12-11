package AOC2018.day09;

import one.util.streamex.EntryStream;
import org.magicwerk.brownies.collections.primitive.IntObjBigList;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day09 {
    public static void main(String[] args) throws IOException {
        // 446 players; last marble is worth 71522 points
        Long answerA = calculate(446, 71522);
        System.out.printf("Result of 09 A: %s%n", answerA);

        // What would the new winning Elf's score be if the number of the last marble were 100 times larger?
        Long answerB = calculate(446, 71522 * 100);
        System.out.printf("Result of 09 B: %s%n", answerB);
    }

    private static Long calculate(int players, int marbles) {
        List<Integer> game = new IntObjBigList();
        game.add(0);

        int currentPlayerIndex = -1;
        int currentMarbleIndex = 1;

        Map<Integer, Long> playerPoints = IntStream.of(players).boxed().collect(Collectors.toMap(Function.identity(), i -> 0L));

        for (int nextMarble = 1; nextMarble <= marbles; nextMarble++) {
            currentPlayerIndex += 1;
            currentPlayerIndex %= players;

            if (nextMarble % 23 == 0) {
                currentMarbleIndex = addSpecial(game, currentMarbleIndex, nextMarble, currentPlayerIndex, playerPoints);
            } else {
                currentMarbleIndex = addRegular(game, currentMarbleIndex, nextMarble);
            }

            if (nextMarble % 100000 == 0)
                System.out.printf("Currently at %d, %.2f%n", nextMarble, 1.0 * nextMarble / marbles);
        }


        Comparator<Map.Entry<Integer, Long>> sortMaxValue = Comparator.comparing(Map.Entry::getValue);
        return EntryStream.of(playerPoints).sorted(sortMaxValue.reversed()).values().findFirst().get();
    }

    private static int addSpecial(List<Integer> game, int currentMarbleIndex, long nextMarble, int currentPlayerIndex, Map<Integer, Long> playerPoints) {
        playerPoints.merge(currentPlayerIndex, nextMarble, Long::sum);
        currentMarbleIndex = ((game.size() + currentMarbleIndex - 7) % game.size());

        Integer removedMarble = game.remove(currentMarbleIndex);
        playerPoints.merge(currentPlayerIndex, Long.valueOf(removedMarble), Long::sum);

        return currentMarbleIndex;
    }

    private static int addRegular(List<Integer> game, int currentMarbleIndex, int nextMarble) {
        currentMarbleIndex += 1;
        currentMarbleIndex %= game.size();
        currentMarbleIndex += 1;

        game.add(currentMarbleIndex, nextMarble);

        return currentMarbleIndex;
    }
}

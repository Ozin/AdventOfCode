package AOC2022;


import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.function.Function;

public class Day12 {
    public static void main(final String[] args) {
        System.out.printf("Solution for the first riddle: %s%n", one(input()));
        System.out.printf("Solution for the second riddle: %s%n", two(input()));
    }

    private static Object one(final String[] input) {
        final Map<Point, Character> grid = parseGrid(input);
        final Map<Point, List<Point>> graph = parseGraph(grid);
        final var start = grid.find(t -> t._2 == 'S').get()._1;
        final var end = grid.find(t -> t._2 == 'E').get()._1;

        final Try<List<Point>> findPath = Dijkstra.dijkstra(start, end, graph);

        return findPath.get().size() - 1;
    }

    private static Object two(final String[] input) {
        final Map<Point, Character> grid = parseGrid(input);
        final Map<Point, List<Point>> graph = parseGraph(grid);
        final var start = grid.filter(t -> t._2 == 'S' || t._2 == 'a').keySet();
        final var end = grid.find(t -> t._2 == 'E').get()._1;

        return start.map(s -> Dijkstra.dijkstra(s, end, graph)).flatMap(Try::iterator).map(List::size).min().get() - 1;
    }

    private static Map<Point, Character> parseGrid(final String[] lines) {
        Map<Point, Character> grid = HashMap.empty();

        for (int y = 0; y < lines.length; y++) {
            final String line = lines[y];
            for (int x = 0; x < line.length(); x++) {
                final var current = new Point(x, y);
                grid = grid.put(current, line.charAt(x));
            }
        }

        return grid;
    }

    public static Map<Point, List<Point>> parseGraph(final Map<Point, Character> grid) {
        final Function<Point, Option<Integer>> getChar = p -> grid.get(p)
                                                                  .map(c -> c == 'S' ? 'a' - 1 : (int) c)
                                                                  .map(c -> c == 'E' ? 'z' + 1 : (int) c);
        Map<Point, List<Point>> graph = HashMap.empty();

        for (final var currentPoint : grid.keySet()) {
            final var targets = Stream.of(
                                              new Point(currentPoint.x(), currentPoint.y() - 1),
                                              new Point(currentPoint.x() + 1, currentPoint.y()),
                                              new Point(currentPoint.x(), currentPoint.y() + 1),
                                              new Point(currentPoint.x() - 1, currentPoint.y())
                                      )
                                      .filter(grid::containsKey)
                                      .filter(p -> getChar.apply(currentPoint).get() + 1 >= getChar.apply(p).get())
                                      .toList();

            graph = graph.put(currentPoint, targets);
        }

        return graph;
    }

    record Point(int x, int y) {
    }

    private static String[] testInput() {
        return """
                Sabqponm
                abcryxxl
                accszExk
                acctuvwj
                abdefghi
                """.stripIndent().split("\n");
    }

    private static String[] input() {
        return """
                abcccccccccccccccccccccccccccccccaaaaaaaaaaaaaaaaccaaaaaaaaccccccccccccccccccccccccccccccccccccaaaaaa
                abcccccccccccccccccccccccccccccccaaaaaaaaaaaaaaaaaccaaaaaaccccccccccccccccccccccccccccccccccccccaaaaa
                abcccccccccccccccccccccccccccccccccaaaaaaaacccaaaaccaaaaaaccccccccccccccccccccaaaccccccccccccccccaaaa
                abcccccccccccccccccccccccccccccccccccaaaaaaaccaaccccaaaaaaccccccccccccccccccccaaaccccccccccccccccaaaa
                abcccccccccccccccccccccccccccccaaacccaaaaaaaacccccccaaccaaccccccccccccccccccccaaaccccccccccccccccaaac
                abcccccccccccccccccccccccccccccaaaaaaaaacaaaacccccccccccccccaccaaccccccccccccciiaaccaaaccccccccccaacc
                abccccccccaaccccccccccccccccccaaaaaaaaaaccaaacccccccccccccccaaaaaccccccccacaiiiiijjaaaacccccccccccccc
                abacccaaccaacccccccccccccccccaaaaaaaaaaccccacccccaaaaccccccccaaaaacccccccaaiiiiijjjjaaaccccccaacccccc
                abacccaaaaaacccccccccccccccccaaaaaaaaccccccccccccaaaacccccccaaaaaacccccccaiiiioojjjjjacccaaaaaacccccc
                abcccccaaaaaaacccccccccccccccccaaaaaaccccaaccccccaaaacccccccaaaaccccccccciiinnoooojjjjcccaaaaaaaccccc
                abccccccaaaaaccccccccccccccccccaaaaaacccaaaaccccccaaacccccccccaaaccccccchiinnnooooojjjjcccaaaaaaacccc
                abcccccaaaaacccccccccccccccccccaacccccccaaaaccccccccccccccccccccccccccchhiinnnuuoooojjjjkcaaaaaaacccc
                abccccaaacaaccccccccccccccccccccccccccccaaaaccccccccccccccccccaaacccchhhhhnnntuuuoooojjkkkkaaaacccccc
                abccccccccaacccccccccccccccccccccccccccccccccccccccccccccccccccaacchhhhhhnnnnttuuuuoookkkkkkkaacccccc
                abcccccccccccccccccccaacaaccccccccccccccccccccccccccccccccccaacaahhhhhhnnnnntttxuuuoopppppkkkkacccccc
                abcccccccccccccccccccaaaaacccccccccaccccccccccccccccccccccccaaaaahhhhmnnnnntttxxxuuupppppppkkkccccccc
                abccccccccccccccccccccaaaaacccccaaaacccccccccccccccccccccccccaaaghhhmmmmttttttxxxxuuuuuupppkkkccccccc
                abcccccccccccccccccccaaaaaaaccccaaaaaaccccccccccccccccccccccccaagggmmmmtttttttxxxxuuuuuuvppkkkccccccc
                abcccccccccccccccccccaaaaaaaaaaacaaaaacccccccccccccccccccccccaaagggmmmttttxxxxxxxyyyyyvvvppkkkccccccc
                abccccccccccccccccccccaaaaaaaaaaaaaaaccccccccccccccccccccaacaaaagggmmmtttxxxxxxxyyyyyyvvppplllccccccc
                SbcccccccccccccccccccaaaaaaaaaacaccaaccccccccccccccccccccaaaaaccgggmmmsssxxxxEzzzyyyyvvvpplllcccccccc
                abcccccccccccccccccccccaaaaaaccccccccccccccaacaaccccccccaaaaaccccgggmmmsssxxxxyyyyyvvvvqqplllcccccccc
                abccccccccccccccccccccccaaaaaacccccccccccccaaaacccccccccaaaaaacccgggmmmmsssswwyyyyyvvvqqqlllccccccccc
                abcccccccccccccccccccccaaaaaaaccccccccccccaaaaacccccccccccaaaaccccgggmmmmsswwyyyyyyyvvqqllllccccccccc
                abcccccccccccccccccccccaaaccaaacccccccccccaaaaaaccccccccccaccccccccgggooosswwwywwyyyvvqqlllcccccccccc
                abccccccccccccccccccccccacccccccccccccccccacaaaacccccccccccccccccccfffooosswwwwwwwwvvvqqqllcccccccccc
                abccccccccccccccccccccccccccccccccccccccccccaacccccccccccccccccccccfffooosswwwwwrwwvvvqqqllcccccccccc
                abccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccffooossswwwrrrwvvvqqqmmcccccccccc
                abccccaaacccccccccccccccccccccccccccccccccccccccccccccccccccccccccccffooosssrrrrrrrrqqqqmmmcccccccccc
                abccccaaacaacccccaaccccaaaacccccccccccccccccccccccccccccccccccccccccffooossrrrrrnrrrqqqqmmmcccaaacccc
                abcccccaaaaaccaaaaacccaaaaacccccccccccccccccccccccccccccccccccccccccfffoooorrnnnnnnmqqmmmmmcccaaacccc
                abccaaaaaaaacccaaaaaccaaaaaaccccccccccccccccccccccccccccccccccccccccfffooonnnnnnnnnmmmmmmmcccaaaccccc
                abcccaaaaacccccaaaaaccaaaaaaccccccaacccccccccccccccccccccccccccccccccfffoonnnnneddnmmmmmmccccaaaccccc
                abccccaaaaacccaaaaacccaaaaaacccccaaaaaaccccccccccccccccccccaaccccccccffeeeeeeeeeddddddddccccaaaaccccc
                abccccaacaaacccccaacccccaacccccccaaaaaaaccccccccccccccccaaaaaccccccccceeeeeeeeeedddddddddccaccaaccccc
                abccccaacccccccccccccccccccccccccaaaaaaaccaaaccccccccccccaaaaaccccccccceeeeeeeeaaaaddddddcccccccccccc
                abcccccccccccaaccccccccccccccccccccccaaaaaaaaacccccccccccaaaaacccccccccccccaaaacaaaacccccccccccccccaa
                abccccccccaacaaacccccccccccccccccccccaaaaaaaacccccccccccaaaaaccccccccccccccaaaccaaaaccccccccccccccaaa
                abccccccccaaaaacccccccccccccccccccccacaaaaaaccccccccccccccaaacccccccccccccccaccccaaacccccccccccacaaaa
                abcccccccccaaaaaaccccccccccccccccaaaaaaaaaaacccccccccccccccccccccccccccccccccccccccacccccccccccaaaaaa
                abcccccccaaaaaaaaccccccccccccccccaaaaaaaaaaaaacccccccccccccccccccccccccccccccccccccccccccccccccaaaaaa
                """.stripIndent().split("\n");
    }
}

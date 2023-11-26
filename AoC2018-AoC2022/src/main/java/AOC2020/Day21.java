package AOC2020;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import utils.AbstractDay;

import java.util.Comparator;

import static java.util.function.Predicate.not;

public class Day21 extends AbstractDay<List<Day21.Food>> {
    public static void main(final String[] args) {
        new Day21().run();
    }

    @Override
    protected List<Food> parseInput(String[] rawInput) throws Exception {
        return Stream.of(rawInput).map(this::parseSingleLine).toList();
    }

    private Food parseSingleLine(String s) {
        var split = s.substring(0, s.length() - 1).split(" \\(contains ");
        final String[] ingredients = split[0].split(" ");
        final String[] allergens = split[1].split(", ");
        return new Food(HashSet.of(ingredients), allergens);
    }

    @Override
    protected Object a(List<Food> input) throws Exception {
        final Map<String, Set<String>> allergensToIngredients = getAllergensToIngredients(input);

        final Set<String> ingredientsWithAllergenes = allergensToIngredients.values().reduceLeft(Set::union);

        return input.flatMap(Food::ingredients)
                    .filter(not(ingredientsWithAllergenes::contains))
                    .size();
    }

    private static Map<String, Set<String>> getAllergensToIngredients(List<Food> input) {
        Map<String, Set<String>> allergensToIngredients = HashMap.empty();

        for (Food f : input) {
            for (String allergene : f.allergenes) {
                allergensToIngredients = allergensToIngredients.put(allergene, f.ingredients, Set::intersect);
            }
        }
        return allergensToIngredients;
    }

    @Override
    protected Object b(List<Food> input) throws Exception {
        Map<String, Set<String>> allergensToIngredients = getAllergensToIngredients(input);

        Map<String, String> identified = HashMap.empty();
        while (allergensToIngredients.nonEmpty()) {
            final Tuple2<? extends Map<String, Set<String>>, ? extends Map<String, Set<String>>> partition =
                    allergensToIngredients.partition(t -> t._2.size() == 1);

            identified = identified.merge(partition._1.mapValues(Set::head));

            final Map<String, String> finalIdentified = identified;
            allergensToIngredients = partition._2.removeAll(identified.keySet())
                                                 .mapValues(values -> values.removeAll(finalIdentified.values()));
        }
        return identified.toList().sorted(Comparator.comparing(Tuple2::_1)).map(Tuple2::_2).mkString(",");

    }

    public static record Food(HashSet<String> ingredients, String[] allergenes) {
    }
}

package adventOfCode2019;

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.Predicate.not;

public class Day14 extends AbstractDay<Set<Reaction>> {

    public static void main(final String[] args) {
        new Day14().run();
    }

    @Override
    protected Set<Reaction> parseInput(final String[] rawInput) throws Exception {
        return StreamEx.of(rawInput).map(Reaction::new).toSet();
    }

    @Override
    protected Object a(final Set<Reaction> input) throws Exception {
        return null;
    }

    @Override
    protected Object b(final Set<Reaction> input) throws Exception {
        return null;
    }
}

package AOC2018.day15;

import lombok.extern.java.Log;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
public class Board {

    public static final Predicate<Unit> IS_CREATURE = unit -> unit.getType().isCreature();
    public static final Predicate<Unit> IS_LIVING_CREATURE = IS_CREATURE.and(unit -> unit.getHp() > 0);

    private final Unit[][] units;

    public Board(final String[][] input) {
        units = Unit.from(input);
    }

    public Unit[][] getUnits() {
        return units;
    }

    public StreamEx<Unit> streamUnits() {
        final Stream.Builder<Unit> attackers = Stream.builder();
        final Stream.Builder<Unit> movers = Stream.builder();

        for (int y = 0; y < units.length; y++) {
            for (int x = 0; x < units[y].length; x++) {
                Unit currentUnit = units[y][x];
                if (!IS_LIVING_CREATURE.test(currentUnit)) {
                    continue;
                }

                Optional<Map.Entry<Unit, List<PathCell>>> getPathToNextEnemy = getPathToNextEnemy(currentUnit);
                if (getPathToNextEnemy.isPresent() && getPathToNextEnemy.get().getValue().size() == 2) {
                    attackers.add(currentUnit);
                } else {
                    movers.add(currentUnit);
                }
            }
        }

        return StreamEx.of(Stream.concat(attackers.build(), movers.build())).filter(Board.IS_LIVING_CREATURE);
    }

    public StreamEx<Unit> stream() {
        return StreamEx.of(StreamEx.of(units)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList())
                .stream());
    }

    public void streamBoard() {
        final Stream.Builder<Object> builder = Stream.builder();
        for (int y = 0; y < units.length; y++) {
            for (int x = 0; x < units[y].length; x++) {

            }
        }
    }

    public Optional<Map.Entry<Unit, List<PathCell>>> getPathToNextEnemy(final Unit unit) {
        final Pathfinding pathfinding = new Pathfinding(this);

        Map<Unit, List<PathCell>> entries = stream()
                .filter(potentialEnemy -> potentialEnemy.isEnemyOf(unit))
                // .peek(u -> System.out.printf("potential new enemy: %s%n", u))
                .mapToEntry(enemy -> pathfinding.path(enemy, unit))
                .filterValues(Optional::isPresent)
                .mapValues(Optional::orElseThrow)
                .toMap();

        Map<Boolean, List<Map.Entry<Unit, List<PathCell>>>> collect = entries.entrySet().stream().collect(Collectors.partitioningBy(e -> e.getValue().size() == 2));

        List<Map.Entry<Unit, List<PathCell>>> nextToEnemy = collect.get(true);
        if (nextToEnemy.isEmpty()) {
            return EntryStream.of(entries).minByInt(entry -> entry.getValue().size());
        } else {
            Comparator<Map.Entry<Unit, List<PathCell>>> lowestHP = Comparator.comparingInt(e -> e.getKey().getHp());
            Comparator<Map.Entry<Unit, List<PathCell>>> readingOrderX = Comparator.comparingInt(e -> e.getValue().get(0).getX());
            Comparator<Map.Entry<Unit, List<PathCell>>> readingOrderY = Comparator.comparingInt(e -> e.getValue().get(0).getY());
            return StreamEx.of(nextToEnemy).mapToEntry(Map.Entry::getKey, Map.Entry::getValue).min(lowestHP.thenComparing(readingOrderX).thenComparing(readingOrderY));
        }

    }

    public Cell getUnitsCell(final Unit unit) {
        for (int y = 0; y < units.length; y++) {
            for (int x = 0; x < units[y].length; x++) {
                if (unit == units[y][x]) {
                    return new Cell(x, y);
                }
            }
        }

        throw new IllegalArgumentException("Could not find unit on board");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < units.length; y++) {
            for (int x = 0; x < units[y].length; x++) {
                sb.append(units[y][x].getType());
            }
            String unitString = Arrays.stream(units[y]).filter(IS_LIVING_CREATURE).map(u -> String.format("%s(%s)", u.getType(), u.getHp())).collect(Collectors.joining(", "));
            sb.append("   ");
            sb.append(unitString);
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public void move(final Unit currentUnit, final PathCell pathCell) {
        if (pathCell.getType() != Type.PATH) {
            throw new IllegalArgumentException("Can only move unit on PATH");
        }

        removeUnit(currentUnit);
        this.units[pathCell.getY()][pathCell.getX()] = currentUnit;
    }

    public void attack(final Unit currentUnit, final Unit nextOponent) {
        if (!currentUnit.isEnemyOf(nextOponent)) {
            throw new IllegalArgumentException(String.format("The two units need to be enemies: %s <> %s", currentUnit, nextOponent));
        }

        nextOponent.addHP(-3);

        if (nextOponent.getHp() < 0) {
            removeUnit(nextOponent);
        }
    }

    private void removeUnit(final Unit unit) {
        final Cell currentCell = getUnitsCell(unit);
        this.units[currentCell.getY()][currentCell.getX()] = new Unit(200, Type.PATH);
    }

    public boolean isFinished() {
        return stream().map(Unit::getType).noneMatch(Type.GOBLIN::equals)
                || stream().map(Unit::getType).noneMatch(Type.ELF::equals);
    }
}

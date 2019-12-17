package adventOfCode2018.day15;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Unit {
    int hp;
    final Type type;

    public static Unit[][] from(final String[][] input) {
        final Unit[][] units = new Unit[input.length][];

        for (int y = 0; y < input.length; y++) {
            units[y] = new Unit[input[y].length];
            for (int x = 0; x < input[y].length; x++) {
                String s = input[y][x];
                units[y][x] = new Unit(200, Type.from(s));
            }
        }

        return units;
    }

    public boolean isEnemyOf(final Unit unit) {
        switch (type) {
            case GOBLIN:
                return unit.getType() == Type.ELF;
            case ELF:
                return unit.getType() == Type.GOBLIN;
            default:
                return false;
        }
    }

    public void addHP(final int amount) {
        this.hp += amount;
    }
}

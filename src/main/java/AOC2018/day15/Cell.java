package AOC2018.day15;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;

@Getter
@EqualsAndHashCode
public class Cell {
    public static final Comparator<? super Cell> READING_ORDER = Comparator
        .comparingInt(Cell::getY)
        .thenComparingInt(Cell::getX);

    int x;
    int y;

    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
}

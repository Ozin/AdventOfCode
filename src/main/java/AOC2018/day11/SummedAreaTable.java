package AOC2018.day11;

import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class SummedAreaTable {
    Map<Point, Integer> summedTable;
    Map<Point, Integer> originTable;

    public SummedAreaTable(Map<Point, Integer> originTable) {
        this.originTable = originTable;
        summedTable = new HashMap<>();
    }

    public int get(Point point, int gridSize) {
        Point tl = point.addY(-1).addX(-1);
        Point tr = tl.addX(gridSize);
        Point bl = tl.addY(gridSize);
        Point br = tr.addY(gridSize);
        return getSum(tl) + getSum(br) - getSum(tr) - getSum(bl);
    }

    private int getSum(Point point) {
        Integer value = summedTable.get(point);

        if(point.getY() < 0 || point.getX() < 0) {
            return 0;
        }

        if (value == null) {

            value = point.getY() <= 0 ? 0 : getSum(point.addY(-1));

            for (int x = 0; x < point.getX(); x++) {
                Integer integer = originTable.get(point.addX(-x));

                if(integer == null) {
                    throw new NullPointerException("Could not find " + point.addX(-x));
                }

                value += integer;
            }

            summedTable.put(point, value);
        }

        return value;
    }
}

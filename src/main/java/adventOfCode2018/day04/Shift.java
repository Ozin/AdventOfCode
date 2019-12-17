package adventOfCode2018.day04;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import one.util.streamex.LongStreamEx;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class Shift {
    private final LocalDateTime dateTime;
    private final int id;
    private final List<LocalTime> sleeping;

    private LocalTime asleep;

    public Shift(Event e) {
        dateTime = e.getDateTime();
        id = e.getId().get();
        sleeping = new ArrayList<>();

        asleep = null;
    }

    static Map<Integer, List<LocalTime>> extractShifts(Event[] events) {
        var shifts = new HashMap<Integer, List<LocalTime>>();
        Shift currentShift = null;

        for (Event e : events) {
            switch (e.getEventType()) {
                case START:
                    if (currentShift != null) {
                        List<LocalTime> storedSleepings = shifts.computeIfAbsent(currentShift.id, ArrayList::new);
                        storedSleepings.addAll(currentShift.sleeping);
                    }

                    currentShift = new Shift(e);
                    break;
                case ASLEEP:
                    currentShift.asleep(e);
                    break;
                case AWAKE:
                    currentShift.awake(e);
                    break;
            }
        }

        return shifts;
    }

    public void asleep(Event e) {
        asleep = e.getDateTime().toLocalTime();
    }

    public void awake(Event e) {
        LocalTime awake = e.getDateTime().toLocalTime();

        long sleepingMinutes = Duration.between(asleep, awake).toMinutes();
        LongStreamEx.range(sleepingMinutes).mapToObj(asleep::plusMinutes).forEach(sleeping::add);
    }
}

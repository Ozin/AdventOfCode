package adventOfCode2019;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.util.Collection;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Value
@RequiredArgsConstructor
public class Moon extends AbstractDay.InputEntity {
    @Wither
    private final List<Integer> velocity;

    @Wither
    private final List<Integer> position;

    public Moon(final String input) {
        final MatchResult matchResult = getMatchResult(input);
        this.position = List.of(
                Integer.parseInt(matchResult.group(1)),
                Integer.parseInt(matchResult.group(2)),
                Integer.parseInt(matchResult.group(3))
        );
        this.velocity = List.of(0, 0, 0);
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("^<x=([^,]+), y=([^,]+), z=([^,]+)>$");
    }

    public Moon applyGravity(final Collection<Moon> others) {
        //return others.stream().reduce(Moon::applyGravity).orElseThrow();
        Moon result = this;
        for (final Moon other : others) {
            result = result.applyGravity(other);
        }
        return result;
    }

    public Moon applyGravity(final Moon other) {
        final List<Integer> newVelocity = List.of(
                getAttraction(0, other),
                getAttraction(1, other),
                getAttraction(2, other)
        );

        return withVelocity(newVelocity);
    }

    public Moon applyVelocity() {
        return withPosition(StreamEx.zip(position, velocity, (a, b) -> a + b).toList());
    }

    public int potentialEnergy() {
        return IntStreamEx.of(position).map(Math::abs).sum();
    }

    public int kineticEnergy() {
        return IntStreamEx.of(velocity).map(Math::abs).sum();
    }

    public int totalEnergy() {
        return potentialEnergy() * kineticEnergy();
    }

    private int getAttraction(final int index, final Moon other) {
        final int thisPosition = position.get(index);
        final int otherPosition = other.position.get(index);
        return Integer.signum(otherPosition - thisPosition) + velocity.get(index);
    }

    public String toString() {
        // pos=<x=-1, y=-7, z= 3>, vel=<x= 0, y= 2, z= 1>
        return String.format("pos=<x=%2d, y=%2d, z=%2d>, vel=<x=%2d, y=%2d, z=%2d>",
                position.get(0),
                position.get(1),
                position.get(2),
                velocity.get(0),
                velocity.get(1),
                velocity.get(2));
    }
}

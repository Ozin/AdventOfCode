package AOC2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class IntcodeComputer {
    private final LinkedList<Long> input;
    private long[] state;
    private int pointer;
    private int relativeBase;
    private boolean done;

    public IntcodeComputer(final long[] state, final long[] input) {
        this.state = Arrays.copyOf(state, state.length * 2);
        this.pointer = 0;
        this.relativeBase = 0;
        this.input = Arrays.stream(input)
            .boxed()
            .collect(Collectors.toCollection(LinkedList::new));
        this.done = false;
    }

    public IntcodeComputer(final long[] state) {
        this(state, new long[0]);
    }

    public long[] finishProgram() {
        final var outputs = new ArrayList<Long>();
        while (true) {
            final long output = nextOutput();
            if (isDone()) {
                return outputs.stream().mapToLong(Long::longValue).toArray();
            } else {
                outputs.add(output);
            }
        }
    }

    public long nextOutput() {
        if (done) {
            throw new IllegalStateException("Already done!");
        }

        while (true) {
            final int opsCode = Long.valueOf(getStateValue(pointer) % 100).intValue();
            switch (opsCode) {
                case 1:
                    add();
                    break;
                case 2:
                    mult();
                    break;
                case 3:
                    in(input.removeFirst());
                    break;
                case 4:
                    return out();
                case 5:
                    jump_if_true();
                    break;
                case 6:
                    jump_if_false();
                    break;
                case 7:
                    less_than();
                    break;
                case 8:
                    equals();
                    break;
                case 9:
                    adjustRelativeBase();
                    break;
                case 99:
                    done = true;
                    return -1;
                default:
                    throw new IllegalStateException("Unkown ops code: " + opsCode);
            }
        }
    }

    private long getStateValue(final int pointer) {
        increaseArray(pointer);
        return state[pointer];
    }

    private void setStateValue(final int index, final long value) {
        increaseArray(index);
        state[index] = value;
    }

    private void increaseArray(final int pointer) {
        if (pointer < state.length) {
            return;
        }

        state = Arrays.copyOf(state, pointer + 1000);
    }

    // if the first parameter is less than the second parameter,
    // it stores 1 in the position given by the third parameter.
    // Otherwise, it stores 0.
    private void less_than() {
        final var value1 = getStateValue(getIndex(1));
        final var value2 = getStateValue(getIndex(2));

        setStateValue(getIndex(3), value1 < value2 ? 1 : 0);
        pointer += 4;
    }

    private void equals() {
        final var value1 = getStateValue(getIndex(1));
        final var value2 = getStateValue(getIndex(2));

        setStateValue(getIndex(3), value1 == value2 ? 1 : 0);
        pointer += 4;
    }

    private void jump_if_true() {
        final var value1 = getStateValue(getIndex(1));
        final var value2 = getStateValue(getIndex(2));

        pointer = Long.valueOf(value1 != 0 ? value2 : pointer + 3).intValue();
    }

    private void jump_if_false() {
        final var value1 = getStateValue(getIndex(1));
        final var value2 = getStateValue(getIndex(2));

        pointer = Long.valueOf(value1 == 0 ? value2 : pointer + 3).intValue();
    }

    private long out() {
        final long value = getStateValue(getIndex(1));
        pointer += 2;
        return value;
    }

    private void in(final long inputValue) {
        setStateValue(getIndex(1), inputValue);
        pointer += 2;
    }

    private void add() {
        final var value1 = getStateValue(getIndex(1));
        final var value2 = getStateValue(getIndex(2));

        setStateValue(getIndex(3), value1 + value2);
        pointer += 4;
    }

    private void mult() {
        final var value1 = getStateValue(getIndex(1));
        final var value2 = getStateValue(getIndex(2));

        setStateValue(getIndex(3), value1 * value2);
        pointer += 4;
    }

    private void adjustRelativeBase() {
        this.relativeBase += getStateValue(getIndex(1));
        pointer += 2;
    }

    private int getIndex(final int param) {
        final int paramMode = indexValAt(0) / powTen(1 + param) % 10;

        switch (paramMode) {
            case 0:
                return indexValAt(param);
            case 1:
                return pointer + param;
            case 2:
                return relativeBase + indexValAt(param);
            default:
                throw new IllegalStateException(String.format("Unknown parameter mode: %s, instruction: %s, paramPos: %s", paramMode, getStateValue(pointer), param));
        }
    }

    private int indexValAt(final int relativToPointer) {
        return Long.valueOf(getStateValue(pointer + relativToPointer)).intValue();
    }

    private int powTen(final int i) {
        if (i == 0) {
            return 1;
        }
        return 10 * powTen(i - 1);
    }

    public boolean isDone() {
        return done;
    }

    public long[] getState() {
        return state;
    }

    public void addInput(final long input) {
        this.input.add(input);
    }
}

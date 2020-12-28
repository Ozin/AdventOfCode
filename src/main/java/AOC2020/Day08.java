package AOC2020;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.AbstractDay;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;

public class Day08 extends AbstractDay<Day08.Instruction[]> {
    public static void main(final String[] args) {
        new Day08().run();
    }

    @Override
    protected Instruction[] parseInput(final String[] rawInput) throws Exception {
        return StreamEx.of(rawInput)
            .map(Instruction::new)
            .toArray(Instruction[]::new);
    }

    @Override
    protected Object a(final Instruction[] input) throws Exception {
        return runProgram(input);
    }

    @Override
    protected Object b(final Instruction[] input) throws Exception {
        return IntStreamEx.range(input.length)
            .filter(i -> input[i].command != Command.acc)
            .mapToObj(i -> flip(i, input))
            .map(this::runProgram)
            .findFirst(State::isTerminated)
            .orElseThrow();
    }

    private Instruction[] flip(final int index, final Instruction[] instructions) {
        final Instruction[] newInstructions = Arrays.copyOf(instructions, instructions.length);
        final Instruction target = newInstructions[index];
        if (target.command == Command.jmp) {
            newInstructions[index] = target.withCommand(Command.nop);
        } else {
            newInstructions[index] = target.withCommand(Command.jmp);
        }

        return newInstructions;
    }

    private State runProgram(final Instruction[] input) {
        State state = new State(0, 0, false);
        final Set<Integer> alreadyExecuted = new HashSet<>();

        while (true) {
            if (alreadyExecuted.contains(state.index)) {
                return state.withTerminated(false);
            } else if (state.getIndex() >= input.length) {
                return state.withTerminated(true);
            }

            alreadyExecuted.add(state.getIndex());
            state = input[state.index].apply(state);
        }
    }

    @Value
    @With
    @AllArgsConstructor
    public static class Instruction {
        Command command;
        int value;

        public Instruction(final String instruction) {
            this(instruction.split(" "));
        }

        private Instruction(final String[] s) {
            this(Command.valueOf(s[0]), Integer.parseInt(s[1]));
        }

        public State apply(final State state) {
            return command.changeState(state, value);
        }
    }

    public enum Command {
        nop((state, value) -> state
            .withIndexBy(i -> i + 1)
        ),
        acc((state, value) -> state
            .withAccumulatorBy(acc -> acc + value)
            .withIndexBy(i -> i + 1)
        ),
        jmp((state, value) -> state
            .withIndexBy(i -> i + value)
        );

        private final BiFunction<State, Integer, State> changeStateFunction;

        Command(final BiFunction<State, Integer, State> changeStateFunction) {
            this.changeStateFunction = changeStateFunction;
        }

        public State changeState(final State state, final int value) {
            return this.changeStateFunction.apply(state, value);
        }
    }

    @Value
    @With
    public static class State {
        int index;
        int accumulator;
        boolean terminated;

        public State withIndexBy(final IntUnaryOperator operator) {
            return withIndex(operator.applyAsInt(getIndex()));
        }

        public State withAccumulatorBy(final IntUnaryOperator operator) {
            return withAccumulator(operator.applyAsInt(getAccumulator()));
        }
    }
}

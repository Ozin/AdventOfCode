package AOC2018.day16;

public enum OpsCode {
    addr((register, a, b) -> register[a] + register[b]),
    addi((register, a, b) -> register[a] + b),
    mulr((register, a, b) -> register[a] * register[b]),
    muli((register, a, b) -> register[a] * b),
    banr((register, a, b) -> register[a] & register[b]),
    bani((register, a, b) -> register[a] & b),
    borr((register, a, b) -> register[a] | register[b]),
    bori((register, a, b) -> register[a] | b),
    setr((register, a, b) -> register[a]),
    seti((register, a, b) -> a),
    gtir((register, a, b) -> a > register[b] ? 1 : 0),
    gtri((register, a, b) -> register[a] > b ? 1 : 0),
    gtrr((register, a, b) -> register[a] > register[b] ? 1 : 0),
    eqir((register, a, b) -> a == register[b] ? 1 : 0),
    eqri((register, a, b) -> register[a] == b ? 1 : 0),
    eqrr((register, a, b) -> register[a] == register[b] ? 1 : 0);

    private final InstructionImpl instructionImpl;

    OpsCode(final InstructionImpl InstructionImpl) {
        this.instructionImpl = InstructionImpl;
    }

    public int[] execute(final int[] register, final int[] instruction) {
        final int a = instruction[1];
        final int b = instruction[2];
        final int c = instruction[3];

        final int[] output = clone(register);
        output[c] = instructionImpl.run(register, a, b);

        return output;
    }

    private int[] clone(final int[] register) {
        return new int[]{register[0], register[1], register[2], register[3]};
    }

    @FunctionalInterface
    public interface InstructionImpl {
        int run(int[] register, int a, int b);
    }
}

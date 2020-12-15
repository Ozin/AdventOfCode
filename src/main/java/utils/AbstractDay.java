package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public abstract class AbstractDay<T> {

    private String inputFilePath = defaultInputFile();

    protected void run() {
        runA();
        runB();
    }

    private void runB() {
        try {
            System.out.printf("Result of day %02d B is %s%n", dayNumber(), b(getInput()));
            System.out.flush();
        } catch (final Exception e) {
            e.printStackTrace();
            System.err.flush();
        }
    }

    private void runA() {
        try {
            System.out.printf("Result of day %02d A is %s%n", dayNumber(), a(getInput()));
            System.out.flush();
        } catch (final Exception e) {
            e.printStackTrace();
            System.err.flush();
        }
    }

    public Object getA() throws Exception {
        return a(getInput());
    }

    public Object getB() throws Exception {
        return b(getInput());
    }

    protected T getInput() {
        final InputStream resourceAsStream = requireNonNull(getClass().getResourceAsStream(getInputFilePath()), "Could not find resource: " + getInputFilePath());
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            final String[] input = br.lines().toArray(String[]::new);

            return parseInput(input);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String defaultInputFile() {
        return String.format("/2019/%02d.txt", dayNumber());
    }

    private int dayNumber() {
        return Integer.parseInt(getClass().getSimpleName().substring(3));
    }


    protected abstract T parseInput(final String[] rawInput) throws Exception;

    protected abstract Object a(final T input) throws Exception;

    protected abstract Object b(final T input) throws Exception;

    public final String getInputFilePath() {
        return inputFilePath;
    }

    public final void setInputFilePath(final String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public abstract static class InputEntity {

        public abstract Pattern getPattern();

        public MatchResult getMatchResult(final String input) {
            final Matcher matcher = getPattern().matcher(input);
            if (!matcher.find()) {
                throw new IllegalStateException("Could not match pattern on input: " + input);
            }
            return matcher.toMatchResult();
        }
    }
}

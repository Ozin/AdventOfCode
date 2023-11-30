package AOC2021;

import com.esotericsoftware.yamlbeans.YamlReader;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public interface AocTest {
    @TestFactory
    default Stream<DynamicTest> advent_of_code() throws Exception {
        final String day = getClass().getSimpleName().substring(3, 5);
        final Object dayImplementation = Class.forName(getClass().getPackageName() + ".Day" + day)
                                              .getDeclaredConstructor()
                                              .newInstance();

        return Stream.iterate(0, i -> i + 1)
                     .map(i -> String.format("/2021/day%s-%02d.yml", day, i))
                     .map(filePath -> getClass().getResource(filePath))
                     .takeWhile(Objects::nonNull)
                     .flatMap(testFile -> getDynamicTest(dayImplementation, testFile));
    }

    private Stream<DynamicTest> getDynamicTest(final Object dayImplementation, final URL is) {
        if (is == null) return Stream.empty();

        try(final InputStreamReader reader = new InputStreamReader(is.openStream())) {
            List<String> testContent = new YamlReader(reader).read(List.class);

            final String testName = is.toString().substring(78);
            final DynamicTest getA = Arrays.stream(dayImplementation.getClass().getDeclaredMethods())
                                           .map(Method::getName)
                                           .anyMatch("a"::equals) ? dynamicTest(
                    testName + " - getA",
                    getExecutable(dayImplementation, "a", testContent.get(0), testContent.get(1))
            ) : null;

            final DynamicTest getB = Arrays.stream(dayImplementation.getClass().getDeclaredMethods())
                                           .map(Method::getName)
                                           .anyMatch("b"::equals) ? dynamicTest(
                    testName + " - getB",
                    getExecutable(dayImplementation, "b", testContent.get(0), testContent.get(2))
            ) : null;

            return Stream.of(
                    getA,
                    getB
            ).filter(Objects::nonNull);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Executable getExecutable(final Object dayImplementation, final String methodName, final String testContent, final String expected) {
        return () -> {
            final Object resultA = dayImplementation.getClass()
                                                    .getDeclaredMethod(methodName, String[].class)
                                                    .invoke(dayImplementation, (Object) testContent.split("\\n"));
            assertEquals(expected, resultA);
        };
    }
}

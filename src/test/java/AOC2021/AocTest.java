package AOC2021;

import com.esotericsoftware.yamlbeans.YamlReader;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

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
        final Object dayImplementation = Class.forName(getClass().getPackageName() + ".Day" + day).getDeclaredConstructor().newInstance();

        return Stream.iterate(0, i -> i + 1)
                .map(i -> String.format("/2021/day%s-%02d.yml", day, i))
                .map(filePath -> getClass().getResource(filePath))
                .takeWhile(Objects::nonNull)
                .flatMap(testFile -> getDynamicTest(dayImplementation, testFile));
    }

    @SneakyThrows
    private Stream<DynamicTest> getDynamicTest(Object dayImplementation, URL is) {
        if (is == null) return Stream.empty();

        List<String> testContent = new YamlReader(new InputStreamReader(is.openStream())).read(List.class);

        final String testName = is.toString().substring(78);
        final DynamicTest getA = testContent.get(1) != null && Arrays.stream(dayImplementation.getClass().getDeclaredMethods()).map(Method::getName).anyMatch("a"::equals) ? dynamicTest(
                testName + " - getA",
                getExecutable(dayImplementation, "a", testContent.get(0), testContent.get(1))
        ) : null;

        final DynamicTest getB = testContent.get(2) != null && Arrays.stream(dayImplementation.getClass().getDeclaredMethods()).map(Method::getName).anyMatch("b"::equals) ? dynamicTest(
                testName + " - getB",
                getExecutable(dayImplementation, "b", testContent.get(0), testContent.get(2))
        ) : null;

        return Stream.of(
                getA,
                getB
        ).filter(Objects::nonNull);
    }

    private Executable getExecutable(Object dayImplementation, String methodName, String testContent, String expected) {
        return () -> {
            final Object resultA = dayImplementation.getClass().getDeclaredMethod(methodName, String[].class).invoke(dayImplementation, (Object) testContent.split("\\n"));
            assertEquals(expected, resultA);
        };
    }
}

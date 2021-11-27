package AOC2021;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
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

        return Stream.iterate(1, i -> i + 1)
                .map(i -> String.format("/2021/day%s-%02d.yml", day, i))
                .flatMap(testFile -> getDynamicTest(dayImplementation, testFile))
                .takeWhile(Objects::nonNull);
                /*
                .map(i -> getClass().getResourceAsStream(String.format("/2021/day%s-%02d.yml", day, i)))
                .takeWhile(Objects::nonNull)
                .map(is -> new YamlReader(new InputStreamReader(is)))
                .map(this::readList)
                .flatMap(l -> getDynamicTestStream(o, l));

                 */
    }

    @SneakyThrows
    private Stream<DynamicTest> getDynamicTest(Object dayImplementation, String testFile) {
        InputStream is = getClass().getResourceAsStream(testFile);
        if (is == null) return Stream.empty();

        List<String> testContent = new YamlReader(new InputStreamReader(is)).read(List.class);

        final DynamicTest getA = testContent.get(1) != null && Arrays.stream(dayImplementation.getClass().getDeclaredMethods()).map(Method::getName).anyMatch("a"::equals) ? dynamicTest(
                testFile + " - getA",
                getExecutable(dayImplementation, "a", testContent.get(0), testContent.get(1))
        ) : null;

        final DynamicTest getB = testContent.get(2) != null && Arrays.stream(dayImplementation.getClass().getDeclaredMethods()).map(Method::getName).anyMatch("b"::equals) ? dynamicTest(
                testFile + " - getB",
                getExecutable(dayImplementation, "b", testContent.get(0), testContent.get(2))
        ) : null;

        return Stream.of(
                getA,
                getB
        ).filter(Objects::nonNull);
    }

    private Executable getExecutable(Object dayImplementation, String methodName, String testContent, String expected) {
        return () -> {
            final Object resultA = dayImplementation.getClass().getDeclaredMethod(methodName, String.class).invoke(dayImplementation, testContent);
            assertEquals(expected, resultA);
        };
    }
}

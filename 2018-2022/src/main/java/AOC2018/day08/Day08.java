package AOC2018.day08;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day08 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day08.class.getResourceAsStream("/2018/input08")))) {
            var integers = StreamEx.of(br.lines()).flatMap(s -> Stream.of(s.split(" "))).map(Integer::parseInt).toList();
            var queue = new LinkedList<>(integers);

            Node root = Node.from(queue);

            a(root);

            b(root);
        }
    }

    private static void a(Node root) {
        int sum = root.stream().map(Node::getMetadata).flatMapToInt(IntStream::of).sum();
        System.out.printf("Result of 08 A: %s%n", sum);
    }

    private static void b(Node root) {
        System.out.printf("Result of 08 B: %s%n", root.getValue());
    }
}

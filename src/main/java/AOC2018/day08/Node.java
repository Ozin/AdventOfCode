package AOC2018.day08;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
public class Node {
    final Node[] childs;
    final int[] metadata;
    int value = -1;

    public static Node from(LinkedList<Integer> queue) {
        Node[] nodes = new Node[queue.pollFirst()];
        int[] metadata = new int[queue.pollFirst()];

        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = from(queue);
        }

        for (int i = 0; i < metadata.length; i++) {
            metadata[i] = queue.pollFirst();
        }

        return new Node(nodes, metadata);
    }

    public Stream<Node> stream() {
        return Stream.concat(
                Stream.of(this),
                Stream.of(childs).flatMap(Node::stream)
        );
    }

    public int getValue() {
        if (value == -1) {
            value = calculateValue();
        }

        return value;
    }

    private int calculateValue() {
        if (childs.length == 0) {
            return IntStream.of(metadata).sum();
        }

        return IntStream.of(metadata)
                .map(i -> i - 1)
                .mapToObj(this::getChild)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapToInt(Node::calculateValue)
                .sum();
    }

    private Optional<Node> getChild(int index) {
        return index < childs.length ? Optional.of(childs[index]) : Optional.empty();
    }
}

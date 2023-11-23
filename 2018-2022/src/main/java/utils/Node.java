package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
@Setter
public class Node {
    private String name;
    private List<Node> children;
    private Node parent;

    public Stream<Node> flatten() {
        return Stream.concat(
            Stream.of(this),
            children.stream().flatMap(Node::flatten)
        );
    }

    public List<Node> getParents() {
        final List<Node> parents = new ArrayList<>();
        Node node = this;

        while (node.getParent() != null) {
            parents.add(node);
            node = node.getParent();
        }

        return parents;
    }
}

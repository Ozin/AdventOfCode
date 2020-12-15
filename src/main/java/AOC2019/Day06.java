package AOC2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import utils.AbstractDay;
import utils.Node;

public class Day06 extends AbstractDay<Node> {
    public static void main(final String[] args) {
        new Day06().run();
    }

    @Override
    protected Node parseInput(final String[] rawInput) throws Exception {
        final Map<String, Node> nodes = new HashMap<>();

        for (final String rawInputLine : rawInput) {
            final String[] parts = rawInputLine.split("\\)");
            final Node inner = nodes.computeIfAbsent(parts[0], name -> new Node(name, new ArrayList<>(), null));
            final Node outer = nodes.computeIfAbsent(parts[1], name -> new Node(name, new ArrayList<>(), inner));
            outer.setParent(inner);
            inner.getChildren().add(outer);
        }

        return nodes.get("COM");
    }

    @Override
    protected Object a(final Node root) throws Exception {
        return root.flatten().mapToInt(this::countParents).sum();
    }

    @Override
    protected Object b(final Node root) throws Exception {
        final var youParents = getParents(root, "YOU");
        final var sanParents = getParents(root, "SAN");

        while (true) {
            if (!youParents.removeLast().equals(sanParents.removeLast())) {
                return youParents.size() + sanParents.size();
            }
        }

    }

    private LinkedList<Node> getParents(final Node root, final String name) {
        return new LinkedList<>(root.flatten().filter(n -> n.getName().equals(name)).findAny().orElseThrow().getParents());
    }

    private int countParents(Node node) {
        int count = 0;
        while (node.getParent() != null) {
            count++;
            node = node.getParent();
        }
        return count;
    }
}

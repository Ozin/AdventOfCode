package AOC2018.day07;

import one.util.streamex.EntryStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeFactory {
    private static Map<String, Node> map = new HashMap<>();

    public static Node get(String id) {
        return map.computeIfAbsent(id, Node::new);
    }

    public static Collection<Node> getAllNodes() {
        return map.values();
    }

    public static void build(Map<String, List<String>> dependencies) {
        EntryStream.of(dependencies)
                .mapKeys(NodeFactory::get)
                .flatMapValues(List::stream)
                .mapValues(NodeFactory::get)
                .invert()
                .forKeyValue(Node::addPrecondition);
    }

    public static void reset() {
        map.clear();
    }
}

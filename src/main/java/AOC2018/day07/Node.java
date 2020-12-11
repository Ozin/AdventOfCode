package AOC2018.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {
    private final List<Node> preconditions;
    private final String id;
    private final int timeToFinish;

    public Node(String id) {
        this.preconditions = new ArrayList<>();
        this.id = id;
        this.timeToFinish = id.charAt(0) - 4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Node o) {
        return this.id.compareTo(o.id);
    }

    public void addPrecondition(Node precondition) {
        this.preconditions.add(precondition);
    }

    public List<Node> getPreconditions() {
        return preconditions;
    }

    @Override
    public String toString() {
        return id;
    }

    public int getTimeToFinish() {
        return timeToFinish;
    }
}

package adventOfCode2018.day07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class Worker {
    private final static Set<Node> inProgress = new HashSet<>();
    private final TreeSet<Node> possibilities;
    private final ArrayList<Node> finished;
    private final Collection<Node> graph;
    private int count = 0;

    private Node currentJob;

    public Worker(TreeSet<Node> possibilities, ArrayList<Node> finished, Collection<Node> graph) {
        this.possibilities = possibilities;
        this.finished = finished;
        this.graph = graph;
    }

    private void addNewPossibilities() {
        List<Node> next = graph.stream()
                .filter(not(finished::contains))
                .filter(not(inProgress::contains))
                .filter(n -> finished.containsAll(n.getPreconditions()))
                .collect(Collectors.toList());

        possibilities.addAll(next);
    }

    public boolean isFinished() {
        return currentJob == null && possibilities.isEmpty();
    }

    @Override
    public String toString() {
        return "Worker{" +
                "count=" + count +
                ", currentJob=" + currentJob +
                '}';
    }

    public Node getCurrentJob() {
        return currentJob;
    }

    public void doWork() {
        count++;

        if(currentJob == null) {
            pickNext();
        } else {
            checkFinished();
        }
    }

    public void pickNext() {
        addNewPossibilities();
        currentJob = possibilities.pollFirst();
        inProgress.add(currentJob);
        count = 0;
    }

    public boolean isAvailable() {
        return currentJob == null;
    }

    public void checkFinished() {
        if (currentJob != null && count >= currentJob.getTimeToFinish()) {
            finished.add(currentJob);
            inProgress.remove(currentJob);
            currentJob = null;
            count = 0;
            pickNext();
        }
    }
}

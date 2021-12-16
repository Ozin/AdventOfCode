package utils;

import java.util.function.Consumer;

public class LinkedList<T> {
    private final T value;
    private LinkedList<T> next = null;
    private LinkedList<T> prev = null;

    public LinkedList(T value) {
        this.value = value;
    }

    public LinkedList<T> add(T value) {
        var newElement = new LinkedList<>(value);

        newElement.next = next;
        newElement.prev = this;

        next = newElement;

        return newElement;
    }

    public T getValue() {
        return value;
    }

    public LinkedList<T> getNext() {
        return next;
    }

    public LinkedList<T> getPrev() {
        return prev;
    }

    public void forEach(Consumer<T> consumer) {
        var current = this;
        while(current != null) {
            consumer.accept(current.value);
            current = current.next;
        }
    }
}

package ru.bekh.training.datastructures;

import java.util.List;

public interface Collection<T> extends Iterable<T> {
    void add(T element);

    @SuppressWarnings("unchecked")
    default void add(T... elements) {
        for (T e : elements) {
            add(e);
        }
    }

    @SuppressWarnings("unchecked")
    default void add(List<T> elements) {
        elements.forEach(this::add);
    }

    boolean remove(T element);

    int removeAll(T element);

    int find(T element);

    boolean contains(T element);

    boolean isEmpty();
}
